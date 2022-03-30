package com.infernalsuite.ultimatecore.anvil.configs;

import com.infernalsuite.ultimatecore.anvil.HyperAnvil;

public class Config extends YAMLFile{
    public String prefix;
    public boolean setAsDefaultAnvil;
    public String anvilFuseSound;
    public int nameTagFuseCost;
    public int nameTagCreateCost;
    public boolean allowColorsInNameTag;
    public double aurelliumXP;
    public String aurelliumSkill;

    public Config(HyperAnvil hyperSkills, String name) {
        super(hyperSkills, name);
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
        //------------------------------------------------//
        prefix = getConfig().get().getString("prefix");
        setAsDefaultAnvil = getConfig().get().getBoolean("setAsDefaultAnvil");
        anvilFuseSound = getConfig().get().getString("anvilFuseSound");
        nameTagFuseCost = getConfig().get().getInt("nameTagFuseCost");
        nameTagCreateCost = getConfig().get().getInt("nameTagCreateCost");
        allowColorsInNameTag = getConfig().get().getBoolean("allowColorsInNameTag");
        aurelliumXP = getConfig().get().getDouble("aurelliumSkills.xp");
        aurelliumSkill = getConfig().get().getString("aurelliumSkills.skill");

        //------------------------------------------------//
    }
}
