package mc.ultimatecore.menu.configs;


import mc.ultimatecore.menu.HyperCore;
import mc.ultimatecore.menu.utils.StringUtils;

import java.util.HashMap;

public class Messages extends YAMLFile{

    private HashMap<String, String> messages;

    public Messages(HyperCore hyperCore, String name) {
        super(hyperCore, name);
    }

    @Override
    public void enable(){
        super.enable();
        this.loadMessages();
    }

    @Override
    public void reload(){
        getConfig().reload();
        this.loadMessages();
    }


    private void loadMessages() {
        messages = new HashMap<>();
        for (String key : getConfig().get().getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, StringUtils.color(getConfig().get().getString("messages." + key)));
        }
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

}
