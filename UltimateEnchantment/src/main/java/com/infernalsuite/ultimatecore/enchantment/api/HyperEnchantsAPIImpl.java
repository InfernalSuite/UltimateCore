package com.infernalsuite.ultimatecore.enchantment.api;

import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class HyperEnchantsAPIImpl implements HyperEnchantsAPI{

    private final EnchantmentsPlugin plugin;

    @Override
    public ItemStack enchantItem(ItemStack itemStack, HyperEnchant hyperEnchant, int level, boolean reforged) {
        return hyperEnchant.getEnchantedItem(itemStack, level, reforged);
    }

    @Override
    public ItemStack removeEnchant(ItemStack itemStack, HyperEnchant hyperEnchant) {
        return hyperEnchant.removeEnchant(itemStack);
    }

    @Override
    public boolean hasReforgedEnchantment(ItemStack itemStack, HyperEnchant hyperEnchant) {
        return hyperEnchant.hasEnchantmentByReforge(itemStack);
    }

    @Override
    public HyperEnchant getEnchantmentInstance(String enchantmentID) {
        return plugin.getHyperEnchantments().getEnchantmentByID(enchantmentID);
    }

    @Override
    public boolean itemCanBeEnchanted(ItemStack itemStack, HyperEnchant hyperEnchant) {
        return hyperEnchant.itemCanBeEnchanted(itemStack);
    }

    @Override
    public boolean itemIsEnchanted(ItemStack itemStack, HyperEnchant hyperEnchant, int level) {
        return hyperEnchant.itemIsEnchanted(itemStack, level);
    }

    @Override
    public List<HyperEnchant> getAvailableEnchantments(ItemStack itemStack) {
        return plugin.getHyperEnchantments().getAvailableEnchantments(itemStack);
    }

    @Override
    public Map<HyperEnchant, Integer> getItemEnchantments(ItemStack itemStack) {
        return plugin.getHyperEnchantments().getItemEnchantments(itemStack);
    }

    @Override
    public List<HyperEnchant> getAllEnchantments() {
        return new ArrayList<>(plugin.getHyperEnchantments().enchantments.values());
    }

    @Override
    public ItemStack getEnchantedBook(HyperEnchant hyperEnchant, int level) {
        return plugin.getEnchantsHandler().getEnchantedBook(hyperEnchant, level);
    }
}