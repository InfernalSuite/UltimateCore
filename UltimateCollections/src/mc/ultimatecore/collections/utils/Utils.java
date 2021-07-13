package mc.ultimatecore.collections.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.Item;
import mc.ultimatecore.collections.objects.Category;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.objects.PlayerCollections;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

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
    
    
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    
    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }
    
    public static boolean hasMetadata(org.bukkit.entity.Item item) {
        return item != null && item.hasMetadata("placeBlock");
    }
    
    public static boolean hasNBTdata(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem != null && nbtItem.hasKey("collected"))
            return true;
        return false;
    }
    
    public static String getKey(String string) {
        return string.toUpperCase().replace(" ", "_");
    }
    
    public static List<Placeholder> getCategoriesPlaceholder(UUID uuid, Category category) {
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(uuid);
        int xp = playerCollection.getUnlocked(category);
        int maxXP = category != null ? HyperCollections.getInstance().getCollections().getCollectionQuantity(category).size() : 100;
        int currentPercentage = getPlayerPercentage(xp, maxXP);
        return new ArrayList<>(Arrays.asList(new Placeholder("progress_percentage", String.valueOf(currentPercentage)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("progress_bar", getProgressBar(currentPercentage)),
                new Placeholder("max_xp", String.valueOf(maxXP))));
    }
    
    public static List<Placeholder> getTopPlaceholders(UUID uuid, Category category) {
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(uuid);
        int xp = playerCollection.getUnlocked(category);
        int maxXP = category != null ? HyperCollections.getInstance().getCollections().getCollectionQuantity(category).size() : 100;
        int currentPercentage = getPlayerPercentage(xp, maxXP);
        int playersQuantity = Math.max(HyperCollections.getInstance().getCollectionsManager().playersQuantity, 1);
        return new ArrayList<Placeholder>() {{
            add(new Placeholder("progress_percentage", String.valueOf(currentPercentage)));
            add(new Placeholder("current_xp", String.valueOf(xp)));
            add(new Placeholder("progress_bar", getProgressBar(currentPercentage)));
            add(new Placeholder("max_xp", String.valueOf(maxXP)));
            for (String collection : HyperCollections.getInstance().getCollections().getCollectionQuantity(category)) {
                PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(uuid);
                int rankPosition = playerCollection.getRankPosition(collection);
                String percentage = String.valueOf(round((rankPosition / playersQuantity) * 100));
                add(new Placeholder(collection + "_RANK_PLACEHOLDER", rankPosition == -1 ?
                        HyperCollections.getInstance().getConfiguration().getNoRankPlaceholder() :
                        HyperCollections.getInstance().getConfiguration().getRankPlaceholder().replace("%top_percentage%", percentage).replace("%xp%", String.valueOf(playerCollection.getXP(collection)))
                ));
            }
        }};
    }
    
    public static Double round(double value) {
        return Math.round(value * 10) / 10D;
    }
    
    public static List<Placeholder> getGlobalPlaceHolders(UUID uuid) {
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(uuid);
        int xp = playerCollection.getAllUnlocked();
        int maxXP = HyperCollections.getInstance().getCollections().getCollectionQuantity();
        int currentPercentage = getPlayerPercentage(xp, maxXP);
        return Arrays.asList(new Placeholder("progress_percentage", String.valueOf(currentPercentage)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("progress_bar", getProgressBar(currentPercentage)),
                new Placeholder("max_xp", String.valueOf(maxXP)));
    }
    
    public static List<Placeholder> getCollectionPlaceholders(UUID uuid, Collection collection) {
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(uuid);
        int level = playerCollection.getLevel(collection.getKey()) + 1;
        level = Math.min(level, 9);
        int xp = playerCollection.getXP(collection.getKey());
        Double maxXP = collection.getRequirement(level);
        int currentPercentage = maxXP == null ? 0 : getPlayerPercentage(xp, maxXP.intValue());
        return Arrays.asList(new Placeholder("progress_percentage", String.valueOf(Math.min(currentPercentage, 100))),
                new Placeholder("next_level", Utils.toRoman(level)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("level", Utils.toRoman(level - 1)),
                new Placeholder("progress_bar", getProgressBar(currentPercentage)),
                new Placeholder("collection_name", collection.getName()),
                new Placeholder("max_xp", String.valueOf(maxXP)));
    }
    
    public static List<Placeholder> getCollectionPlaceholders(UUID uuid, Collection collection, int level) {
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(uuid);
        int xp = playerCollection.getXP(collection.getKey());
        Double maxXP = collection.getRequirement(level);
        int currentPercentage = maxXP == null ? 0 : getPlayerPercentage(xp, maxXP.intValue());
        return Arrays.asList(new Placeholder("progress_percentage", String.valueOf(Math.min(currentPercentage, 100))),
                new Placeholder("next_level", Utils.toRoman(level)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("current_level", Utils.toRoman(level)),
                new Placeholder("progress_bar", getProgressBar(currentPercentage)),
                new Placeholder("collection_name", collection.getName()),
                new Placeholder("max_xp", String.valueOf(maxXP)));
    }
    
    public static int getPlayerPercentage(int xp, int maxXP) {
        if (xp > 0)
            return (xp * 100) / maxXP;
        return 0;
    }
    
    public static int getPercentageQuantity(int percentage) {
        if (percentage > 0)
            return (percentage * 20) / 100;
        return 0;
    }
    
    
    public static final String toRoman(int number) {
        if (number >= 0) {
            int l = map.floorKey(number);
            if (number == l)
                return map.get(number);
            return map.get(l) + toRoman(number - l);
        }
        return String.valueOf(number);
    }
    
    public static String getProgressBar(int percentage) {
        StringBuilder bar = new StringBuilder();
        int current = Math.min(getPercentageQuantity(percentage), 20);
        for (int i = 0; i < current; i++) {
            bar.append("&a-");
        }
        for (int i = 0; i < 20 - current; i++) {
            bar.append("&f-");
        }
        return bar.toString();
    }
    
    
    public static HashMap<Integer, List<String>> getCollectionObject(YamlConfiguration yamlConfiguration
            , String collection, String path) {
        HashMap<Integer, List<String>> map = new HashMap<>();
        ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("collections." + collection + "." + path);
        if (configurationSection == null) return map;
        try {
            for (String key : configurationSection.getKeys(false)) {
                Integer level = Integer.parseInt(key);
                List<String> objects = yamlConfiguration.getStringList("collections." + collection + "." + path + "." + key);
                map.put(level, objects);
            }
        } catch (Exception e) {
            return map;
        }
        return map;
    }
    
    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material")) item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".category")) {
            Optional<Category> category = HyperCollections.getInstance().getCollections().getCategory(yamlConfig.getString(path + ".category"));
            category.ifPresent(value -> item.category = value);
        }
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }
    
    public static HashMap<Integer, Double> getCollectionRequirements(YamlConfiguration yamlConfiguration
            , String collection) {
        HashMap<Integer, Double> map = new HashMap<>();
        ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("collections." + collection + ".requirements");
        if (configurationSection == null) return map;
        try {
            for (String key : configurationSection.getKeys(false)) {
                Integer level = Integer.parseInt(key);
                Double value = yamlConfiguration.getDouble("collections." + collection + ".requirements." + key);
                map.put(level, value);
            }
        } catch (Exception e) {
            return map;
        }
        return map;
    }
    
}
