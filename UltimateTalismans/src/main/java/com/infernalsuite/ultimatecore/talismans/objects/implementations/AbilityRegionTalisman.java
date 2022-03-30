package com.infernalsuite.ultimatecore.talismans.objects.implementations;

import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;

import java.util.*;

public class AbilityRegionTalisman extends AbilityTalisman implements RegionTalisman, StatsTalisman {
    private final List<String> regions;
    private final Set<UUID> regionPlayers;

    public AbilityRegionTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, Map<Ability, Double> abilities, List<String> regions) {
        super(name, talismanType, displayName, lore, texture, abilities, false);
        this.regions = regions;
        this.regionPlayers = new HashSet<>();
    }

    @Override
    public List<String> getRegions() {
        return regions;
    }

    @Override
    public Set<UUID> getRegionPlayers() {
        return regionPlayers;
    }
}
