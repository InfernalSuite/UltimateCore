package mc.ultimatecore.anvil.api.events;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.anvil.enums.AnvilState;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class AnvilUseEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final AnvilState runeState;

    private boolean isCancelled;

    /**
     * Called when player use special anvil.
     *
     * @param player Player
     * @param runeState AnvilState current anvil state
     * */
    public AnvilUseEvent(Player player, AnvilState runeState) {
        super(player);
        this.runeState = runeState;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    public final HandlerList getHandlers() {
        return handlers;
    }


}
