package com.infernalsuite.ultimatecore.talismans.configs;

import com.infernalsuite.ultimatecore.talismans.utils.StringUtils;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;

import java.util.HashMap;

public class Messages extends YAMLFile {

    private HashMap<String, String> messages;

    public Messages(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadMessages();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadMessages();
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