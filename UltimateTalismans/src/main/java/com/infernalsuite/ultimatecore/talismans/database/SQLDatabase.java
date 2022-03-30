package com.infernalsuite.ultimatecore.talismans.database;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class SQLDatabase extends Database {

	protected final String TALISMANS_TABLE_NAME = "Talismans";
	protected final String TALISMANS_UUID_COLNAME = "UUID";

	public SQLDatabase(HyperTalismans plugin) {
		super(plugin);
	}

	@Override
	public void createTables() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> execute("CREATE TABLE IF NOT EXISTS " + TALISMANS_TABLE_NAME + "(UUID varchar(36), talismans text, primary key (UUID))"));
	}

	@Override
	public String getBagTalismans(OfflinePlayer p) {
		try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + TALISMANS_TABLE_NAME + " WHERE " + TALISMANS_UUID_COLNAME + "=?")) {
			statement.setString(1, p.getUniqueId().toString());
			try (ResultSet set = statement.executeQuery()) {
				if (set.next())
					return set.getString("talismans");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setBagTalismans(OfflinePlayer p, List<String> talismans) {
		this.execute("UPDATE " + TALISMANS_TABLE_NAME + " SET talismans =? WHERE " + TALISMANS_UUID_COLNAME + "=?", talismans.toString(), p.getUniqueId().toString());
	}

	@Override
	public void addIntoTalismansDatabase(OfflinePlayer player) {
		this.execute("INSERT IGNORE INTO " + TALISMANS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), "");
	}

}
