package com.infernalsuite.ultimatecore.skills.database;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.implementations.DatabaseImpl;
import com.infernalsuite.ultimatecore.skills.objects.PlayerSkills;
import com.infernalsuite.ultimatecore.skills.objects.abilities.PlayerAbilities;
import com.infernalsuite.ultimatecore.skills.objects.perks.PlayerPerks;
import org.bukkit.OfflinePlayer;

import java.util.Set;
import java.util.UUID;

public abstract class Database extends DatabaseImpl {

	public Database(UltimatePlugin plugin) {
		super(plugin);
	}

	public abstract Set<UUID> getAllPlayers();

	public abstract void addIntoSkillsDatabase(OfflinePlayer player);

	public abstract void addIntoPerksDatabase(OfflinePlayer player);

	public abstract void addIntoAbilitiesDatabase(OfflinePlayer player);

	public abstract String getPlayerSkills(OfflinePlayer player);

	public abstract void savePlayerSkills(OfflinePlayer player, PlayerSkills playerSkills);

	public abstract String getPlayerAbilities(OfflinePlayer player);

	public abstract void savePlayerAbilities(OfflinePlayer player, PlayerAbilities playerAbilities);

	public abstract String getPlayerPerks(OfflinePlayer player);

	public abstract void savePlayerPerks(OfflinePlayer player, PlayerPerks playerPerks);

}
