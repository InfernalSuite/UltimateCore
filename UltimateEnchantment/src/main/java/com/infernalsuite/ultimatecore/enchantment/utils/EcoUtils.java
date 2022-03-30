package com.infernalsuite.ultimatecore.enchantment.utils;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.hooks.HyperEcoEnchant;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class EcoUtils {
    public static int loadAllEcoEnchants(){
        int amount = 0;
        for(EcoEnchant ecoEnchant : EcoEnchants.values()){
            String enchantID = ecoEnchant.getKey().getKey();
            if(EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.containsKey(enchantID.toLowerCase())) continue;
            String displayName = ecoEnchant.getName();
            HyperEcoEnchant hyperEcoEnchant = new HyperEcoEnchant(displayName, 0, false, new HashMap<>(), enchantID, new HashMap<>(), 0);
            EnchantmentsPlugin.getInstance().getHyperEnchantments().enchantments.put(enchantID.toLowerCase(), hyperEcoEnchant);
            amount++;
        }
        return amount;
    }

    public static EcoEnchant instance(String name){
        return EcoEnchants.getByKey(new NamespacedKey("minecraft", name));
    }
}
