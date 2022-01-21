package mc.ultimatecore.anvil.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import mc.ultimatecore.anvil.Item;
import mc.ultimatecore.anvil.enums.AnvilState;
import mc.ultimatecore.anvil.objects.EnchantItem;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class InventoryUtils {

    public static ItemStack makeItem(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack item = material.parseItem();
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.setLore(StringUtils.color(lore));
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(ItemStack itemStack, String name, List<String> lore) {
        if (itemStack == null)
            return null;
        ItemMeta m = itemStack.getItemMeta();
        m.setLore(StringUtils.color(lore));
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(m);
        return itemStack;
    }

    public static ItemStack makeDecorationItem(Item item) {
        ItemStack itemStack = makeItem(item.material, item.amount, item.title, item.lore);
        ItemMeta m = itemStack.getItemMeta();
        m.setLore(new ArrayList<>());
        m.setDisplayName(" ");
        itemStack.setItemMeta(m);
        return itemStack;
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
            return makeItem(XMaterial.STONE, item.amount, item.title, item.lore);
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, ItemStack itemStack) {
        try {
            ItemMeta meta = itemStack.getItemMeta();
            String displayName = meta != null && meta.hasDisplayName() ? meta.getDisplayName() : "";
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%item_name%")){
                        add(displayName);
                        continue;
                    }
                    if(line.contains("%item_description%")){
                        addAll(meta != null && meta.hasLore() ? meta.getLore() :  new ArrayList<>());
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack itemstack = makeItem(itemStack, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(lore, placeholders));
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
            return makeItem(XMaterial.STONE, item.amount, item.title, item.lore);
        }
    }

    public static ItemStack makeItem(Item item, ItemStack itemStack) {
        try {
            ItemMeta meta = itemStack.getItemMeta();
            String displayName = meta != null && meta.hasDisplayName() ? meta.getDisplayName() : "";
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%item_name%")){
                        add(displayName);
                        continue;
                    }
                    if(line.contains("%item_description%")){
                        addAll(meta != null && meta.hasLore() ? meta.getLore() :  new ArrayList<>());
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack itemstack = makeItem(itemStack, StringUtils.processMultiplePlaceholders(item.title, Collections.singletonList(new Placeholder("item_name", displayName))), lore);

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
            return makeItem(XMaterial.STONE, item.amount, item.title, item.lore);
        }
    }

    public static ItemStack makeItem(Item item, AnvilState runeState) {
        try {
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%error_information%")){
                        addAll(runeState.description);
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack itemstack = makeItem(item.material, item.amount, item.title, lore);
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
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, EnchantItem ultimateItem) {
        try {
            ItemStack ultimateStack = ultimateItem.getItemStack();
            boolean meta = ultimateStack != null && ultimateStack.hasItemMeta();
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%item_title%")){
                        if(meta && ultimateStack.getItemMeta().hasDisplayName())
                            add(ultimateStack.getItemMeta().getDisplayName());
                        continue;
                    }
                    if(line.contains("%item_lore%")){
                        List<String> itemLore = meta && ultimateStack.getItemMeta().hasLore() ? ultimateStack.getItemMeta().getLore() : new ArrayList<>();
                        if(itemLore.size() > 0) addAll(itemLore);
                        continue;
                    }
                    add(line);
                }
            }};
            return makeItem(ultimateStack != null ? ultimateStack : XMaterial.STONE.parseItem(), StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(lore, placeholders));
        } catch (Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

}
