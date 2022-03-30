package com.infernalsuite.ultimatecore.farm.objects;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.farm.HyperRegions;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

@Getter
@Setter
public class WrappedBlockData {
    private byte legacyData;
    private BlockData blockData;

    public WrappedBlockData(Block block) {
        legacyData = block.getData();
        if (XMaterial.getVersion() > 13)
            blockData = block.getBlockData();
    }

    public void apply(Block bl) {
        if (XMaterial.getVersion() > 13)
            HyperRegions.getInstance().getNms().setBlockData(bl, blockData);
        else
            HyperRegions.getInstance().getNms().setBlockData(bl, legacyData);
    }
}
