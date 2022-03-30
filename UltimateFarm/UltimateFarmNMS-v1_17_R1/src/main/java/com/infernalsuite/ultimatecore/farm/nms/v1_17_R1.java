package com.infernalsuite.ultimatecore.farm.nms;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

public class v1_17_R1 implements NMS {
    
    @Override
    public void sendParticle(Location point1, Location point2, double space, String particle, Color color) {
        World world = point1.getWorld();
        Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double covered = 0;
        for (; covered < distance; p1.add(vector)) {
            if (particle.equals("REDSTONE")) {
                Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
                world.spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(), 1, dustOptions);
            } else {
                world.spawnParticle(Particle.valueOf(particle), p1.getX(), p1.getY(), p1.getZ(), (int) space);
            }
            
            covered += space;
        }
    }
    
    @Override
    public void setBlockData(Block block, BlockData data) {
        block.setBlockData(data);
    }
    
    @Override
    public void setBlockData(Block block, byte data) {
        if (block.getBlockData() instanceof Ageable) {
            Ageable crop = (Ageable) block.getBlockData();
            crop.setAge(crop.getMaximumAge());
            block.setBlockData(crop);
        }
    }
}