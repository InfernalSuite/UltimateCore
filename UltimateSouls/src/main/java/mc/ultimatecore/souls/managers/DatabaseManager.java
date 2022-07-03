package mc.ultimatecore.souls.managers;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.objects.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class DatabaseManager {

    private final HyperSouls plugin;
    private final Set<UUID> _currentlyLoading = new HashSet<>();

    private final Map<UUID, PlayerSouls> playerCache = new HashMap<>();

    public DatabaseManager(HyperSouls plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
    }

    public void disable() {
        savePlayerDataOnDisable();
    }

    public void savePlayerData(Player player) {
        if(_currentlyLoading.contains(player.getUniqueId())) return;
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (playerCache.containsKey(uuid)) {
                PlayerSouls user = playerCache.get(uuid);
                this.plugin.getPluginDatabase().setSouls(Bukkit.getOfflinePlayer(uuid), user.getSoulsStr());
                this.plugin.getPluginDatabase().setExchanged(Bukkit.getOfflinePlayer(uuid), user.getSoulsExchanged());
                playerCache.remove(player.getUniqueId());
                this.plugin.getLogger().info(String.format("Saved data of player %s to database.", player.getName()));
            }
        });
    }

    private void savePlayerDataOnDisable() {

        this.plugin.getLogger().info("[PLUGIN DISABLE] Saving all player data");
        for (UUID uuid : playerCache.keySet()) {

            if(_currentlyLoading.contains(uuid)) return;

            this.plugin.getPluginDatabase().setSouls(Bukkit.getOfflinePlayer(uuid), playerCache.get(uuid).getSoulsStr());
            this.plugin.getPluginDatabase().setExchanged(Bukkit.getOfflinePlayer(uuid), playerCache.get(uuid).getSoulsExchanged());
        }
        playerCache.clear();
        this.plugin.getLogger().info("[PLUGIN DISABLE] Saved all player data to database - souls");
    }

    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPluginDatabase().addIntoPlayerDatabase(player));
    }

    private void loadPlayerDataOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void loadPlayerData(Player player) {
        if(_currentlyLoading.contains(player.getUniqueId())) return;
        _currentlyLoading.add(player.getUniqueId());
        this.plugin.sendDebug(String.format("Attempting to load Souls of player %s from database", player.getName()), DebugType.LOG);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                PlayerSouls user = new PlayerSouls();
                String playerSouls = this.plugin.getPluginDatabase().getPlayerSouls(player);
                int exchanged = this.plugin.getPluginDatabase().getExchanged(player);
                user.setSoulsStr(playerSouls);
                user.setSoulsExchanged(exchanged);
                playerCache.put(player.getUniqueId(), user);
                this.plugin.getLogger().info(String.format("Loaded Souls of player %s from database", player.getName()));
            } finally {
                _currentlyLoading.remove(player.getUniqueId());
            }
        }, plugin.getConfiguration().syncDelay);
    }

    public PlayerSouls getSoulsData(OfflinePlayer p) {
        return playerCache.getOrDefault(p.getUniqueId(), new PlayerSouls());
    }

    public PlayerSouls getSoulsData(UUID uuid) {
        return playerCache.getOrDefault(uuid, new PlayerSouls());
    }
}
