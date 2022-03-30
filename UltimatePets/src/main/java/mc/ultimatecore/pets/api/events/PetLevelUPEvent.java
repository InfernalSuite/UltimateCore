package mc.ultimatecore.pets.api.events;

import mc.ultimatecore.pets.objects.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PetLevelUPEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Pet pet;

    private final int petLevel;

    public PetLevelUPEvent(Player player, Pet pet, int petLevel) {
        super(player);
        this.pet = pet;
        this.petLevel = petLevel;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public Pet getPet() {
        return pet;
    }

    public int getPetLevel() {
        return petLevel;
    }
}
