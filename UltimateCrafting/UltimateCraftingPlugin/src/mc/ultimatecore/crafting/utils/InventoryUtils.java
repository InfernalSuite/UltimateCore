package mc.ultimatecore.crafting.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import mc.ultimatecore.crafting.Item;
import mc.ultimatecore.crafting.objects.Category;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryUtils {
    public static ItemStack makeItem(ItemStack item, String name, List<String> lore) {
        ItemMeta m = item.getItemMeta();
        if(item.getItemMeta() == null)
            return null;
        if(lore != null)
            m.setLore(Utils.color(lore));
        m.setDisplayName(Utils.color(name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack item = material.parseItem();
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(lore != null && lore.size() > 0)
            m.setLore(Utils.color(lore));
        m.setDisplayName(Utils.color(name == null ? " " : name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(Item item, ItemStack it, String nbt) {
        try {
            ItemStack itemStack = it.clone();
            ItemMeta meta = itemStack.getItemMeta();
            String title = getNewTitle(item.title, meta);
            List<String> lore = new ArrayList<String>() {{
                for(String line : item.lore) {
                    if(line.contains("%item_lore%"))
                        addAll(meta == null || !meta.hasLore() ? new ArrayList<>() : meta.getLore());
                    else
                        add(line);
                }
            }};
            ItemStack itemstack = makeItem(itemStack.clone(), title, lore);
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
            NBTItem nbtItem = new NBTItem(itemstack);
            nbtItem.setBoolean(nbt, true);
            return nbtItem.getItem();
        } catch (Exception e) {
            e.printStackTrace();
            return makeItem(XMaterial.STONE, item.amount, item.title, item.lore);
        }
    }

    public static String getNewTitle(String title, ItemMeta meta){
        if(meta == null || !meta.hasDisplayName()) return "";
        return title.replace("%item_title%", meta.getDisplayName());
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

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, Category category) {
        try {
            XMaterial material = XMaterial.valueOf(category.getMaterial());
            ItemStack itemstack = makeItem(material, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.processMultiplePlaceholders(item.lore, placeholders));
            if (material == XMaterial.PLAYER_HEAD && item.headData != null) {
                NBTItem nbtItem = new NBTItem(itemstack);
                NBTCompound skull = nbtItem.addCompound("SkullOwner");
                skull.setString("Name", "tr7zw");
                skull.setString("Id", UUID.randomUUID().toString());
                NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
                texture.setString("Value", item.headData);
                return nbtItem.getItem();
            }
            if (material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(Utils.processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(XMaterial.STONE, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.processMultiplePlaceholders(item.lore, placeholders));
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
                m.setOwner(Utils.processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(XMaterial.STONE, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }


    public static ItemStack makeItem(Item item, List<Placeholder> placeholders, Material material) {
        try {
            item.material = XMaterial.matchXMaterial(material);
            ItemStack itemstack = makeItem(item.material, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.processMultiplePlaceholders(item.lore, placeholders));
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
                m.setOwner(Utils.processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(XMaterial.STONE, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItemHidden(Item item) {
        try {
            ItemStack itemstack = makeItemHidden(item.material, item.amount, item.title, item.lore);
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
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();
            return makeItemHidden(XMaterial.STONE, item.amount, item.title, item.lore);
        }
    }


    public static ItemStack makeItemHidden(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack item = material.parseItem();
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.setLore(Utils.color(lore));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }
}
