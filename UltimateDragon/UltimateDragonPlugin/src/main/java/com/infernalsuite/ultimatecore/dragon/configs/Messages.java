package com.infernalsuite.ultimatecore.dragon.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.objects.title.TitleObject;
import com.infernalsuite.ultimatecore.dragon.objects.title.TitleType;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages extends YAMLFile {

    private Map<String, String> messages;
    @Getter
    private List<String> editorModeJoinMsg;
    @Getter
    private List<String> finishGameMessage;
    @Getter
    private Map<TitleType, TitleObject> titles;
    @Getter
    private boolean enabled;

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
        editorModeJoinMsg = new ArrayList<>();
        finishGameMessage = new ArrayList<>();
        messages = new HashMap<>();
        titles = new HashMap<>();
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false))
            if(!key.equals("eventStarted"))
                messages.put(key, StringUtils.color(getConfig().getString("messages." + key)));
        editorModeJoinMsg = getConfig().getStringList("editModeJoin");
        finishGameMessage = getConfig().getStringList("gameEndMessage");
        titles.put(TitleType.SPAWNING, new TitleObject(getConfig().getString("titles.spawning.title"), getConfig().getString("titles.spawning.subtitle")));
        titles.put(TitleType.SPAWNED, new TitleObject(getConfig().getString("titles.spawned.title"), getConfig().getString("titles.spawned.subtitle")));
        enabled = getConfig().getBoolean("messages.eventStarted.enabled");
        for (String key : getConfig().getConfigurationSection("messages.eventStarted").getKeys(false))
            if(!key.equals("enabled"))
                messages.put(key, StringUtils.color(getConfig().getString("messages.eventStarted."+key)));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }
}
