package mc.ultimatecore.pets.objects;

import mc.ultimatecore.pets.HyperPets;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public abstract class PlayerPet {

    protected ArmorStand petEntity;

    protected ArmorStand nameEntity;

    protected final UUID petOwner;

    protected Integer taskID;

    protected int count;

    protected Vector last;

    protected final PetData petData;

    public PlayerPet(UUID petOwner, int petUUID) {
        this.petOwner = petOwner;
        this.petData = HyperPets.getInstance().getPetsManager().getPetDataByID(petUUID);
    }

    public abstract void spawnStand();

    public abstract void removeStand();

    public abstract void reloadPet();

    public abstract void createPet();

    public abstract void removePet(boolean serverClose);

    public abstract PetData getPetData();

    public abstract Pet getPet();

    public abstract Player getPlayer();

}
