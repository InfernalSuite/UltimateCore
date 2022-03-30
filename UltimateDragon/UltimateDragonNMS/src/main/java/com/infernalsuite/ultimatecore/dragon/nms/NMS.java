package com.infernalsuite.ultimatecore.dragon.nms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;

public abstract class NMS {
    
    protected final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    ;
    
    public void setAFK(EnderDragon enderDragon) {
        enderDragon.setPhase(EnderDragon.Phase.HOVER);
    }
    
    public void setTarget(EnderDragon enderDragon, Entity entity) {
        try {
            Field b;
            Object eDR = enderDragon.getClass().getMethod("getHandle", new Class[0]).invoke(enderDragon);
            Object dcm = eDR.getClass().getMethod("getDragonControllerManager", new Class[0]).invoke(eDR);
            Field current = dcm.getClass().getDeclaredField("currentDragonController");
            Object phase = getNMSClass("DragonControllerLandingFly").getConstructor(new Class[]{getNMSClass("EntityEnderDragon")}).newInstance(eDR);
            if (version.equals("v1_13_R2"))
                b = phase.getClass().getDeclaredField("c");
            else
                b = phase.getClass().getDeclaredField("d");
            
            b.setAccessible(true);
            b.set(phase, getNMSClass("Vec3D").getConstructor(new Class[]{double.class, double.class, double.class}).newInstance(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()));
            b.setAccessible(false);
            current.setAccessible(true);
            current.set(dcm, phase);
            current.setAccessible(false);
        } catch (Exception ignored) {
        }
    }
    
    public abstract void move(EnderDragon enderDragon);
    
    public abstract Location getHeadLocation(EnderDragon enderDragon);
    
    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception var3) {
            return null;
        }
    }
}