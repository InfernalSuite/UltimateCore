package com.infernalsuite.ultimatecore.dragon.configs;

import lombok.Getter;
import com.infernalsuite.ultimatecore.dragon.objects.HyperDragon;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IHyperDragon;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class Dragons extends YAMLFile {
    @Getter
    private final Map<String, IHyperDragon> dragonMap = new HashMap<>();

    public Dragons(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDragons();
    }


    @Override
    public void reload(){
        super.reload();
        this.loadDragons();
    }


    private void loadDragons() {
        ConfigurationSection section = getConfig().getConfigurationSection("dragons");
        int loaded = 0;
        if(section == null) return;
        for(String key : section.getKeys(false)) {
            if(!getConfig().getBoolean("dragons." + key + ".enabled")) continue;
            String displayName = getConfig().getString("dragons." + key + ".displayName");
            double health = getConfig().getDouble("dragons." + key + ".health");
            double chance = getConfig().getDouble("dragons." + key + ".chance");
            double xp = getConfig().getDouble("dragons." + key + ".xp");
            dragonMap.put(key, new HyperDragon(key, health, displayName, chance, xp));
            loaded++;
        }
        if(loaded == 0) return;
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[UltimateCore-Dragon] &aSuccessfully loaded "+loaded+" dragons!"));
    }
}
