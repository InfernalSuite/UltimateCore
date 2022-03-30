package com.infernalsuite.ultimatecore.pets.objects.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class UltimateStats implements PetStats{
    private final Map<Ability, Double> petAbilities;
    private final Map<Perk, Double> petPerks;

    public void addStats(Player player){
        petAbilities.forEach((ability, amount) -> HyperSkills.getInstance().getApi()
                .addArmorAbility(player.getUniqueId(), ability, amount));
        petPerks.forEach((perk, amount) -> HyperSkills.getInstance().getApi()
                .addArmorPerk(player.getUniqueId(), perk, amount));
    }

    public void removeStats(Player player){
        petAbilities.forEach((ability, amount) -> HyperSkills.getInstance().getApi()
                .removeArmorAbility(player.getUniqueId(), ability, petAbilities.get(ability)));
        petPerks.forEach((ability, amount) -> HyperSkills.getInstance().getApi()
                .removeArmorPerk(player.getUniqueId(), ability, amount));
    }
}
