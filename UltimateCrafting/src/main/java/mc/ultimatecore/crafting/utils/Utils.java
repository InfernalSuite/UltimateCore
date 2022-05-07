package mc.ultimatecore.crafting.utils;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.Item;
import mc.ultimatecore.crafting.objects.Category;
import mc.ultimatecore.crafting.objects.StatsMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    
    public static void openGUISync(Player player, Inventory inventory, HyperCrafting plugin) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.openInventory(inventory), 3L);
    }
    
    public static boolean itemIsNull(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR;
    }
    
    public static boolean matchType(ItemStack itemStack, XMaterial material) {
        return !itemIsNull(itemStack) && itemStack.getType() == material.parseMaterial();
    }
    
    public static int getItemQuantity(Collection<ItemStack> itemStacks, ItemStack itemStackParam) {
        int quantity = 0;
        if (itemStacks == null)
            return quantity;
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                if (itemStack.isSimilar(itemStackParam))
                    quantity += itemStack.getAmount();
            }
        }
        return quantity;
    }
    
    public static List<Placeholder> getRecipeBookPlaceholders(Category category, StatsMap statsMap) {
        int xp = statsMap.getUnlockedPlayer().getOrDefault(category.getKey(), 0);
        int maxXP = statsMap.getTotalAmount().getOrDefault(category.getKey(), 0);
        double currentPercentage = getCurrentPercentage(xp, maxXP);
        
        return Arrays.asList(
                new Placeholder("category", category.getDisplayName()),
                new Placeholder("percentage", String.valueOf(round(currentPercentage))),
                new Placeholder("progress_bar", getProgressBar((int) currentPercentage)),
                new Placeholder("current", String.valueOf(xp)),
                new Placeholder("total", String.valueOf(maxXP)));
    }
    
    public static List<Placeholder> getRecipeBookPlaceholders(StatsMap statsMap) {
        int xp = statsMap.getUnlockedPlayer().values().stream().mapToInt(amount -> amount).sum();
        int maxXP = statsMap.getTotalAmount().values().stream().mapToInt(amount -> amount).sum();
        double currentPercentage = getCurrentPercentage(xp, maxXP);
        return Arrays.asList(
                new Placeholder("percentage", String.valueOf(round(currentPercentage))),
                new Placeholder("progress_bar", getProgressBar((int) currentPercentage)),
                new Placeholder("current", String.valueOf(xp)),
                new Placeholder("total", String.valueOf(maxXP)));
    }
    
    public static Double round(double value) {
        return Math.round(value * 10) / 10D;
    }
    
    public static int getCurrentPercentage(int xp, int maxXP) {
        if (xp > 0)
            return (xp * 100) / maxXP;
        return 0;
    }
    
    public static int getPercentageQuantity(int percentage) {
        if (percentage > 0)
            return (percentage * 20) / 100;
        return 0;
    }
    
    public static String getProgressBar(int percentage) {
        StringBuilder bar = new StringBuilder();
        int current = getPercentageQuantity(percentage);
        for (int i = 0; i < current; i++) {
            bar.append("&a-");
        }
        for (int i = 0; i < 20 - current; i++) {
            bar.append("&f-");
        }
        return bar.toString();
    }
    
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    
    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }
    
    
    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders) {
        List<String> newlist = new ArrayList<>();
        for (String string : lines) {
            if (string == null) continue;
            newlist.add(processMultiplePlaceholders(string, placeholders));
        }
        return newlist;
    }
    
    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders) {
        for (Placeholder placeholder : placeholders) {
            if (placeholder == null) continue;
            line = placeholder.process(line);
        }
        return color(line);
    }
    
    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material")) item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".enabled")) item.enabled = yamlConfig.getBoolean(path + ".enabled");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }
    
}
