package mc.ultimatecore.runes.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.runes.HyperRunes;
import mc.ultimatecore.runes.Item;
import mc.ultimatecore.runes.enums.RuneState;
import mc.ultimatecore.runes.enums.RuneType;
import mc.ultimatecore.runes.objects.Rune;
import mc.ultimatecore.runes.runetable.RuneTableManager;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
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
    
    
    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material")) item.material = com.cryptomorin.xseries.XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        
        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }
    
    
    public static List<String> color(List<String> strings) {
        return strings.stream().map(StringUtils::color).collect(Collectors.toList());
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
    
    public static int randomNumber(int max) {
        return new Random().nextInt(max);
    }
    
    
    public static RuneState manageRunes(RuneTableManager manager) {
        Player player = Bukkit.getPlayer(manager.getUuid());
        
        int runeCraftingLevel = HyperRunes.getInstance().isHyperSkills() ? HyperSkills.getInstance().getApi().getLevel(player.getUniqueId(), SkillType.Runecrafting) : player.getLevel();
        
        NBTItem firstItem = new NBTItem(manager.getFirstItem());
        NBTItem secondItem = new NBTItem(manager.getSecondItem());
        
        if (firstItem.hasKey("runeLevel") && secondItem.hasKey("runeLevel")) {
            int firstRuneLevel = firstItem.getInteger("runeLevel");
            int secondRuneLevel = secondItem.getInteger("runeLevel");
            if (firstRuneLevel != secondRuneLevel) return RuneState.INCOMPATIBLE_RUNE_LEVEL;
            String firstRuneName = firstItem.getString("runeName");
            String secondRuneName = secondItem.getString("runeName");
            if (!firstRuneName.equalsIgnoreCase(secondRuneName)) return RuneState.INCOMPATIBLE_RUNE_TYPE;
            Rune rune = HyperRunes.getInstance().getRunes().getRuneByName(secondItem.getString("runeName"));
            if (runeCraftingLevel < rune.getRequiredLevel(firstRuneLevel)) return RuneState.NO_REQUIRED_LEVEL;
            return RuneState.NO_ERROR_RUNES;
        } else if (!firstItem.hasKey("runeLevel") && secondItem.hasKey("runeLevel")) {
            Rune rune = HyperRunes.getInstance().getRunes().getRuneByName(secondItem.getString("runeName"));
            int runeLevel = secondItem.getInteger("runeLevel");
            if (!itemCanBeFused(firstItem, rune.getRuneType())) return RuneState.INCOMPATIBLE_ITEMS;
            if (runeCraftingLevel < rune.getRequiredLevel(runeLevel)) return RuneState.NO_REQUIRED_LEVEL;
            return RuneState.NO_ERROR_ITEMS;
        } else {
            return RuneState.INCOMPATIBLE_ITEMS;
        }
    }
    
    public static ItemStack getRunedItem(ItemStack firstItem, ItemStack secondItem) {
        NBTItem firstNBT = new NBTItem(firstItem);
        NBTItem secondNBT = new NBTItem(secondItem);
        Rune rune = HyperRunes.getInstance().getRunes().getRuneByName(secondNBT.getString("runeName"));
        int currentLevel = firstNBT.hasKey(rune.getRuneName()) ? firstNBT.getInteger(rune.getRuneName()) : 0;
        List<String> lore = firstItem.hasItemMeta() && firstItem.getItemMeta().getLore() != null ? firstItem.getItemMeta().getLore() : new ArrayList<>();
        lore.remove(StringUtils.color(rune.getDisplayName().replace("%rune_level%", toRoman(currentLevel))));
        int newLevel = secondNBT.getInteger("runeLevel");
        firstNBT.setInteger(rune.getRuneName(), newLevel);
        //new lore
        lore.add("");
        lore.add(StringUtils.color(rune.getDisplayName().replace("%rune_level%", toRoman(newLevel))));
        ItemStack item = firstNBT.getItem();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    
    
    public static ItemStack getNewRune(ItemStack firstItem) {
        NBTItem firstNBT = new NBTItem(firstItem);
        int firstLevel = firstNBT.getInteger("runeLevel");
        return HyperRunes.getInstance().getRunes().getRune(firstNBT.getString("runeName"), firstLevel * 2);
    }
    
    public static boolean itemCanBeFused(NBTItem firstItem, RuneType runeType) {
        String mat = firstItem.getItem().getType().toString();
        Bukkit.getConsoleSender().sendMessage(mat);
        if (runeType == RuneType.WEAPON && mat.contains("SWORD")) {
            return true;
        } else return runeType == RuneType.BOW && mat.contains("BOW");
    }
    
    public static int getSuccesChance(ItemStack itemStack) {
        NBTItem secondNBT = new NBTItem(itemStack);
        if (!secondNBT.hasKey("runeName"))
            return 0;
        return HyperRunes.getInstance().getRunes().getRuneByName(secondNBT.getString("runeName")).getSuccessChance(secondNBT.getInteger("runeLevel"));
    }
    
    
}
