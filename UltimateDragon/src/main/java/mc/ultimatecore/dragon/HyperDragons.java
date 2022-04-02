package mc.ultimatecore.dragon;

import lombok.Getter;
import mc.ultimatecore.dragon.configs.*;
import mc.ultimatecore.dragon.database.Database;
import mc.ultimatecore.dragon.database.implementations.MySQLDatabase;
import mc.ultimatecore.dragon.database.implementations.SQLiteDatabase;
import mc.ultimatecore.dragon.listeners.DragonListener;
import mc.ultimatecore.dragon.listeners.InventoryClickListener;
import mc.ultimatecore.dragon.listeners.PlayerJoinListener;
import mc.ultimatecore.dragon.listeners.PlayerListener;
import mc.ultimatecore.dragon.managers.*;
import mc.ultimatecore.dragon.nms.*;
import mc.ultimatecore.dragon.objects.others.DebugType;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.helper.database.DatabaseType;
import mc.ultimatecore.helper.objects.messages.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;

@Getter
public class HyperDragons extends UltimatePlugin {

    /*
     * This block prevents the Maven Shade plugin to remove the specified classes
     */
    static {
        @SuppressWarnings("unused") Class<?>[] classes = new Class<?>[]{
                v1_8_R3.class,
                v1_12_R1.class,
                v1_14_R1.class,
                v1_15_R1.class,
                v1_16_R3.class,
                v1_17_R1.class,
                v1_18_R1.class,
                v1_18_R2.class,
        };
    }

    //Files
    private Configuration configuration;
    private Messages messages;
    private Dragons dragons;
    private Rewards rewards;
    private Inventories inventories;
    private Structures structures;
    private static HyperDragons INSTANCE;
    private NMS nms;
    private Database database;
    private DragonEvents dragonEvents;
    private DragonGuardians dragonGuardians;
    private SetupManager setupManager;

    //Managers
    private AddonsManager addonsManager;
    private DragonManager dragonManager;
    private SchematicManager schematicManager;
    private CommandsManager commandsManager;
    private DatabaseManager databaseManager;

    public static HyperDragons getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        //NMS
        this.nms = setupNMS();
        if (this.nms == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        //Files
        loadFiles();
        //Database
        Credentials credentials = Credentials.fromConfig(configuration.getConfig());
        database = credentials.getDatabaseType() == DatabaseType.MYSQL ? new MySQLDatabase(this, credentials) : new SQLiteDatabase(this, credentials);
        //Managers
        addonsManager = new AddonsManager(this);
        schematicManager = new SchematicManager(this);
        commandsManager = new CommandsManager(this);
        dragonManager = new DragonManager(this);
        databaseManager = new DatabaseManager(this);
        setupManager = new SetupManager(this);
        dragonManager.setPlayMode();
        //Listeners
        registerListeners(new DragonListener(this), new PlayerListener(this), new PlayerJoinListener(this), new InventoryClickListener());
        sendConsoleMessage("Has been enabled!", MessageType.COLORED);
    }


    public void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }


    private NMS setupNMS() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return (NMS) Class.forName("mc.ultimatecore.dragon.nms." + version).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getLogger().warning("Un-Supported Minecraft Version: " + version);
        }
        return null;
    }


    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(Player::closeInventory);
        if (schematicManager != null) schematicManager.save();
        if (structures != null) structures.save();
        if (dragonGuardians != null) dragonGuardians.save();
        if (databaseManager != null) databaseManager.disable();
    }

    public void reloadFiles() {
        rewards.reload();
        dragonGuardians.reload();
        dragonEvents.reload();
        configuration.reload();
        messages.reload();
        dragons.reload();
        structures.reload();
    }

    public void loadFiles() {
        rewards = new Rewards(this, "rewards", true, false);
        dragonGuardians = new DragonGuardians(this, "guardians", true, false);
        dragonEvents = new DragonEvents(this, "dragonevent", false, false);


        inventories = new Inventories();

        configuration = new Configuration(this, "config", true, false);
        messages = new Messages(this, "messages", true, false);
        dragons = new Dragons(this, "dragons", false, false);
        structures = new Structures(this, "structures", true, true);
    }

    public void sendDebug(String message, DebugType debugType) {
        if (!configuration.debug) return;
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