package mc.ultimatecore.pets.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.database.SQLDatabase;
import mc.ultimatecore.pets.objects.PetData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.CompletableFuture;

public class SQLiteDatabase extends SQLDatabase {

	public SQLiteDatabase(HyperPets plugin, Credentials credentials) {
		super(plugin, "Players");
		this.plugin.getLogger().info("Using SQLite (local) database.");
		this.connect(credentials);
	}

	@Override
	public CompletableFuture<Void> createTablesAsync() {
		CompletableFuture<Void> future = new CompletableFuture<>();
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			execute("CREATE TABLE IF NOT EXISTS " + PETS_TABLE_NAME + "(UUID bigint, pet_name varchar(10), pet_level int, pet_xp double, pet_tier varchar(10), primary key (UUID))");
			execute("CREATE TABLE IF NOT EXISTS " + playersTable + "(UUID varchar(36) NOT NULL UNIQUE, spawned_id bigint, inventory_pets varchar(50), primary key (UUID))");
			future.complete(null);
		});
		return future;
	}

	@Override
	public void addIntoPetsDatabase(PetData petData) {
		this.execute("INSERT OR IGNORE INTO " + PETS_TABLE_NAME + " VALUES(?,?,?,?,?)", petData.getPetUUID().toString(), petData.getPetName(), petData.getLevel(), petData.getXp(), petData.getTier().getName());
	}

	@Override
	public void addIntoPlayerDatabase(OfflinePlayer offlinePlayer) {
		this.execute("INSERT OR IGNORE INTO " + playersTable + " VALUES(?,?,?)", offlinePlayer.getUniqueId().toString(), -1, "");
	}


}
