package mc.ultimatecore.trades;

import lombok.Getter;
import mc.ultimatecore.trades.commands.CommandManager;
import mc.ultimatecore.trades.configs.*;
import mc.ultimatecore.trades.gui.TradingOptionsGUI;
import mc.ultimatecore.trades.listeners.InventoryClickListener;
import mc.ultimatecore.trades.listeners.TradeSetupListener;
import mc.ultimatecore.trades.objects.TradeObject;
import mc.ultimatecore.trades.utils.StringUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

@Getter
public class HyperTrades extends JavaPlugin {
    private static HyperTrades instance;
    private Config configuration;
    private Messages messages;
    private FileManager fileManager;
    private Inventories inventories;
    private CommandManager commandManager;
    private Economy economy;
    private TradesManager tradesManager;
    private HashMap<TradeObject, TradingOptionsGUI> tradeShopGUI;

    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        loadConfigs();
        tradeShopGUI = new HashMap<>();
        commandManager = new CommandManager("hypertrades");

        registerListeners(new InventoryClickListener(), new TradeSetupListener());
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), this::saveTradesManager, 0L, (1200 * 60));
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Has been enabled! &fVersion: " + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e" + getDescription().getName() + " Thanks for using my plugin!  &f~Reb4ck"));

        if (Bukkit.getPluginManager().getPlugin("Vault") != null){
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null)
                this.economy = rsp.getProvider();
        }
    }

    @Override
    public void onDisable() {
        if (tradesManager != null)
            tradesManager.save();
        getLogger().info(getDescription().getName() + " Disabled!");

    }

    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
    }

    public void saveTradesManager(){
        if (this.tradesManager != null)
            this.tradesManager.save();
    }

    public void loadConfigs() {
        configuration = new Config(this, "config");
        messages = new Messages(this, "messages");
        inventories = new Inventories(this, "inventories");
        tradesManager = new TradesManager(this, "tradesManager");

        configuration.enable();
        messages.enable();
        inventories.enable();
        tradesManager.enable();
    }

    public void reloadConfigs() {
        configuration.reload();
        messages.reload();
        inventories.reload();
        tradesManager.reload();
    }

    public static HyperTrades getInstance(){
        return instance;
    }
}
