package mc.ultimatecore.talismans;

import lombok.Getter;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.helper.database.DatabaseType;
import mc.ultimatecore.talismans.commands.CommandManager;
import mc.ultimatecore.talismans.configs.Config;
import mc.ultimatecore.talismans.configs.Inventories;
import mc.ultimatecore.talismans.configs.Messages;
import mc.ultimatecore.talismans.configs.Talismans;
import mc.ultimatecore.talismans.database.Database;
import mc.ultimatecore.talismans.database.implementations.MySQLDatabase;
import mc.ultimatecore.talismans.database.implementations.SQLiteDatabase;
import mc.ultimatecore.talismans.listener.BlockPlaceListener;
import mc.ultimatecore.talismans.listener.DamageListener;
import mc.ultimatecore.talismans.listener.HealthListener;
import mc.ultimatecore.talismans.listener.PlayerJoinLeaveListener;
import mc.ultimatecore.talismans.managers.AddonsManager;
import mc.ultimatecore.talismans.managers.FileManager;
import mc.ultimatecore.talismans.managers.TalismanManager;
import mc.ultimatecore.talismans.managers.UserManager;
import mc.ultimatecore.talismans.objects.DebugType;
import mc.ultimatecore.talismans.utils.StringUtils;
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
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Has been enabled! &fVersion: " + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Thanks for using my plugin!  &f~Reb4ck"));
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
            Bukkit.getConsoleSender().sendMessage(mc.ultimatecore.skills.utils.StringUtils.color(message));
    }
    
    @Override
    public String getPluginName() {
        return getDescription().getName();
    }
}
