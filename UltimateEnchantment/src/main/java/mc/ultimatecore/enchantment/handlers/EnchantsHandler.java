package mc.ultimatecore.enchantment.handlers;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.enchantment.enchantments.hooks.HyperAdvancedEnchantment;
import mc.ultimatecore.enchantment.object.AdvancedSettings;
import mc.ultimatecore.enchantment.utils.AEUtils;
import mc.ultimatecore.enchantment.utils.InventoryUtils;
import mc.ultimatecore.enchantment.utils.Placeholder;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EnchantsHandler {
    private final EnchantmentsPlugin plugin;

    private ItemStack convertToUltimateBook(ItemStack itemStack, HyperEnchant hyperEnchant, int level, Map<HyperEnchant, Integer> enchantments) {
        int cost = calculateCost(enchantments);
        ItemStack ultimateBook = InventoryUtils.makeItem(plugin.getConfiguration().bookItem, Collections.singletonList(new Placeholder(
                "apply_cost", plugin.getMessages().getMessage("levelCostLine").replace("%cost_level%", String.valueOf(cost))
        )));
        ItemMeta ultimateMeta = ultimateBook.getItemMeta();
        itemStack.setType(ultimateBook.getType());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ultimateMeta.getDisplayName());
        meta.setLore(ultimateMeta.getLore());
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("applyCost", cost);
        nbtItem.setInteger("ue_enchant-" + hyperEnchant.getEnchantmentName(), level);
        return nbtItem.getItem();
    }

    public int calculateCost(Map<HyperEnchant, Integer> enchantments) {
        int finalCost = 0;
        for (HyperEnchant hyperEnchant : enchantments.keySet())
            finalCost += hyperEnchant.getRequiredLevel(enchantments.get(hyperEnchant));
        return finalCost;
    }

    public ItemStack getEnchantedBook(HyperEnchant hyperEnchant, int level) {
        return plugin.getApi().enchantItem(XMaterial.BOOK.parseItem(), hyperEnchant, level, false);
    }

    public ItemStack addEnchantment(ItemStack itemStack, int level, boolean reforged, HyperEnchant hyperEnchant) {

        String enchantmentName = hyperEnchant.getEnchantmentName();

        Map<HyperEnchant, Integer> enchantments = plugin.getApi().getItemEnchantments(itemStack);

        enchantments.put(hyperEnchant, level);

        if (itemStack.getType().toString().contains("BOOK"))
            itemStack = convertToUltimateBook(itemStack, hyperEnchant, level, enchantments);

        //Add NBT Tag
        NBTItem nbtItem = new NBTItem(itemStack);

        nbtItem.setInteger("hp_" + enchantmentName, level);

        //Add NBT Tag if cause is reforge
        if (reforged) nbtItem.setInteger("rf_" + enchantmentName, level);

        itemStack = nbtItem.getItem();

        //Adding Vanilla Enchantment
        itemStack = addEnchant(itemStack, hyperEnchant, level).clone();

        //Setting UP ItemFlag
        itemStack = checkItemFlags(itemStack).clone();

        //Removing Old Enchantments Lore
        itemStack = removeLore(itemStack, enchantments).clone();

        //Updating Lore
        itemStack = updateLore(itemStack, enchantments);

        //Removing Incompatible Enchantments

        HyperEnchant incompatible = getIncompatible(itemStack, hyperEnchant);

        if (incompatible != null) itemStack = removeEnchantment(itemStack, incompatible).clone();

        return itemStack;
    }

    private ItemStack updateLore(ItemStack itemStack, Map<HyperEnchant, Integer> enchantments) {
        if (enchantments.size() >= plugin.getConfiguration().advancedSettings.getLimit())
            itemStack = addAdvancedLore(itemStack, enchantments).clone();
        else
            itemStack = addLore(itemStack, enchantments).clone();
        return itemStack;
    }

    public ItemStack removeEnchantment(ItemStack itemStack, HyperEnchant hyperEnchant) {
        Map<HyperEnchant, Integer> enchantments = plugin.getApi().getItemEnchantments(itemStack);

        itemStack = removeLore(itemStack, enchantments).clone();

        enchantments.remove(hyperEnchant);

        String enchantmentName = hyperEnchant.getEnchantmentName();
        //Removing Enchantment Lore
        itemStack = updateLore(itemStack, enchantments).clone();
        //Removing Vanilla Enchantment
        itemStack = removeEnchant(itemStack, hyperEnchant).clone();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.removeKey("hp_" + enchantmentName);
        nbtItem.removeKey("rf_" + enchantmentName);
        return nbtItem.getItem();
    }

    private ItemStack addLore(ItemStack itemStack, Map<HyperEnchant, Integer> enchantments) {
        List<String> newLore = null;
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta != null && meta.hasLore() ? itemStack.getItemMeta().getLore() : Collections.emptyList();
        if (plugin.getConfiguration().addInfoOnEnchant) {
            newLore = getNormalLore(enchantments);
        }

        if (lore != null && meta != null) {
            meta.setLore(plugin.getConfiguration().loreSettings.getToAddLore(lore, newLore));
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ItemStack addAdvancedLore(ItemStack itemStack, Map<HyperEnchant, Integer> enchantments) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta != null && meta.hasLore() ? itemStack.getItemMeta().getLore() : Collections.emptyList();
        if (plugin.getConfiguration().addInfoOnEnchant && meta != null) {
            //getShrinkLore(enchantments).forEach(lien -> Bukkit.getConsoleSender().sendMessage(lien));
            meta.setLore(plugin.getConfiguration().loreSettings.getToAddLore(lore, getShrinkLore(enchantments)));
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    private ItemStack removeLore(ItemStack itemStack, Map<HyperEnchant, Integer> enchantments) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta != null && meta.hasLore() ? itemStack.getItemMeta().getLore() : Collections.emptyList();
        List<String> newLore = new ArrayList<>();
        if (lore != null && meta != null) {
            for (String line : lore) {
                boolean add = true;
                for (HyperEnchant hyperEnchant : enchantments.keySet()) {
                    String unLine = Utils.uncolor(line);
                    String unColorName = Utils.uncolor(Utils.color(hyperEnchant.getDisplayName()));
                    if (unLine.contains(unColorName) || unLine.contains(hyperEnchant.getEnchantmentName())) {
                        add = false;
                        continue;
                    }
                    for (String descriptionLine : hyperEnchant.getDescription()) {
                        String uncolorDesc = Utils.uncolor(descriptionLine);
                        if (unLine.contains(uncolorDesc))
                            add = false;
                    }
                }
                if (add)
                    newLore.add(Utils.color(line));
            }
            meta.setLore(newLore);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ItemStack addEnchant(ItemStack itemStack, HyperEnchant hyperEnchant, int level) {
        if (hyperEnchant instanceof HyperAdvancedEnchantment) {
            return AEUtils.getEnchantedItem(hyperEnchant.getEnchantmentName(), itemStack, level);
        } else {
            Enchantment enchantment = hyperEnchant.getEnchantment();
            ItemMeta meta = itemStack.getItemMeta();
            if (enchantment != null) {
                if (meta instanceof EnchantmentStorageMeta)
                    ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
                else
                    meta.addEnchant(enchantment, level, false);
            }
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    private ItemStack removeEnchant(ItemStack itemStack, HyperEnchant hyperEnchant) {
        if (hyperEnchant instanceof HyperAdvancedEnchantment) {
            return AEUtils.removeEnchant(hyperEnchant.getEnchantmentName(), itemStack);
        } else {
            ItemMeta meta = itemStack.getItemMeta();
            Enchantment enchant = hyperEnchant.getEnchantment();
            if (meta instanceof EnchantmentStorageMeta) ((EnchantmentStorageMeta) meta).removeStoredEnchant(enchant);
            else meta.removeEnchant(enchant);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    private HyperEnchant getIncompatible(ItemStack itemStack, HyperEnchant hyperEnchant) {
        if (plugin.getConfiguration().removeIncompatibleEnchants) {
            Optional<Enchantment> conflict = hyperEnchant.hasEnchantmentConflicts(itemStack);
            String key = conflict.map(Utils::getEnchantmentKey).orElse(null);
            return plugin.getHyperEnchantments().getEnchantmentByID(key);
        }
        return null;
    }

    private ItemStack checkItemFlags(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (plugin.getConfiguration().hideOriginalEnchant) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        else meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private List<String> getShrinkLore(Map<HyperEnchant, Integer> enchantments) {
        AdvancedSettings settings = plugin.getConfiguration().advancedSettings;
        List<String> newLore = new ArrayList<>();
        if (enchantments.size() >= settings.getPerLine()) {
            List<String> enchantNames = enchantments.keySet().stream().map(enchant -> Utils.color(settings.getLine()
                    .replace("%enchant_level%", Utils.toRoman(enchantments.get(enchant)))
                    .replace("%enchant_name%", ChatColor.stripColor(enchant.getDisplayName())))).sorted().distinct().collect(Collectors.toList());
            List<List<String>> partitionedCombinedLoreList = Lists.partition(enchantNames, settings.getPerLine());
            partitionedCombinedLoreList.forEach(list -> {
                StringBuilder builder = new StringBuilder();
                for (String s : list) {
                    builder.append(s);
                    builder.append(", ");
                }
                String line = builder.toString();
                line = line.substring(0, line.length() - 2);
                newLore.add(line);
            });
        }
        return newLore;
    }

    private List<String> getNormalLore(Map<HyperEnchant, Integer> enchantments) {
        List<String> newLore = new ArrayList<>();
        for (HyperEnchant hyperEnchant : enchantments.keySet())
            plugin.getConfiguration().infoToEnchantedItem.forEach(line -> {
                if (line.contains("%enchant_description%")) {
                    hyperEnchant.getDescription().forEach(desLine -> newLore.add(Utils.color(line.replace("%enchant_description%", desLine))));
                } else {
                    newLore.add(Utils.color(line
                            .replace("%enchant_level%", Utils.toRoman(enchantments.get(hyperEnchant)))
                            .replace("%enchant_name%", hyperEnchant.getDisplayName())));
                }
            });
        return newLore;
    }

}
