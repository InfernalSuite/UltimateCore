package com.infernalsuite.ultimatecore.dragon.objects.structures;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.dragon.nms.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
@Setter
public class DragonAltar {
    private final Location location;
    private boolean inUse;

    public void setInUse(boolean inUse){
        this.inUse = inUse;
        if(XMaterial.getVersion() > 13)
            BlockUtils.setBlockData(location, inUse);
    }

    public void clearEye(){
        if(XMaterial.getVersion() <= 13) return;
        if(location.getBlock() != null && location.getBlock().getType() != Material.AIR)
            BlockUtils.setBlockData(location.getBlock(), false);
    }
}
