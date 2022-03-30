package mc.ultimatecore.farm.nms;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public interface NMS {
    
    void sendParticle(Location firstPoint, Location secondPoint, double distance, String particle, Color color);
    default void sendParticle(Location location) {}
    
    void setBlockData(Block block, byte data);
    default void setBlockData(Block block, BlockData data) {}
}