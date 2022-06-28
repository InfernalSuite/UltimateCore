package mc.ultimatecore.collections.configs;

import lombok.Getter;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.Item;
import mc.ultimatecore.collections.utils.Utils;
import mc.ultimatecore.helper.files.YAMLFile;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Inventories extends YAMLFile {

    private HashMap<String, Item> levelMenuItems;
    //MAIN MENU
    private String mainMenuTitle;
    private int mainMenuSize;
    private Item mainMenuItem;
    private List<Item> mainMenu;
    private Item mainMenuBack;
    private boolean mainMenuBackEnabled;

    //TOP MENU
    private String topMenuTitle;
    private int topMenuSize;
    private Item topMenuItem;
    private List<Item> topMenu;

    //SUB MENU
    private int subMenuSize;
    private HashMap<String, String> subMenuTitles;
    private List<Integer> subMenuDecoration;
    private HashMap<String, Item> subMenuItems;

    //LEVELS MENU
    private String levelsMenuTitle;
    private int levelsMenuSize;
    private List<Integer> levelsMenuSlots;

    //BUTTONS
    private Item background;
    private Item previousPage;
    private Item closeButton;
    private boolean backButtons;

    public Inventories(HyperCollections plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadItems();
    }

    @Override
    public void reload() {
        super.reload();
        loadItems();
    }

    private void loadItems() {
        levelMenuItems = new HashMap<>();
        topMenu = new ArrayList<>();
        mainMenu = new ArrayList<>();
        subMenuItems = new HashMap<>();



        ConfigurationSection configurationSection = getConfig().getConfigurationSection("");

        if (configurationSection == null) return;
        for (String inventoryName : configurationSection.getKeys(false)) {
            ConfigurationSection newConfigSection = getConfig().getConfigurationSection(inventoryName + ".items");
            if (newConfigSection == null) continue;
            for (String item : newConfigSection.getKeys(false)) {
                if (item.equals("mainMenuItem"))
                    mainMenuItem = Utils.getItemFromConfig(getConfig(), inventoryName + ".items." + item);
                else if (item.equals("topMenuItem"))
                    topMenuItem = Utils.getItemFromConfig(getConfig(), inventoryName + ".items." + item);
                else if (inventoryName.equals("mainMenu"))
                    mainMenu.add(Utils.getItemFromConfig(getConfig(), inventoryName + ".items." + item));
                else if (inventoryName.equals("topMenu"))
                    topMenu.add(Utils.getItemFromConfig(getConfig(), inventoryName + ".items." + item));
                else if (inventoryName.equals("subMenu"))
                    subMenuItems.put(item, Utils.getItemFromConfig(getConfig(), inventoryName + ".items." + item));
                else if (inventoryName.equals("levelsMenu"))
                    levelMenuItems.put(item, Utils.getItemFromConfig(getConfig(), inventoryName + ".items." + item));
            }
        }
        //MAIN MENU
        mainMenuTitle = getConfig().getString("mainMenu.title");
        mainMenuSize = getConfig().getInt("mainMenu.size");
        //TOP MENU
        topMenuTitle = getConfig().getString("topMenu.title");
        topMenuSize = getConfig().getInt("topMenu.size");
        //SUB MENU
        subMenuSize = getConfig().getInt("subMenu.size");
        subMenuTitles = new HashMap<String, String>() {{
            for (String key : getConfig().getConfigurationSection("subMenu.titles").getKeys(false)) {
                put(key, getConfig().getString("subMenu.titles." + key));
            }
        }};
        subMenuDecoration = getConfig().getIntegerList("subMenu.decoration");
        //LEVELS MENU
        levelsMenuTitle = getConfig().getString("levelsMenu.title");
        levelsMenuSize = getConfig().getInt("levelsMenu.size");
        levelsMenuSlots = getConfig().getIntegerList("levelsMenu.itemsSlots");
        //BUTTONS
        background = Utils.getItemFromConfig(getConfig(), "background");
        previousPage = Utils.getItemFromConfig(getConfig(), "previousPage");
        closeButton = Utils.getItemFromConfig(getConfig(), "closeButton");
        backButtons = getConfig().getBoolean("backButtons");
        mainMenuBack = Utils.getItemFromConfig(getConfig(), "main-menu-back");
        mainMenuBackEnabled = getConfig().getBoolean("main-menu-back.enabled");
    }

    public Item getItem(String key) {
        return levelMenuItems.get(key);
    }

    public List<Item> getTopMenu() {
        return topMenu;
    }

    public List<Item> getMainMenu() {
        return mainMenu;
    }
}
