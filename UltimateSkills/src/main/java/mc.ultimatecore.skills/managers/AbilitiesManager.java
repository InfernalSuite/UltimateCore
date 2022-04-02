package mc.ultimatecore.skills.managers;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.api.events.PlayerEnterEvent;
import mc.ultimatecore.skills.objects.DebugType;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.abilities.PlayerAbilities;
import mc.ultimatecore.skills.utils.ItemStatsUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilitiesManager {
    private final HyperSkills plugin;
    private final Map<UUID, PlayerAbilities> abilitiesCache = new HashMap<>();

    public AbilitiesManager(HyperSkills plugin) {
        this.plugin = plugin;
        this.loadPlayerDataOnEnable();
    }

    public void disable(){
        savePlayerDataOnDisable();
    }

    public void savePlayerData(Player player, boolean removeFromCache, boolean async) {
        UUID uuid = player.getUniqueId();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                this.plugin.getPluginDatabase().savePlayerAbilities(Bukkit.getOfflinePlayer(uuid), abilitiesCache.get(uuid));
                if (removeFromCache) abilitiesCache.remove(player.getUniqueId());

                this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
            });
        } else {
            this.plugin.getPluginDatabase().savePlayerAbilities(Bukkit.getOfflinePlayer(uuid), abilitiesCache.get(uuid));
            if (removeFromCache) abilitiesCache.remove(player.getUniqueId());
            this.plugin.sendDebug(String.format("Saved data of player %s to database.", player.getName()), DebugType.LOG);
        }
    }

    private void savePlayerDataOnDisable() {
        this.plugin.sendDebug("[PLUGIN DISABLE] Saving all player data", DebugType.LOG);
        for (UUID uuid : abilitiesCache.keySet())
            this.plugin.getPluginDatabase().savePlayerAbilities(Bukkit.getOfflinePlayer(uuid), abilitiesCache.get(uuid));
        abilitiesCache.clear();
        this.plugin.sendDebug("[PLUGIN DISABLE] Saved all player data to database - abilities", DebugType.LOG);
    }

    public void addIntoTable(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getPluginDatabase().addIntoAbilitiesDatabase(player));
    }

    private void loadPlayerDataOnEnable() {
        Bukkit.getServer().getOnlinePlayers().forEach(this::loadPlayerData);
    }

    public void loadPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String strAbilities = plugin.getPluginDatabase().getPlayerAbilities(player);
            PlayerAbilities playerAbilities = (strAbilities != null && !strAbilities.equals("null")) ? plugin.getGson().fromStringAbilities(strAbilities) : new PlayerAbilities();
            for(Ability ability : Ability.values()) {
                double quantity = playerAbilities.getAbility(ability);
                if (ability == Ability.Max_Intelligence && quantity < plugin.getConfiguration().initialMana)
                    quantity = plugin.getConfiguration().initialMana;
                if (ability == Ability.Defense && quantity < plugin.getConfiguration().initialDefense)
                    quantity = plugin.getConfiguration().initialDefense;
                playerAbilities.setAbility(ability, quantity);
            }
            playerAbilities.setAbility(Ability.Intelligence, playerAbilities.getAbility(Ability.Max_Intelligence));
            abilitiesCache.put(player.getUniqueId(), playerAbilities);
            ItemStatsUtils.setupArmorAbilities(player);
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().callEvent(new PlayerEnterEvent(player)));
            this.plugin.sendDebug(String.format("Loaded abilities of player %s from database", player.getName()), DebugType.LOG);
        });
    }

    public Double getAbility(UUID uuid, Ability key) {
        return abilitiesCache.getOrDefault(uuid, new PlayerAbilities()).getAbility(key);
    }


    public Double getAbility(OfflinePlayer p, Ability key) {
        return abilitiesCache.getOrDefault(p.getUniqueId(), new PlayerAbilities()).getAbility(key);
    }

    public void setAbility(OfflinePlayer p, Ability key, double xp) {
        if (p.isOnline())
            abilitiesCache.getOrDefault(p.getUniqueId(), new PlayerAbilities()).setAbility(key, xp);
    }

    public void addAbility(OfflinePlayer p, Ability key, double ability) {
        if (p.isOnline())
            abilitiesCache.getOrDefault(p.getUniqueId(), new PlayerAbilities()).addAbility(key, ability);
    }

    public void resetData(UUID uuid) {
        if (abilitiesCache.containsKey(uuid))
            abilitiesCache.put(uuid, new PlayerAbilities());
    }

    public PlayerAbilities getPlayerAbilities(OfflinePlayer p) {
        if (!p.isOnline()) {
            return new PlayerAbilities();
        } else {
            return abilitiesCache.getOrDefault(p.getUniqueId(), new PlayerAbilities());
        }
    }

    public PlayerAbilities getPlayerAbilities(UUID uuid) {
        return abilitiesCache.getOrDefault(uuid, new PlayerAbilities());
    }

}
