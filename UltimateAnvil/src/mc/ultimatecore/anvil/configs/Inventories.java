package mc.ultimatecore.anvil.configs;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.Item;
import mc.ultimatecore.anvil.utils.Utils;

import java.util.*;

@Getter
public class Inventories extends YAMLFile{

    //ANVIL
    private Item infoItem;
    private Item errorItem;
    private Item combineItemsNormal;
    private Item combineItemsToFuse;
    private Item renameItem;
    private Item itemPreview;
    private int anvilMenuSize;
    private String anvilMenuTitle;
    private String renameMenuTitle;
    //
    private Item itemToUpgradeError;
    private Item itemToSacrificeError;
    //
    private Item itemToUpgradeSuccess;
    private Item itemToSacrificeSuccess;
    //BUTTONS
    private Item background;
    private Item previousPage;
    private Item closeButton;
    private boolean backButtons;

    //EDIT MENU
    public Item armorItem = new Item(XMaterial.STONE, 4, 1, "&a%item_id%", Arrays.asList(
            "%item_title%", "", "%item_lore%", "", "&e ► Click + Shift above an item to switch"));
    public Item allArmorItem = new Item(XMaterial.STONE, 4, 1, "&a%item_id%", Arrays.asList(
            "%item_title%", "", "%item_lore%", "", "&e ► Left-Click to Edit item", "&e ► Right-Click to remove item"));
    public Set<Integer> armorDecorationSlots = new HashSet<>(Arrays.asList(0, 1, 9, 7, 8, 17, 36, 46, 52, 53, 45, 49));

    public Item manaCost = new Item(XMaterial.GOLD_INGOT, 20, 1, "&aApply Cost: %cost%", Arrays.asList(
            "", "&e ► Click to change apply cost"));
    public Item loreEditor = new Item(XMaterial.BOOK, 24, 1, "&aItem Lore", Arrays.asList(
            "", "&e ► Left-Click to Add a new line", "&e ► Right-Click to remove last line"));

    public Item giveItem = new Item(XMaterial.GUNPOWDER, 40, 1, "&aGet Item", Arrays.asList(
            "", "&e ► Click to get item!"));
    public Item armorDecoration = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "", Collections.singletonList(""));

    public Item cancel = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&cCancel", Arrays.asList(
            "", "&7 ► Click to cancel"));

    public Item confirm = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&aConfirm", Arrays.asList(
            "", "&7 ► Click to confirm"));

    public Item nextPage = new Item(XMaterial.ARROW, 1, "&aNext Page", new ArrayList<>());

    public Inventories(HyperAnvil hyperAnvil, String name) {
        super(hyperAnvil, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        getConfig().reload();
        loadDefaults();
    }

    private void loadDefaults(){
        //RUNETABLE
        infoItem = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.infoItem");
        errorItem = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.errorItem");
        combineItemsNormal = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.combineItemsNormal");
        combineItemsToFuse = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.combineItemsToFuse");
        itemPreview = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.itemPreview");
        //
        itemToUpgradeError = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.itemToUpgrade");
        itemToSacrificeError = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.itemToSacrifice");
        itemToUpgradeSuccess = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.itemToUpgrade");
        itemToSacrificeSuccess = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.itemToSacrifice");
        //
        itemToUpgradeError.material = XMaterial.valueOf(getConfig().get().getString("anvilGUI.items.emptyMaterial"));
        itemToSacrificeError.material = XMaterial.valueOf(getConfig().get().getString("anvilGUI.items.emptyMaterial"));
        itemToUpgradeSuccess.material = XMaterial.valueOf(getConfig().get().getString("anvilGUI.items.succesMaterial"));
        itemToSacrificeSuccess.material = XMaterial.valueOf(getConfig().get().getString("anvilGUI.items.succesMaterial"));
        //
        anvilMenuTitle = getConfig().get().getString("anvilGUI.title");
        anvilMenuSize = getConfig().get().getInt("anvilGUI.size");
        //BUTTONS
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");
        previousPage = Utils.getItemFromConfig(getConfig().get(), "buttons.previousPage");
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        backButtons = getConfig().get().getBoolean("buttons.backButtons");
        renameItem = Utils.getItemFromConfig(getConfig().get(), "anvilGUI.items.nameTag");
        renameMenuTitle = getConfig().get().getString("renameGUI.title");
    }
}
