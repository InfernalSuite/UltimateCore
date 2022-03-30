package com.infernalsuite.ultimatecore.skills.objects.item;

import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class Armors {
    private final Map<String, Map<Ability, Double>> armors;
    private final Map<String, Map<Perk, Double>> armorsPerks;
}
