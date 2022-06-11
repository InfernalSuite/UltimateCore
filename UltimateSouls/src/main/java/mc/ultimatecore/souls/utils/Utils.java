package mc.ultimatecore.souls.utils;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.Item;
import mc.ultimatecore.souls.objects.Soul;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.*;

public class Utils {
    
    private static final TreeMap<Integer, String> map = new TreeMap<>();
    
    static {
        map.put(1000000, "m");
        map.put(900000, "cm");
        map.put(500000, "d");
        map.put(100000, "c");
        map.put(90000, "xc");
        map.put(50000, "l");
        map.put(10000, "x");
        map.put(9000, "Mx");
        map.put(5000, "v");
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
        map.put(0, "0");
    }
    
    public static List<Placeholder> getSoulEditGUIPlaceHolders(Soul soul) {
        Location location = null;
        if (soul != null)
            location = soul.getLocation();
        return new ArrayList<>(Arrays.asList(
                new Placeholder("world", location != null ? location.getWorld().getName() : "*"),
                new Placeholder("x", location != null ? String.valueOf(location.getBlockX()) : "*"),
                new Placeholder("y", location != null ? String.valueOf(location.getBlockY()) : "*"),
                new Placeholder("z", location != null ? String.valueOf(location.getBlockZ()) : "*")));
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }
    
    public static void playSound(Player player, String sound) {
        if (sound == null || sound.equals("") || player == null) return;
        try {
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        } catch (Exception ignored) {
        }
    }
    
    public static void playParticle(Player player, Soul soul) {
        if (soul == null || soul.getParticle() == null || player == null) return;
        String name = soul.getParticle().particleName();
        try {
            player.spawnParticle(Particle.valueOf(name), soul.getLocation(), 10);
        } catch (Exception e) {
            player.playEffect(soul.getLocation(), Effect.valueOf(name), 10);
        }
    }
    
    public static void giveMoney(Player player, Soul soul) {
        if (soul == null || soul.getMoneyReward() <= 0 || player == null || HyperSouls.getInstance().getAddonsManager().getEconomyPlugin() == null) return;
        HyperSouls.getInstance().getAddonsManager().getEconomyPlugin().deposit(player, soul.getMoneyReward());
        player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("moneyRecieved").replace("%money%", String.valueOf(soul.getMoneyReward()))));
    }
    
    public static boolean hasName(ItemStack itemStack, String displayName) {
        try {
            if (itemStack == null || !itemStack.hasItemMeta() || displayName == null) return false;
            return itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(StringUtils.color(displayName));
        } catch (NullPointerException e) {
            return false;
        }
    }
    
    
    public static void openGUIAsync(Player player, Inventory inventory) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSouls.getInstance(), () -> player.openInventory(inventory), 3L);
    }
    
    
    public static Location loadLocation(YamlConfiguration yamlConfiguration, String s) {
        double x = yamlConfiguration.getDouble(s + ".X");
        double y = yamlConfiguration.getDouble(s + ".Y");
        double z = yamlConfiguration.getDouble(s + ".Z");
        String world = yamlConfiguration.getString(s + ".World");
        return new Location(Bukkit.getWorld(world), x, y, z);
        
    }
    
    public static void saveLoc(YamlConfiguration yamlFile, String s, Location location) {
        yamlFile.set(s + ".X", location.getBlockX());
        yamlFile.set(s + ".Y", location.getBlockY());
        yamlFile.set(s + ".Z", location.getBlockZ());
        yamlFile.set(s + ".World", location.getWorld().getName());
    }
    
    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material"))
            item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".headData")) item.headData = yamlConfig.getString(path + ".headData");
        if (yamlConfig.contains(path + ".headOwner")) item.headOwner = yamlConfig.getString(path + ".headOwner");
        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }
}
