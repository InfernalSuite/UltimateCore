package com.infernalsuite.ultimatecore.runes;

import com.infernalsuite.ultimatecore.runes.commands.CommandManager;
import com.infernalsuite.ultimatecore.runes.configs.*;
import com.infernalsuite.ultimatecore.runes.gui.RuneGUI;
import com.infernalsuite.ultimatecore.runes.listeners.PlayerJoinLeaveListener;
import com.infernalsuite.ultimatecore.runes.listeners.runes.ArrowEffects;
import com.infernalsuite.ultimatecore.runes.listeners.runes.RuneTableListener;
import com.infernalsuite.ultimatecore.runes.listeners.runes.SwordEffects;
import com.infernalsuite.ultimatecore.runes.listeners.skills.HyperRunesListener;
import com.infernalsuite.ultimatecore.runes.managers.RunesManager;
import com.infernalsuite.ultimatecore.runes.utils.StringUtils;
import lombok.Getter;
import mc.ultimatecore.runes.configs.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HyperRunes extends JavaPlugin {
    
    private static HyperRunes instance;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private Runes runes;
    private CommandManager commandManager;
    private FileManager fileManager;
    private RunesManager runesManager;
    private RuneGUI runesGUI;
    private boolean hyperSkills;
    
    public static HyperRunes getInstance() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        loadConfigs();
        commandManager = new CommandManager("hyperrunes");
        
        this.runesManager = new RunesManager();
        runesGUI = new RuneGUI();
        hyperSkills = Bukkit.getPluginManager().getPlugin("UltimateCore-Skills") != null;
        registerListeners(new ArrowEffects(), new SwordEffects(), new RuneTableListener(), new PlayerJoinLeaveListener());
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[" + getDescription().getName() + "] Has been enabled!"));
        
    }
    
    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers())
            p.closeInventory();
        getLogger().info(getDescription().getName() + " Disabled!");
        
    }
    
    public void sendErrorMessage(Exception e) {
        e.printStackTrace();
    }
    
    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
        if (hyperSkills) Bukkit.getPluginManager().registerEvents(new HyperRunesListener(), this);
    }
    
    public void loadConfigs() {
        configuration = new Config(this, "config");
        messages = new Messages(this, "messages");
        runes = new Runes(this, "runes");
        inventories = new Inventories(this, "inventories");
        configuration.enable();
        messages.enable();
        runes.enable();
        inventories.enable();
    }
    
    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        runes.reload();
        inventories.reload();
    }
}
