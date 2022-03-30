package com.infernalsuite.ultimatecore.talismans.objects.implementations;

import com.infernalsuite.ultimatecore.talismans.objects.ImmunityType;
import com.infernalsuite.ultimatecore.talismans.objects.Talisman;
import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ImmunityTalisman extends Talisman {
    private final Map<ImmunityType, Double> immunities;

    public ImmunityTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, Map<ImmunityType, Double> immunities) {
        super(name, talismanType, displayName, lore, texture, false);
        this.immunities = immunities;
    }
}
