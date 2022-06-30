package mc.ultimatecore.pets.api.events;

import mc.ultimatecore.pets.objects.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PetXPGainEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Pet pet;

    private final double xpAmount;

    public PetXPGainEvent(Player player, Pet pet, double xpAmount) {
        super(player);
        this.pet = pet;
        this.xpAmount = xpAmount;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public Pet getPet() {
        return pet;
    }

    public double getXpAmount() {
        return xpAmount;
    }
}