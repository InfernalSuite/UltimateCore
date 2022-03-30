package com.infernalsuite.ultimatecore.talismans.objects.implementations;

import com.infernalsuite.ultimatecore.talismans.objects.Talisman;
import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import lombok.Getter;

import java.util.List;

public class HealingTalisman extends Talisman {
    @Getter
    private final double percentage;

    public HealingTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, double percentage) {
        super(name, talismanType, displayName, lore, texture, false);
        this.percentage = percentage;
    }
}
