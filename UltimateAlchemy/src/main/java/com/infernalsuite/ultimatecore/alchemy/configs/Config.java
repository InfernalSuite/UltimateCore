package com.infernalsuite.ultimatecore.alchemy.configs;

import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;

public class Config extends YAMLFile{

    public String prefix = "&e&lUltimateCore-Alchemy &7";
    public String mainCommandPerm = "";
    public double aurelliumXP;
    public String aurelliumSkill;

    public Config(HyperAlchemy hyperCrafting, String name) {
        super(hyperCrafting, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        getConfig().reload();
        loadDefaults();
    }

    private void loadDefaults(){
        prefix = getConfig().get().getString("prefix", "&e&lUltimateCore-Alchemy &7");
        mainCommandPerm = getConfig().get().getString("mainCommandPerm", "");
        aurelliumXP = getConfig().get().getDouble("aurelliumSkills.xp");
        aurelliumSkill = getConfig().get().getString("aurelliumSkills.skill");

    }
}
