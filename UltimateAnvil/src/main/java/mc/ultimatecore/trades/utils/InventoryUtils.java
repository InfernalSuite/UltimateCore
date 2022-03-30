package mc.ultimatecore.trades.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import mc.ultimatecore.trades.Item;
import mc.ultimatecore.trades.objects.TradeObject;
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

    private static final boolean SUPPORT;

    static {
        boolean support = true;
        try {
            new NBTItem(XMaterial.DIRT.parseItem()).setUUID("Id", UUID.randomUUID());
        } catch (Exception e) {
            support = false;
        }
        SUPPORT = support;
    }

    public static ItemStack makeItem(ItemStack item, int amount, String name, List<String> lore) {
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        if(item.getItemMeta() == null){
            return null;
        }
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
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(Item item) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, item.title, item.lore);
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                if (SUPPORT) {
                    skull.setUUID("Id", UUID.randomUUID());
                } else {
                    skull.setString("Id", UUID.randomUUID().toString());
                }
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
                if (SUPPORT) {
                    skull.setUUID("Id", UUID.randomUUID());
                } else {
                    skull.setString("Id", UUID.randomUUID().toString());
                }
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


    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, ItemStack itemStackParam) {
        try {
            ItemStack itemstack = itemStackParam.clone();
            itemstack = makeItem(itemstack, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
            return itemstack;
        } catch (Exception e) {
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }


    public static ItemStack makeItem(Item item, TradeObject tradeObject) {
        List<Placeholder> placeholders = Utils.getTradeGUIPlaceholders(tradeObject);
        ItemStack tradeItem = tradeObject.getTradeItem().clone();
        try {
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%trade_description%")){
                        addAll(tradeObject.getDescription());
                        continue;
                    }
                    add(line);
                }
            }};

            ItemStack itemstack = makeItem(tradeItem, tradeItem.getAmount(), StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(lore, placeholders));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                if (SUPPORT) {
                    skull.setUUID("Id", UUID.randomUUID());
                } else {
                    skull.setString("Id", UUID.randomUUID().toString());
                }
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
            return makeItem(XMaterial.STONE, item.multiplier, StringUtils.processMultiplePlaceholders(item.title, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }
/*
    public static ItemStack makeItem(Item item, TradeObject tradeObject, int multiplier) {
        List<Placeholder> placeholders = Utils.getTradeGUIPlaceholders(tradeObject, multiplier);
        ItemStack tradeItem = tradeObject.getTradeItem();
        try {
            List<String> lore = new ArrayList<String>(){{
                for(String line : item.lore){
                    if(line.contains("%trade_description%")){
                        addAll(tradeObject.getDescription());
                        continue;
                    }
                    add(line);
                }
            }};
            ItemStack itemstack = makeItem(tradeItem.clone(), multiplier, StringUtils.processMultiplePlaceholders(item.title.replace("%displayname%", tradeObject.getDisplayName()), placeholders), StringUtils.processMultiplePlaceholders(lore, placeholders));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                if (supports) {
                    skull.setUUID("Id", UUID.randomUUID());
                } else {
                    skull.setString("Id", UUID.randomUUID().toString());
                }
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
    }*/

  /*public static ItemStack makeItemHidden(Inventories.Item item) {
    return makeItemHidden(item, getIslandPlaceholders());
  }*/


}
