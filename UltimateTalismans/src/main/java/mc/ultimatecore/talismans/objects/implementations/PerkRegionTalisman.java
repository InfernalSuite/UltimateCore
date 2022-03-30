package mc.ultimatecore.talismans.objects.implementations;

import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.talismans.objects.TalismanType;

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
