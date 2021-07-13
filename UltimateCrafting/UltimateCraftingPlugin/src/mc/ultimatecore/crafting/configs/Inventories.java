package mc.ultimatecore.crafting.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.Item;
import mc.ultimatecore.crafting.utils.Utils;

import java.util.*;

public class Inventories extends YAMLFile{
    public String mainMenuTitle = "&8Crafting Table";
    public int mainMenuSize = 54;
    public String recipePreviewTitle = "&8Recipe Preview";
    public int recipePreviewSize = 54;
    public int resultSlot;
    public Item previewDecorationItem;

    public List<Integer> recipePreviewSlots;

    public Set<Integer> recipePreviewDecoration;

    public Set<Integer> decorationSlots;

    public List<Integer> craftingSlots;

    public List<Integer> availableRecipesSlots;

    public Item previewbackPageButton;


    public Set<Integer> successSlots ;

    public Item recipeNotFound = new Item(XMaterial.BARRIER, 23, 1, "&cRecipe not found", new ArrayList<>());

    public Item recipeNoPermission = new Item(XMaterial.BARRIER, 23, 1, "&cYou haven't unlock this yet!", new ArrayList<>());

    public Item background = new Item(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());

    public Item closeButton = new Item(XMaterial.BARRIER, 49, 1, "&cClose Menu", new ArrayList<>());

    public Item recipeItemMenu = new Item(XMaterial.STONE, 1, "&a%recipe_name%", Arrays.asList("", "&eClick to edit this recipe"));

    public Item nextPage = new Item(XMaterial.ARROW, 1, "&eNext Page", new ArrayList<>());

    public Item previousPage = new Item(XMaterial.ARROW, 1, "&ePrevious Page", new ArrayList<>());

    public Item permissionEditItem = new Item(XMaterial.PAPER, 9, 1, "&eRecipe Permission", Arrays.asList("", "&aCurrent permission: &7%permission%", "", "  &a• Click to change permission"));

    public Item itemsEditItem = new Item(XMaterial.ANVIL, 11, 1, "&eRecipe Crafting", Arrays.asList("", "  &a• Click to change recipe items"));

    public Item itemPreview = new Item(XMaterial.CRAFTING_TABLE, 13, 1, "&eRecipe Preview", Arrays.asList("", "  &a• Click to view recipe preview"));

    public Item recipesSlot = new Item(XMaterial.GOLD_NUGGET, 15, 1, "&eRecipe GUI Slot", Arrays.asList("", "&aCurrent slot: &7%slot%", "", "  &a• Click to change slot"));

    public Item recipesCategory = new Item(XMaterial.EMERALD, 17, 1, "&eRecipe GUI Category", Arrays.asList("", "&aCurrent category: &7%category%", "", "  &a• Click to change category"));

    public Item recipesPage = new Item(XMaterial.BOOK, 29, 1, "&eRecipe GUI Page", Arrays.asList("", "&aCurrent page: &7%page%", "", "  &a• Click to change page"));

    public Item overrideRecipe = new Item(XMaterial.DIAMOND, 33, 1, "&eOverride Recipe", Arrays.asList("", "&7Override: &6%isOverride%", "", "&7Enabling this the recipe will", "&7appear only in auto-recipes", "&7and in the recipe book", "&7but won't be possible craft it.", "", "  &a• Click to change switch"));


    public Item giveItem = new Item(XMaterial.CRAFTING_TABLE, 13, 1, "&a%name%", Collections.singletonList("%lore%"));

    //RECIPE BOOK
    public Item recipeBook;
    public Item categoryItem;
    public Item backMainMenu;
    public Item bookCloseButton;
    public Item bookDecoration;
    public Set<Integer> bookSlots;
    public int bookSize;
    public String bookTitle;
    //CATEGORY GUI
    public Set<Integer> itemSlots;
    public Item categoryDecoration;
    public Set<Integer> categoryDecorationSlots;
    public Item categoryCloseButton;
    public Item categoryBackMainMenuButton;
    public Item categoryNextPage;
    public Item categoryBackPage;
    public Item recipeUnlocked;
    public Item recipeLocked;
    public Item categoryGUIItem;
    public int categorySize;
    public String categoryTitle;

