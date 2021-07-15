package mc.ultimatecore.farm.skullcreator;


import mc.ultimatecore.farm.HyperRegions;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HeadDatabase implements Listener {
    static HeadDatabaseAPI headApi = new HeadDatabaseAPI();

    public HeadDatabase() {
        Bukkit.getPluginManager().registerEvents(this, HyperRegions.getInstance());
    }

    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent e) {
        headApi = new HeadDatabaseAPI();
        HyperRegions.getInstance().getLogger().info("HeadDatabase detected.");
    }
}

