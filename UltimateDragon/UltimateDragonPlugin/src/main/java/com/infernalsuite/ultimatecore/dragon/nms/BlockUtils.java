package com.infernalsuite.ultimatecore.dragon.nms;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockUtils {

    public static void setBlockData(Block block, boolean inUse) {
        try {
            if (block.getBlockData() instanceof org.bukkit.block.data.type.EndPortalFrame) {
                org.bukkit.block.data.type.EndPortalFrame endPortalFrame = (org.bukkit.block.data.type.EndPortalFrame) block.getBlockData();
                endPortalFrame.setEye(inUse);
                block.setBlockData(endPortalFrame);
            }
        } catch (NoClassDefFoundError ignored) {
        }
    }

    public static void setBlockData(Location location, boolean inUse) {
        Block block = location.getBlock();
        try {
            if (block.getBlockData() instanceof org.bukkit.block.data.type.EndPortalFrame) {
                org.bukkit.block.data.type.EndPortalFrame endPortalFrame = (org.bukkit.block.data.type.EndPortalFrame) block.getBlockData();
                endPortalFrame.setEye(inUse);
                block.setBlockData(endPortalFrame);
            }
        } catch (NoClassDefFoundError ignored) {
        }
    }
}
