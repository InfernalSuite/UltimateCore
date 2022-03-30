package com.infernalsuite.ultimatecore.dragon.objects.guardian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IGuardian;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

@AllArgsConstructor
@Getter
public class MythicGuardian implements IGuardian {
    private final String name;
    private final int level;

    @Override
    public Entity spawn(Location location) {
        return HyperDragons.getInstance().getAddonsManager().getMythicMobs().spawn(name, location, 1);
    }

    @Override
    public String getID() {
        return name;
    }
}
