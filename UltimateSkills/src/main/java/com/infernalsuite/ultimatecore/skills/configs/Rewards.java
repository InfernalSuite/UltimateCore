package com.infernalsuite.ultimatecore.skills.configs;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rewards extends YAMLFile {

    private Map<SkillType, Map<Integer, List<String>>> commands;

    private Map<SkillType, Map<Integer, List<String>>> rewardPlaceholders;

    public Rewards(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        //REWARDS AND COMMANDS
        commands = getObject("commands");
        rewardPlaceholders = getObject("rewardPlaceholders");
    }

    private Map<SkillType, Map<Integer, List<String>>> getObject(String path){
        Map<SkillType, Map<Integer, List<String>>> hashMap = new HashMap<>();
        for(String skill : getConfig().getConfigurationSection(path).getKeys(false)){
            Map<Integer, List<String>> levelsMap = new HashMap<>();
            for(String level : getConfig().getConfigurationSection(path + "." + skill).getKeys(false))
                levelsMap.put(Integer.valueOf(level), getConfig().getStringList(path + "." + skill + "." + level));
            hashMap.put(SkillType.valueOf(skill), levelsMap);
        }
        return hashMap;
    }


    public List<String> getCommandRewards(SkillType skill, int level) {
        if (commands.containsKey(skill)) {
            if (commands.get(skill).size() > level)
                return commands.get(skill).get(level);
        }
        return null;
    }

    public List<String> getRewardPlaceholders(SkillType skill, int level) {
        if (rewardPlaceholders.containsKey(skill)) {
            if (rewardPlaceholders.get(skill).size() > level)
                return rewardPlaceholders.get(skill).get(level);
        }
        return null;
    }
}
