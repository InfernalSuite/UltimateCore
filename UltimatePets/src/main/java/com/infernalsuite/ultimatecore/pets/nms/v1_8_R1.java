package com.infernalsuite.ultimatecore.pets.nms;

import com.infernalsuite.ultimatecore.pets.objects.PlayerPet;
import com.infernalsuite.ultimatecore.pets.objects.implementations.PlayerPetLegacy;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.UUID;

public class v1_8_R1 implements NMS {
    @Override
    public boolean inverted() {
        return false;
    }

    @Override
    public Vector vectorName() {
        return new Vector(0.0D, 0.6D, 0.0D);
    }

    @Override
    public Vector vectorStand() {
        return new Vector(0.0D, 0.5D, 0.0D);
    }

    @Override
    public double addName() {
        return -0.7D;
    }

    @Override
    public double addStand() {
        return -1.2D;
    }

    @Override
    public EulerAngle rightArm() {
        return new EulerAngle(0.05207085800252904D, 0.8784299702830227D, 0.0042994904876079865D);
    }

    @Override
    public PlayerPet playerPet(UUID uuid, int petUUID) {
        return new PlayerPetLegacy(uuid, petUUID);
    }

}