package com.infernalsuite.ultimatecore.collections.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.utils.StringUtils;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Messages extends YAMLFile {
    
    @Getter
    private List<String> levelUPMessage;
    
    private Map<String, String> messages;
    
    public Messages(HyperCollections plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadMessages();
    }
    
    @Override
    public void reload() {
        super.reload();
        this.loadMessages();
    }
    
    
    private void loadMessages() {
        messages = new HashMap<>();
        levelUPMessage = getConfig().getStringList("levelUpMessage");
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, StringUtils.color(getConfig().getString("messages." + key)));
        }
    }
    
    public String getMessage(String key) {
        return messages.get(key);
    }
    
    
}
