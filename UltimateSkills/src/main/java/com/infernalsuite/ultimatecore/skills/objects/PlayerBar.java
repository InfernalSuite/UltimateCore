package com.infernalsuite.ultimatecore.skills.objects;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.api.HyperSkillsAPI;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class PlayerBar {
    private int taskID;

    private final UUID uuid;

    private int firstJoin;

    private int timer;

    public PlayerBar(Player player) {
        this.uuid = player.getUniqueId();
        this.firstJoin = 0;
        this.timer = 0;
        start(player);
    }

    private static String round(Double d) {
        return String.valueOf(Math.round(d));
    }

    public void start(Player player) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.taskID = scheduler.scheduleAsyncRepeatingTask(HyperSkills.getInstance(), () -> {
            if (!sendActionBar(player))
                Bukkit.getScheduler().cancelTask(taskID);
        }, 0L, 10L);
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(taskID);
    }

    private boolean sendActionBar(Player player) {
        if (player != null && player.isOnline()) {
            if(!HyperSkills.getInstance().getConfiguration().actionBar) return true;
            if(timer > 0) timer-=10;
            if(timer > 0) return true;
            sendNormalActionBar(player);
            return true;
        }
        return false;
    }

    private void sendNormalActionBar(Player player){
        if(firstJoin < 2){
            firstJoin++;
            return;
        }
        HyperSkillsAPI api = HyperSkills.getInstance().getApi();
        String actionBar;
        double defense = api.getTotalAbility(uuid, Ability.Defense);
        double maxHealth = api.getTotalAbility(uuid, Ability.Health);
        double currentHealth = Utils.getHealth(player);
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
        if(defense > 0)
            actionBar = StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("actionBarMessage_Defense")
                    .replace("%health%", round(currentHealth))
                    .replace("%maxhealth%", round(maxHealth))
                    .replace("%defense%", round(defense))
                    .replace("%max_intelligence%", round(api.getTotalAbility(uuid, Ability.Max_Intelligence)))
                    .replace("%intelligence%", round(api.getTotalAbility(uuid, Ability.Intelligence))));
        else
            actionBar = StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("actionBarMessage_NoDefense")
                    .replace("%health%", round(currentHealth))
                    .replace("%maxhealth%", round(maxHealth))
                    .replace("%defense%", round(defense))
                    .replace("%max_intelligence%", round(api.getTotalAbility(uuid, Ability.Max_Intelligence)))
                    .replace("%intelligence%", round(api.getTotalAbility(uuid, Ability.Intelligence))));
        Utils.sendActionBar(player, actionBar);
    }


    public void sendXPActionBar(Player player, SkillType skillType, double amount) {
        timer+=20;
        HyperSkillsAPI api = HyperSkills.getInstance().getApi();
        Skill skill = HyperSkills.getInstance().getSkills().getAllSkills().get(skillType);
        int level = HyperSkills.getInstance().getApi().getLevel(uuid, skillType);
        Double maxXP = HyperSkills.getInstance().getRequirements().getLevelRequirement(skillType, level);
        double maxHealth = api.getTotalAbility(uuid, Ability.Health);
        double currentHealth = Utils.getHealth(player);
        String message = StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("xpGainMessage")
                .replaceAll("%health%", round(currentHealth))
                .replaceAll("%maxhealth%", round(maxHealth))
                .replaceAll("%defense%", round(api.getTotalAbility(uuid, Ability.Defense)))
                .replaceAll("%displayname%", skill.getName())
                .replaceAll("%xp%", round(amount))
                .replaceAll("%current_xp%", round(api.getXP(uuid, skillType)))
                .replaceAll("%max_xp%", round(maxXP))
                .replaceAll("%max_intelligence%", round(api.getTotalAbility(uuid, Ability.Max_Intelligence)))
                .replaceAll("%intelligence%", round(api.getTotalAbility(uuid, Ability.Intelligence))));
        Utils.sendActionBar(player, message);
    }

}
