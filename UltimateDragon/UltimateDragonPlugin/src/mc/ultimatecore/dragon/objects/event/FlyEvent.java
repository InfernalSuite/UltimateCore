package mc.ultimatecore.dragon.objects.event;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.implementations.IDragonEvent;
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
