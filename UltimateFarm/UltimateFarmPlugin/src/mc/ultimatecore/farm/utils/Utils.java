package mc.ultimatecore.farm.utils;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.farm.configs.Inventories;
import mc.ultimatecore.farm.objects.blocks.RegionBlock;
import mc.ultimatecore.farm.skullcreator.SkullCreator;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    public static int getRandom(int min, int max) {
        return Math.max(new Random().nextInt(max - min) + min, 1);
    }

    public static ItemStack makeItem(Material material, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.setLore(color(lore));
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
        m.setLore(color(lore));
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(Inventories.Item item) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, item.title, item.lore);
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                ItemMeta meta = itemstack.getItemMeta();
                return changeItemMeta(SkullCreator.getSkull(item.headData), meta);
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

    public static ItemStack makeItem(Inventories.Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                ItemMeta meta = itemstack.getItemMeta().clone();
                itemstack = SkullCreator.getSkull(item.headData);
                itemstack.setItemMeta(meta);
                return itemstack;
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(XMaterial.STONE, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Inventories.Item item, List<Placeholder> placeholders, RegionBlock regionBlock) {
        try {
            String material = regionBlock == null ? "AIR" : XItem.getMaterial(regionBlock.getMaterial());

            Material mat = Material.valueOf(material);
            ItemStack itemstack = mat == Material.AIR ?
                    makeItem(XMaterial.WHITE_STAINED_GLASS_PANE, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders)) :
                    makeItem(mat, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                ItemMeta meta = itemstack.getItemMeta().clone();
                itemstack = SkullCreator.getSkull(item.headData);
                itemstack.setItemMeta(meta);
                return itemstack;
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItemHidden(Inventories.Item item) {
        try {
            ItemStack itemstack = makeItemHidden(item.material, item.amount, item.title, item.lore);
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                ItemMeta meta = itemstack.getItemMeta().clone();
                itemstack = SkullCreator.getSkull(item.headData);
                itemstack.setItemMeta(meta);
                return itemstack;
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

    public static ItemStack makeItemHidden(Inventories.Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemstack = makeItemHidden(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), color(processMultiplePlaceholders(item.lore, placeholders)));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                ItemMeta meta = itemstack.getItemMeta();
                return changeItemMeta(SkullCreator.getSkull(item.headData), meta);
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();
            return makeItemHidden(XMaterial.STONE, item.amount, processMultiplePlaceholders(item.title, placeholders), color(processMultiplePlaceholders(item.lore, placeholders)));
        }
    }

    public static ItemStack makeItemHidden(Inventories.Item item, List<Placeholder> placeholders, String texture) {
        try {
            item.headData = texture;
            ItemStack itemstack = makeItemHidden(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), color(processMultiplePlaceholders(item.lore, placeholders)));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                ItemMeta meta = itemstack.getItemMeta();
                return changeItemMeta(SkullCreator.getSkull(item.headData), meta);
            }
            if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            e.printStackTrace();
            return makeItemHidden(XMaterial.STONE, item.amount, processMultiplePlaceholders(item.title, placeholders), color(processMultiplePlaceholders(item.lore, placeholders)));
        }
    }

    public static ItemStack makeItemHidden(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack item = material.parseItem();
        if (item == null)
            return null;
        item.setAmount(amount);
        ItemMeta m = item.getItemMeta();
        m.setLore(color(lore));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack changeItemMeta(ItemStack itemStack, ItemMeta oldItemMeta) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(oldItemMeta.getDisplayName());
        meta.setLore(oldItemMeta.getLore());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }

    public static String getFormattedName(String str) {
        return getFormattedText(str.replace("_", " ").replace("_BUSH", ""));
    }

    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders) {
        List<String> newlist = new ArrayList<>();
        for (String string : lines)
            newlist.add(processMultiplePlaceholders(string, placeholders));
        return newlist;
    }

    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders) {
        for (Placeholder placeholder : placeholders)
            line = placeholder.process(line);
        return color(line);
    }

    public static String getFormattedText(String text) {
        StringBuilder formattedText = new StringBuilder();
        String[] splitText = text.split("");
        for (int i = 0; i < splitText.length; i++) {
            if (i == 0) formattedText.append(splitText[i].toUpperCase());
            else formattedText.append(splitText[i].toLowerCase());
        }
        return formattedText.toString();
    }

    public static Location getLocationFromConfig(YamlConfiguration config, String path) {
        try {
            World world = Bukkit.getServer().getWorld(config.getString(path + ".world"));
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");
            float yaw = Float.parseFloat(Objects.requireNonNull(config.getString(path + ".yaw")));
            float pitch = Float.parseFloat(Objects.requireNonNull(config.getString(path + ".pitch")));
            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveLocationToConfig(YamlConfiguration config, String path, Location location) {
        try {
            config.set(path + ".world", location.getWorld().getName());
            config.set(path + ".x", location.getX());
            config.set(path + ".y", location.getY());
            config.set(path + ".z", location.getZ());
            config.set(path + ".yaw", location.getYaw());
            config.set(path + ".pitch", location.getPitch());
        } catch (Exception ignored) {
        }
    }


    public static class Placeholder {
        private final String key;

        private final String value;

        public Placeholder(String key, String value) {
            this.key = "%" + key + "%";
            this.value = value;
        }

        public String process(String line) {
            if (line == null)
                return "";
            return line.replace(this.key, this.value);
        }
    }
}
