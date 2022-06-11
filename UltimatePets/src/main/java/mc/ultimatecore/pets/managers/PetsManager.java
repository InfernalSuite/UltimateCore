package mc.ultimatecore.pets.managers;

import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.DebugType;
import mc.ultimatecore.pets.objects.PetData;
import mc.ultimatecore.pets.objects.Tier;
import mc.ultimatecore.skills.objects.perks.*;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetsManager {

    private final HyperPets plugin;
    private final Map<Integer, PetData> petsCache = new HashMap<>();

    public PetsManager(HyperPets plugin) {
        this.plugin = plugin;
        this.loadPetDataOnEnable();
    }

    public void disable() {
        savePetDataOnDisable();
    }


    public void savePetDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all pet data", DebugType.LOG);
        if (this.plugin.getPluginDatabase() != null) {
            List<Integer> petsIds = new ArrayList<>(petsCache.keySet());
            petsIds.forEach(id -> savePetData(id, false));
        }
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all pet data to database", DebugType.LOG);
    }

    public void loadPetDataOnEnable() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPluginDatabase().getPetsID().forEach(this::loadPetData));
    }

    public void savePetData(Integer id, boolean async) {
        if (async)
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> savePetData(id));
        else
            savePetData(id);
    }

    private void savePetData(Integer id) {
        if (id == null) return;
        PetData petData = petsCache.getOrDefault(id, null);
        if (petData == null) return;
        this.plugin.getPluginDatabase().setPetLevel(id, petData.getLevel());
        this.plugin.getPluginDatabase().setPetXP(id, petData.getXp());
        this.plugin.getPluginDatabase().setPetName(id, petData.getPetName());
        this.plugin.getPluginDatabase().setPetTier(id, petData.getTier().getName());
        petsCache.remove(id);
    }

    public void loadPetData(PetData petData) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.plugin.getPluginDatabase().addIntoPetsDatabase(petData);
            petsCache.put(petData.getPetUUID(), petData);
        });
    }

    public void loadPetData(int petUUID) {
        this.plugin.sendDebug(String.format("Attempting to load Data of Pet %s from database", petUUID), DebugType.LOG);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            PetData petData = new PetData();
            double xp = this.plugin.getPluginDatabase().getPetXP(petUUID);
            int level = this.plugin.getPluginDatabase().getPetLevel(petUUID);
            String name = this.plugin.getPluginDatabase().getPetName(petUUID);
            Tier tier = this.plugin.getPluginDatabase().getPetTier(petUUID);
            petData.setLevel(level);
            petData.setXp(xp);
            petData.setPetName(name);
            petData.setPetUUID(petUUID);
            petData.setTier(tier != null ? tier : plugin.getTiers().first());
            petsCache.put(petUUID, petData);
            if (plugin.getConfiguration().debug) {
                this.plugin.sendDebug(String.format("Loaded Data of Pet %s from database", petUUID), DebugType.LOG);
            }
        }, plugin.getConfiguration().syncDelay);
    }

    public void loadPetDataSync(int petUUID) {
        this.plugin.sendDebug(String.format("Attempting to load Data of Pet %s from database", petUUID), DebugType.LOG);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            PetData petData = new PetData();
            double xp = this.plugin.getPluginDatabase().getPetXP(petUUID);
            int level = this.plugin.getPluginDatabase().getPetLevel(petUUID);
            String name = this.plugin.getPluginDatabase().getPetName(petUUID);
            Tier tier = this.plugin.getPluginDatabase().getPetTier(petUUID);
            petData.setLevel(level);
            petData.setXp(xp);
            petData.setPetName(name);
            petData.setPetUUID(petUUID);
            petData.setTier(tier != null ? tier : plugin.getTiers().first());
            petsCache.put(petUUID, petData);
            if (plugin.getConfiguration().debug) {
                this.plugin.sendDebug(String.format("Loaded Data of Pet %s from database sync", petUUID), DebugType.LOG);
            }
        }, plugin.getConfiguration().syncDelay);

    }


    public Double getPetXP(int petUUID) {
        if (!petsCache.containsKey(petUUID)) {
            return this.plugin.getPluginDatabase().getPetXP(petUUID);
        } else {
            return petsCache.get(petUUID).getXp();
        }
    }

    public String getPetName(int petUUID) {
        if (!petsCache.containsKey(petUUID)) {
            return this.plugin.getPluginDatabase().getPetName(petUUID);
        } else {
            return petsCache.get(petUUID).getPetName();
        }
    }

    public int getPetLevel(int petUUID) {
        if (!petsCache.containsKey(petUUID)) {
            return this.plugin.getPluginDatabase().getPetLevel(petUUID);
        } else {
            return petsCache.get(petUUID).getLevel();
        }
    }


    public PetData getPetDataByID(int petUUID) {
        if (petsCache.containsKey(petUUID))
            return petsCache.get(petUUID);
        return null;
    }

    public PetData getPetDataByID(int petUUID, String petName) {
        if (petsCache.containsKey(petUUID))
            return petsCache.get(petUUID);
        return null;
    }

}
