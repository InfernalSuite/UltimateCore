package mc.ultimatecore.anvil.configs;

import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.utils.StringUtils;

import java.util.HashMap;
import java.util.List;

public class Messages extends YAMLFile{

    private HashMap<String, String> messages;

    private HashMap<String, List<String>> descriptions;

    public Messages(HyperAnvil hyperSkills, String name) {
        super(hyperSkills, name);
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
        descriptions = new HashMap<>();
        for (String key : getConfig().get().getConfigurationSection("errorInformation").getKeys(false))
            descriptions.put(key, getConfig().get().getStringList("errorInformation." + key));
        for (String key : getConfig().get().getConfigurationSection("messages").getKeys(false))
            messages.put(key, StringUtils.color(getConfig().get().getString("messages." + key)));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

    public List<String> getDescription(String key) {
        return descriptions.get(key);
    }

}
