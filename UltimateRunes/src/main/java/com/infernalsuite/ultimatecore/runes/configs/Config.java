package com.infernalsuite.ultimatecore.runes.configs;

import com.infernalsuite.ultimatecore.runes.HyperRunes;

public class Config extends YAMLFile{
    public String prefix;

    public Config(HyperRunes hyperSkills, String name) {
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
        //------------------------------------------------//
    }
}
