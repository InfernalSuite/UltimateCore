package com.infernalsuite.ultimatecore.pets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.objects.commands.PetCommands;
import com.infernalsuite.ultimatecore.pets.objects.potions.PetPotions;
import com.infernalsuite.ultimatecore.pets.objects.rewards.PetReward;
import com.infernalsuite.ultimatecore.pets.objects.stats.PetStats;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Pet {

    private final String displayName;

    private final String entityName;

    private final List<String> description;

    private final String texture;

    private final Map<String, PetLevel> petLevels;

    private final PetCommands petCommands;

    public Double getLevelRequirement(String tier, int level){
        if(petLevels.containsKey(tier))
            return petLevels.get(tier).getRequirement(level);
        return 0D;
    }

    public int getMaxLevel(String tier){
        if(petLevels.containsKey(tier))
            return petLevels.get(tier).getRequirements().size();
        return 0;
    }

    public boolean tierHasNextLevel(String tier, int level){
        return petLevels.get(tier).getRequirements().containsKey(level);
    }

    public void applyNewStats(Player player, String tier, int level){
        PetLevel petLevel = petLevels.getOrDefault(tier, null);
        if(petLevel == null) return;
        //Abilities
        if(HyperPets.getInstance().getAddonsManager().isHyperSkills()) {
            PetStats petStats = petLevel.getPetStats(level);
            if(petStats != null) petStats.addStats(player);
        }
        //Potions
        PetPotions petPotions = petLevel.getPetPotions(level);
        if(petPotions != null) petPotions.apply(player);
    }

    public void removeStats(Player player, String tier, int level){
        PetLevel petLevel = petLevels.getOrDefault(tier, null);
        if(petLevel == null) return;
        //Abilities
        if(HyperPets.getInstance().getAddonsManager().isHyperSkills()) {
            PetStats petStats = petLevel.getPetStats(level);
            if(petStats != null) petStats.removeStats(player);
        }
        //Potions
        PetPotions petPotions = petLevel.getPetPotions(level);
        if(petPotions != null) petPotions.remove(player);
    }

    public PetReward getPetReward(String tier, int level){
        PetLevel petLevel = petLevels.getOrDefault(tier, null);
        if(petLevel != null)
            return petLevel.getPetRewards(level);
        return null;
    }

    public PetStats getPetStats(String tier, int level){
        PetLevel petLevel = petLevels.getOrDefault(tier, null);
        if(petLevel != null)
            return petLevel.getPetStats(level);
        return null;
    }
}
