package mc.ultimatecore.enchantment.utils;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.Item;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.enchantment.enums.EnchantState;
import mc.ultimatecore.enchantment.managers.GUIManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
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
        map.put(0, "");
    }

    public static void playSound(Player player, String sound){
        try{
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static EnchantState manageEnchant(GUIManager manager){
        return manager.getToEnchantItem().getAmount() > 1 ? EnchantState.REACH_STACK_SIZE :
                EnchantmentsPlugin.getInstance().getHyperEnchantments().getAvailableEnchantments(manager.getToEnchantItem()).size() > 0
                        ? EnchantState.READY_TO_ENCHANT : EnchantState.NO_ENCHANTS_AVAILABLE;
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path){
        Item item = new Item();
        if(yamlConfig.contains(path+".material")) item.material = XMaterial.valueOf(yamlConfig.getString(path+".material"));
        if(yamlConfig.contains(path+".title")) item.title = yamlConfig.getString(path+".title");
        if(yamlConfig.contains(path+".lore")) item.lore = yamlConfig.getStringList(path+".lore");
        if(yamlConfig.contains(path+".slot")) item.slot = yamlConfig.getInt(path+".slot");
        if(yamlConfig.contains(path+".command")) item.command = yamlConfig.getString(path+".command");
        if(yamlConfig.contains(path+".headData")) item.headData = yamlConfig.getString(path+".headData");
        if(yamlConfig.contains(path+".amount")) item.amount = yamlConfig.getInt(path+".amount");
        return item;
    }


    public static String getEnchantmentKey(Enchantment enchantment) {
        return XMaterial.getVersion() > 13 ? enchantment.getKey().getKey() : enchantment.getName();
    }


    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String uncolor(String string) {
        return ChatColor.stripColor(string);
    }

    public static List<String> translateDescription(String description){
        description = description.replaceAll("\\r|\\n", "%separator%");
        return Arrays.asList(description.split("%separator%"));

    }

    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }

    public static List<Placeholder> getEnchantPlaceholders(HyperEnchant hyperEnchant) {
        return new ArrayList<>(Arrays.asList(
                new Placeholder("enchant_name", hyperEnchant.getDisplayName()),
                new Placeholder("bookshelf_required", String.valueOf(hyperEnchant.getRequiredBookShelf()))

        ));
    }

    public static HashMap<Integer, Double> deserializeRequiredMoney(List<String> map){
        HashMap<Integer, Double> moneyMap = new HashMap<>();
        try {
            for(String money : map){
                String[] moneyArray = money.split(":");
                moneyMap.put(Integer.valueOf(moneyArray[0]), Double.valueOf(moneyArray[1]));
            }
        }catch (Exception e){
            return moneyMap;
        }
        return moneyMap;
    }


    public static HashMap<Integer, Integer> deserializeRequiredLevels(List<String> map){
        HashMap<Integer, Integer> moneyMap = new HashMap<>();
        try {
            for(String money : map){
                String[] moneyArray = money.split(":");
                moneyMap.put(Integer.valueOf(moneyArray[0]), Integer.valueOf(moneyArray[1]));
            }
        }catch (Exception e){
            return moneyMap;
        }
        return moneyMap;
    }



    public static List<Placeholder> getMoneyPlaceholders(UUID player, HyperEnchant hyperEnchant, int level, ItemStack itemStack) {
        Economy eco = EnchantmentsPlugin.getInstance().getEconomy();
        double currentMoney = eco.getBalance(Bukkit.getOfflinePlayer(player));
        double required = hyperEnchant.getRequiredMoney(level);
        int itemLevel = hyperEnchant.getItemLevel(itemStack);
        String requiredMessage = !hyperEnchant.itemIsEnchanted(itemStack, level) ? itemLevel > 0 ? currentMoney < required ?  EnchantmentsPlugin.getInstance().getMessages().getMessage("notEnoughMoney") : EnchantmentsPlugin.getInstance().getMessages().getMessage("clickToEnchantState") : currentMoney < required ? EnchantmentsPlugin.getInstance().getMessages().getMessage("notEnoughMoney") : EnchantmentsPlugin.getInstance().getMessages().getMessage("clickToEnchantState") : EnchantmentsPlugin.getInstance().getMessages().getMessage("alreadyState");

        return new ArrayList<>(Arrays.asList(new Placeholder("enchant_name", hyperEnchant.getDisplayName())
                ,new Placeholder("enchant_level", toRoman(level))
                ,new Placeholder("state", requiredMessage)
                ,new Placeholder("enchant_cost", Utils.color(requiredMessage.replace("%cost%", String.valueOf(required)))
                )));
    }

    public static List<Placeholder> getEnchantPlaceholders(UUID uuid, HyperEnchant hyperEnchant, int level, ItemStack itemStack) {
        Player player = Bukkit.getPlayer(uuid);
        int playerLevel = player != null ? player.getLevel() : 0;
        int required = hyperEnchant.getRequiredLevel(level);
        int itemLevel = hyperEnchant.getItemLevel(itemStack);
        int requiredLevel = !hyperEnchant.itemIsEnchanted(itemStack, level) ? itemLevel > 0 ?  getNewRequiredLevel(required) : required : required;
        String requiredMessage = !hyperEnchant.itemIsEnchanted(itemStack, level) ? itemLevel > 0 ? playerLevel < requiredLevel ?  EnchantmentsPlugin.getInstance().getMessages().getMessage("notEnoughState") : EnchantmentsPlugin.getInstance().getMessages().getMessage("clickToEnchantState") : playerLevel < requiredLevel ? EnchantmentsPlugin.getInstance().getMessages().getMessage("notEnoughState") : EnchantmentsPlugin.getInstance().getMessages().getMessage("clickToEnchantState") : EnchantmentsPlugin.getInstance().getMessages().getMessage("alreadyState");
        return new ArrayList<>(Arrays.asList(
                new Placeholder("enchant_name", hyperEnchant.getDisplayName())
                ,new Placeholder("enchant_level", toRoman(level))
                ,new Placeholder("state", requiredMessage)
                ,new Placeholder("enchant_cost", requiredLevel == required ? Utils.color(EnchantmentsPlugin.getInstance().getMessages().getMessage("normalCost").replace("%cost%", String.valueOf(required))) : Utils.color(EnchantmentsPlugin.getInstance().getMessages().getMessage("replacedCost").replace("%new_cost%", String.valueOf(requiredLevel)).replace("%cost%", String.valueOf(required))))

        ));
    }

    public static int getNewRequiredLevel(int required){
        return (93 * required) / 100;
    }


    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders) {
        List<String> newlist = new ArrayList<>();
        for (String string : lines)
            newlist.add(processMultiplePlaceholders(string, placeholders));
        return newlist;
    }

    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders) {
        for (Placeholder placeholder : placeholders)
            line = placeholder.process(line);
        return color(line);
    }


    public static int getBookShelfPower(Block block) {
        int quantity = 0;
        if(block == null) return quantity;
        for(Block nearBlock : Utils.getNearbyBlocks(block.getLocation(), 5)){
            if(nearBlock.getType().equals(XMaterial.BOOKSHELF.parseMaterial()))
                quantity++;
        }
        return quantity;
    }

    public static int getBookShelfPower(Player player) {
        int quantity = 0;
        if(player == null) return quantity;
        for(Block nearBlock : Utils.getNearbyBlocks(player.getLocation(), 5)){
            if(nearBlock.getType().equals(XMaterial.BOOKSHELF.parseMaterial()))
                quantity++;
        }
        return quantity;
    }


    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static String toRoman(int number) {
        if (number >= 0) {
            int l = map.floorKey(number);
            if (number == l)
                return map.get(number);
            return map.get(l) + toRoman(number - l);
        }
        return String.valueOf(number);
    }

}
