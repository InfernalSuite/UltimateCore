package com.infernalsuite.ultimatecore.collections.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;

@Getter
public class Config extends YAMLFile {
    
    private Integer xpToRank;
    
    private String prefix;
    
    private String xpGainSound;
    
    private String levelUPSound;
    
    private String rankPlaceholder;
    
    private String noRankPlaceholder;
    
    private boolean debug;
    
    private boolean jetMinions;
    
    private boolean ultraMinions;
    
    public Config(HyperCollections plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }
    
    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }
    
    private void loadDefaults() {
        xpToRank = getConfig().getInt("xpToRank");
        prefix = getConfig().getString("prefix");
        xpGainSound = getConfig().getString("xpGainSound");
        levelUPSound = getConfig().getString("levelUPSound");
        rankPlaceholder = getConfig().getString("rankPlaceholder");
        noRankPlaceholder = getConfig().getString("noRankPlaceholder");
        debug = getConfig().getBoolean("debug");
        jetMinions = getConfig().getBoolean("addons.jetMinions");
        ultraMinions = getConfig().getBoolean("addons.ultraMinions");
    }
}
