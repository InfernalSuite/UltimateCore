package com.infernalsuite.ultimatecore.enchantment.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages extends YAMLFile{
    private Map<String, String> messages;

    @Getter
    private List<String> incompatibilityError;

    public Messages(EnchantmentsPlugin enchantmentsPlugin, String name) {
        super(enchantmentsPlugin, name);
    }

    @Override
    public void enable(){
        super.enable();
        this.loadMessages();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadMessages();
    }


    private void loadMessages() {
        messages = new HashMap<>();
        for (String key : getConfig().get().getConfigurationSection("messages").getKeys(false))
            messages.put(key, Utils.color(getConfig().get().getString("messages." + key)));
        incompatibilityError = getConfig().get().getStringList("incompatibilityError");
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

}
