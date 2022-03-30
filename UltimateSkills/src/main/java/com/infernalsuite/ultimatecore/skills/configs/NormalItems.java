package com.infernalsuite.ultimatecore.skills.configs;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.item.Armors;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalItems extends YAMLFile {
    //private Weapons weapons;
    private Armors armors;

    public NormalItems(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        //weapons = new Weapons(getStatsMap("weapons"), getPerksMap("weapons"));
        armors = new Armors(getStatsMap("armors"), getPerksMap("armors"));
    }

    private Map<String, Map<Perk, Double>> getPerksMap(String type){
        Map<String, Map<Perk, Double>> map = new HashMap<>();
        ConfigurationSection sections = getConfig().getConfigurationSection("stats."+type);
        if(sections != null){
            for(String key : sections.getKeys(false)){
                try{
                    Material.valueOf(key);
                    Map<Perk, Double> stats = new HashMap<>();
                    List<String> abilities = getConfig().getStringList("stats."+type+"."+key+".perks");
                    for(String value : abilities){
                        try {
                            String[] split = value.split(":");
                            if (split.length == 2) {
                                Perk ability = Perk.valueOf(split[0]);
                                Double amount = Double.parseDouble(split[1]);
                                stats.put(ability, amount);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSkills] &cError loading perks stats for "+key+"!"));
                        }
                    }
                    map.put(key, stats);
                }catch (Exception e){
                    Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSkills] &c"+key+" is not a valid material! | Check normalitems.yml"));
                }
            }
        }
        return map;
    }

    private Map<String, Map<Ability, Double>> getStatsMap(String type){
        Map<String, Map<Ability, Double>> map = new HashMap<>();
        ConfigurationSection sections = getConfig().getConfigurationSection("stats."+type);
        if(sections != null){
            for(String key : sections.getKeys(false)){
                try{
                    Material.valueOf(key);
                    Map<Ability, Double> stats = new HashMap<>();
                    List<String> abilities = getConfig().getStringList("stats."+type+"."+key+".abilities");
                    for(String value : abilities){
                        try {
                            String[] split = value.split(":");
                            if (split.length == 2) {
                                Ability ability = Ability.valueOf(split[0]);
                                Double amount = Double.parseDouble(split[1]);
                                stats.put(ability, amount);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSkills] &cError loading abilities stats for "+key+"!"));
                        }
                    }
                    map.put(key, stats);
                }catch (Exception e){
                    Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperSkills] &c"+key+" is not a valid material! | Check normalitems.yml"));
                }
            }
        }
        return map;
    }

    public Map<Ability, Double> getArmorAbility(String item){
        return armors.getArmors().getOrDefault(item, new HashMap<>());
    }

    public Map<Perk, Double> getArmorPerk(String item){
        return armors.getArmorsPerks().getOrDefault(item, new HashMap<>());
    }


    /*public Map<Ability, Double> getWeaponAbility(String item){
        return weapons.getWeapons().getOrDefault(item, new HashMap<>());
    }

    public Map<Perk, Double> getWeaponPerk(String item){
        return weapons.getWeaponsPerks().getOrDefault(item, new HashMap<>());
    }
*/
}
