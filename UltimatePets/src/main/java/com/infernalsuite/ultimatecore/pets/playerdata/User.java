package com.infernalsuite.ultimatecore.pets.playerdata;


import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.gui.PetGUI;
import com.infernalsuite.ultimatecore.pets.objects.PlayerPet;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {

    public UUID uuid;

    private String name;

    public int spawnedID;

    private List<Integer> inventoryPets;

    public User(final UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        this.spawnedID = -1;
        this.inventoryPets = new ArrayList<>();
    }

    public String getInventoryPetsStr(){
        return inventoryPets.toString().replace("]", "").replace("[", "").replace(" ", "");
    }

    public void setInventoryPetsStr(String str){
        List<Integer> set = new ArrayList<>();
        String[] inventorySplit = str.split(",");
        for(String id : inventorySplit){
            try{
                set.add(Integer.parseInt(id));
            }catch (Exception ignored){
            }
        }
        this.inventoryPets = set;
    }

    /*
    NO SQL
     */
    public PetGUI petGUI;
    private PlayerPet playerPet;
    private boolean hidePets;

    public PetGUI getPetGUI() {
        if(petGUI == null) petGUI = new PetGUI(this);
        return petGUI;
    }

    public PlayerPet getPlayerPet() {
        return playerPet;
    }

    public PlayerPet setPlayerPet(UUID uuid, int petUUID) {
        if(playerPet == null) playerPet = HyperPets.getInstance().getNms().playerPet(uuid, petUUID);
        return playerPet;
    }

    public void removePetManager(){
        playerPet = null;
    }

    public boolean isHidePets(){
        if(petGUI == null) return false;
        return petGUI.isHideMode();
    }


}
