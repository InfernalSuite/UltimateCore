package mc.ultimatecore.souls;

import lombok.Getter;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.helper.database.DatabaseType;
import mc.ultimatecore.souls.commands.CommandManager;
import mc.ultimatecore.souls.configs.*;
import mc.ultimatecore.souls.database.Database;
import mc.ultimatecore.souls.database.implementations.MySQLDatabase;
import mc.ultimatecore.souls.database.implementations.SQLiteDatabase;
import mc.ultimatecore.souls.gui.AllSoulsGUI;
import mc.ultimatecore.souls.gui.SoulEditGUI;
import mc.ultimatecore.souls.listeners.*;
import mc.ultimatecore.souls.managers.AddonsManager;
import mc.ultimatecore.souls.managers.DatabaseManager;
import mc.ultimatecore.souls.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HyperSouls extends UltimatePlugin {
    
    private static HyperSouls INSTANCE;
    private Config configuration;
    private Messages messages;
    private Inventories inventories;
    private Souls souls;
    private CommandManager commandManager;
    private Map<Integer, AllSoulsGUI> allSoulsGUI;
    private SoulEditGUI soulEditGUI;
    private Database pluginDatabase;
    private DatabaseManager databaseManager;
    private AddonsManager addonsManager;
    private TiaSystem tiaSystem;
    
    public static HyperSouls getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void onEnable() {
        INSTANCE = this;
        
        loadConfigs();
        
        Credentials credentials = Credentials.fromConfig(configuration.getConfig());
        
        pluginDatabase = credentials.getDatabaseType() == DatabaseType.MYSQL ? new MySQLDatabase(this, credentials, "Player_Souls") : new SQLiteDatabase(this, credentials);
        
        pluginDatabase.createTablesAsync().thenRun(() -> databaseManager = new DatabaseManager(this));
        
        
        commandManager = new CommandManager(this);
        databaseManager = new DatabaseManager(this);
        addonsManager = new AddonsManager(this);
        
        registerListeners(new InventoryClickListener(), new SoulCollectListener(this), new SoulPlaceListener(), new SoulRemoveListener(), new PlayerJoinLeaveListener(this));
        
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Has been enabled! &fVersion: " + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Thanks for using my plugin!  &f~Reb4ck"));
        soulEditGUI = new SoulEditGUI();
        allSoulsGUI = new HashMap<>();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), this::addPages, 0L, 1200L);
    }
    
    @Override
    public void onDisable() {
        souls.save();
        databaseManager.disable();
        pluginDatabase.close();
        Bukkit.getOnlinePlayers().forEach(Player::closeInventory);
        getLogger().info(getDescription().getName() + " Disabled!");
    }
    
    private void addPages() {
        int size = (int) (Math.floor(getSouls().souls.size() / 45.0D) + 1.0D);
        for (int i = 1; i <= size; i++) {
            if (!allSoulsGUI.containsKey(i))
                allSoulsGUI.put(i, new AllSoulsGUI(i));
        }
    }
    
    public void registerListeners(Listener... listener) {
        Arrays.stream(listener).forEach(listener1 -> Bukkit.getPluginManager().registerEvents(listener1, this));
    }
    
    public void loadConfigs() {
        configuration = new Config(this, "config", true, false);
        messages = new Messages(this, "messages", true, false);
        inventories = new Inventories(this, "inventories", true, false);
        souls = new Souls(this, "souls", true, false);
        tiaSystem = new TiaSystem(this, "tiaSystem", true, false);
        
    }
    
    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        tiaSystem.reload();
        inventories.reload();
    }
    
    @Override
    public String getPluginName() {
        return getDescription().getName();
    }
}
