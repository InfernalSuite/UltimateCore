package com.infernalsuite.ultimatecore.menu;

import lombok.Getter;
import com.infernalsuite.ultimatecore.menu.commands.CommandManager;
import com.infernalsuite.ultimatecore.menu.configs.Config;
import com.infernalsuite.ultimatecore.menu.configs.FileManager;
import com.infernalsuite.ultimatecore.menu.configs.Inventories;
import com.infernalsuite.ultimatecore.menu.configs.Messages;
import com.infernalsuite.ultimatecore.menu.listeners.InventoryClickListener;
import com.infernalsuite.ultimatecore.menu.listeners.PlayerJoinLeaveListener;
import com.infernalsuite.ultimatecore.menu.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HyperCore extends JavaPlugin {
    
    private static HyperCore instance;
    private Config configuration;
    private Messages messages;
    private FileManager fileManager;
    private Inventories inventories;
    private CommandManager commandManager;
    private PlayersData playersData;
    private boolean pets;
    
    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        loadConfigs();
        commandManager = new CommandManager("hypercore");
        
        playersData = new PlayersData();
        
        pets = Bukkit.getPluginManager().getPlugin("UltimateCore-Pets") != null;
        
        registerListeners(new InventoryClickListener(), new PlayerJoinLeaveListener());
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Has been enabled!"));
    }
    
    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " Disabled!");
    }
    
    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
    }
    
    
    public void loadConfigs() {
        messages = new Messages(this, "messages");
        inventories = new Inventories(this, "inventories");
        configuration = new Config(this, "config");
        
        configuration.enable();
        messages.enable();
        inventories.enable();
    }
    
    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        inventories.reload();
    }
    
    public static HyperCore getInstance() {
        return instance;
    }
}
