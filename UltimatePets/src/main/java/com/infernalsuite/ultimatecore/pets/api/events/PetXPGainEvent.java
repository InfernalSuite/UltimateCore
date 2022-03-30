package com.infernalsuite.ultimatecore.pets.api.events;

import com.infernalsuite.ultimatecore.pets.objects.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PetXPGainEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Pet pet;

    private final int xpAmount;

    public PetXPGainEvent(Player player, Pet pet, int xpAmount) {
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

    public int getXpAmount() {
        return xpAmount;
    }
}