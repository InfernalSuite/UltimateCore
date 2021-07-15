package mc.ultimatecore.pets.nms;

import mc.ultimatecore.pets.objects.PlayerPet;
import mc.ultimatecore.pets.objects.implementations.PlayerPetNoLegacy;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.UUID;

public class v1_14_R1 implements NMS {

    @Override
    public boolean inverted() {
        return true;
    }
    @Override
    public Vector vectorStand() {
        return new Vector(0.0D, 0.3D, 0.0D);
    }

    @Override
    public Vector vectorName() {
        return new Vector(0.0D, 0.6D, 0.0D);
    }

    @Override
    public double addStand() {
        return -0.3D;
    }

    @Override
    public double addName() {
        return -0.9D;
    }
    @Override
    public EulerAngle rightArm() {
        return new EulerAngle(2.361844120128839D, 2.330267551715D, 3.177584743089996D);
    }

    @Override
    public PlayerPet playerPet(UUID uuid, int petUUID) {
        return new PlayerPetNoLegacy(uuid, petUUID);
    }
}
