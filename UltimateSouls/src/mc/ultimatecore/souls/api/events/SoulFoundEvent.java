package mc.ultimatecore.souls.api.events;

import mc.ultimatecore.souls.objects.Soul;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SoulFoundEvent extends PlayerEvent {
    
    private static final HandlerList handlers = new HandlerList();
    
    private final Soul soul;
    
    /**
     * Called when player found a soul.
     *
     * @param player Player
     * @param soul   Soul return soul found
     */
    public SoulFoundEvent(Player player, Soul soul) {
        super(player);
        this.soul = soul;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public final HandlerList getHandlers() {
        return handlers;
    }
    
    public Soul getSoul() {
        return soul;
    }
}
