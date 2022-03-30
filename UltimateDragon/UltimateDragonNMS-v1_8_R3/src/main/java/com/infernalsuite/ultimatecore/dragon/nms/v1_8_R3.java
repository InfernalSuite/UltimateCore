package com.infernalsuite.ultimatecore.dragon.nms;

import net.minecraft.server.v1_8_R3.EntityComplexPart;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;

public class v1_8_R3 extends NMS {
    
    @Override
    public void setTarget(EnderDragon enderDragon, Entity ent) {
        try {
            Object eDR = enderDragon.getClass().getMethod("getHandle", new Class[0]).invoke(enderDragon);
            Object entity = ent.getClass().getMethod("getHandle", new Class[0]).invoke(ent);
            Field target = eDR.getClass().getDeclaredField("target");
            target.setAccessible(true);
            target.set(eDR, entity);
        } catch (Exception ignored) {
        }
    }
    
    @Override
    public void move(EnderDragon enderDragon) {
        try {
            Object eDR = enderDragon.getClass().getMethod("getHandle", new Class[0]).invoke(enderDragon);
            eDR.getClass().getMethod("m", new Class[0]).invoke(enderDragon);
        } catch (Exception ignored) {
        }
    }
    
    @Override
    public Location getHeadLocation(EnderDragon enderDragon) {
        EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().bn;
        return new Location(enderDragon.getWorld(), part.lastX, part.lastY, part.lastZ);
    }
}
