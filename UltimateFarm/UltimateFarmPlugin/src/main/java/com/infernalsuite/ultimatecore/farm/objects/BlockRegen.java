package com.infernalsuite.ultimatecore.farm.objects;

import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.objects.blocks.BlockAfter;
import com.infernalsuite.ultimatecore.farm.objects.blocks.ChanceBlock;
import com.infernalsuite.ultimatecore.farm.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class BlockRegen {

    private final Location location;
    private final HyperRegion farmRegion;
    private final String regionName;
    private final BlockAfter blockAfter;
    private final HyperRegions plugin;
    private int taskID;
    private int time;

    public BlockRegen(Location location, HyperRegion farmRegion, String regionName, BlockAfter blockAfter) {
        this.location = location;
        this.regionName = regionName;
        this.blockAfter = blockAfter;
        this.farmRegion = farmRegion;
        this.plugin = HyperRegions.getInstance();
    }

    public void startRegen() {
        this.time = farmRegion.getRegenTime().getRegenTime();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        plugin.getFarmManager().addCache(this);
        //Setting Block While Regen
        BlockUtils.setBlockSync(location, farmRegion.getBlockWhileRegen(), 0);
        //Starting Countdown
        this.taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
            if (!blockIsRegen())
                scheduler.cancelTask(taskID);
        }, 0L, 20L);
    }

    private boolean blockIsRegen() {
        if (time > 0) {
            time--;
            return true;
        } else {
            regenBlock(false);
        }
        return false;
    }

    private void regenBlock(boolean stopServer) {
        Block bl = location.getBlock();

        //Getting Random Block After
        ChanceBlock chanceBlock = farmRegion.getChanceBlocks().getRandomBlock();

        if (chanceBlock == null) return;

        //Setting Main Block
        if(stopServer)
            //If Server is Stop
            BlockUtils.setBlock(location, bl, blockAfter, chanceBlock);
        else
            BlockUtils.setBlockSync(location, chanceBlock, 0).thenRun(() -> {
                //Getting Initial Data
                WrappedBlockData wrappedBlockData = blockAfter.getWrappedBlockData();

                //Setting Data of random block if initial data is not used
                if (chanceBlock.getData() != -1) wrappedBlockData.setLegacyData(chanceBlock.getData());

                //Applying Data to Main Block
                wrappedBlockData.apply(bl);
            });

        //Drawing Guardian Line
        plugin.getGuardians().getGuardian(regionName).ifPresent(guardian -> guardian.makeLineParticle(location));

        //Setting Blocks around
        if(farmRegion.isTripleBlock()) setBlocksAround(chanceBlock, stopServer);

        if (!stopServer) plugin.getFarmManager().removeCache(this);
    }

    public void disableRegen(boolean stopServer) {
        Bukkit.getScheduler().cancelTask(this.taskID);
        regenBlock(stopServer);
    }

    private void setBlocksAround(ChanceBlock chanceBlock, boolean stopServer) {
        int above = blockAfter.getBlocksAround().getAbove();
        List<Location> locations = new ArrayList<>(getRadiusBlocks(above));
        locations.sort(Comparator.comparingInt(Location::getBlockY));
        int h = 1;
        for(Location location : locations){
            if(stopServer) BlockUtils.setBlock(location, chanceBlock);
            else BlockUtils.setBlockSync(location, chanceBlock, h);
            h+=3;
        }
    }

    private Set<Location> getRadiusBlocks(int radius) {
        Set<Location> locations = new HashSet<>();
        Block bl = location.getBlock().getRelative(BlockFace.UP);
        for (int i = 0; i < radius; i++) {
            locations.add(bl.getLocation());
            bl = bl.getRelative(BlockFace.UP);
        }
        return locations;
    }
}