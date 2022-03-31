package mc.ultimatecore.dragon.objects;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.implementations.IHyperDragon;
import mc.ultimatecore.dragon.objects.implementations.UCEnderDragon;
import mc.ultimatecore.dragon.utils.Placeholder;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;

import java.util.Arrays;

public class HyperDragon extends IHyperDragon {
    private final String displayName;
    private final double health;
    public HyperDragon(String id, double health, String displayName, double chance, double xp) {
        super(id, chance, xp);
        this.displayName = displayName;
        this.health = health;
    }

    @Override
    public UCEnderDragon getEnderDragon(Location location){
        World w = location.getWorld();
        UCEnderDragon enderDragon = new UCEnderDragon(w.spawn(location, EnderDragon.class));
        enderDragon.setName(StringUtils.color(StringUtils.processMultiplePlaceholders(displayName, Arrays.asList(
                new Placeholder("max_health", String.valueOf(health)),
                new Placeholder("health", String.valueOf(Utils.getHealth(enderDragon.getHealth(), health)))
        ))));
        return enderDragon;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public double getMaxHealth() {
        try {
            EnderDragon enderDragon = HyperDragons.getInstance().getDragonManager().getEnderDragon().get();
            if(enderDragon == null) return 0;
            return Utils.getHealth(enderDragon.getHealth(), health);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public double getHealth() {
        return health;
    }
}
