package mc.ultimatecore.farm.nms;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class v1_8_R3 implements NMS {
    
    @Override
    public void sendParticle(Location location) {
        location.getWorld().spigot().playEffect(location, Effect.LAVADRIP);
    }
    
    @Override
    public void sendParticle(Location firstPoint, Location secondPoint, double space, String particle, Color color) {
        World world = firstPoint.getWorld();
        Validate.isTrue(secondPoint.getWorld().equals(world), "Lines cannot be in different worlds!");
        double distance = firstPoint.distance(secondPoint);
        Vector p1 = firstPoint.toVector();
        Vector p2 = secondPoint.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double covered = 0;
        for (; covered < distance; p1.add(vector)) {
            double x = p1.getX();
            double y = p1.getY();
            double z = p1.getZ();
            Location loc = new Location(world, x, y, z);
            if (particle.equals("COLOURED_DUST")) {
                float r = (float) 122 / 255;
                float g = (float) 255 / 255;
                float b = (float) 0 / 255;
                world.spigot().playEffect(loc, Effect.COLOURED_DUST, 0, 1, r, g, b, 1, 0, 30);
            } else {
                world.spigot().playEffect(loc, Effect.valueOf(particle));
            }
            covered += space;
        }
    }
    
    @Override
    public void setBlockData(Block bl, byte data) {
        bl.setData(data);
    }
}