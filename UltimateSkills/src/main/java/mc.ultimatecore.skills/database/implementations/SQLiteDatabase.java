package mc.ultimatecore.skills.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.database.SQLDatabase;
import mc.ultimatecore.skills.objects.PlayerSkills;
import mc.ultimatecore.skills.objects.abilities.PlayerAbilities;
import mc.ultimatecore.skills.objects.perks.PlayerPerks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SQLiteDatabase extends SQLDatabase {

	public SQLiteDatabase(HyperSkills plugin, Credentials credentials) {
		super(plugin);
		this.plugin.getLogger().info("Using SQLite (local) database.");
		this.connect(credentials);
	}

	@Override
	public void createTables() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			execute("CREATE TABLE IF NOT EXISTS " + SKILLS_TABLE_NAME + "(UUID varchar(36) primary key, Data LONGTEXT)");
			execute("CREATE TABLE IF NOT EXISTS " + PERKS_TABLE_NAME + "(UUID varchar(36) primary key, Data LONGTEXT)");
			execute("CREATE TABLE IF NOT EXISTS " + ABILITIES_TABLE_NAME +"(UUID varchar(36) primary key, Data LONGTEXT)");
		});
	}

	@Override
	public void addIntoSkillsDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + MySQLDatabase.SKILLS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), plugin.getGson().toStringSkills(new PlayerSkills()));
	}

	@Override
	public void addIntoAbilitiesDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + MySQLDatabase.ABILITIES_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), plugin.getGson().toStringAbilities(new PlayerAbilities()));
	}

	@Override
	public void addIntoPerksDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + MySQLDatabase.PERKS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), plugin.getGson().toStringPerks(new PlayerPerks()));
	}
}