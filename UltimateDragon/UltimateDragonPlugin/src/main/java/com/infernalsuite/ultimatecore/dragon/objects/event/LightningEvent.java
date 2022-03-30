package com.infernalsuite.ultimatecore.dragon.objects.event;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IDragonEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Set;

public class LightningEvent extends IDragonEvent {
    private final double damage;

    public LightningEvent(int duration, int repeat, double dragonSpeed, boolean freeze, double damage) {
        super(duration, repeat, dragonSpeed, freeze);
        this.damage = damage;
    }
    @Override
    public void start() {
        time = 0;
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(HyperDragons.getInstance(), () -> {
            checkFreeze();
            //Cancelling Event
            if(time >= duration)
                end();
            else
                //Check Event
                if(time % repeat == 0)
                    manageDragon();
            time+=1;
        }, 0, 20);
    }

    protected void manageDragon(){
        move();
        makeLightnings();
    }

    private void makeLightnings(){
        Set<Player> players = HyperDragons.getInstance().getDragonManager().getEventPlayers();
        if(players.isEmpty()) return;
        players.forEach(this::makeLightning);
    }


    private void makeLightning(Player player) {
        Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> {
            if(enderDragon != null && player != null && player.isOnline()) {
                LightningStrike ls = player.getLocation().getWorld().strikeLightning(player.getLocation());
                ls.setMetadata("LightningStrike", new FixedMetadataValue(HyperDragons.getInstance(), this.damage));
            }
        });
    }
}
