package com.infernalsuite.ultimatecore.crafting.configs;

import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.objects.Category;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Categories extends YAMLFile {
    
    private final Map<String, Category> categories = new HashMap<>();
    
    public Categories(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadDefaults();
    }
    
    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }
    
    
    private void loadDefaults() {
        YamlConfiguration cf = getConfig();
        ConfigurationSection section = cf.getConfigurationSection("categories");
        for (String key : section.getKeys(false)) {
            try {
                if (!cf.getBoolean("categories." + key + ".enabled")) continue;
                String displayName = cf.getString("categories." + key + ".displayName", "Unknown");
                int slot = cf.getInt("categories." + key + ".slot");
                String material = cf.getString("categories." + key + ".item");
                categories.put(key, new Category(key, displayName, slot, material));
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(Utils.color("&e[HyperCrafting] &cError loading category " + key));
            }
        }
    }
    
    public Category getCategory(String key) {
        return categories.getOrDefault(key, null);
    }
    
    public Collection<Category> getAllCategories() {
        return categories.values();
    }
}
