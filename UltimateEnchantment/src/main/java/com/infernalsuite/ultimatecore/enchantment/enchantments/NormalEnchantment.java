package com.infernalsuite.ultimatecore.enchantment.enchantments;

import com.cryptomorin.xseries.XEnchantment;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;

@Getter
public class NormalEnchantment extends HyperEnchant{

    public NormalEnchantment(String displayName, int maxLevel, boolean useMoney, HashMap<Integer, Double> requiredMoney, String enchantmentName, String description, HashMap<Integer, Integer> requiredLevel, int requiredBookShelf) {
        super(displayName,
                XEnchantment.valueOf(enchantmentName.toUpperCase()).getEnchant() == null ? maxLevel : Objects.requireNonNull(XEnchantment.valueOf(enchantmentName.toUpperCase()).getEnchant()).getMaxLevel()
                , useMoney, requiredMoney, enchantmentName, Utils.translateDescription(description), requiredLevel, requiredBookShelf);
    }

    public int getMaxLevel(){
        if(maxLevel == 0) setMaxLevel(getEnchantment().getMaxLevel());
        return super.maxLevel;
    }

    @Override
    public boolean itemCanBeEnchanted(ItemStack itemStack) {
        if(getEnchantment() == null || itemStack == null) return false;
        return getEnchantment().canEnchantItem(itemStack);
    }

    @Override
    public boolean itemIsEnchanted(ItemStack itemStack, int level) {
        return itemStack.getEnchantmentLevel(getEnchantment()) >= level;
    }

    @Override
    public int getItemLevel(ItemStack itemStack) {
        if(itemStack == null || itemStack.getType() == Material.AIR) return 0;
        NBTItem nbtItem = new NBTItem(itemStack);
        if(nbtItem.hasKey("hp_"+ super.enchantmentName)) return nbtItem.getInteger("hp_"+ super.enchantmentName);
        ItemMeta meta = itemStack.getItemMeta();
        int vanillaLevel = itemStack.getEnchantmentLevel(getEnchantment());
        if(meta instanceof EnchantmentStorageMeta)
            return ((EnchantmentStorageMeta ) meta).getStoredEnchantLevel(getEnchantment());
        return vanillaLevel;
    }

    @Override
    public boolean hasConflictWith(Enchantment enchantment) {
        Enchantment enchant = getEnchantment();
        try {
            return enchant != enchantment && enchant.conflictsWith(enchantment);
        }catch (Exception e){
            return false;
        }
    }


}
