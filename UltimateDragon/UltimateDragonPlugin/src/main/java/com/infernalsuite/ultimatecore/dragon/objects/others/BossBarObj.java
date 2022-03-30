package com.infernalsuite.ultimatecore.dragon.objects.others;

import com.infernalsuite.ultimatecore.dragon.objects.DragonGame;
import com.infernalsuite.ultimatecore.dragon.objects.EventPlayer;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IHyperDragon;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.Objects;

public class BossBarObj {
    private BossBar bossBar;
    private Integer task;
    private final DragonGame dragonGame;

    public BossBarObj(DragonGame hyperDragonEntity){
        this.dragonGame = hyperDragonEntity;
        start();
    }

    private void start(){
        task = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(dragonGame.getPlugin(), () -> {
            if (dragonGame.getEnderDragon() != null)
                sendBossBar();
        }, 20, 20);
    }

    private void sendBossBar(){
        if(bossBar != null) bossBar.removeAll();
        IHyperDragon hyperDragon = dragonGame.getHyperDragon();
        String displayName = hyperDragon.getDisplayName();
        double health = hyperDragon.getHealth();
        double maxHealth = hyperDragon.getMaxHealth();
        int remaining = dragonGame.getDragonEvent() != null && dragonGame.getDragonEvent().getCurrentEvent() != null ? dragonGame.getDragonEvent().getCurrentEvent().getRemainingTime() : 0;
        bossBar = Bukkit.createBossBar(
                StringUtils.color(dragonGame.getPlugin().getMessages().getMessage("bossBar")
                        .replace("%dragon_name%", displayName != null ? displayName : " ")
                        .replace("%max_health%", String.valueOf(maxHealth))
                        .replace("%remaining_time%", String.valueOf(remaining))
                        .replace("%health%", String.valueOf(health))), BarColor.PURPLE, BarStyle.SEGMENTED_10, BarFlag.DARKEN_SKY);
        dragonGame.getEventPlayers().stream()
                .map(EventPlayer::getPlayer)
                .filter(Objects::nonNull)
                .forEach(bossBar::addPlayer);
    }

    public void stop(){
        if(task != null) Bukkit.getScheduler().cancelTask(task);
        bossBar.removeAll();
    }
}

