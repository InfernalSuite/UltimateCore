package mc.ultimatecore.pets.api.events;

import lombok.Getter;
import mc.ultimatecore.pets.objects.Pet;
import mc.ultimatecore.pets.objects.PetData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class PetRegisterEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Pet pet;

    private final PetData petData;

    public PetRegisterEvent(Pet pet, PetData petData) {
        this.pet = pet;
        this.petData = petData;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
