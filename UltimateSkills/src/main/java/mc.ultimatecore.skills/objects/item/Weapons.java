package mc.ultimatecore.skills.objects.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class Weapons {
    private final Map<String, Map<Ability, Double>> weapons;
    private final Map<String, Map<Perk, Double>> weaponsPerks;
}
