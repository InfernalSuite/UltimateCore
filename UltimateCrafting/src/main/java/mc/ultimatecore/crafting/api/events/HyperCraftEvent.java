package mc.ultimatecore.crafting.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class HyperCraftEvent extends PlayerEvent {
    
    private static final HandlerList handlers = new HandlerList();
    
    @Getter
    private final ItemStack result;
    
    /**
     * Called when player craft an item in the hypercrafting table.
     *
     * @param player Player
     * @param result ItemStack crafted
     */
    
    public HyperCraftEvent(Player player, ItemStack result) {
        super(player);
        this.result = result;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public final HandlerList getHandlers() {
        return handlers;
    }
}
