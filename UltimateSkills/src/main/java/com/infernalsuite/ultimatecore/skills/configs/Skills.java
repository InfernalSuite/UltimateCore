package com.infernalsuite.ultimatecore.skills.configs;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.DebugType;
import lombok.Getter;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.skills.objects.Skill;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Skills extends YAMLFile {

    @Getter
    private final Map<SkillType, Skill> allSkills = new HashMap<>();

    public Skills(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadSkills();
    }

    @Override
    public void reload(){
        super.reload();
        loadSkills();
    }

    private void loadSkills(){
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("skills");
        if(configurationSection == null) return;
        try {
            for(SkillType skillType : SkillType.values()){
                String displayName = getConfig().getString("skills."+skillType+".displayName");
                int slot = getConfig().getInt("skills."+skillType+".slot");
                List<String> description = getConfig().getStringList("skills."+skillType+".description");
                List<String> blockedWorlds = getConfig().getStringList("skills."+skillType+".blockedWorlds");
                List<String> blockedRegions = getConfig().getStringList("skills."+skillType+".blockedRegions");
                boolean multiplier = getConfig().getBoolean("skills."+skillType+".multiplierEnabled");
                boolean enabled = getConfig().getBoolean("skills."+skillType+".enabled");
                boolean creative = getConfig().getBoolean("skills."+skillType+".xpInCreative");
                XMaterial xMaterial = XMaterial.valueOf(getConfig().getString("skills."+skillType+".item"));
                allSkills.put(skillType, new Skill(displayName, description, blockedWorlds, blockedRegions, enabled, multiplier, creative, slot, xMaterial, skillType));
            }
        }catch (Exception e){
            HyperSkills.getInstance().sendDebug("Error Loading Skills!, Disabling Plugin", DebugType.LOG);
            e.printStackTrace();
        }

    }
}
