package com.infernalsuite.ultimatecore.dragon.nms;

import net.minecraft.server.v1_16_R3.EntityComplexPart;
import net.minecraft.server.v1_16_R3.EntityEnderDragon;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEnderDragon;
import org.bukkit.entity.EnderDragon;

public class v1_16_R3 extends NMS {
    
    @Override
    public void move(EnderDragon enderDragon) {
        EntityEnderDragon entityEnderDragon = ((CraftEnderDragon) enderDragon).getHandle();
        entityEnderDragon.movementTick();
    }
    
    @Override
    public Location getHeadLocation(EnderDragon enderDragon) {
        EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().bo;
        return new Location(enderDragon.getWorld(), part.lastX, part.lastY, part.lastZ);
    }
}