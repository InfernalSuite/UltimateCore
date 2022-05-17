package mc.ultimatecore.enchantment.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.Item;
import mc.ultimatecore.enchantment.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class Inventories extends YAMLFile{
    public String mainMenuTitle = "&8Main Menu";
    public int mainMenuSize = 54;
    //MAIN MENU ENCHANTING
    public Item enchantingTable = new Item(XMaterial.ENCHANTING_TABLE, 28, 1, "&aEnchant Item", Arrays.asList("&7Add an item to the slot above to", "&7view enchantment options"));

    public Item emptyItem = new Item(XMaterial.GRAY_DYE, 1, "&cEnchant Item", Arrays.asList("&7Place an item in the open slot", "&7to enchant it"));

    public Item errorItem = new Item(XMaterial.RED_DYE, 1, "&cCannot Enchant Item!", Collections.singletonList("%error_placeholder%"));

    public Item bookShelfItem = new Item(XMaterial.BOOKSHELF, 48,1, "&dBookshelf Power",
            Arrays.asList("&7Stronger enchantments require", "&7more bookshelf power which can", "&7be increased by placing", "&7bookshelves nearby.", "", "&7Current Bookshelf Power: &d%bookshelf_power%"));

    public Item availableBook = new Item(XMaterial.ENCHANTED_BOOK, 1, "&a%enchant_name%", Arrays.asList("&7%enchant_description%", "", "&eClick to view!"));
    public Item notAvailableBook = new Item(XMaterial.ENCHANTED_BOOK, 1, "&c%enchant_name%", Arrays.asList("&7%enchant_description%", "", "&cRequires %bookshelf_required% power!"));

    public List<Integer> bookSlots = Arrays.asList(12,13,14,15,16,21,22,23,24,25,30,31,32,33,34);

    //SUBMENU
    public Item levelEnchantBook = new Item(XMaterial.ENCHANTED_BOOK, 1, "&fEnchanted Book", Arrays.asList("&9%enchant_name% %enchant_level%", "&7%enchant_description%", "", "&7Cost: &3%enchant_cost%", "", "%state%"));
    public Item moneyEnchantBook = new Item(XMaterial.ENCHANTED_BOOK, 1, "&fEnchanted Book", Arrays.asList("&9%enchant_name% %enchant_level%", "&7%enchant_description%", "", "&7Cost: &3$%enchant_cost%", "", "%state%"));



    public Map<Integer, List<Integer>> levelSlots = new HashMap<Integer, List<Integer>>(){{
        put(1, Collections.singletonList(22));
        put(2, Arrays.asList(21,22));
        put(3, Arrays.asList(20,21,22));
        put(4, Arrays.asList(20,21,22,23));
        put(5, Arrays.asList(20,21,22,23,24));
    }};

    public Item backPageButton = new Item(XMaterial.PLAYER_HEAD, 17, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRhNTY2N2VmNzI4NWM5MjI1ZmMyNjdkNDUxMTdlYWI1NDc4Yzc4NmJkNWFmMGExOTljMjlhMmMxNGMxZiJ9fX0=", 1, "&aBack Page", Collections.singletonList("Page %previous_page%"));
    public Item nextPageButton = new Item(XMaterial.PLAYER_HEAD, 35, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFiNjJkYjVjMGEzZmExZWY0NDFiZjcwNDRmNTExYmU1OGJlZGY5YjY3MzE4NTNlNTBjZTkwY2Q0NGZiNjkifX19",1, "&aNext Page", Collections.singletonList("Page %next_page%"));


    //BUTTONS
    public Item previousPage = new Item(XMaterial.ARROW, 48, 1, "&ePrevious Page", new ArrayList<>());

    public Item background = new Item(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());

    public Item closeButton = new Item(XMaterial.BARRIER, 49, 1, "&cClose Menu", new ArrayList<>());

    public Inventories(EnchantmentsPlugin enchantmentsPlugin, String name) {
        super(enchantmentsPlugin, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadItems();
    }

    @Override
    public void reload(){
        super.reload();
        loadItems();
    }

    private void loadItems() {
        //MAIN MENU
        mainMenuTitle = getConfig().get().getString("mainMenu.title");
        mainMenuSize = getConfig().get().getInt("mainMenu.size");
        enchantingTable = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.enchantingTable");
        enchantingTable = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.enchantingTable");
        emptyItem = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.emptyItem");
        errorItem = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.errorItem");
        bookShelfItem = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.bookShelfItem");
        availableBook = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.availableBook");
        notAvailableBook = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.notAvailableBook");
        //SUBMENU
        levelEnchantBook = Utils.getItemFromConfig(getConfig().get(), "subMenu.items.levelEnchantBook");
        moneyEnchantBook = Utils.getItemFromConfig(getConfig().get(), "subMenu.items.moneyEnchantBook");
        bookSlots = getConfig().get().getIntegerList("mainMenu.bookSlots");
        //BUTTONS
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");
        previousPage = Utils.getItemFromConfig(getConfig().get(), "buttons.previousPage");
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        backPageButton = Utils.getItemFromConfig(getConfig().get(), "buttons.backPageButton");
        nextPageButton = Utils.getItemFromConfig(getConfig().get(), "buttons.nextPageButton");
        ConfigurationSection section = getConfig().get().getConfigurationSection("subMenu.levelSlots");
        if(section == null) return;
        for(String key : section.getKeys(false))
            levelSlots.put(Integer.valueOf(key), getConfig().get().getIntegerList("subMenu.levelSlots."+key));
    }
}
