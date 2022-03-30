package mc.ultimatecore.enchantment.api.events;

import lombok.Getter;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class HyperEnchantEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final int levelCost;

    private final double moneyCost;

    private final HyperEnchant enchant;

    private final int enchantLevel;

    private final ItemStack item;

    private final boolean isAdvancedEnchantment;

    /**
     * Called when player enchant an item in the hyperenchanting table.
     *
     * @param player Player
     * @param levelCost enchantment cost in levels (if it has it)
     * @param moneyCost enchantment cost in money (if it has it)
     * @param enchant HyperEnchant enchantment
     * @param enchantLevel enchantment level after enchant item
     * @param item ItemStack enchanted
     * @param isAdvancedEnchantment return if enchantment belongs to AE
     * */
    public HyperEnchantEvent(@NotNull Player player, int levelCost, double moneyCost, HyperEnchant enchant, int enchantLevel, ItemStack item, boolean isAdvancedEnchantment) {
        super(player);
        this.levelCost = levelCost;
        this.moneyCost = moneyCost;
        this.enchant = enchant;
        this.enchantLevel = enchantLevel;
        this.item = item;
        this.isAdvancedEnchantment = isAdvancedEnchantment;
    }

    @NotNull
    public final HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
