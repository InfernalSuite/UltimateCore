package com.infernalsuite.ultimatecore.enchantment.enchantments.hooks;

import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import com.infernalsuite.ultimatecore.enchantment.utils.AEUtils;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class HyperAdvancedEnchantment extends HyperEnchant {

    public HyperAdvancedEnchantment(String displayName, int maxLevel, boolean useMoney, Map<Integer, Double> requiredMoney, String enchantmentName, Map<Integer, Integer> requiredLevel, int requiredBookShelf) {
        super(displayName,
                AEUtils.instance(enchantmentName).getHighestLevel() < 0 ? maxLevel : AEUtils.instance(enchantmentName).getHighestLevel()
                , useMoney, requiredMoney, enchantmentName, Utils.translateDescription(AEUtils.instance(enchantmentName).getDescription()), requiredLevel, requiredBookShelf);
    }

    @Override
    public int getMaxLevel(){
        if(super.maxLevel == 0) super.maxLevel = AEUtils.instance(enchantmentName).getHighestLevel();
        return super.maxLevel;
    }

    @Override
    public boolean itemCanBeEnchanted(ItemStack itemStack) {
        return AEUtils.instance(enchantmentName).canBeApplied(itemStack.getType());
    }

    @Override
    public boolean itemIsEnchanted(ItemStack itemStack, int level) {
        return getItemLevel(itemStack) >= level;
    }

    @Override
    public int getItemLevel(ItemStack itemStack) {
        return AEUtils.getItemLevel(enchantmentName, itemStack);
    }

    @Override
    public boolean hasConflictWith(Enchantment enchantment) {
        return AEUtils.hasConflictWith(enchantmentName, enchantment);
    }
}
