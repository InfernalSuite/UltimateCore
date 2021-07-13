package mc.ultimatecore.crafting.configs;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.utils.Utils;

import java.util.HashMap;

public class Messages extends YAMLFile{
    private HashMap<String, String> messages;

    public Messages(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadMessages();
    }

    @Override
    public void reload(){
        super.reload();
        loadMessages();
    }



    private void loadMessages() {
        messages = new HashMap<>();
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, Utils.color(getConfig().getString("messages." + key)));
        }
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

}
