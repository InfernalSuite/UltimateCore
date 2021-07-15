package mc.ultimatecore.enchantment.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.Item;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.enchantment.enums.EnchantState;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InventoryUtils {
    public static ItemStack makeItemHidden(Item item, List<Placeholder> placeholders, HyperEnchant hyperEnchant, ItemStack itemStack) {
        try {
            Optional<Enchantment> conflict = hyperEnchant.hasEnchantmentConflicts(itemStack);
            String key = conflict.map(Utils::getEnchantmentKey).orElse(null);
            List<String> lore = new ArrayList<>();
            for(String line : item.lore){
                if(line.contains("%enchant_incompatibility%")){
                    if(key != null && EnchantmentsPlugin.getInstance().getConfiguration().removeIncompatibleEnchants){
                        HyperEnchant conflictEnchant = EnchantmentsPlugin.getInstance().getHyperEnchantments().getEnchantmentByID(key);
                        EnchantmentsPlugin.getInstance().getMessages().getIncompatibilityError().forEach(subLine -> lore.add(subLine.replace("%incompatible_enchant%", conflictEnchant.getDisplayName())));
                    }
                    continue;
                }
                if(line.contains("%enchant_description%")){
                    hyperEnchant.getDescription().forEach(desLine -> lore.add(Utils.color(line.replace("%enchant_description%", desLine))));
                    continue;
                }
                lore.add(line);
            }

            ItemStack itemstack = makeItemHidden(item.material, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.color(Utils.processMultiplePlaceholders(lore, placeholders)));
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
            return makeItemHidden(XMaterial.STONE, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.color(Utils.processMultiplePlaceholders(item.lore, placeholders)));
        }
    }

    public static ItemStack makeItemHidden(Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemstack = makeItemHidden(item.material, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.color(Utils.processMultiplePlaceholders(item.lore, placeholders)));
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
            return makeItemHidden(XMaterial.STONE, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.color(Utils.processMultiplePlaceholders(item.lore, placeholders)));
        }
    }

    public static ItemStack makeItemHidden(Item item, List<Placeholder> placeholders, HyperEnchant hyperEnchant) {
        try {
            List<String> lore = new ArrayList<>();
            for(String line : item.lore){
                if(line.contains("%enchant_description%")){
                    hyperEnchant.getDescription().forEach(desLine -> lore.add(Utils.color(line.replace("%enchant_description%", desLine))));
                    continue;
                }
                lore.add(line);
            }
            ItemStack itemstack = makeItemHidden(item.material, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.color(Utils.processMultiplePlaceholders(lore, placeholders)));

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
            return makeItemHidden(XMaterial.STONE, item.amount, Utils.processMultiplePlaceholders(item.title, placeholders), Utils.color(Utils.processMultiplePlaceholders(item.lore, placeholders)));
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


    public static ItemStack makeItemHidden(Item item, EnchantState enchantState) {
        try {
            List<String> lore = new ArrayList<>();
            if(enchantState != null){
                for(String line : item.lore){
                    if (line.contains("%error_placeholder%"))
                        lore.add(enchantState.error);
                    else
                        lore.add(line);
                }
            }
            ItemStack itemstack = makeItemHidden(item.material, item.amount, item.title, lore);
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

    public static ItemStack makeItem(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack item = material.parseItem();
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.setLore(Utils.color(lore));
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

}
