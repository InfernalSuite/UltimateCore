package mc.ultimatecore.talismans.database;

import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.implementations.DatabaseImpl;
import org.bukkit.OfflinePlayer;

import java.util.List;

public abstract class Database extends DatabaseImpl {

	public Database(UltimatePlugin plugin) {
		super(plugin);
	}
	public abstract void setBagTalismans(OfflinePlayer player, List<String> talismans);

	public abstract String getBagTalismans(OfflinePlayer player);

	public abstract void addIntoTalismansDatabase(OfflinePlayer player);

}
