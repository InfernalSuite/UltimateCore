package com.infernalsuite.ultimatecore.menu.configs;


import com.infernalsuite.ultimatecore.menu.HyperCore;

public class Config extends YAMLFile{
    public String prefix;
    public String mainCommandPerm;
    public boolean translatePAPIPlaceholders;

    public Config(HyperCore hyperCore, String name) {
        super(hyperCore, name);
    }

    @Override
    public void enable(){
        super.enable();
        this.loadDefaults();
    }

    @Override
    public void reload() {
        getConfig().reload();
        this.loadDefaults();
    }

    private void loadDefaults() {
        prefix = getConfig().get().getString("prefix", "&e&lHyper Core &7");
        mainCommandPerm = getConfig().get().getString("mainCommandPerm", "");
        translatePAPIPlaceholders = getConfig().get().getBoolean("translatePAPIPlaceholders");

    }
}
