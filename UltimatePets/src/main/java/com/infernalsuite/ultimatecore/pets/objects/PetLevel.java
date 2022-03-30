package com.infernalsuite.ultimatecore.pets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.infernalsuite.ultimatecore.pets.objects.potions.PetPotions;
import com.infernalsuite.ultimatecore.pets.objects.rewards.PetReward;
import com.infernalsuite.ultimatecore.pets.objects.stats.PetStats;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PetLevel {
    private final Map<Integer, Double> requirements;
    private final Map<Integer, PetStats> petStats;
    private final Map<Integer, PetReward> petRewards;
    private final Map<Integer, PetPotions> petPotions;

    public Double getRequirement(int level){
        return requirements.getOrDefault(level, 0d);
    }

    public PetStats getPetStats(int level){
        return petStats.getOrDefault(level,  null);
    }

    public PetReward getPetRewards(int level){
        return petRewards.getOrDefault(level,  null);
    }

    public PetPotions getPetPotions(int level){
        return petPotions.getOrDefault(level,  null);
    }
}
