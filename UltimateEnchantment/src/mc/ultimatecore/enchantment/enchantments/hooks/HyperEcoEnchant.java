package mc.ultimatecore.enchantment.enchantments.hooks;

import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.enchantment.utils.EcoUtils;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class HyperEcoEnchant extends HyperEnchant {

    public HyperEcoEnchant(String displayName, int maxLevel, boolean useMoney, Map<Integer, Double> requiredMoney, String enchantmentName, Map<Integer, Integer> requiredLevel, int requiredBookShelf) {
        super(displayName,
                EcoUtils.instance(enchantmentName).getMaxLevel() < 0 ? maxLevel : EcoUtils.instance(enchantmentName).getMaxLevel()
                , useMoney, requiredMoney, enchantmentName, Utils.translateDescription(EcoUtils.instance(enchantmentName).getDescription()), requiredLevel, requiredBookShelf);
    }

    @Override
    public String getDisplayName(){
        return displayName;
    }

    @Override
    public int getMaxLevel(){
        if(maxLevel == 0) maxLevel = getEnchantment().getMaxLevel();
        return maxLevel;
    }

    @Override
    public boolean itemCanBeEnchanted(ItemStack itemStack) {
        return getEnchantment().canEnchantItem(itemStack);
    }

    @Override
    public boolean itemIsEnchanted(ItemStack itemStack, int level) {
        return getItemLevel(itemStack) >= level;
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
        return getEnchantment().conflictsWith(enchantment);
    }

    @Override
    public Enchantment getEnchantment(){
        return EcoUtils.instance(enchantmentName);
    }

}
