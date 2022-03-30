package mc.ultimatecore.talismans.managers;

import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.api.events.PlayerEnterEvent;
import mc.ultimatecore.talismans.gui.TalismanBagGUI;
import mc.ultimatecore.talismans.objects.BagTalismans;
import mc.ultimatecore.talismans.objects.DebugType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final HyperTalismans plugin;
    private final Map<UUID, BagTalismans> bagCache = new HashMap<>();
    private final Map<UUID, TalismanBagGUI> gui = new HashMap<>();

    public UserManager(HyperTalismans plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
    }

    public void disable() {
        savePlayerDataOnDisable();
    }

    public void savePlayerData(Player player, boolean removeFromCache, boolean async) {
        UUID uuid = player.getUniqueId();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                BagTalismans bagTalismans = this.bagCache.get(uuid);
                if (bagCache.containsKey(uuid))
                    this.plugin.getPluginDatabase().setBagTalismans(Bukkit.getOfflinePlayer(uuid), bagTalismans == null ? new ArrayList<>() : bagCache.get(uuid).getTalismans());
                if (removeFromCache)
                    bagCache.remove(player.getUniqueId());
                this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
            });
        } else {
            if (bagCache.containsKey(uuid))
                this.plugin.getPluginDatabase().setBagTalismans(Bukkit.getOfflinePlayer(uuid), bagCache.get(uuid).getTalismans());
            if (removeFromCache) {
                bagCache.remove(player.getUniqueId());
            }
            this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
        }
    }

    private void savePlayerDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all player data", DebugType.LOG);
        for (UUID uuid : bagCache.keySet())
            if (bagCache.containsKey(uuid))
                this.plugin.getPluginDatabase().setBagTalismans(Bukkit.getOfflinePlayer(uuid), bagCache.get(uuid).getTalismans());
        bagCache.clear();
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all player data to database - talismans", DebugType.LOG);
    }

    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPluginDatabase().addIntoTalismansDatabase(player));
    }

    private void loadPlayerDataOnEnable() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void loadPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            BagTalismans bagTalismans = new BagTalismans(player.getUniqueId());
            String talismans = this.plugin.getPluginDatabase().getBagTalismans(player);
            if (talismans != null)
                bagTalismans.setTalismans(talismans);
            bagCache.put(player.getUniqueId(), bagTalismans);
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().callEvent(new PlayerEnterEvent(player)));
            this.plugin.sendDebug(String.format("Loaded talismans of player %s from database", player.getName()), DebugType.LOG);
        });
    }


    public BagTalismans getBagTalismans(UUID uuid) {
        return bagCache.getOrDefault(uuid, new BagTalismans(uuid));
    }

    public TalismanBagGUI getGUI(UUID uuid) {
        if (!gui.containsKey(uuid)) gui.put(uuid, new TalismanBagGUI(uuid, this.plugin));
        return gui.get(uuid);
    }
}
