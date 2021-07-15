package mc.ultimatecore.alchemy.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.Item;
import mc.ultimatecore.alchemy.utils.Utils;

import java.util.*;

public class Inventories extends YAMLFile{

    public HashMap<Integer, Integer> relationSlots = new HashMap<Integer, Integer>(){{
        put(0, 38);
        put(1, 40);
        put(2, 42);
        put(3, 13);
    }};

    public String mainMenuTitle = "&8Brewing Stand";
    public int mainMenuSize = 54;

    //RECIPE
    public String recipePreviewTitle = "&8Recipe Preview";
    public int recipePreviewSize = 54;

    public Set<Integer> recipePreviewDecoration;

    public Item specialDecoration = new Item(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());
    public Set<Integer> specialDecorationSlots;

    public int mainIngredientSlot;
    public Set<Integer> secondaryIngredientSlots;

    //

    public Set<Integer> decorationSlots;

    public Item background = new Item(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());

    public Item closeButton = new Item(XMaterial.BARRIER, 49, 1, "&cClose Menu", new ArrayList<>());

    public Item recipeItemMenu = new Item(XMaterial.GLASS_BOTTLE, 1, "&a%recipe_name%", Arrays.asList("", "&e► Left-Click to edit this recipe", "&e► Right-Click to remove this recipe"));

    public Item nextPage = new Item(XMaterial.ARROW, 1, "&eNext Page", new ArrayList<>());

    public Item previousPage = new Item(XMaterial.ARROW, 1, "&ePrevious Page", new ArrayList<>());

    public Item normalItem;

    public Item brewingItem;

    //RECIPE CREATOR ITEMS
    public Item inputItem = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&a► Drag a item here to set input item", new ArrayList<>());
    public Item fuelItem = new Item(XMaterial.ORANGE_STAINED_GLASS_PANE, 1, "&a► Drag a item here to set fuel item", new ArrayList<>());
    public Item outPutItem = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&a► Drag a item here to set output item", new ArrayList<>());
    public Item saveRecipeItem = new Item(XMaterial.LIME_DYE, 1, "&a► Click here to save recipe", new ArrayList<>());
    public Item permissionEditItem = new Item(XMaterial.PAPER, 1, "&eRecipe Permission", Arrays.asList("", "&aCurrent permission: &7%permission%", "", "  &a► Click to change permission"));
    public Item timeEditItem = new Item(XMaterial.CLOCK, 1, "&eBrewing time", Arrays.asList("", "&aCurrent Time: &7%time%", "", "  &a► Click to change brewing time"));


    public Inventories(HyperAlchemy hyperCrafting, String name) {
        super(hyperCrafting, name);
    }

    @Override
    public void enable(){
        super.enable();
        this.loadItems();
    }

    @Override
    public void reload(){
        getConfig().reload();
        this.loadItems();
    }

    private void loadItems() {
        //MAIN MENU
        mainMenuTitle = getConfig().get().getString("mainMenu.title");
        mainMenuSize = getConfig().get().getInt("mainMenu.size");
        decorationSlots = new HashSet<>(getConfig().get().getIntegerList("mainMenu.decorationSlots"));
        brewingItem = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.brewingItem");
        normalItem = Utils.getItemFromConfig(getConfig().get(), "mainMenu.items.normalItem");
        //BUTTONS
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        //PREVIEW MENU
        recipePreviewTitle = getConfig().get().getString("recipePreviewGUI.title");
        recipePreviewSize = getConfig().get().getInt("recipePreviewGUI.size");
        recipePreviewDecoration = new HashSet<>(getConfig().get().getIntegerList("recipePreviewGUI.decorationSlots"));
        specialDecoration = Utils.getItemFromConfig(getConfig().get(), "recipePreviewGUI.specialDecoration");
        specialDecorationSlots = new HashSet<>(getConfig().get().getIntegerList("recipePreviewGUI.specialDecorationSlots"));
        mainIngredientSlot = getConfig().get().getInt("recipePreviewGUI.mainIngredientSlot");
        secondaryIngredientSlots = new HashSet<>(getConfig().get().getIntegerList("recipePreviewGUI.secondaryIngredientSlots"));


    }

}
