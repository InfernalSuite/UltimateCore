package com.infernalsuite.ultimatecore.pets.database;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.implementations.DatabaseImpl;
import com.infernalsuite.ultimatecore.pets.objects.PetData;
import com.infernalsuite.ultimatecore.pets.objects.Tier;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class Database extends DatabaseImpl {

	public Database(UltimatePlugin plugin) {
		super(plugin);
	}

	//PET METHODS
	public abstract CompletableFuture<Void> createTablesAsync();

	public abstract void addIntoPetsDatabase(PetData petData);

	public abstract void setPetName(Integer petUUID, String name);

	public abstract void setPetTier(Integer petUUID, String tier);

	public abstract void setPetLevel(Integer petUUID, int level);

	public abstract void setPetXP(Integer petUUID, Double xp);

	public abstract String getPetName(Integer petUUID);

	public abstract Tier getPetTier(Integer petUUID);

	public abstract Integer getPetLevel(Integer petUUID);

	public abstract Double getPetXP(Integer petUUID);

	//PLAYER METHODS

	public abstract void addIntoPlayerDatabase(OfflinePlayer offlinePlayer);

	public abstract void setInventoryPets(OfflinePlayer offlinePlayer, String inventoryPets);

	public abstract void setSpawnedID(OfflinePlayer offlinePlayer, Integer petUUID);

	public abstract String getInventoryPets(OfflinePlayer offlinePlayer);

	public abstract Integer getSpawnedID(OfflinePlayer offlinePlayer);

	public abstract List<Integer> getPetsID();
}
