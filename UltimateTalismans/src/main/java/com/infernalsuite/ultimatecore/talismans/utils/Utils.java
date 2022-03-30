package com.infernalsuite.ultimatecore.talismans.utils;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.Item;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.talismans.objects.ImmunityType;
import com.infernalsuite.ultimatecore.talismans.objects.Talisman;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class Utils {
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



    public static Map<ImmunityType, Double> getTalismanImmunities(FileConfiguration fileConfiguration, String path){
        Map<ImmunityType, Double> map = new HashMap<>();
        if(fileConfiguration == null) return map;
        for(String key : fileConfiguration.getStringList(path)){
            String[] split = key.split(":");
            if(split.length != 2) continue;
            ImmunityType immunityType = ImmunityType.valueOf(split[0]);
            double value = Double.parseDouble(split[1]);
            map.put(immunityType, value);
        }
        return map;
    }

    public static Set<String> getInventoryTalismans(Player player){
        Set<String> talismans = new HashSet<>();
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack == null) continue;
            NBTItem nbtItem = new NBTItem(itemStack);
            if(!nbtItem.hasKey("uc_talisman")) continue;
            if(!nbtItem.hasKey("uc_executable")) continue;
            String name = nbtItem.getString("uc_talisman_name");
            Talisman talisman = HyperTalismans.getInstance().getTalismans().getTalisman(name);
            if(talisman == null) continue;
            talismans.add(name);
        }
        for(String id : HyperTalismans.getInstance().getUserManager().getBagTalismans(player.getUniqueId()).getTalismans()){
            Talisman talisman = HyperTalismans.getInstance().getTalismans().getTalisman(id);
            if(talisman == null) continue;
            talismans.add(id);
        }
        return talismans;
    }



    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path){
        Item item = new Item();
        if(yamlConfig.contains(path+".material")) item.material = XMaterial.valueOf(yamlConfig.getString(path+".material"));
        if(yamlConfig.contains(path+".title")) item.title = yamlConfig.getString(path+".title");
        if(yamlConfig.contains(path+".lore")) item.lore = yamlConfig.getStringList(path+".lore");
        if(yamlConfig.contains(path+".slot")) item.slot = yamlConfig.getInt(path+".slot");
        if(yamlConfig.contains(path+".headData")) item.headData = yamlConfig.getString(path+".headData");
        if(yamlConfig.contains(path+".headOwner")) item.headOwner = yamlConfig.getString(path+".headOwner");
        if(yamlConfig.contains(path+".command")) item.command = yamlConfig.getString(path+".command");
        if(yamlConfig.contains(path+".amount")) item.amount = yamlConfig.getInt(path+".amount");
        return item;
    }


    public static String[] getSplitList(String str){
        return str.replace("]", "").replace("[", "").split(", ");
    }



    public static boolean isTalisman(ItemStack itemStack){
        if(itemStack == null || itemStack.getType() == Material.AIR) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("uc_talisman");
    }
}
