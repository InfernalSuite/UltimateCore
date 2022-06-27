package mc.ultimatecore.enchantment.addons;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.configs.HyperEnchantments;

public class MVDWPlaceholderAPIManager {

    public MVDWPlaceholderAPIManager(EnchantmentsPlugin plugin) {
        HyperEnchantments hyperEnchantments = plugin.getHyperEnchantments();
        hyperEnchantments.enchantments
                .forEach((name, hyperEnchantment) -> {
                    PlaceholderAPI.registerPlaceholder(plugin, "ultimateenchantment_lore_" + name, e -> String.join(" ",hyperEnchantment.getDescription(0)) + "");
                    PlaceholderAPI.registerPlaceholder(plugin, "ultimateenchantment_name_" + name, e -> hyperEnchantment.getDisplayName() + "");
                });
    }
}