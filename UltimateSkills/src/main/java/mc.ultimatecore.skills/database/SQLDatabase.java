package mc.ultimatecore.skills.database;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.database.implementations.MySQLDatabase;
import mc.ultimatecore.skills.objects.PlayerSkills;
import mc.ultimatecore.skills.objects.abilities.PlayerAbilities;
import mc.ultimatecore.skills.objects.perks.PlayerPerks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class SQLDatabase extends Database {

	public static final String SKILLS_TABLE_NAME = "Skills";
	public static final String SKILLS_UUID_COLNAME = "UUID";

	public static final String ABILITIES_TABLE_NAME = "Abilities";
	public static final String ABILITIES_UUID_COLNAME = "UUID";

	public static final String PERKS_TABLE_NAME = "Perks";
	public static final String PERKS_UUID_COLNAME = "UUID";

	public static final String[] ALL_TABLES = new String[]{
			SKILLS_TABLE_NAME,
	};

	protected HyperSkills plugin;

	public SQLDatabase(HyperSkills plugin) {
		super(plugin);
		this.plugin = plugin;
	}


	public void createTables() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			execute("CREATE TABLE IF NOT EXISTS " + SKILLS_TABLE_NAME + "(UUID varchar(36) NOT NULL UNIQUE, Data LONGTEXT)");
			execute("CREATE TABLE IF NOT EXISTS " + PERKS_TABLE_NAME + "(UUID varchar(36) NOT NULL UNIQUE, Data LONGTEXT)");
			execute("CREATE TABLE IF NOT EXISTS " + ABILITIES_TABLE_NAME + "(UUID varchar(36) NOT NULL UNIQUE, Data LONGTEXT)");
		});
	}

	@Override
	public void addIntoSkillsDatabase(OfflinePlayer player) {
		this.execute("INSERT IGNORE INTO " + MySQLDatabase.SKILLS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), null);
	}

	@Override
	public void addIntoPerksDatabase(OfflinePlayer player) {
		this.execute("INSERT IGNORE INTO " + MySQLDatabase.PERKS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), null);
	}

	@Override
	public void addIntoAbilitiesDatabase(OfflinePlayer player) {
		this.execute("INSERT IGNORE INTO " + MySQLDatabase.ABILITIES_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), null);
	}

	@Override
	public Set<UUID> getAllPlayers() {
		Set<UUID> uuids = new HashSet<>();
		try (Connection con = this.hikari.getConnection(); ResultSet set = con.prepareStatement("SELECT "+MySQLDatabase.SKILLS_UUID_COLNAME+" FROM " + MySQLDatabase.SKILLS_TABLE_NAME).executeQuery()) {
			while (set.next())
				uuids.add(UUID.fromString(set.getString(MySQLDatabase.SKILLS_UUID_COLNAME)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uuids;
	}

	@Override
	public String getPlayerAbilities(OfflinePlayer p) {
		try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + MySQLDatabase.ABILITIES_TABLE_NAME + " WHERE " + MySQLDatabase.ABILITIES_UUID_COLNAME + "=?")) {
			statement.setString(1, p.getUniqueId().toString());
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getString("Data");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void savePlayerAbilities(OfflinePlayer p, PlayerAbilities playerAbilities) {
		this.execute("UPDATE " + MySQLDatabase.ABILITIES_TABLE_NAME + " SET Data =? WHERE " + MySQLDatabase.ABILITIES_UUID_COLNAME + "=?", plugin.getGson().toStringAbilities(playerAbilities), p.getUniqueId().toString());
	}

	@Override
	public String getPlayerPerks(OfflinePlayer p) {
		try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + MySQLDatabase.PERKS_TABLE_NAME + " WHERE " + MySQLDatabase.PERKS_UUID_COLNAME + "=?")) {
			statement.setString(1, p.getUniqueId().toString());
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getString("Data");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void savePlayerPerks(OfflinePlayer p, PlayerPerks playerPerks) {
		this.execute("UPDATE " + MySQLDatabase.PERKS_TABLE_NAME + " SET Data =? WHERE " + MySQLDatabase.PERKS_UUID_COLNAME + "=?", plugin.getGson().toStringPerks(playerPerks), p.getUniqueId().toString());
	}

	@Override
	public String getPlayerSkills(OfflinePlayer p) {
		try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + MySQLDatabase.SKILLS_TABLE_NAME + " WHERE " + MySQLDatabase.SKILLS_UUID_COLNAME + "=?")) {
			statement.setString(1, p.getUniqueId().toString());
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getString("Data");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void savePlayerSkills(OfflinePlayer p, PlayerSkills playerSkills) {
		this.execute("UPDATE " + MySQLDatabase.SKILLS_TABLE_NAME + " SET Data =? WHERE " + MySQLDatabase.SKILLS_UUID_COLNAME + "=?", plugin.getGson().toStringSkills(playerSkills), p.getUniqueId().toString());
	}
}
