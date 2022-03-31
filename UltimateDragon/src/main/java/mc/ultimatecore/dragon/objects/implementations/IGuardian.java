package mc.ultimatecore.dragon.objects.implementations;


import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface IGuardian {
    Entity spawn(Location location);
    String getID();
}
