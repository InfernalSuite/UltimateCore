package com.infernalsuite.ultimatecore.talismans.objects.implementations;

import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;

import java.util.*;

public class PerkRegionTalisman extends PerkTalisman implements RegionTalisman, StatsTalisman{
    private final List<String> regions;
    private final Set<UUID> regionPlayers;

    public PerkRegionTalisman(String name, TalismanType talismanType, String displayName, List<String> lore, String texture, Map<Perk, Double> perks, List<String> regions) {
        super(name, talismanType, displayName, lore, texture, perks, false);
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
