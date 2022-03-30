package com.infernalsuite.ultimatecore.pets.configs;

import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.api.events.PetRegisterEvent;
import mc.ultimatecore.pets.objects.*;
import com.infernalsuite.ultimatecore.pets.objects.commands.PetCommands;
import com.infernalsuite.ultimatecore.pets.playerdata.User;
import com.infernalsuite.ultimatecore.pets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pets extends YAMLFile {

    public Map<String, Pet> pets;

    public Pets(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadDefaults();
    }


    private void loadDefaults() {
        pets = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("pets");
        if(section == null) return;
        for (String key : section.getKeys(false)){
            try {
                String displayName = getConfig().getString("pets." + key + ".displayName");
                String entityName = getConfig().getString("pets." + key + ".entityName");
                List<String> description = getConfig().getStringList("pets." + key + ".description");
                String texture = getConfig().getString("pets." + key + ".texture");
                PetCommands petCommands = new PetCommands(Utils.getCommands(getConfig(), "pets." + key + ".petCommands"));
                Pet pet = new Pet(displayName, entityName, description, texture, HyperPets.getInstance().getLevels().getPetLevels(key), petCommands);
                pets.put(key, pet);
            }catch (Exception e){
                e.printStackTrace();
                HyperPets.getInstance().sendDebug("Error Loading Pet "+key, DebugType.COLORED);
            }
        }
    }


    public Pet getPetByID(String petID){
        if(pets.containsKey(petID))
            return pets.get(petID);
        return null;
    }

    public ItemStack getPetItem(String petID, int petUUID, Tier tier){
        if(pets.containsKey(petID)){
            Pet pet = pets.get(petID);
            PetData petData = HyperPets.getInstance().getPetsManager().getPetDataByID(petUUID);
            if(petData == null) petData = new PetData(petUUID, petID, tier);
            Bukkit.getServer().getPluginManager().callEvent(new PetRegisterEvent(pet, petData));
            ItemStack itemStack = Utils.makeItemHidden(HyperPets.getInstance().getConfiguration().petItems.get(tier), Utils.getPetItemPlaceholders(petData), pet);
            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setInteger("petUUID", petUUID);
            nbtItem.setString("petName", petID);
            nbtItem.setInteger("petLevel", 1);
            nbtItem.setInteger("petXP", 0);
            nbtItem.setString("petTier", tier.getName());
            return nbtItem.getItem();
        }
        return null;
    }

    public void managePet(Player player, String petName, int petUUID){
        User user = HyperPets.getInstance().getUserManager().getUser(player);
        Pet pet = HyperPets.getInstance().getPets().getPetByID(petName);
        if(pet == null) return;
        PlayerPet petManager = user.getPlayerPet();
        //playerPets.getPetManager() != null && playerPets.getPetManager().getPetUUID() == petUUID
        if(petManager != null && petManager.getPetData().getPetName().equals(petName)) {
            if(petManager.getPetData().getPetUUID() == petUUID){
                petManager.removePet(false);
                player.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("petDeactivated").replace("%name%", pet.getDisplayName())));
            }else{
                player.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("alreadyActivated").replace("%name%", pet.getDisplayName())));
            }
        }else{
            if(petManager == null){
                user.setPlayerPet(player.getUniqueId(), petUUID).createPet();
                player.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("petActivated").replace("%name%", pet.getDisplayName())));
            }else{
                player.sendMessage(Utils.color(HyperPets.getInstance().getMessages().getMessage("alreadyActivated").replace("%name%", pet.getDisplayName())));
            }
        }
    }

}
