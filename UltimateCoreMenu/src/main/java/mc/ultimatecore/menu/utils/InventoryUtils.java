package mc.ultimatecore.menu.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import mc.ultimatecore.menu.HyperCore;
import mc.ultimatecore.menu.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
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

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, UUID uuid) {
        try {
            Player player = Bukkit.getPlayer(uuid);
            ItemStack itemstack = !HyperCore.getInstance().getConfiguration().translatePAPIPlaceholders || player == null?
                    makeItem(item.material, item.amount,
                        StringUtils.processMultiplePlaceholders(item.title, placeholders),
                        StringUtils.processMultiplePlaceholders(item.lore, placeholders)) :
                    makeItem(item.material, item.amount,
                            PlaceholderAPIUtils.processMultiplePlaceholders(item.title, placeholders, player),
                            PlaceholderAPIUtils.processMultiplePlaceholders(item.lore, placeholders, player));
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
                String headOwner = String.valueOf(item.headOwner).replace("%player%", player.getName());
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


    public static ItemStack makeItem(Item item, UUID uuid) {
        try {
            Player player = Bukkit.getPlayer(uuid);
            ItemStack itemstack = !HyperCore.getInstance().getConfiguration().translatePAPIPlaceholders || player == null?
                    makeItem(item.material, item.amount, item.title, item.lore) :
                    makeItem(item.material, item.amount,
                            PlaceholderAPIUtils.processMultiplePlaceholders(item.title, Collections.emptyList(), player),
                            PlaceholderAPIUtils.processMultiplePlaceholders(item.lore, Collections.emptyList(), player));
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

}
