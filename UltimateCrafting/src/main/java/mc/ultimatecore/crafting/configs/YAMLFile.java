package mc.ultimatecore.crafting.configs;

import lombok.Getter;
import mc.ultimatecore.crafting.HyperCrafting;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class YAMLFile {
    @Getter
    private final YamlConfiguration config;

    private final File file;

    public YAMLFile(HyperCrafting plugin, String name, boolean defaults) {
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
        Reader reader = new InputStreamReader(plugin.getResource(name + ".yml"), StandardCharsets.UTF_8);
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
        } catch (IOException |org.bukkit.configuration.InvalidConfigurationException ignored) {}
    }

    public void reload() {
        try {
            this.config.load(this.file);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public boolean save() {
        try {
            this.config.save(this.file);
        } catch (IOException ignored) {}
        return true;
    }
}
