package com.infernalsuite.ultimatecore.farm.configs;

import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;

public class Config extends YAMLFile {
    public String prefix;
    public String guardianTexture;

    public boolean byPassWorldGuard;
    public boolean upAndDownGuardians;
    public boolean useDefaultPluginRegions;
    public int priorityLevel;
    public boolean debug;
    public String particle;

    public Config(HyperRegions plugin, String name, boolean defaults) {
        super(plugin, name, defaults, false);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        //------------------------------------------------//
        prefix = getConfig().getString("prefix", "&e&lUltimateCore-Farm &7");
        guardianTexture = getConfig().getString("guardianTexture", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI2NWY5NmY1NGI3ODg4NWM0NmU3ZDJmODZiMWMxZGJmZTY0M2M2MDYwZmM3ZmNjOTgzNGMzZTNmZDU5NTEzNSJ9fX0=");
        byPassWorldGuard = getConfig().getBoolean("byPassWorldGuard", false);
        upAndDownGuardians = getConfig().getBoolean("upAndDownGuardians", true);
        useDefaultPluginRegions = getConfig().getBoolean("useDefaultPluginRegions", true);
        priorityLevel = getConfig().getInt("priorityLevel");
        particle = getConfig().getString("guardian_particle");
        debug = getConfig().getBoolean("debug");

        //------------------------------------------------//
    }
}
