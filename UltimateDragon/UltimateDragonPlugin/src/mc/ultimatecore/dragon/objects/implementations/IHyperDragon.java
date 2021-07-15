package mc.ultimatecore.dragon.objects.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
public abstract class IHyperDragon {
    protected final String id;
    protected final double chance;
    protected final double xp;
    public abstract UCEnderDragon getEnderDragon(Location location);
    public abstract String getDisplayName();
    public abstract double getMaxHealth();
    public abstract double getHealth();
}
