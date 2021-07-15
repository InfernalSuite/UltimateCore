package mc.ultimatecore.enchantment.addons;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.configs.HyperEnchantments;

public class MVDWPlaceholderAPIManager {
    
    public MVDWPlaceholderAPIManager(EnchantmentsPlugin plugin) {
        HyperEnchantments hyperEnchantments = plugin.getHyperEnchantments();
        hyperEnchantments.enchantments
                .forEach((name, hyperEnchantment) -> {
                    PlaceholderAPI.registerPlaceholder(plugin, "hyperenchants_lore_" + name, e -> hyperEnchantment.getDescription() + "");
                    PlaceholderAPI.registerPlaceholder(plugin, "hyperenchants_name_" + name, e -> hyperEnchantment.getDisplayName() + "");
                });
    }
}