package com.infernalsuite.ultimatecore.skills.database.implementations;

import com.infernalsuite.ultimatecore.helper.database.Credentials;
import com.infernalsuite.ultimatecore.skills.database.SQLDatabase;
import com.infernalsuite.ultimatecore.skills.objects.abilities.PlayerAbilities;
import com.infernalsuite.ultimatecore.skills.objects.perks.PlayerPerks;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.PlayerSkills;
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
		this.execute("INSERT OR IGNORE INTO " + SKILLS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), plugin.getGson().toStringSkills(new PlayerSkills()));
	}

	@Override
	public void addIntoAbilitiesDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + ABILITIES_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), plugin.getGson().toStringAbilities(new PlayerAbilities()));
	}

	@Override
	public void addIntoPerksDatabase(OfflinePlayer player) {
		this.execute("INSERT OR IGNORE INTO " + PERKS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), plugin.getGson().toStringPerks(new PlayerPerks()));
	}
}