package com.infernalsuite.ultimatecore.skills.addons;

import com.infernalsuite.ultimatecore.skills.implementations.SoftDependImpl;
import org.bukkit.entity.Entity;

public class CitizensAPIManager extends SoftDependImpl {
    public CitizensAPIManager(String displayName) {
        super(displayName);
    }

    public boolean isNPCEntity(Entity entity){
        return entity.hasMetadata("NPC");
    }
}
