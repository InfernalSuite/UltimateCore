package com.infernalsuite.ultimatecore.dragon.nms;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerStrafe;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class v1_17_R1 extends NMS {
    
    @Override
    public void setTarget(EnderDragon enderDragon, Entity entity) {
        EntityEnderDragon entityEnderDragon = ((CraftEnderDragon) enderDragon).getHandle();
        entityEnderDragon.getDragonControllerManager().setControllerPhase(DragonControllerPhase.b);
        EntityLiving entityLiving = entity instanceof Player ? ((CraftPlayer) entity).getHandle() : ((CraftArmorStand) entity).getHandle();
        if (entityEnderDragon.getDragonControllerManager().a() instanceof DragonControllerStrafe) {
            DragonControllerStrafe charge = (DragonControllerStrafe) entityEnderDragon.getDragonControllerManager().a();
            charge.a(entityLiving);
        }
    }
    
    @Override
    public void move(EnderDragon enderDragon) {
        EntityEnderDragon entityEnderDragon = ((CraftEnderDragon) enderDragon).getHandle();
        entityEnderDragon.getDragonControllerManager().setControllerPhase(DragonControllerPhase.c);
    }
    
    @Override
    public Location getHeadLocation(EnderDragon enderDragon) {
        EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().e;
        return new Location(enderDragon.getWorld(), part.u, part.v, part.w);
    }
}