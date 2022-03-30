package com.infernalsuite.ultimatecore.reforge.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;

@NoArgsConstructor
@Getter
@Setter
public class NextCost {
    private HyperEnchant hyperEnchant;
    private int level;

    public void setValues(HyperEnchant hyperEnchant, int level){
        this.hyperEnchant = hyperEnchant;
        this.level = level;
    }
}
