package mc.ultimatecore.dragon.addons;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class MythicMobsAddon {
    public Entity spawn(String name, Location location, int level){
        MythicMob mythicMob = MythicMobs.inst().getAPIHelper().getMythicMob(name);
        AbstractLocation abstractLocation = BukkitAdapter.adapt(location);
        ActiveMob activeMob = mythicMob.spawn(abstractLocation, level);
        return activeMob.getEntity().getBukkitEntity();
    }
}
