package mc.ultimatecore.dragon.managers;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.DragonPlayer;
import mc.ultimatecore.dragon.objects.others.DebugType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class DatabaseManager {
    private final HyperDragons plugin;
    private final Map<UUID, DragonPlayer> dragonCache = new HashMap<>();
    private final Set<UUID> _currentlyLoading = new HashSet<>();
    public DatabaseManager(HyperDragons plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
    }

    public void disable(){
        savePlayerDataOnDisable();
    }

    public void savePlayerData(Player player, boolean removeFromCache, boolean async) {
        if(_currentlyLoading.contains(player.getUniqueId())) return;
        UUID uuid = player.getUniqueId();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                if(dragonCache.containsKey(uuid))
                    this.plugin.getDatabase().setRecord(Bukkit.getOfflinePlayer(uuid), dragonCache.get(uuid).getRecord());
                if (removeFromCache)
                    dragonCache.remove(player.getUniqueId());
                this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
            });
        } else {
            if(dragonCache.containsKey(uuid))
                this.plugin.getDatabase().setRecord(Bukkit.getOfflinePlayer(uuid), dragonCache.get(uuid).getRecord());
            if (removeFromCache)
                dragonCache.remove(player.getUniqueId());
            this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
        }
    }

    private void savePlayerDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all player data", DebugType.LOG);
        for (UUID uuid : dragonCache.keySet()) {
            if(_currentlyLoading.contains(uuid)) return;
            if(dragonCache.containsKey(uuid)) {
                this.plugin.getDatabase().setRecord(Bukkit.getOfflinePlayer(uuid), dragonCache.get(uuid).getRecord());
            }
        }

        dragonCache.clear();
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all player data to database - record", DebugType.LOG);
    }

    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.plugin.getDatabase().addIntoDragonsDatabase(player);
        });
    }

    private void loadPlayerDataOnEnable() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void loadPlayerData(Player player) {
        if(_currentlyLoading.contains(player.getUniqueId())) return;
        _currentlyLoading.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                DragonPlayer dragonPlayer = new DragonPlayer(player.getUniqueId());
                double record = this.plugin.getDatabase().getRecord(player);
                dragonPlayer.setRecord(record);
                dragonCache.put(player.getUniqueId(), dragonPlayer);
                this.plugin.sendDebug(String.format("Loaded Record of player %s from database", player.getName()), DebugType.LOG);
            } finally {
                _currentlyLoading.remove(player.getUniqueId());
            }
        });
    }

    public Optional<DragonPlayer> getDragonPlayer(UUID uuid) {
        if(uuid == null) return Optional.empty();
        return dragonCache.values().stream()
                .filter(dragonPlayer -> dragonPlayer != null && dragonPlayer.getUuid() != null)
                .filter(dragonPlayer -> dragonPlayer.getUuid().equals(uuid))
                .findFirst();
    }

    public double getRecord(OfflinePlayer p) {
        if (!p.isOnline()) {
            return this.plugin.getDatabase().getRecord(p);
        } else {
            return dragonCache.getOrDefault(p.getUniqueId(), new DragonPlayer(p.getUniqueId())).getRecord();
        }
    }

    public void setRecord(OfflinePlayer p, double value) {
        if (!p.isOnline())
            this.plugin.getDatabase().setRecord(p, value);
        else
            dragonCache.getOrDefault(p.getUniqueId(), new DragonPlayer(p.getUniqueId())).setRecord(value);
    }

}
