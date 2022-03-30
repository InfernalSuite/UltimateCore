package com.infernalsuite.ultimatecore.dragon.database;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class SQLDatabase extends Database {

	public static final String DRAGONS_TABLE_NAME = "Dragons";
	public static final String DRAGONS_UUID_COLNAME = "UUID";

	public static final String[] ALL_TABLES = new String[]{
			DRAGONS_TABLE_NAME,
	};

	public SQLDatabase(HyperDragons plugin) {
		super(plugin);
	}

	public void createTables() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			execute("CREATE TABLE IF NOT EXISTS " + DRAGONS_TABLE_NAME + "(UUID varchar(36), record double, primary key (UUID))");
		});
	}


	@Override
	public double getRecord(OfflinePlayer p) {
		try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + DRAGONS_TABLE_NAME + " WHERE " + DRAGONS_UUID_COLNAME + "=?")) {
			statement.setString(1, p.getUniqueId().toString());
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getDouble("record");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0d;
	}

	@Override
	public void setRecord(OfflinePlayer p, double record) {
		this.execute("UPDATE " + DRAGONS_TABLE_NAME + " SET record =? WHERE " + DRAGONS_UUID_COLNAME + "=?", record, p.getUniqueId().toString());
	}

	@Override
	public void addIntoDragonsDatabase(OfflinePlayer player) {
		this.execute("INSERT IGNORE INTO " + DRAGONS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), 0);
	}

}
