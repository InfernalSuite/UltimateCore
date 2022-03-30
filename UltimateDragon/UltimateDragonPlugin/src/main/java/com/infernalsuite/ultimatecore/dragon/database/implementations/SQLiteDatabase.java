package com.infernalsuite.ultimatecore.dragon.database.implementations;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SQLiteDatabase extends SQLDatabase {

	public SQLiteDatabase(HyperDragons plugin, Credentials credentials) {
		super(plugin);
		this.plugin.getLogger().info("Using SQLite (local) database.");
		this.connect(credentials);
	}

	@Override
	public void createTables() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			execute("CREATE TABLE IF NOT EXISTS " + DRAGONS_TABLE_NAME + "(UUID varchar(36), record double, primary key (UUID))");
		});
	}

	@Override
	public void addIntoDragonsDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + DRAGONS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), 0);
	}
}