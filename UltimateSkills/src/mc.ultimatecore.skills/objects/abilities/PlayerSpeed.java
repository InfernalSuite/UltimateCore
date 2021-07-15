package mc.ultimatecore.skills.objects.abilities;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerSpeed {
    private int taskID;
    private float last;

    public PlayerSpeed(Player player) {
        this.last = 0;
        start(player);
    }

    private void start(Player player) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.taskID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (!updateSpeed(player))
                Bukkit.getScheduler().cancelTask(taskID);
        }, 0L, 20L);
    }

    private boolean updateSpeed(Player player) {
        if (player == null || !player.isOnline())
            return false;
        float newSpeed = Utils.calculateSpeed(HyperSkills.getInstance().getApi().getTotalAbility(player.getUniqueId(), Ability.Speed));
        if(newSpeed != last)
            Bukkit.getScheduler().runTask(HyperSkills.getInstance(), () -> player.setWalkSpeed(newSpeed));
        this.last = newSpeed;
        return true;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}