package com.infernalsuite.ultimatecore.dragon.objects.event;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IDragonEvent;
import org.bukkit.Bukkit;

public class FlyEvent extends IDragonEvent {

    public FlyEvent(int duration, int repeat, double dragonSpeed, boolean freeze) {
        super(duration, repeat, dragonSpeed, freeze);
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
            time++;
        }, 0, 20);
    }

    protected void manageDragon(){
        move();
    }
}
