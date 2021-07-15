package mc.ultimatecore.enchantment;

import lombok.Getter;
import mc.ultimatecore.enchantment.api.HyperEnchantsAPI;
import mc.ultimatecore.enchantment.api.HyperEnchantsAPIImpl;
import mc.ultimatecore.enchantment.commands.CommandManager;
import mc.ultimatecore.enchantment.configs.*;
import mc.ultimatecore.enchantment.handlers.EnchantsHandler;
import mc.ultimatecore.enchantment.listener.AureliumSkillsListener;
import mc.ultimatecore.enchantment.listener.CommandsListener;
import mc.ultimatecore.enchantment.listener.EnchantingTableListener;
import mc.ultimatecore.enchantment.managers.AddonsManager;
import mc.ultimatecore.enchantment.managers.ItemManager;
import mc.ultimatecore.enchantment.playerdata.PlayersData;
import mc.ultimatecore.enchantment.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class EnchantmentsPlugin extends JavaPlugin {
    private static EnchantmentsPlugin instance;
    private Config configuration;
    private Messages messages;
    private HyperEnchantments hyperEnchantments;
    private Inventories inventories;
    private PlayersData playersData;
    private CommandManager commandManager;
    private AddonsManager addonsManager;
    private Economy economy;
    private FileManager fileManager;
    private boolean advancedEnchantments;
    private boolean ecoEnchants;
    private EnchantsHandler enchantsHandler;
    private Commands commands;
    private HyperEnchantsAPI api;

    public static EnchantmentsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        fileManager = new FileManager(this);

        enchantsHandler = new EnchantsHandler(this);

        setupAddons();

        loadConfigs();

        registerListeners(new EnchantingTableListener(), new ItemManager(), new CommandsListener(this));

        hookAurelium();

        commandManager = new CommandManager("hyperenchants");

        addonsManager = new AddonsManager(this);

        playersData = new PlayersData();

        api = new HyperEnchantsAPIImpl(this);

        Bukkit.getConsoleSender().sendMessage(Utils.color("&e"+getDescription().getName()+" Has been enabled! &fVersion: " + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&e"+ getDescription().getName() +" Thanks for using my plugin!  &f~Reb4ck"));
        setupEconomy();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        getLogger().info(getDescription().getName() + " Disabled!");
    }

    public void registerListeners(Listener... listener) {
        for (Listener l : listener)
            Bukkit.getPluginManager().registerEvents(l, this);
    }


    private void hookAurelium(){
        if(Bukkit.getPluginManager().getPlugin("AureliumSkills") != null){
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e["+getDescription().getName()+"] &aSuccessfully hooked with AureliumSkills!"));
            registerListeners(new AureliumSkillsListener(this));
        }
    }

    private void setupEconomy(){
        if (Bukkit.getPluginManager().getPlugin("Vault") != null){
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null)
                this.economy = rsp.getProvider();
        }
    }

    private void setupAddons(){
        if (Bukkit.getPluginManager().getPlugin("AdvancedEnchantments") != null){
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e"+ getDescription().getName() +" &aSuccessfully hooked into AdvancedEnchantments!"));
            this.advancedEnchantments = true;
        }
        if (Bukkit.getPluginManager().getPlugin("EcoEnchants") != null){
            Bukkit.getConsoleSender().sendMessage(Utils.color("&e"+ getDescription().getName() +" &aSuccessfully hooked into EcoEnchants!"));
            this.ecoEnchants = true;
        }
    }

    public void loadConfigs() {
        configuration = new Config(this, "config");
        messages = new Messages(this, "messages");
        inventories = new Inventories(this, "inventories");
        hyperEnchantments = new HyperEnchantments(this, "hyperEnchants");
        commands = new Commands(this, "commands");
        configuration.enable();
        hyperEnchantments.enable();
        messages.enable();
        inventories.enable();
        commands.enable();
    }

    public void reloadConfigs() {
        configuration.reload();
        hyperEnchantments.reload();
        messages.reload();
        inventories.reload();
        commands.reload();
    }
}
