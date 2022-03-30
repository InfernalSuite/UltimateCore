package mc.ultimatecore.skills.objects;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class DataReset {
    private int taskID;

    private int timer;

    private final UUID uuid;

    private final UUID executor;

    public DataReset(UUID uuid, UUID executor) {
        this.uuid = uuid;
        this.executor = executor;
        this.timer = 0;
        start();
    }

    private void start() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.taskID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (!checkData())
                stop(false);
        }, 0L, 20L);
    }

    private boolean checkData(){
        if(timer <= 9) {
            timer++;
            return true;
        }else{
            return false;
        }
    }

    public void stop(boolean done){
        if(!done) {
            Utils.sendOfflineMessage(executor, HyperSkills.getInstance().getMessages().getMessage("dataResetExpired"));
            HyperSkills.getInstance().getResetDataManager().removePlayer(uuid, false);
        }
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
