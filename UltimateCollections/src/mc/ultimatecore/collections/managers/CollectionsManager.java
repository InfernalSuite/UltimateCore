package mc.ultimatecore.collections.managers;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.api.events.CollectionsLevelUPEvent;
import mc.ultimatecore.collections.api.events.CollectionsUnlockEvent;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.objects.DebugType;
import mc.ultimatecore.collections.objects.PlayerCollections;
import mc.ultimatecore.collections.utils.HyperSound;
import mc.ultimatecore.collections.utils.Utils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class CollectionsManager {
    
    private final HyperCollections plugin;
    private final Map<UUID, PlayerCollections> collectionsCache = new HashMap<>();
    private int task;
    public int playersQuantity;
    
    public CollectionsManager(HyperCollections plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
        this.updateTop10();
    }
    
    public void stopUpdating() {
        this.plugin.sendDebug("Stopping updating Top - Collections", DebugType.LOG);
        Bukkit.getScheduler().cancelTask(task);
    }
    
    private void updateTop10() {
        task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(p -> savePlayerData(p, false, false));
            this.updateCollectionsTop();
        }, 1200L, 72000L);
    }
    
    public void savePlayerData(Player player, boolean removeFromCache, boolean async) {
        UUID uuid = player.getUniqueId();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                this.plugin.getPluginDatabase().savePlayerCollections(Bukkit.getOfflinePlayer(uuid), collectionsCache.get(uuid));
                
                if (removeFromCache) {
                    collectionsCache.remove(player.getUniqueId());
                }
                this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
            });
        } else {
            this.plugin.getPluginDatabase().savePlayerCollections(Bukkit.getOfflinePlayer(uuid), collectionsCache.get(uuid));
            if (removeFromCache) {
                collectionsCache.remove(player.getUniqueId());
            }
            this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
        }
    }
    
    public void savePlayerDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all player data", DebugType.LOG);
        for (UUID uuid : collectionsCache.keySet())
            this.plugin.getPluginDatabase().savePlayerCollections(Bukkit.getOfflinePlayer(uuid), collectionsCache.get(uuid));
        collectionsCache.clear();
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all player data to database - collections", DebugType.LOG);
    }
    
    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.plugin.getPluginDatabase().addIntoDatabase(player);
        });
    }
    
    private void loadPlayerDataOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
    }
    
    public void loadPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String strCollections = plugin.getPluginDatabase().getPlayerCollections(player);
            PlayerCollections playerCollections = strCollections != null ? plugin.getGson().fromString(strCollections) : new PlayerCollections();
            collectionsCache.put(player.getUniqueId(), playerCollections);
            this.plugin.sendDebug(String.format("Loaded Collections of player %s from database", player.getName()), DebugType.LOG);
        });
    }
    
    
    public Integer getLevel(OfflinePlayer p, String key) {
        return collectionsCache.getOrDefault(p.getUniqueId(), new PlayerCollections()).getLevel(key);
    }
    
    public Integer getXP(OfflinePlayer p, String key) {
        return collectionsCache.getOrDefault(p.getUniqueId(), new PlayerCollections()).getXP(key);
    }
    
    public void setLevel(OfflinePlayer p, String key, int level) {
        if (p.isOnline())
            collectionsCache.getOrDefault(p.getUniqueId(), new PlayerCollections()).setLevel(key, level);
    }
    
    public void setXP(OfflinePlayer p, String key, int xp) {
        if (p.isOnline()) {
            collectionsCache.getOrDefault(p.getUniqueId(), new PlayerCollections()).setXP(key, xp);
            checkLevelUp(p, key);
        }
    }
    
    public void addLevel(OfflinePlayer p, String key, int level) {
        if (p.isOnline())
            collectionsCache.getOrDefault(p.getUniqueId(), new PlayerCollections()).addLevel(key, level);
    }
    
    
    public PlayerCollections getPlayerCollections(OfflinePlayer p) {
        if (!p.isOnline()) {
            return new PlayerCollections();
        } else {
            return collectionsCache.getOrDefault(p.getUniqueId(), new PlayerCollections());
        }
    }
    
    public PlayerCollections getPlayerCollections(UUID uuid) {
        return collectionsCache.getOrDefault(uuid, new PlayerCollections());
    }
    
    
    private void updateCollectionsTop() {
        this.plugin.sendDebug("Starting updating Collections Top", DebugType.LOG);
        playersQuantity = 0;
        Set<UUID> uuids = plugin.getPluginDatabase().getAllPlayers();
        for (String key : plugin.getCollections().getCollections().keySet()) {
            int rank = 1;
            for (UUID uuid : uuids) {
                boolean fromSQL = false;
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                PlayerCollections playerCollections = collectionsCache.getOrDefault(uuid, null);
                if (playerCollections == null) {
                    String strSkills = plugin.getPluginDatabase().getPlayerCollections(offlinePlayer);
                    if (strSkills == null) continue;
                    fromSQL = true;
                    playerCollections = plugin.getGson().fromString(strSkills);
                }
                if (playerCollections.getXP(key) > plugin.getConfiguration().getXpToRank().doubleValue()) {
                    playerCollections.setRankPosition(key, rank);
                    rank++;
                    playersQuantity++;
                }
                if (fromSQL) plugin.getPluginDatabase().savePlayerCollections(offlinePlayer, playerCollections);
            }
        }
        this.plugin.sendDebug("Collections Top updated!", DebugType.LOG);
    }
    
    
    public void addXP(OfflinePlayer p, String key, int xp) {
        if (p.isOnline())
            if (xp > 0) {
                Player player = p.getPlayer();
                if (player == null) return;
                PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId());
                Collection collection = HyperCollections.getInstance().getCollections().getCollection(key);
                if (playerCollection.getLevel(key) < collection.getMaxLevel()) {
                    double currentXP = playerCollection.getXP(key);
                    if (currentXP == 0) {
                        CollectionsUnlockEvent event = new CollectionsUnlockEvent(player, key);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if (event.isCancelled())
                            return;
                        String name = collection.getName();
                        BaseComponent[] components = TextComponent.fromLegacyText(Utils.color(HyperCollections.getInstance().getMessages().getMessage("collectionUnlocked")
                                                                                                              .replace("%collection_name%", name)));
                        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/collections levelmenu " + key);
                        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.color(HyperCollections.getInstance().getMessages().getMessage("collectionUnlockedHoverMessage")
                                                                                                                                             .replace("%collection_name%", name))).create());
                        for (BaseComponent component : components) {
                            component.setClickEvent(clickEvent);
                            component.setHoverEvent(hoverEvent);
                        }
                        player.getPlayer().spigot().sendMessage(components);
                    }
                    playerCollection.addXP(key, xp);
                    checkLevelUp(player, key);
                }
            }
    }
    
    public void checkLevelUp(OfflinePlayer offlinePlayer, String key) {
        if (!offlinePlayer.isOnline()) return;
        Player player = offlinePlayer.getPlayer();
        if (player == null) return;
        Collection collection = plugin.getCollections().getCollection(key);
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId());
        int level = playerCollection.getLevel(key);
        int currentXP = playerCollection.getXP(key);
        Double maxXP = collection.getRequirement(level + 1);
        if (currentXP >= maxXP) {
            new HyperSound(HyperCollections.getInstance().getConfiguration().getLevelUPSound(), 1, 1).play(player);
            playerCollection.addLevel(key, 1);
            Bukkit.getServer().getPluginManager().callEvent(new CollectionsLevelUPEvent(player, key, level + 1));
            levelUp(key, player, collection);
        }
    }
    
    private void levelUp(String key, Player player, Collection collection) {
        PlayerCollections playerCollection = HyperCollections.getInstance().getCollectionsManager().getPlayerCollections(player.getUniqueId());
        int level = playerCollection.getLevel(key);
        List<String> commands = collection.getCommands(level);
        if (commands != null) {
            for (String command : commands)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
        }
        List<String> messages = HyperCollections.getInstance().getMessages().getLevelUPMessage();
        if (messages != null) {
            for (String line : messages) {
                if (line.contains("%level_rewards%")) {
                    List<String> placeholders = collection.getRewards(level);
                    if (placeholders != null) {
                        for (String placeholderLine : placeholders)
                            player.sendMessage(Utils.color(placeholderLine
                                    .replaceAll("%previous_level%", Utils.toRoman(level))
                                    .replaceAll("%current_level%", Utils.toRoman(level + 1))
                                    .replaceAll("%collection_name%", collection.getName())
                                    .replaceAll("%money_reward%", Utils.toRoman(0))));
                    }
                } else {
                    player.sendMessage(Utils.color(line
                            .replaceAll("%current_level%", Utils.toRoman(level))
                            .replaceAll("%previous_level%", Utils.toRoman(level - 1))
                            .replaceAll("%collection_name%", collection.getName())
                            .replaceAll("%money_reward%", Utils.toRoman(0))));
                    
                }
            }
        }
    }
}
