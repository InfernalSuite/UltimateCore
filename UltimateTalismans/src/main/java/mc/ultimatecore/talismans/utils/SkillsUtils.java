package mc.ultimatecore.talismans.utils;

import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class SkillsUtils {
    public static Map<Ability, Double> getTalismanAbilities(FileConfiguration fileConfiguration, String path){
        Map<Ability, Double> map = new HashMap<>();
        if(fileConfiguration == null) return map;
        for(String key : fileConfiguration.getStringList(path)){
            String[] split = key.split(":");
            if(split.length != 2) continue;
            Ability ability = Ability.valueOf(split[0].toUpperCase());
            double value = Double.parseDouble(split[1]);
            map.put(ability, value);
        }
        return map;
    }

    public static Map<Perk, Double> getTalismanPerks(FileConfiguration fileConfiguration, String path){
        Map<Perk, Double> map = new HashMap<>();
        if(fileConfiguration == null) return map;
        for(String key : fileConfiguration.getStringList(path)){
            String[] split = key.split(":");
            if(split.length != 2) continue;
            Perk perk = Perk.valueOf(split[0]);
            double value = Double.parseDouble(split[1]);
            map.put(perk, value);
        }
        return map;
    }
}
