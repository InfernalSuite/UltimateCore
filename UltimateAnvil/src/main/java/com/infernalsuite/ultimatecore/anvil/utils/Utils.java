package com.infernalsuite.ultimatecore.anvil.utils;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.anvil.HyperAnvil;
import com.infernalsuite.ultimatecore.anvil.Item;
import com.infernalsuite.ultimatecore.anvil.enums.AnvilState;
import com.infernalsuite.ultimatecore.anvil.managers.AnvilGUIManager;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Utils {

    public static void playSound(Player player, String sound){
        try{
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material"))
            item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".enabled")) item.enabled = yamlConfig.getBoolean(path + ".enabled");

        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }


    public static AnvilState manageAnvil(AnvilGUIManager manager){
        if(manager.getSecondItem().getType().equals(XMaterial.NAME_TAG.parseMaterial())){
            return AnvilState.NO_ERROR_TAG;
        } else {
            Map<HyperEnchant, Integer> enchants = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getSecondItem());
            Optional<HyperEnchant> optional = enchants.keySet().stream()
                    .filter(hyperEnchant -> hyperEnchant.itemCanBeEnchanted(manager.getFirstItem()))
                    .findFirst();
            if(!optional.isPresent() && !areCompatibleBooks(manager)) return AnvilState.INCOMPATIBLE_ENCHANTMENTS;
            return AnvilState.NO_ERROR_ITEMS;
        }
    }


    public static int getCost(AnvilGUIManager manager){
        int finalCost = 0;
        if(manager.getAnvilState() == AnvilState.NO_ERROR_TAG) return HyperAnvil.getInstance().getConfiguration().nameTagFuseCost;
        if(manager.getAnvilState().equals(AnvilState.NO_ERROR_ITEMS)){
            Map<HyperEnchant, Integer> enchantmentsFirst = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getFirstItem());
            Map<HyperEnchant, Integer> enchantmentsSecond = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getSecondItem());
            Set<HyperEnchant> enchantsToAdd = new HashSet<HyperEnchant>(){{
                addAll(enchantmentsFirst.keySet());
                addAll(enchantmentsSecond.keySet());
            }};
            ItemStack itemStack = manager.getFirstItem();

            for(HyperEnchant hyperEnchant : enchantsToAdd){
                int firstLevel = enchantmentsFirst.getOrDefault(hyperEnchant, 0);
                int secondLevel = enchantmentsSecond.getOrDefault(hyperEnchant, 0);
                int levelToAdd = firstLevel+1;
                if(firstLevel != secondLevel){
                    if(firstLevel == 0 || secondLevel == 0)
                        levelToAdd = Math.max(firstLevel, secondLevel);
                }
                if(levelToAdd < 1) continue;
                if(levelToAdd > hyperEnchant.getMaxLevel()) continue;
                if(!hyperEnchant.itemCanBeEnchanted(itemStack) && !itemStack.getType().toString().contains("BOOK")) continue;
                finalCost+=hyperEnchant.getRequiredLevel(levelToAdd);
            }
        }else if(manager.getAnvilState().equals(AnvilState.NO_ERROR_BOOK)){
            Map<HyperEnchant, Integer> enchantmentsSecond = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getSecondItem());
            for(HyperEnchant hyperEnchant : enchantmentsSecond.keySet())
                finalCost+=hyperEnchant.getRequiredLevel(enchantmentsSecond.get(hyperEnchant));
        }
        return finalCost;
    }

    public static int getDurableItem(AnvilGUIManager manager){
        ItemStack itemStack = manager.getFirstItem();
        ItemStack secondStack = manager.getSecondItem();
        if(itemStack.getType() == secondStack.getType()){
            if(itemStack.getType().toString().contains("BOOK")) return -1;
            int first = itemStack.getDurability();
            int sec = secondStack.getDurability();
            return Math.min(first + sec, itemStack.getType().getMaxDurability());
        }else{
            return -1;
        }

    }

    public static ItemStack getModifiedNameTag(){
        NBTItem nbtItem = new NBTItem(XMaterial.NAME_TAG.parseItem());
        nbtItem.setBoolean("hyperAnvilRename", true);
        return nbtItem.getItem();
    }

    public static boolean isModifiedNameTag(ItemStack itemStack){
        return new NBTItem(itemStack).hasKey("hyperAnvilRename");
    }

    public static ItemStack getColoredNameTag(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(StringUtils.color(meta.hasDisplayName() ? meta.getDisplayName() : ""));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getFusedItem(AnvilGUIManager manager){
        ItemStack itemStack = manager.getFirstItem().clone();
        if(manager.getAnvilState() == AnvilState.NO_ERROR_TAG) return getRenamedItemStack(manager.getFirstItem(), manager.getSecondItem());
        if(manager.getAnvilState().equals(AnvilState.NO_ERROR_ITEMS)){
            Map<HyperEnchant, Integer> enchantmentsFirst = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getFirstItem());
            Map<HyperEnchant, Integer> enchantmentsSecond = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getSecondItem());
            Set<HyperEnchant> enchantsToAdd = new HashSet<HyperEnchant>(){{
                addAll(enchantmentsFirst.keySet());
                addAll(enchantmentsSecond.keySet());
            }};
            for(HyperEnchant hyperEnchant : enchantsToAdd){
                int firstLevel = enchantmentsFirst.getOrDefault(hyperEnchant, 0);
                int secondLevel = enchantmentsSecond.getOrDefault(hyperEnchant, 0);
                int levelToAdd = firstLevel+1;
                if(firstLevel != secondLevel){
                    if(firstLevel == 0 || secondLevel == 0)
                        levelToAdd = Math.max(firstLevel, secondLevel);
                }
                if(levelToAdd < 1) continue;
                if(levelToAdd > hyperEnchant.getMaxLevel()) continue;
                if(!hyperEnchant.itemCanBeEnchanted(itemStack) && !itemStack.getType().toString().contains("BOOK")) continue;
                itemStack = EnchantmentsPlugin.getInstance().getApi().enchantItem(itemStack, hyperEnchant, levelToAdd, false);
                int durability = getDurableItem(manager);
                if(durability != -1)
                    itemStack.setDurability((short) durability);
            }
        }else if(manager.getAnvilState().equals(AnvilState.NO_ERROR_BOOK)) {
            Map<HyperEnchant, Integer> enchantmentsSecond = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(manager.getSecondItem());
            for(HyperEnchant hyperEnchant : enchantmentsSecond.keySet()){
                int level = enchantmentsSecond.get(hyperEnchant);
                itemStack = EnchantmentsPlugin.getInstance().getApi().enchantItem(itemStack, hyperEnchant, level, false);
            }
        }
        return itemStack;
    }

    public static ItemStack getRenamedItemStack(ItemStack itemStack, ItemStack nameTag){
        ItemMeta meta = nameTag.getItemMeta();
        String newName = meta.hasDisplayName() ? meta.getDisplayName() : "";
        ItemStack newItem = itemStack.clone();
        ItemMeta newMeta =  newItem.getItemMeta();
        if(!newName.equals("")) newMeta.setDisplayName(newName);
        newItem.setItemMeta(newMeta);
        return newItem;
    }

    public static boolean areCompatibleBooks(AnvilGUIManager manager){
        ItemStack firstStack = manager.getFirstItem();
        ItemStack secondStack = manager.getSecondItem();
        if(!firstStack.getType().toString().contains("BOOK") || !secondStack.getType().toString().contains("BOOK")) return false;
        Map<HyperEnchant, Integer> enchants = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(firstStack);
        Map<HyperEnchant, Integer> secondEnchants = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(secondStack);
        return enchants.size() >= 1 && secondEnchants.size() >= 1;
    }

}
