package mc.ultimatecore.collections.managers;

import lombok.Getter;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.addons.ClipPlaceholderAPIManager;
import mc.ultimatecore.collections.addons.MVDWPlaceholderAPIManager;
import mc.ultimatecore.collections.addons.VaultAPIManager;
import mc.ultimatecore.collections.listeners.addons.JetMinionsListener;
import mc.ultimatecore.collections.listeners.addons.UltraMinionsListener;
import mc.ultimatecore.collections.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class AddonsManager {
    
    private final HyperCollections plugin;
    @Getter
    private ClipPlaceholderAPIManager placeholderAPI;
    @Getter
    private MVDWPlaceholderAPIManager mvdwPlaceholderAPI;
    @Getter
    private VaultAPIManager vaultAPIManager;
    
    public AddonsManager(HyperCollections plugin) {
        this.plugin = plugin;
        load();
    }
    
    public void load() {
        String name = plugin.getDescription().getName();
        if (isPlugin("PlaceholderAPI")) {
            placeholderAPI = new ClipPlaceholderAPIManager(plugin);
            placeholderAPI.register();
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into PlaceholderAPI!"));
        }
        if (isPlugin("MVDWPlaceholderAPI")) {
            mvdwPlaceholderAPI = new MVDWPlaceholderAPIManager(plugin);
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into MVDWPlaceholderAPI!"));
        }
        if (isPlugin("Vault")) {
            vaultAPIManager = new VaultAPIManager(plugin);
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into Vault!"));
        }
        if (isPlugin("JetsMinions") && plugin.getConfiguration().isJetMinions()) {
            registerListeners(new JetMinionsListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into JetMinions!"));
        }
        if (isPlugin("UltraMinions") && plugin.getConfiguration().isUltraMinions()) {
            registerListeners(new UltraMinionsListener(plugin));
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + name + "] &aSuccessfully hooked into UltraMinions!"));
        }
    }
    
    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, plugin);
    }
    
    private boolean isPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }
    
}
