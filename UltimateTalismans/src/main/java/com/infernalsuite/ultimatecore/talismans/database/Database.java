package com.infernalsuite.ultimatecore.talismans.database;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.implementations.DatabaseImpl;
import org.bukkit.OfflinePlayer;

import java.util.List;

public abstract class Database extends DatabaseImpl {

	public Database(UltimatePlugin plugin) {
		super(plugin);
	}
	public abstract void setBagTalismans(OfflinePlayer player, List<String> talismans);

	public abstract String getBagTalismans(OfflinePlayer player);

	public abstract void addIntoTalismansDatabase(OfflinePlayer player);

}
