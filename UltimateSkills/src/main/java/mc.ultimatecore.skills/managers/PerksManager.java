package mc.ultimatecore.skills.managers;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.DebugType;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.objects.perks.PlayerPerks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PerksManager {
    private final HyperSkills plugin;
    public final Map<UUID, PlayerPerks> perksCache = new HashMap<>();
    private final Set<UUID> _currentlyLoading = new HashSet<>();

    public PerksManager(HyperSkills plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
    }

    public void disable(){
        savePlayerDataOnDisable();
    }

    public void savePlayerData(Player player, boolean removeFromCache, boolean async) {
        if (_currentlyLoading.contains(player.getUniqueId())) {
            return;
        }
        UUID uuid = player.getUniqueId();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                this.plugin.getPluginDatabase().savePlayerPerks(Bukkit.getOfflinePlayer(uuid), perksCache.get(uuid));
                if (removeFromCache) perksCache.remove(player.getUniqueId());
                this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
            });
        } else {
            this.plugin.getPluginDatabase().savePlayerPerks(Bukkit.getOfflinePlayer(uuid), perksCache.get(uuid));
            if (removeFromCache) perksCache.remove(player.getUniqueId());
            this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
        }
    }

    private void savePlayerDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all player data", DebugType.LOG);
        for (UUID uuid : perksCache.keySet()) {
            if (_currentlyLoading.contains(uuid)) {
                continue;
            }
            this.plugin.getPluginDatabase().savePlayerPerks(Bukkit.getOfflinePlayer(uuid), perksCache.get(uuid));
        }
        perksCache.clear();
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all player data to database - perks", DebugType.LOG);
    }

    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.plugin.getPluginDatabase().addIntoPerksDatabase(player);
        });
    }

    private void loadPlayerDataOnEnable() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void loadPlayerData(Player player) {
        if (_currentlyLoading.contains(player.getUniqueId())) {
            return;
        }
        _currentlyLoading.add(player.getUniqueId());
        this.plugin.sendDebug(String.format("Attempting to load perks of player %s from database", player.getName()), DebugType.LOG);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                String strPerks = plugin.getPluginDatabase().getPlayerPerks(player);
                PlayerPerks playerPerks = strPerks != null ? plugin.getGson().fromStringPerks(strPerks) : new PlayerPerks();
                perksCache.put(player.getUniqueId(), playerPerks);
                this.plugin.sendDebug(String.format("Loaded perks of player %s from database", player.getName()), DebugType.LOG);
            }  catch(Exception e) {
                System.out.println("[DEBUG LoadPlayerData Perks] " + e.getMessage());
            } finally {
                _currentlyLoading.remove(player.getUniqueId());
            }
        }, plugin.getConfiguration().syncDelay);
    }

    public Double getPerk(UUID uuid, Perk key) {
        return perksCache.getOrDefault(uuid, new PlayerPerks()).getPerk(key);
    }


    public Double getPerk(OfflinePlayer p, Perk key) {
        return perksCache.getOrDefault(p.getUniqueId(), new PlayerPerks()).getPerk(key);
    }

    public void resetData(UUID uuid) {
        if (perksCache.containsKey(uuid))
            perksCache.put(uuid, new PlayerPerks());
    }

    public void setPerk(OfflinePlayer p, Perk key, double xp) {
        if (p.isOnline())
            perksCache.getOrDefault(p.getUniqueId(), new PlayerPerks()).setPerk(key, xp);
    }

    public void addPerk(OfflinePlayer p, Perk key, double perk) {
        if (p.isOnline())
            perksCache.getOrDefault(p.getUniqueId(), new PlayerPerks()).addPerk(key, perk);
    }
    public PlayerPerks getPlayerPerks(OfflinePlayer p) {
        return perksCache.getOrDefault(p.getUniqueId(), new PlayerPerks());
    }

    public PlayerPerks getPlayerPerks(UUID uuid) {
        return perksCache.getOrDefault(uuid, new PlayerPerks());
    }

}
