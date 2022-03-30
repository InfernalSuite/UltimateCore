package com.infernalsuite.ultimatecore.souls.configs;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Messages extends YAMLFile {
    
    private Map<String, String> messages;
    
    public Messages(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadMessages();
    }
    
    
    @Override
    public void reload() {
        super.reload();
        loadMessages();
    }
    
    private void loadMessages() {
        messages = new HashMap<>();
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false))
            messages.put(key, StringUtils.color(getConfig().getString("messages." + key)));
    }
    
    public String getMessage(String key) {
        return messages.get(key);
    }
}
