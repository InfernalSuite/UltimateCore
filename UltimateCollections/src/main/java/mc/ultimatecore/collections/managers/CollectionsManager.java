package mc.ultimatecore.collections.managers;

import mc.ultimatecore.collections.*;
import mc.ultimatecore.collections.api.events.*;
import mc.ultimatecore.collections.objects.*;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class CollectionsManager {

    private final HyperCollections plugin;
    private int task;
    public int playersQuantity;
    private final Map<UUID, PlayerCollection> collectionsCache = new HashMap<>();
    private final Set<UUID> _currentlyLoading = new HashSet<>();

    public CollectionsManager(HyperCollections plugin) {
        this.plugin = plugin;
    }

    public PlayerCollection createOrGetUser(UUID uuid) {
        return collectionsCache.computeIfAbsent(uuid, x -> new PlayerCollection(uuid));
    }

    public void removeCacheEntry(UUID uuid) {
        this.collectionsCache.remove(uuid);
    }

    public void purgeCache() {
        if(!this.collectionsCache.isEmpty()) {
            this.collectionsCache.values().forEach(collection -> removeCacheEntry(collection.getUuid()));
        }
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
        if(_currentlyLoading.contains(player.getUniqueId())) return;
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
        for (UUID uuid : collectionsCache.keySet()) {
            if(_currentlyLoading.contains(uuid)) return;

            this.plugin.getPluginDatabase().savePlayerCollections(Bukkit.getOfflinePlayer(uuid), collectionsCache.get(uuid));
        }
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
        if(_currentlyLoading.contains(player.getUniqueId())) return;
        _currentlyLoading.add(player.getUniqueId());
        this.plugin.sendDebug(String.format("Attempting to load Collections of player %s from database", player.getName()), DebugType.LOG);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                String strCollections = plugin.getPluginDatabase().getPlayerCollections(player);
                PlayerCollection playerCollection = strCollections != null ? plugin.getGson().fromString(strCollections) : new PlayerCollection(player.getUniqueId());
                collectionsCache.put(player.getUniqueId(), playerCollection);
                this.plugin.sendDebug(String.format("Loaded Collections of player %s from database", player.getName()), DebugType.LOG);
            } finally {
                _currentlyLoading.remove(player.getUniqueId());
            }
        }, plugin.getConfiguration().getSyncDelay());
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
                PlayerCollection playerCollection = collectionsCache.getOrDefault(uuid, null);
                if (playerCollection == null) {
                    String strSkills = plugin.getPluginDatabase().getPlayerCollections(offlinePlayer);
                    if (strSkills == null) continue;
                    fromSQL = true;
                    playerCollection = plugin.getGson().fromString(strSkills);
                }
                if (playerCollection.getXP(key) > plugin.getConfiguration().getXpToRank().doubleValue()) {
                    playerCollection.setRankPosition(key, rank);
                    rank++;
                    playersQuantity++;
                }
                if (fromSQL) plugin.getPluginDatabase().savePlayerCollections(offlinePlayer, playerCollection);
            }
        }
        this.plugin.sendDebug("Collections Top updated!", DebugType.LOG);
    }

    public void addXP(OfflinePlayer p, String key, int xp) {
        if (!p.isOnline() || xp <= 0) {
            return;
        }

        Player player = p.getPlayer();
        if (player == null) {
            return;
        }

        PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(player.getUniqueId());
        Collection collection = HyperCollections.getInstance().getCollections().getCollection(key);
        if (collection == null) {
            return;
        }

        if(playerCollection.getLevel(key) >= collection.getMaxLevel()) {
            playerCollection.addXP(key, xp);
            return;
        }


        double currentXP = playerCollection.getXP(key);
        if (currentXP == 0) {
            CollectionsUnlockEvent event = new CollectionsUnlockEvent(player, key);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }

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

    public void checkLevelUp(OfflinePlayer offlinePlayer, String key) {
        if (!offlinePlayer.isOnline()) {
            return;
        }

        Player player = offlinePlayer.getPlayer();
        if (player == null) {
            return;
        }
        Collection collection = plugin.getCollections().getCollection(key);
        PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(player.getUniqueId());
        int level = playerCollection.getLevel(key);
        int currentXP = playerCollection.getXP(key);
        Double maxXP = collection.getRequirement(level + 1);
        if (maxXP != null && currentXP >= maxXP) {
            new HyperSound(HyperCollections.getInstance().getConfiguration().getLevelUPSound(), 1, 1).play(player);
            playerCollection.addLevel(key, 1);
            Bukkit.getServer().getPluginManager().callEvent(new CollectionsLevelUPEvent(player, key, level + 1));
            levelUp(key, player, collection);
            checkLevelUp(offlinePlayer, key);
        }
    }

    private void levelUp(String key, Player player, Collection collection) {
        PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(player.getUniqueId());
        int level = playerCollection.getLevel(key);
        List<String> commands = collection.getCommands(level);
        if (commands != null) {
            for (String command : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
            }
        }

        List<String> messages = HyperCollections.getInstance().getMessages().getLevelUPMessage();
        if (messages == null) {
            return;
        }

        for (String line : messages) {
            if (!line.contains("%level_rewards%")) {
                player.sendMessage(Utils.color(line
                        .replaceAll("%current_level%", Utils.toRoman(level))
                        .replaceAll("%previous_level%", Utils.toRoman(level - 1))
                        .replaceAll("%collection_name%", collection.getName())
                        .replaceAll("%money_reward%", Utils.toRoman(0))));
                continue;
            }
            List<String> placeholders = collection.getRewards(level);
            if (placeholders == null) {
                continue;
            }
            for (String placeholderLine : placeholders) {
                player.sendMessage(Utils.color(placeholderLine
                        .replaceAll("%previous_level%", Utils.toRoman(level))
                        .replaceAll("%current_level%", Utils.toRoman(level + 1))
                        .replaceAll("%collection_name%", collection.getName())
                        .replaceAll("%money_reward%", Utils.toRoman(0))));
            }
        }
    }

}
