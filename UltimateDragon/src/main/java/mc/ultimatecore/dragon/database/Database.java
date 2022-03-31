package mc.ultimatecore.dragon.database;

import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.implementations.DatabaseImpl;
import org.bukkit.OfflinePlayer;

public abstract class Database extends DatabaseImpl {

	public Database(UltimatePlugin plugin) {
		super(plugin);
	}

	public abstract void setRecord(OfflinePlayer player, double record);

	public abstract double getRecord(OfflinePlayer player);

	public abstract void addIntoDragonsDatabase(OfflinePlayer player);

}
