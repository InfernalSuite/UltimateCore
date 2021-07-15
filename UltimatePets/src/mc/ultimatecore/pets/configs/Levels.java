package mc.ultimatecore.pets.configs;

import com.archyx.aureliumskills.stats.Stat;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.DebugType;
import mc.ultimatecore.pets.objects.PetLevel;
import mc.ultimatecore.pets.objects.potions.PetPotions;
import mc.ultimatecore.pets.objects.rewards.PetReward;
import mc.ultimatecore.pets.objects.stats.AurelliumStats;
import mc.ultimatecore.pets.objects.stats.PetStats;
import mc.ultimatecore.pets.objects.stats.UltimateStats;
import mc.ultimatecore.pets.utils.Utils;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Levels extends YAMLFile {

    public Levels(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
    }

    @Override
    public void reload(){
        super.reload();
    }

    public Map<String, Map<Integer, Double>> getRequirements(String pet) {
        Map<String, Map<Integer, Double>> requirements = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("levels."+pet);
        if(section == null) return requirements;
        for(String tier : section.getKeys(false)){
            ConfigurationSection tierSection = getConfig().getConfigurationSection("levels."+pet+"."+tier);
            Map<Integer, Double> levels = new HashMap<>();
            if(tierSection != null) {
                for (String levelSection : tierSection.getKeys(false)) {
                    Integer level = Integer.valueOf(levelSection.replace("level-", ""));
                    Double requirement = getConfig().getDouble("levels."+pet+"."+tier+"."+levelSection+".xpRequired");
                    levels.put(level, requirement);
                }
            }
            requirements.put(tier, levels);
        }
        return requirements;
    }


    public Map<String, Map<Integer, PetStats>> getPetStats(String pet) {
        Map<String, Map<Integer, PetStats>> requirements = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("levels."+pet);
        boolean hyperSkills = HyperPets.getInstance().getAddonsManager().isHyperSkills();
        boolean aurelium = HyperPets.getInstance().getAddonsManager().isAurellium();
        if(section == null) return requirements;
        if(!hyperSkills && !aurelium) return requirements;
        for(String tier : section.getKeys(false)){
            ConfigurationSection tierSection = getConfig().getConfigurationSection("levels."+pet+"."+tier);
            Map<Integer, PetStats> levels = new HashMap<>();
            if(tierSection != null) {
                for (String levelSection : tierSection.getKeys(false)) {
                    Integer level = Integer.valueOf(levelSection.replace("level-", ""));
                    if(hyperSkills){
                        Map<Ability, Double> petAbilities = new HashMap<>();
                        ConfigurationSection abilitiesSection = getConfig().getConfigurationSection("levels."+pet+"."+tier+"."+levelSection+".petAbilities");
                        if(abilitiesSection != null)
                            for (String ability : abilitiesSection.getKeys(false))
                                petAbilities.put(Ability.valueOf(ability), getConfig().getDouble("levels."+pet+"."+tier+"."+levelSection+".petAbilities."+ability));
                        Map<Perk, Double> petPerks = new HashMap<>();
                        ConfigurationSection petsSection = getConfig().getConfigurationSection("levels."+pet+"."+tier+"."+levelSection+".petPerks");
                        if(petsSection != null)
                            for (String perk : petsSection.getKeys(false))
                                petPerks.put(Perk.valueOf(perk), getConfig().getDouble("levels."+pet+"."+tier+"."+levelSection+".petPerks."+perk));

                        PetStats petStats = new UltimateStats(petAbilities, petPerks);
                        levels.put(level, petStats);
                    }else{
                        try{
                            Map<Stat, Double> petStatss = new HashMap<>();
                            ConfigurationSection section1 = getConfig().getConfigurationSection("levels."+pet+"."+tier+"."+levelSection+".petStats");
                            if(section1 != null)
                                for (String ability : section1.getKeys(false))
                                    petStatss.put(HyperPets.getInstance().getAddonsManager().getAurelliumSkills().getStat(ability), getConfig().getDouble("levels."+pet+"."+tier+"."+levelSection+".petStats."+ability));
                            PetStats petStats = new AurelliumStats(petStatss);
                            levels.put(level, petStats);
                        }catch (Exception e){
                            HyperPets.getInstance().sendDebug("&cError Loading Stats of AureliumSkills for pet "+pet, DebugType.COLORED);
                        }
                    }

                }
            }
            requirements.put(tier, levels);
        }
        return requirements;
    }

    public Map<String, Map<Integer, PetReward>> getPetRewards(String pet) {
        Map<String, Map<Integer, PetReward>> requirements = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("levels."+pet);
        if(section == null) return requirements;
        for(String tier : section.getKeys(false)){
            ConfigurationSection tierSection = getConfig().getConfigurationSection("levels."+pet+"."+tier);
            Map<Integer, PetReward> levels = new HashMap<>();
            if(tierSection != null) {
                for (String levelSection : tierSection.getKeys(false)) {
                    Integer level = Integer.valueOf(levelSection.replace("level-", ""));
                    PetReward petReward = new PetReward(getConfig().getStringList("levels."+pet+"."+tier+"."+levelSection+".rewards"));
                    levels.put(level, petReward);
                }
            }
            requirements.put(tier, levels);
        }
        return requirements;
    }

    public Map<String, Map<Integer, PetPotions>> getPetPotions(String pet) {
        Map<String, Map<Integer, PetPotions>> requirements = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("levels."+pet);
        if(section == null) return requirements;
        for(String tier : section.getKeys(false)){
            ConfigurationSection tierSection = getConfig().getConfigurationSection("levels."+pet+"."+tier);
            Map<Integer, PetPotions> levels = new HashMap<>();
            if(tierSection != null) {
                for (String levelSection : tierSection.getKeys(false)) {
                    Integer level = Integer.valueOf(levelSection.replace("level-", ""));
                    PetPotions petPotions = new PetPotions(Utils.getPotions(new HashSet<>(getConfig().getStringList("levels."+pet+"."+tier+"."+levelSection+".petPotions"))));
                    levels.put(level, petPotions);
                }
            }
            requirements.put(tier, levels);
        }
        return requirements;
    }

    public Map<String, PetLevel> getPetLevels(String pet){
        Map<String, Map<Integer, PetStats>> petStats = HyperPets.getInstance().getLevels().getPetStats(pet);
        Map<String, Map<Integer, PetPotions>> petPotions = HyperPets.getInstance().getLevels().getPetPotions(pet);
        Map<String, Map<Integer, Double>> petRequirements = HyperPets.getInstance().getLevels().getRequirements(pet);
        Map<String, Map<Integer, PetReward>> petRewards = HyperPets.getInstance().getLevels().getPetRewards(pet);
        Map<String, PetLevel> petLevel = new HashMap<>();
        for(String tier : petStats.keySet()){
            Map<Integer, PetStats> petStat = petStats.get(tier);
            Map<Integer, PetPotions> petPotion = petPotions.get(tier);
            Map<Integer, Double> petRequirement = petRequirements.get(tier);
            Map<Integer, PetReward> petReward = petRewards.get(tier);
            petLevel.put(tier, new PetLevel(petRequirement, petStat, petReward, petPotion));
        }
        return petLevel;
    }
}
