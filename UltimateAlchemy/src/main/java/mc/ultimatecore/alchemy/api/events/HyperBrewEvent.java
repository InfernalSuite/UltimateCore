package mc.ultimatecore.alchemy.api.events;

import lombok.Getter;
import mc.ultimatecore.alchemy.objects.AlchemyRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class HyperBrewEvent extends PlayerEvent {
    
    private static final HandlerList handlers = new HandlerList();
    
    @Getter
    private final AlchemyRecipe alchemyRecipe;
    
    /**
     * Called when player brew an item in the hyperbrewing stand table.
     *
     * @param player        Player
     * @param alchemyRecipe AlchemyRecipe recipe brewed
     */
    
    public HyperBrewEvent(Player player, AlchemyRecipe alchemyRecipe) {
        super(player);
        this.alchemyRecipe = alchemyRecipe;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public final HandlerList getHandlers() {
        return handlers;
    }
    
}
