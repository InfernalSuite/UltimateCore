package mc.ultimatecore.talismans.objects.implementations;

import lombok.Getter;
import mc.ultimatecore.talismans.objects.ImmunityType;
import mc.ultimatecore.talismans.objects.Talisman;
import mc.ultimatecore.talismans.objects.TalismanType;

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
