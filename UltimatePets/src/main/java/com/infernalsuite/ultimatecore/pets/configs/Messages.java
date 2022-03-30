package com.infernalsuite.ultimatecore.pets.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Messages extends YAMLFile {

    private HashMap<String, String> messages;

    @Getter
    private List<String> tierLevelUPMessage;

    public Messages(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
        loadMessages();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadMessages();
    }

    private void loadMessages() {
        messages = new HashMap<>();
        tierLevelUPMessage = new ArrayList<>();
        if(getConfig().contains("tierLevelUpMessage"))
            tierLevelUPMessage = getConfig().getStringList("tierLevelUpMessage");
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false))
            messages.put(key, StringUtils.color(getConfig().getString("messages." + key)));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

}
