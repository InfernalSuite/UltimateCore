package mc.ultimatecore.pets.managers;

import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.DebugType;
import mc.ultimatecore.pets.objects.PetData;
import mc.ultimatecore.pets.playerdata.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    private final HyperPets plugin;
    private final HashMap<UUID, User> playerCache = new HashMap<>();

    public UserManager(HyperPets plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
    }

    public void disable(){
        savePlayerDataOnDisable();
    }

    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if(playerCache.containsKey(uuid)){
                User user = playerCache.get(uuid);
                this.plugin.getPluginDatabase().setInventoryPets(Bukkit.getOfflinePlayer(uuid), user.getInventoryPetsStr());
                this.plugin.getPluginDatabase().setSpawnedID(Bukkit.getOfflinePlayer(uuid), user.getSpawnedID());
                playerCache.remove(player.getUniqueId());
                this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
            }
        });
    }

    private void savePlayerDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all player data", DebugType.LOG);
        for (UUID uuid : playerCache.keySet()){
            this.plugin.getPluginDatabase().setInventoryPets(Bukkit.getOfflinePlayer(uuid), playerCache.get(uuid).getInventoryPetsStr());
            this.plugin.getPluginDatabase().setSpawnedID(Bukkit.getOfflinePlayer(uuid), playerCache.get(uuid).getSpawnedID());
        }
        playerCache.clear();
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all player data to database - pets", DebugType.LOG);
    }

    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.plugin.getPluginDatabase().addIntoPlayerDatabase(player);
        });
    }

    private void loadPlayerDataOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void loadPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            User user = new User(player.getUniqueId());
            int spawnedID = this.plugin.getPluginDatabase().getSpawnedID(player);
            String inventoryPets = this.plugin.getPluginDatabase().getInventoryPets(player);
            user.setSpawnedID(spawnedID);
            user.setInventoryPetsStr(inventoryPets);
            playerCache.put(player.getUniqueId(), user);
            if (user.getSpawnedID() != -1)
                HyperPets.getInstance().getPetsManager().loadPetDataSync(user.getSpawnedID());
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (user.getSpawnedID() != -1) {
                    PetData petData = HyperPets.getInstance().getPetsManager().getPetDataByID(user.getSpawnedID());
                    user.setPlayerPet(player.getUniqueId(), petData.getPetUUID()).createPet();
                }
            });
            this.plugin.sendDebug(String.format("Loaded Pets of player %s from database", player.getName()), DebugType.LOG);
        });
    }

    public User getUser(OfflinePlayer p) {
        return playerCache.getOrDefault(p.getUniqueId(), new User(p.getUniqueId()));
    }

    public User getUser(UUID uuid) {
        return playerCache.getOrDefault(uuid, new User(uuid));
    }
}