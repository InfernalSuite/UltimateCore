package mc.ultimatecore.enchantment.addons;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.configs.HyperEnchantments;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class ClipPlaceholderAPIManager extends PlaceholderExpansion {

    private final EnchantmentsPlugin plugin;

    public ClipPlaceholderAPIManager(EnchantmentsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @NotNull
    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "ultimateenchantment";
    }

    @NotNull
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return identifier;

        HyperEnchantments hyperEnchantments = plugin.getHyperEnchantments();
        for (HyperEnchant hyperEnchant : hyperEnchantments.enchantments.values()) {
            if (identifier.equals("name_" + hyperEnchant.getEnchantmentName()))
                return hyperEnchant.getDisplayName();
            else if (identifier.equals("lore_" + hyperEnchant.getEnchantmentName()))
                return String.join(" ", hyperEnchant.getDescription(0));
        }
        return null;
    }
}