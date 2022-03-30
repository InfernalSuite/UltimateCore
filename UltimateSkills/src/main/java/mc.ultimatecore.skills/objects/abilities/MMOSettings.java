package mc.ultimatecore.skills.objects.abilities;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class MMOSettings {
    private int taskID;
    private int hungerID;
    private int statsID;
    private final UUID uuid;

    public MMOSettings(Player player) {
        this.uuid = player.getUniqueId();
        startStats(player);
        startHealth(player);
        startHunger(player);
    }

    private void startHealth(Player player) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.taskID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (!updateHealth(player))
                Bukkit.getScheduler().cancelTask(taskID);

        }, 0L, 0L);
    }

    private void startHunger(Player player) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.hungerID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (updateHunger(player))
                Bukkit.getScheduler().cancelTask(hungerID);
        }, 0L, 10L);
    }

    private void startStats(Player player) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.statsID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (!updateStats(player))
                Bukkit.getScheduler().cancelTask(statsID);
        }, 0L, 10L);
    }

    private boolean updateHealth(Player player) {
        if (player == null || !player.isOnline()) return false;
        if(!HyperSkills.getInstance().getAddonsManager().isMMOItems()){
              double scaled = player.getHealthScale();
              double health = HyperSkills.getInstance().getApi().getTotalAbility(uuid, Ability.Health);
              double newScale = Utils.getScale(health - 100);
              if(HyperSkills.getInstance().getConfiguration().scaledHealth && scaled != newScale)
                  player.setHealthScale(newScale);
        }
        return true;
    }


    private boolean updateHunger(Player player) {
        if(!HyperSkills.getInstance().getAddonsManager().isMMOItems()) return true;
        if (player == null || !player.isOnline()) return true;
        player.setFoodLevel(100);
        return false;
    }

    private boolean updateStats(Player player) {
        boolean isMMO = HyperSkills.getInstance().getAddonsManager().isMMOItems();
        if(!isMMO) return false;
        if (player == null || !player.isOnline()) return false;
        PlayerAbilities playerAbilities = HyperSkills.getInstance().getAbilitiesManager().getPlayerAbilities(uuid);
        for(Ability ability : Ability.values()){
            HyperSkills.getInstance().getAddonsManager().getMmoItems().updateStats(uuid, ability,
                    playerAbilities.getAbility(ability), "_NORMAL");
            HyperSkills.getInstance().getAddonsManager().getMmoItems().updateStats(uuid, ability, playerAbilities.getArmorAbility(ability), "_ARMOR");
        }
        return true;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
        Bukkit.getScheduler().cancelTask(statsID);
        Bukkit.getScheduler().cancelTask(hungerID);
    }
}
