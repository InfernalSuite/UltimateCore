package mc.ultimatecore.dragon.nms;

import net.minecraft.server.v1_12_R1.EntityComplexPart;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEnderDragon;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;

public class v1_12_R1 extends NMS {
    
    @Override
    public void setTarget(EnderDragon enderDragon, Entity entity) {
        try {
            Object dcm = enderDragon.getClass().getMethod("getDragonControllerManager", new Class[0]).invoke(enderDragon);
            Field current = dcm.getClass().getDeclaredField("currentDragonController");
            Object phase = getNMSClass("DragonControllerLandingFly").getConstructor(new Class[]{getNMSClass("EntityEnderDragon")}).newInstance(enderDragon);
            Field b = phase.getClass().getDeclaredField("c");
            b.setAccessible(true);
            b.set(phase, getNMSClass("Vec3D").getConstructor(new Class[]{double.class, double.class, double.class}).newInstance(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()));
            b.setAccessible(false);
            current.setAccessible(true);
            current.set(dcm, phase);
            current.setAccessible(false);
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
        EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().bw;
        return new Location(enderDragon.getWorld(), part.lastX, part.lastY, part.lastZ);
    }
}