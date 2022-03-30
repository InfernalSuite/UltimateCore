package mc.ultimatecore.talismans.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.database.SQLDatabase;
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