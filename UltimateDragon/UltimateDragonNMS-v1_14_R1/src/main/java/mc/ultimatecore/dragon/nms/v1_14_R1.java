package mc.ultimatecore.dragon.nms;

import net.minecraft.server.v1_14_R1.DragonControllerPhase;
import net.minecraft.server.v1_14_R1.EntityComplexPart;
import net.minecraft.server.v1_14_R1.EntityEnderDragon;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEnderDragon;
import org.bukkit.entity.EnderDragon;

public class v1_14_R1 extends NMS {
    
    @Override
    public void move(EnderDragon enderDragon) {
        EntityEnderDragon entityEnderDragon = ((CraftEnderDragon) enderDragon).getHandle();
        entityEnderDragon.getDragonControllerManager().setControllerPhase(DragonControllerPhase.LANDING);
    }
    
    @Override
    public Location getHeadLocation(EnderDragon enderDragon) {
        EntityComplexPart part = ((CraftEnderDragon) enderDragon).getHandle().bA;
        return new Location(enderDragon.getWorld(), part.lastX, part.lastY, part.lastZ);
    }
}