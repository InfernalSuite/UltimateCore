package mc.ultimatecore.skills.configs;

import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.skills.objects.SkillType;

import java.util.HashMap;
import java.util.Map;

public class Requirements extends YAMLFile {

    private Map<SkillType, Map<Integer, Double>> defaultRequirements;

    public Requirements(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        defaultRequirements = new HashMap<>();
        //------------------------------------------------//
        for(SkillType skillType : SkillType.values()){
            HashMap<Integer, Double> requirements = new HashMap<>();
            for(String key : getConfig().getConfigurationSection("requirements."+skillType.toString()).getKeys(false))
                requirements.put(Integer.valueOf(key), getConfig().getDouble("requirements."+skillType.toString()+"."+key));
            defaultRequirements.put(skillType, requirements);
        }
        //------------------------------------------------//
    }


    public Double getLevelRequirement(SkillType skill, int level) {
        if (defaultRequirements.containsKey(skill)) {
            if (defaultRequirements.get(skill).containsKey(level))
                return defaultRequirements.get(skill).get(level);
        }
        return 0D;
    }

}
