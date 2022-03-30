package com.infernalsuite.ultimatecore.pets.api;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.objects.Pet;
import com.infernalsuite.ultimatecore.pets.objects.PetData;

import java.util.UUID;

@RequiredArgsConstructor
public class HyperPetsAPIImpl implements HyperPetsAPI {

    private final HyperPets plugin;

    @Override
    public PetData getPetData(UUID uuid) {
        int petUUID = plugin.getUserManager().getUser(uuid).spawnedID;
        if(petUUID == -1) return null;
        return plugin.getPetsManager().getPetDataByID(petUUID);
    }

    @Override
    public boolean hasPetActive(UUID uuid) {
        int petUUID = plugin.getUserManager().getUser(uuid).spawnedID;
        return petUUID != -1;
    }

    @Override
    public Pet getPet(PetData petData) {
        return plugin.getPets().pets.get(petData.getPetName());
    }
}