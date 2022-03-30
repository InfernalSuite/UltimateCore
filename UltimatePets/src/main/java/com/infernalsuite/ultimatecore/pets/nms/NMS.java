package com.infernalsuite.ultimatecore.pets.nms;

import com.infernalsuite.ultimatecore.pets.objects.PlayerPet;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.UUID;

public interface NMS {

    boolean inverted();

    Vector vectorName();

    Vector vectorStand();

    double addName();

    double addStand();

    EulerAngle rightArm();

    PlayerPet playerPet(UUID uuid, int petUUID);

}
