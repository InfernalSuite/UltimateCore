package mc.ultimatecore.farm.utils;

import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.objects.WrappedBlockData;
import mc.ultimatecore.farm.objects.blocks.BlockAfter;
import mc.ultimatecore.farm.objects.blocks.ChanceBlock;
import mc.ultimatecore.farm.objects.blocks.RegionBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.concurrent.CompletableFuture;

public class BlockUtils {

    public static void setBlock(Location location, Block block, BlockAfter blockAfter, ChanceBlock chanceBlock){
        setBlock(location, chanceBlock);

        //Getting Initial Data
        WrappedBlockData wrappedBlockData = blockAfter.getWrappedBlockData();

        //Setting Data of random block if initial data is not used
        if (chanceBlock.getData() != -1) wrappedBlockData.setLegacyData(chanceBlock.getData());

        //Applying Data to Main Block
        wrappedBlockData.apply(block);
    }

    public static void setBlock(Location location, RegionBlock regionBlock) {
        //Getting Location to put future Block
        Block locBlock = location.getBlock();

        //Setting type based on region Block
        Material material = Material.valueOf(regionBlock.getMaterial());

        //If block isn't already the type it sets to the type
        if(locBlock.getType() != material) locBlock.setType(material);

        //If data is not the default or it's setted to set the first data it put the data to the region block data
        if (regionBlock.getData() != 0 && regionBlock.getData() != -1) HyperRegions.getInstance().getNms().setBlockData(locBlock, regionBlock.getData());
    }

    public static CompletableFuture<Void> setBlockSync(Location location, RegionBlock regionBlock, int delay) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskLater(HyperRegions.getInstance(), () -> {
            //Getting Location to put future Block
            Block locBlock = location.getBlock();

            //Setting type based on region Block
            Material material = Material.valueOf(regionBlock.getMaterial());

            //If block isn't already the type it sets to the type
            if(locBlock.getType() != material) locBlock.setType(material);

            //If data is not the default or it's setted to set the first data it put the data to the region block data
            if (regionBlock.getData() != 0 && regionBlock.getData() != -1) HyperRegions.getInstance().getNms().setBlockData(locBlock, regionBlock.getData());

            //Return future one time created
            future.complete(null);
        }, delay)/*)*/;
        return future;
    }

}
