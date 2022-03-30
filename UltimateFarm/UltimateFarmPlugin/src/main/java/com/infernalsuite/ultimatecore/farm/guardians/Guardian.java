package com.infernalsuite.ultimatecore.farm.guardians;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.infernalsuite.ultimatecore.farm.particle.Particle;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public abstract class Guardian {
    protected final String name;
    protected final Particle particle = new Particle();
    protected final Location location;
    protected ArmorStand armorStand;

    @Getter @Setter
    protected boolean enabled;

    public abstract EulerAngle getAngle(Location var1);

    public abstract Vector getVector(Location var1, Location var2);

    public abstract void remove();

    protected void removeCopies() {
        Arrays.stream(location.getChunk().getEntities())
                .filter(en -> en instanceof ArmorStand && en.getName() != null && en.getName().contains("guardian_" + name))
                .collect(Collectors.toList())
                .forEach(Entity::remove);
    }

    public abstract void makeLineParticle(Location blockLocation);

    public abstract void enable();

    public boolean isNotEnabled(){
        return !enabled;
    }
}
