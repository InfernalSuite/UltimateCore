package com.infernalsuite.ultimatecore.helper.files;

import lombok.Getter;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Getter
public class YAMLFile {

    private final YamlConfiguration config;

    private final File file;

    private final String name;

    private final boolean save;

    public YAMLFile(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.save = save;
        InputStream inputStream = plugin.getResource(name + ".yml");
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(reader);
        try {
            if (!this.file.exists()) {
                this.config.options().header(loadConfiguration.options().header());
                this.config.addDefaults(loadConfiguration);
                this.config.options().copyDefaults(true);
                this.config.save(this.file);
            } else {
                if (defaults) {
                    this.config.addDefaults(loadConfiguration);
                    this.config.options().copyDefaults(true);
                    this.config.save(this.file);
                }
                this.config.load(this.file);
            }
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException ignored) {
        }
    }

    public void reload() {
        try {
            this.config.load(this.file);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public YamlConfiguration get() {
        return config;
    }
}
