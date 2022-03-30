package mc.ultimatecore.dragon.objects.event;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.guardian.RandomGuardian;
import mc.ultimatecore.dragon.objects.implementations.IDragonEvent;
import mc.ultimatecore.dragon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GuardianEvent extends IDragonEvent {
    private final int amount;
    private final Set<Entity> guardians;
    private final RandomGuardian guardian;

    public GuardianEvent(int duration, int repeat, double dragonSpeed, boolean freeze, int amount, RandomGuardian guardian) {
        super(duration, repeat, dragonSpeed, freeze);
        this.amount = amount;
        this.guardians = new HashSet<>();
        this.guardian = guardian;
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
                //Chequear El Evento
                if(time % repeat == 0)
                    manageDragon();
            time++;
        }, 0, 20);
    }

    @Override
    public void end() {
        super.end();
        guardians.stream().filter(Objects::nonNull).forEach(Entity::remove);
    }

    public void manageDragon(){
        if(!freeze && enderDragon.stillAlive()) Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> HyperDragons.getInstance().getNms().move(enderDragon.get()));
        spawn();
    }

    private void spawn(){
        if(!enderDragon.stillAlive()) return;
        Set<Location> locations = getRandomLocations(amount);
        locations.forEach(loc -> Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> guardians.add(guardian.getRandom().spawn(loc))));
    }

    private Set<Location> getRandomLocations(int amount){
        Set<Location> locations = new HashSet<>();
        Location spawn = HyperDragons.getInstance().getStructures().getDragonStructure().getSpawnLocation();
        for(int i = 0; locations.size()<amount; i++){
            Location location = spawn.clone();
            location.add(Utils.getRandom(1, 11), 0, Utils.getRandom(1, 11));
            location = location.getWorld().getHighestBlockAt(location).getLocation();
            location.add(0,1,0);
            locations.add(location);
            if(i == 100) break;
        }
        return locations;
    }
}
