package com.infernalsuite.ultimatecore.skills.utils;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.Item;
import com.infernalsuite.ultimatecore.skills.enums.Status;
import com.infernalsuite.ultimatecore.skills.objects.item.UltimateItem;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import com.infernalsuite.ultimatecore.skills.objects.Skill;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryUtils {

    public static ItemStack makeItem(ItemStack item, int amount, String name, List<String> lore) {
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        if(item.getItemMeta() == null)
            return null;
        if(lore != null)
            m.setLore(StringUtils.color(lore));
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack item = material.parseItem();
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.setLore(StringUtils.color(lore));
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(Item item) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, item.title, item.lore);
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                skull.setString("Name", "tr7zw");
                skull.setString("Id", UUID.randomUUID().toString());
                NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
                texture.setString("Value", item.headData);
                return nbtItem.getItem();
            } else if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();

            return makeItem(XMaterial.STONE, item.amount, item.title, item.lore);
        }
    }


    public static ItemStack makeItem(Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
            if(item.isGlowing){
                ItemMeta meta = itemstack.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemstack.setItemMeta(meta);
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                skull.setString("Name", "tr7zw");
                skull.setString("Id", UUID.randomUUID().toString());
                NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
                texture.setString("Value", item.headData);
                return nbtItem.getItem();
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(StringUtils.processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();

            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, UltimateItem ultimateItem) {
        try {
            ItemStack ultimateStack = ultimateItem.getItemStack();
            boolean meta = ultimateStack != null && ultimateStack.hasItemMeta();
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%item_title%")){
                        if(meta)
                            add(ultimateStack.getItemMeta().getDisplayName());
                        continue;
                    }
                    if(line.contains("%item_lore%")){
                        List<String> itemLore = meta && ultimateStack.getItemMeta().hasLore() ? ultimateStack.getItemMeta().getLore() : new ArrayList<>();
                        addAll(itemLore);
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack newItem = makeItem(ultimateStack != null ? ultimateStack : XMaterial.STONE.parseItem(), item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(lore, placeholders));
            return newItem;
        } catch (Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, String headOwner) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
            if(item.isGlowing){
                ItemMeta meta = itemstack.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemstack.setItemMeta(meta);
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                skull.setString("Name", "tr7zw");
                skull.setString("Id", UUID.randomUUID().toString());
                NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
                texture.setString("Value", item.headData);
                return nbtItem.getItem();
            }
            if (item.material == XMaterial.PLAYER_HEAD && headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(StringUtils.processMultiplePlaceholders(headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();

            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, SkillType skillType, int level) {
        try {
            Skill skill = HyperSkills.getInstance().getSkills().getAllSkills().get(skillType);
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%level_rewards%")){
                        List<String> rewardPlaceholders = HyperSkills.getInstance().getRewards().getRewardPlaceholders(skillType, level-1);
                        addAll(rewardPlaceholders == null ? new ArrayList<>() : rewardPlaceholders);
                        continue;
                    }
                    if(line.contains("%description%")){
                        addAll(skill.getDescription());
                        continue;
                    }
                    if(line.contains("%ranking_information%")){
                        if(level >= HyperSkills.getInstance().getConfiguration().levelToRank)
                            addAll(HyperSkills.getInstance().getConfiguration().rankInformation.get(Status.RANKED));
                        else
                            addAll(HyperSkills.getInstance().getConfiguration().rankInformation.get(Status.UNRANKED));
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack itemstack = makeItem(item.material == null ? skill.getXMaterial() : item.material, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.color(StringUtils.processMultiplePlaceholders(lore, placeholders)));

            if(item.isGlowing){
                ItemMeta meta = itemstack.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemstack.setItemMeta(meta);
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                skull.setString("Name", "tr7zw");
                skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
                NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
                texture.setString("Value", item.headData);
                return nbtItem.getItem();
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(StringUtils.processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, SkillType skill, int level, int amount) {
        try {
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%level_rewards%")){
                        addAll(HyperSkills.getInstance().getRewards().getRewardPlaceholders(skill, level-1));
                        continue;
                    }
                    if(line.contains("%ranking_information%")){
                        if(level >= HyperSkills.getInstance().getConfiguration().levelToRank)
                            addAll(HyperSkills.getInstance().getConfiguration().rankInformation.get(Status.RANKED));
                        else
                            addAll(HyperSkills.getInstance().getConfiguration().rankInformation.get(Status.UNRANKED));
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack itemstack = makeItem(item.material, amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.color(StringUtils.processMultiplePlaceholders(lore, placeholders)));

            if(item.isGlowing){
                ItemMeta meta = itemstack.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemstack.setItemMeta(meta);
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                skull.setString("Name", "tr7zw");
                skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
                NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
                texture.setString("Value", item.headData);
                return nbtItem.getItem();
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();

                m.setOwner(StringUtils.processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();

            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }
}
