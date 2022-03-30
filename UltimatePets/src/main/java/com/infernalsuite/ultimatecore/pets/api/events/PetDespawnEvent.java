package com.infernalsuite.ultimatecore.pets.api.events;

import com.infernalsuite.ultimatecore.pets.objects.Pet;
import com.infernalsuite.ultimatecore.pets.objects.PetData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PetDespawnEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Pet pet;

    private final PetData petData;

    public PetDespawnEvent(Player player, Pet pet, PetData petData) {
        super(player);
        this.pet = pet;
        this.petData = petData;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public Pet getPet() {
        return pet;
    }

    public PetData getPetData() {
        return petData;
    }
}