    public Item succesItem = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "", new ArrayList<>());

    public Item unsuccesItem = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "", new ArrayList<>());

    public Item previewCraftItem = new Item(XMaterial.STONE, 1, "", new ArrayList<>());

    public Inventories(HyperCrafting hyperCrafting, String name, boolean loadDefaults) {
        super(hyperCrafting, name, loadDefaults);
        loadItems();
    }

    @Override
    public void reload(){
        super.reload();
        loadItems();
    }


    private void loadItems() {
        //MAIN MENU
        mainMenuTitle = getConfig().getString("mainMenu.title");
        mainMenuSize = getConfig().getInt("mainMenu.size");
        recipeNotFound = Utils.getItemFromConfig(getConfig(), "mainMenu.recipeNotFound");
        recipeNoPermission = Utils.getItemFromConfig(getConfig(), "mainMenu.recipeNoPermission");
        decorationSlots = new HashSet<>(getConfig().getIntegerList("mainMenu.decorationSlots"));
        craftingSlots = getConfig().getIntegerList("mainMenu.craftingSlots");
        successSlots = new HashSet<>(getConfig().getIntegerList("mainMenu.successSlots"));
        //PREVIEW MENU
        recipePreviewTitle = getConfig().getString("recipePreviewGUI.title");
        recipePreviewSize = getConfig().getInt("recipePreviewGUI.size");
        recipePreviewSlots = getConfig().getIntegerList("recipePreviewGUI.recipeSlots");
        recipePreviewDecoration = new HashSet<>(getConfig().getIntegerList("recipePreviewGUI.decorationSlots"));
        availableRecipesSlots = new ArrayList<>(getConfig().getIntegerList("mainMenu.availableRecipesSlots"));

        //BUTTONS
        background = Utils.getItemFromConfig(getConfig(), "buttons.background");
        closeButton = Utils.getItemFromConfig(getConfig(), "buttons.closeButton");

        succesItem = Utils.getItemFromConfig(getConfig(), "mainMenu.successItem");
        unsuccesItem = Utils.getItemFromConfig(getConfig(), "mainMenu.emptyItem");

        previewCraftItem = Utils.getItemFromConfig(getConfig(), "mainMenu.previewCraftItem");

        resultSlot = getConfig().getInt("recipePreviewGUI.resultItemSlot");
        previewDecorationItem = Utils.getItemFromConfig(getConfig(), "recipePreviewGUI.decorationItem");
        //book
        recipeBook = Utils.getItemFromConfig(getConfig(), "recipeBookGUI.recipeBook");
        categoryItem = Utils.getItemFromConfig(getConfig(), "recipeBookGUI.categoryItem");
        backMainMenu = Utils.getItemFromConfig(getConfig(), "recipeBookGUI.backMainMenu");
        bookCloseButton = Utils.getItemFromConfig(getConfig(), "recipeBookGUI.closeButton");
        bookDecoration = Utils.getItemFromConfig(getConfig(), "recipeBookGUI.decoration");
        bookSlots = new HashSet<>(getConfig().getIntegerList("recipeBookGUI.decoration_slots"));
        bookSize = getConfig().getInt("recipeBookGUI.size");
        bookTitle = getConfig().getString("recipeBookGUI.title");

        //CATEGORY
        itemSlots = new HashSet<>(getConfig().getIntegerList("categoryGUI.itemSlots"));
        categoryDecoration = Utils.getItemFromConfig(getConfig(), "categoryGUI.decoration");
        categoryDecorationSlots = new HashSet<>(getConfig().getIntegerList("categoryGUI.decorationSlots"));
        categoryCloseButton = Utils.getItemFromConfig(getConfig(), "categoryGUI.closeButton");
        categoryBackMainMenuButton = Utils.getItemFromConfig(getConfig(), "categoryGUI.backMainMenuButton");
        categoryNextPage = Utils.getItemFromConfig(getConfig(), "categoryGUI.nextPageButton");
        categoryBackPage = Utils.getItemFromConfig(getConfig(), "categoryGUI.backPageButton");
        recipeUnlocked = Utils.getItemFromConfig(getConfig(), "categoryGUI.recipeUnlocked");
        recipeLocked = Utils.getItemFromConfig(getConfig(), "categoryGUI.recipeLocked");
        categoryGUIItem = Utils.getItemFromConfig(getConfig(), "categoryGUI.categoryItem");
        categorySize = getConfig().getInt("categoryGUI.size");
        categoryTitle = getConfig().getString("categoryGUI.title");
        previewbackPageButton = Utils.getItemFromConfig(getConfig(), "recipePreviewGUI.backPageButton");
    }

}
