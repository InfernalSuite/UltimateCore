package mc.ultimatecore.skills.configs;

import lombok.Getter;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.skills.utils.StringUtils;

import java.util.HashMap;
import java.util.List;

public class Messages extends YAMLFile {

    private HashMap<String, String> messages;

    @Getter
    private List<String> levelUPMessage;

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
        levelUPMessage = getConfig().getStringList("levelUPMessage");
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false))
            messages.put(key, StringUtils.color(getConfig().getString("messages." + key)));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }
}
