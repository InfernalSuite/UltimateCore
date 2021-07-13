package mc.ultimatecore.trades.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.trades.HyperTrades;
import mc.ultimatecore.trades.Item;
import mc.ultimatecore.trades.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class Inventories extends YAMLFile{

    //MAIN MENU
    public String tradesGUITitle;
    public int tradesGUISize;

    public Item unlockedTrade;
    public Item lockedTrade;

    public Item mainMenuPreviousPage;
    public Item mainMenuNextPage;

    public Item closeButton;

    public Set<Integer> tradesGUIExcludedSlots;

    public Item background;

    public String selectItemTitle = "&8Select Trade Item.";
    public int selectItemSize = 45;

    public Item mainMenuBack;
    public boolean mainMenuBackEnabled;

    public Item tradingOptionsPreviousPage;

    public Item goBack = new Item(XMaterial.BARRIER, 49, 1, "&c&l✖ &cClose", new ArrayList<>());


    public String tradeOptionsGUITitle;
    public int tradeOptionsGUISize;

    public Set<Item> tradingOptionsGUI;

    /*
    TRADE CREATION GUI
     */
    public String tradeSetupGUITitle = "&8Trade Setup";
    public int tradeSetupGUISize = 54;

    public Item tradeKey = new Item(XMaterial.NAME_TAG, 4, 1,
            "&aKey", Arrays.asList("&7Just the key that this trade have", "&7this is unique for each", "&7trade.", "&7Key: &a%key%"));

    public Item tradeItem = new Item(XMaterial.EMERALD, 12, 1,
            "&aTrade Item", Arrays.asList("&7This is the item that player", "&7will receive after", "&7trade.", "", "&e ► Click to change"));

    public Item costItem = new Item(XMaterial.EMERALD, 14, 1,
            "&aCost Item", Arrays.asList("&7This is the item will be removed", "&7from player inventory after", "&7trade.", "", "&e ► Click to change"));

    public Item tradePermission = new Item(XMaterial.PAPER, 10, 1,
            "&aTrade Permission", Arrays.asList("&7This is the required permission", "&7to use this trade", "", "&7Permission: &a%permission%", "","&e ► Click to change"));

    public Item moneyCost = new Item(XMaterial.GOLD_INGOT, 16, 1,
            "&aMoney Cost (Optional)", Arrays.asList("&7This is the required money", "&7to use this trade, money", "&7will be removed after trade.", "", "&7Money Cost: &a%money_cost%", "", "&e ► Click to change"));


    public Item tradePage = new Item(XMaterial.BOOK, 29, 1,
            "&aTrade Page", Arrays.asList("&7This is the gui page", "&7where trade will appear", "", "&7Page: &a%page%", "", "&e ► Click to change"));

    public Item tradeSlot = new Item(XMaterial.ENDER_PEARL, 31, 1,
            "&aTrade Slot", Arrays.asList("&7This is the gui slot", "&7where trade will appear", "", "&7Slot: &a%slot%", "", "&e ► Click to change"));

    public Item categorySlot = new Item(XMaterial.DIAMOND, 33, 1,
            "&aCategory", Arrays.asList("&7This is the category", "&7where trade will appear", "", "&7Category: &a%category%", "", "&e ► Click to change"));


    public Inventories(HyperTrades hyperTrades, String name) {
        super(hyperTrades, name);
    }

    @Override
    public void enable(){
        super.enable();
        this.loadDefaults();
    }

    @Override
    public void reload() {
        getConfig().reload();
        this.loadDefaults();
    }

    private void loadDefaults() {
        tradesGUISize = getConfig().get().getInt("tradesGUI.size");
        tradesGUITitle = getConfig().get().getString("tradesGUI.title");

        unlockedTrade = Utils.getItemFromConfig(getConfig().get(), "tradesGUI.items.unlockedTrade");
        lockedTrade = Utils.getItemFromConfig(getConfig().get(), "tradesGUI.items.lockedTrade");
        tradesGUIExcludedSlots = new HashSet<>(getConfig().get().getIntegerList("tradesGUI.excludedSlots"));
        mainMenuPreviousPage = Utils.getItemFromConfig(getConfig().get(), "tradesGUI.items.previousPage");
        mainMenuNextPage = Utils.getItemFromConfig(getConfig().get(), "tradesGUI.items.nextPage");

        //buttons
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");

        //options gui
        tradeOptionsGUISize = getConfig().get().getInt("tradeOptionsGUI.size");
        tradeOptionsGUITitle = getConfig().get().getString("tradeOptionsGUI.title");
        tradingOptionsPreviousPage = Utils.getItemFromConfig(getConfig().get(), "tradeOptionsGUI.items.previousPage");

        mainMenuBack = Utils.getItemFromConfig(getConfig().get(), "main-menu-back");
        mainMenuBackEnabled = getConfig().get().getBoolean("main-menu-back.enabled");

        ConfigurationSection section = getConfig().get().getConfigurationSection("tradeOptionsGUI.tradeItems");
        if(section == null) return;
        tradingOptionsGUI = new HashSet<>();
        section.getKeys(false).forEach(key ->  tradingOptionsGUI.add(Utils.getItemFromConfig(getConfig().get(), "tradeOptionsGUI.tradeItems."+key)));
    }
}
