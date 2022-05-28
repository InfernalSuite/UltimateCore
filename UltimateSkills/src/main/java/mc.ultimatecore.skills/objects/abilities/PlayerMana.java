package mc.ultimatecore.skills.objects.abilities;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.ManaSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class PlayerMana {
    private int taskID;
    private final UUID uuid;

    public PlayerMana(UUID uuid) {
        this.uuid = uuid;

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        ManaSettings manaSettings = HyperSkills.getInstance().getConfiguration().manaSettings;
        this.taskID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (!manageMana(manaSettings)) {
                stop();
            }
        }, 0L, manaSettings.getSecondAmount() * 20L);
    }

    private boolean manageMana(ManaSettings manaSettings) {
        if (!HyperSkills.getInstance().getConfiguration().manaSystem)
            return false;
        PlayerAbilities playerAbilities = HyperSkills.getInstance().getAbilitiesManager().getPlayerAbilities(uuid);
        double maxMana = HyperSkills.getInstance().getApi().getTotalAbility(uuid, Ability.Max_Intelligence);
        double currentMana = HyperSkills.getInstance().getApi().getTotalAbility(uuid, Ability.Intelligence);
        double amount = (manaSettings.getPercentagePerSecond() * maxMana) / 100;
        double newMana = currentMana + amount;
        if(currentMana > maxMana)
            playerAbilities.setAbility(Ability.Intelligence, maxMana);
        if(currentMana < maxMana)
            playerAbilities.setAbility(Ability.Intelligence, Math.min(newMana, maxMana));
        return true;
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
