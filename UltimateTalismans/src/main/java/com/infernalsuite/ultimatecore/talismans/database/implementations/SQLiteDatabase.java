package com.infernalsuite.ultimatecore.talismans.database.implementations;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SQLiteDatabase extends SQLDatabase {

	public SQLiteDatabase(HyperTalismans plugin, Credentials credentials) {
		super(plugin);
		this.plugin.getLogger().info("Using SQLite (local) database.");
		this.connect(credentials);
	}

	@Override
	public void createTables() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> execute("CREATE TABLE IF NOT EXISTS " + TALISMANS_TABLE_NAME + "(UUID varchar(36), talismans text, primary key (UUID))"));
	}

	@Override
	public void addIntoTalismansDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + TALISMANS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), "");
	}
}