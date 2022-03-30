package com.infernalsuite.ultimatecore.enchantment.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;

@RequiredArgsConstructor
@Getter
public class EnchantObject {
    private final HyperEnchant hyperEnchant;
    @Setter
    private int level;
}
