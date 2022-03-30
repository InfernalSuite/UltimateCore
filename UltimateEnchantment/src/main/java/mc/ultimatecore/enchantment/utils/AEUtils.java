package mc.ultimatecore.enchantment.utils;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.enchantments.hooks.HyperAdvancedEnchantment;
import n3kas.ae.api.AEAPI;
import net.advancedplugins.ae.enchanthandler.enchantments.AdvancedEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AEUtils {
    
    public static int loadAllAEEnchants() {
        int amount = 0;
        for (String key : AEAPI.getAllEnchantments()) {
            AdvancedEnchantment advancedEnchantment = instance(key);
            if (EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.containsKey(key.toLowerCase())) continue;
            String displayName = advancedEnchantment.getName();
            HyperAdvancedEnchantment hyperEcoEnchant = new HyperAdvancedEnchantment(displayName, 0, false, new HashMap<>(), key, new HashMap<>(), 0);
            EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.put(key.toLowerCase(), hyperEcoEnchant);
            amount++;
        }
        return amount;
    }
    
    public static AdvancedEnchantment instance(String name) {
        return AEAPI.getEnchantmentInstance(name);
    }
    
    public static ItemStack getEnchantedItem(String aeEnchantment, ItemStack itemStack, int level) {
        return AEAPI.applyEnchant(aeEnchantment, level, itemStack);
    }
    
    public static ItemStack removeEnchant(String aeEnchantment, ItemStack itemStack) {
        return AEAPI.removeEnchantment(itemStack, aeEnchantment);
    }
    
    public static int getItemLevel(String aeEnchantment, ItemStack itemStack) {
        return AEAPI.getEnchantmentsOnItem(itemStack).getOrDefault(aeEnchantment, 0);
    }
    
    public static boolean hasConflictWith(String aeEnchantment, Enchantment enchantment) {
        return instance(aeEnchantment).getBlacklistedEnchants().contains(enchantment.getName());
    }
    
    public static Map<String, Integer> getItemEnchantments(ItemStack itemStack) {
        return AEAPI.getEnchantmentsOnItem(itemStack);
    }
    
    public static ItemStack getEnchantedBook(String name, int level) {
        return AEAPI.createEnchantmentBook(name, level, 100, 0);
    }
    
    
}
