package mc.ultimatecore.collections;

import lombok.Getter;
import mc.ultimatecore.collections.api.HyperCollectionsAPI;
import mc.ultimatecore.collections.api.HyperCollectionsAPIImpl;
import mc.ultimatecore.collections.commands.CommandManager;
import mc.ultimatecore.collections.configs.Collections;
import mc.ultimatecore.collections.configs.Config;
import mc.ultimatecore.collections.configs.Inventories;
import mc.ultimatecore.collections.configs.Messages;
import mc.ultimatecore.collections.database.Database;
import mc.ultimatecore.collections.database.implementations.MySQLDatabase;
import mc.ultimatecore.collections.database.implementations.SQLiteDatabase;
import mc.ultimatecore.collections.listeners.InventoryClickListener;
import mc.ultimatecore.collections.listeners.PlayerJoinLeaveListener;
import mc.ultimatecore.collections.listeners.collections.CollectionsListener;
import mc.ultimatecore.collections.managers.AddonsManager;
import mc.ultimatecore.collections.managers.CollectionsManager;
import mc.ultimatecore.collections.objects.DebugType;
import mc.ultimatecore.collections.serializer.GSON;
import mc.ultimatecore.collections.utils.StringUtils;
import mc.ultimatecore.collections.utils.Utils;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.helper.database.DatabaseType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class HyperCollections extends UltimatePlugin {
    
    private static HyperCollections instance;
    private Config configuration;
    private Messages messages;
    private Collections collections;
    private Inventories inventories;
    private Economy economy;
    private CommandManager commandManager;
    private Database pluginDatabase;
    private CollectionsManager collectionsManager;
    private AddonsManager addonsManager;
    private HyperCollectionsAPI api;
    private GSON gson;
    
    public static HyperCollections getInstance() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        loadConfigs();
        loadDependencies();
        this.commandManager = new CommandManager("collections");
        this.gson = new GSON();
        Credentials credentials = Credentials.fromConfig(configuration.getConfig());
        this.pluginDatabase = credentials.getDatabaseType() == DatabaseType.MYSQL ? new MySQLDatabase(this, credentials) : new SQLiteDatabase(this, credentials);
        this.collectionsManager = new CollectionsManager(this);
        this.addonsManager = new AddonsManager(this);
        this.api = new HyperCollectionsAPIImpl(this);
        registerListeners(new CollectionsListener(this), new InventoryClickListener(), new PlayerJoinLeaveListener());
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e" + getDescription().getName() + " Has been enabled!"));
    }
    
    @Override
    public void onDisable() {
        collectionsManager.savePlayerDataOnDisable();
        collectionsManager.stopUpdating();
        getLogger().info(getDescription().getName() + " Disabled!");
    }
    
    public void sendErrorMessage(Exception e) {
        e.printStackTrace();
    }
    
    
    public void loadDependencies() {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null)
                this.economy = rsp.getProvider();
        }
    }
    
    public void loadConfigs() {
        configuration = new Config(this, "config", true, true);
        messages = new Messages(this, "messages", true, true);
        collections = new Collections(this, "collections", false, true);
        inventories = new Inventories(this, "inventories", true, true);
    }
    
    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        collections.reload();
        inventories.reload();
    }
    
    public void sendDebug(String message, DebugType debugType) {
        if (!configuration.isDebug()) return;
        if (debugType == DebugType.LOG)
            getLogger().info(message);
        else
            Bukkit.getConsoleSender().sendMessage(StringUtils.color(message));
    }
    
    @Override
    public String getPluginName() {
        return getDescription().getName();
    }
    
}
