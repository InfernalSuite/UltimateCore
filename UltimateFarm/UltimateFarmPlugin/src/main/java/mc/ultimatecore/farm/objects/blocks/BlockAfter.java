package mc.ultimatecore.farm.objects.blocks;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.farm.objects.HyperRegion;
import mc.ultimatecore.farm.objects.WrappedBlockData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

@Getter
@Setter
public class BlockAfter {
    private WrappedBlockData wrappedBlockData;
    private BlocksAround blocksAround;

    public BlockAfter(WrappedBlockData wrappedBlockData, Block block, HyperRegion hyperRegion) {
        this.wrappedBlockData = wrappedBlockData;
        this.blocksAround = getBlocksAround(block, hyperRegion);
    }

    private BlocksAround getBlocksAround(Block block, HyperRegion hyperRegion) {
        BlocksAround blocksAround = new BlocksAround(0);
        if (!hyperRegion.isTripleBlock()) return blocksAround;
        blocksAround.setAbove(getNear(block));
        return blocksAround;
    }

    private int getNear(Block block) {
        int amount = 0;
        Block near = block.getRelative(BlockFace.UP);
        Material material = block.getType();
        for (int i = 0; i < 3; i++) {
            if (near != null && near.getType().equals(material))
                amount++;
            else
                break;
            near = near.getRelative(BlockFace.UP);
        }
        return amount;
    }
}
