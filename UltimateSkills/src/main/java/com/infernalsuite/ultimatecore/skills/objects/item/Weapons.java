package com.infernalsuite.ultimatecore.skills.objects.item;

import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class Weapons {
    private final Map<String, Map<Ability, Double>> weapons;
    private final Map<String, Map<Perk, Double>> weaponsPerks;
}
