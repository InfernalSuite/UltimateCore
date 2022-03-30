package com.infernalsuite.ultimatecore.talismans;

import lombok.Getter;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import com.infernalsuite.ultimatecore.helper.database.DatabaseType;
import com.infernalsuite.ultimatecore.talismans.commands.CommandManager;
import com.infernalsuite.ultimatecore.talismans.configs.Config;
import com.infernalsuite.ultimatecore.talismans.configs.Inventories;
import com.infernalsuite.ultimatecore.talismans.configs.Messages;
import com.infernalsuite.ultimatecore.talismans.configs.Talismans;
import com.infernalsuite.ultimatecore.talismans.database.Database;
import com.infernalsuite.ultimatecore.talismans.database.implementations.MySQLDatabase;
import com.infernalsuite.ultimatecore.talismans.database.implementations.SQLiteDatabase;
import com.infernalsuite.ultimatecore.talismans.listener.BlockPlaceListener;
import com.infernalsuite.ultimatecore.talismans.listener.DamageListener;
import com.infernalsuite.ultimatecore.talismans.listener.HealthListener;
import com.infernalsuite.ultimatecore.talismans.listener.PlayerJoinLeaveListener;
import com.infernalsuite.ultimatecore.talismans.managers.AddonsManager;
import com.infernalsuite.ultimatecore.talismans.managers.FileManager;
import com.infernalsuite.ultimatecore.talismans.managers.TalismanManager;
import com.infernalsuite.ultimatecore.talismans.managers.UserManager;
import com.infernalsuite.ultimatecore.talismans.objects.DebugType;
import com.infernalsuite.ultimatecore.talismans.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;

@Getter
public class HyperTalismans extends UltimatePlugin {
    
    private static HyperTalismans INSTANCE;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private FileManager fileManager;
    private UserManager userManager;
    private CommandManager commandManager;
    private AddonsManager addonsManager;
    private TalismanManager talismanManager;
    private Talismans talismans;
    private Database pluginDatabase;
    private boolean isSkills;
    
    public static HyperTalismans getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void onEnable() {
        INSTANCE = this;
        fileManager = new FileManager(this);
        loadConfigs();
        Credentials credentials = Credentials.fromConfig(configuration.getConfig());
        setupSkills();
        pluginDatabase = credentials.getDatabaseType() == DatabaseType.MYSQL ? new MySQLDatabase(this, credentials) : new SQLiteDatabase(this, credentials);
        this.addonsManager = new AddonsManager(this);
        this.userManager = new UserManager(this);
        this.commandManager = new CommandManager(this);
        this.talismanManager = new TalismanManager(this);
        registerListeners(new PlayerJoinLeaveListener(this), new HealthListener(), new DamageListener(), new BlockPlaceListener());
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Has been enabled!"));
    }
    
    private void setupSkills() {
        this.isSkills = Bukkit.getPluginManager().getPlugin("UltimateCore-Skills") != null;
    }
    
    @Override
    public void onDisable() {
        userManager.disable();
        pluginDatabase.close();
        Bukkit.getServer().getOnlinePlayers().forEach(HumanEntity::closeInventory);
        getLogger().info(getDescription().getName() + " Disabled!");
    }
    
    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
    }
    
    public void loadConfigs() {
        configuration = new Config(this, "config", true, false);
        messages = new Messages(this, "messages", true, false);
        inventories = new Inventories(this, "inventories", true, false);
        talismans = new Talismans(this, "talismans", true, false);
    }
    
    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        inventories.reload();
        talismans.reload();
    }
    
    public void sendDebug(String message, DebugType debugType) {
        if (!configuration.debug) return;
        if (debugType == DebugType.LOG)
            getLogger().info(message);
        else
            Bukkit.getConsoleSender().sendMessage(com.infernalsuite.ultimatecore.skills.utils.StringUtils.color(message));
    }
    
    @Override
    public String getPluginName() {
        return getDescription().getName();
    }
}
