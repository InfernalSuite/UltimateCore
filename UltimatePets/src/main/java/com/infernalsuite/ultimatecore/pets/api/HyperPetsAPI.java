package com.infernalsuite.ultimatecore.pets.api;

import com.infernalsuite.ultimatecore.pets.objects.Pet;
import com.infernalsuite.ultimatecore.pets.objects.PetData;

import java.util.UUID;

public interface HyperPetsAPI {

    /**
     * Method to get Player's petdata
     *
     * @param uuid UUID
     * @return return PetData
     */
    PetData getPetData(UUID uuid);

    /**
     * Method to know if a player has a pet active
     *
     * @param uuid UUID
     * @return return boolean
     */

    boolean hasPetActive(UUID uuid);

    /**
     * Method to get Pet instance
     *
     * @param petData PetData
     * @return return boolean
     */

    Pet getPet(PetData petData);
}
