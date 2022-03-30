package com.infernalsuite.ultimatecore.farm.objects.blocks;

import lombok.Getter;

@Getter
public class ChanceBlock extends RegionBlock {
    private final int chance;

    public ChanceBlock(String material, byte data, int chance) {
        super(material, data);
        this.chance = chance;
    }
}
