package com.infernalsuite.ultimatecore.dragon.objects;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IHyperDragon;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.UCEnderDragon;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;

public class MythicDragon extends IHyperDragon {
    private final int level;
    public MythicDragon(String id, double chance, double xp, int level) {
        super(id, chance, xp);
        this.level = level;
    }

    @Override
    public UCEnderDragon getEnderDragon(Location location) {
        return new UCEnderDragon((EnderDragon) HyperDragons.getInstance().getAddonsManager().getMythicMobs().spawn(id, location, level));
    }

    @Override
    public String getDisplayName() {
        try {
            EnderDragon enderDragon = HyperDragons.getInstance().getDragonManager().getEnderDragon().get();
            if(enderDragon == null) return "";
            return enderDragon.getCustomName();
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public double getMaxHealth() {
        try {
            EnderDragon enderDragon = HyperDragons.getInstance().getDragonManager().getEnderDragon().get();
            if(enderDragon == null) return 0;
            return enderDragon.getMaxHealth();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public double getHealth() {
        try {
            EnderDragon enderDragon = HyperDragons.getInstance().getDragonManager().getEnderDragon().get();
            if(enderDragon == null) return 0;
            return enderDragon.getHealth();
        }catch (Exception e){
            return 0;
        }
    }
}
