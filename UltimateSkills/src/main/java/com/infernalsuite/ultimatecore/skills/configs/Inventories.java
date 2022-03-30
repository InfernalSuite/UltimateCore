package com.infernalsuite.ultimatecore.skills.configs;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.skills.Item;
import com.infernalsuite.ultimatecore.skills.objects.abilities.Ability;
import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import lombok.Getter;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

@Getter
public class Inventories extends YAMLFile {
    //ARMOR MENU
    public Item armorItem = new Item(XMaterial.STONE, 4, 1, "&aIdentifier: %item_id%", Arrays.asList(
            "&7Display Name: %item_title%", "", "&7Lore:", "%item_lore%"));
    public Item allArmorItem = new Item(XMaterial.STONE, 4, 1, "&a%item_id%", Arrays.asList(
            "%item_title%", "", "%item_lore%", "", "&e ► Left-Click to Edit item", "&e ► Right-Click to remove item"));
    public Set<Integer> armorDecorationSlots = new HashSet<>(Arrays.asList(0, 1, 9, 7, 8, 17, 36, 46, 52, 53, 45, 49));

    public Item manaCost = new Item(XMaterial.BEACON, 19, 1, "&aMana Cost: %mana_cost%", Arrays.asList(
            "", "&e ► Click to change mana cost"));
    public Item attributes = new Item(XMaterial.BLAZE_ROD, 22, 1, "&aAttributes:", Arrays.asList(
            "", "&e ► Click to edit item attributes"));
    public Item effectInHand = new Item(XMaterial.SLIME_BALL, 25, 1, "&aEffect In Hand: &e%effect_hand%", Arrays.asList(
            "&7If it's true it'll give stats", "&7when held in hand.", "", "&e ► Click to switch"));
    public Item displayName = new Item(XMaterial.NAME_TAG, 31, 1, "&aDisplayName: &e%displayName%", Arrays.asList(
            "&e ► Click to change the display name"));
    public Item giveItem = new Item(XMaterial.GUNPOWDER, 40, 1, "&aGet Item", Arrays.asList(
            "", "&e ► Click to get item!"));
    public Item armorDecoration = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "", Collections.singletonList(""));

    public Item attributeItem = new Item(XMaterial.PLAYER_HEAD, 19, 1, "&a%attribute_name%: %attribute_amount%", Arrays.asList(
            "", "&e ► Click to change amount"));

    public Item loreItem = new Item(XMaterial.BOOK, 37, 1, "&aEdit Lore", Arrays.asList(
            "%item_lore%", "&8&m-------------", "&7Type '%EMPTY%' if you want to add an empty line.", "", "&e ► Left Click to add a new line.", "&e ► Right Click to remove last line."));

    public Item glowItem = new Item(XMaterial.TORCH, 43, 1, "&aItem Glow", Arrays.asList(
            "", "&e ► Click to switch glow!"));

    public Item cancel = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&cCancel", Arrays.asList(
            "", "&7 ► Click to cancel"));

    public Item confirm = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&aConfirm", Arrays.asList(
            "", "&7 ► Click to confirm"));


    public final Map<Ability, Integer> attributesMap = new HashMap<Ability, Integer>() {{
        int slot = 18;
        for (Ability ability : Ability.values()) {
            put(ability, slot);
            slot++;
        }
    }};

    public final Map<Perk, Integer> perksMap = new HashMap<Perk, Integer>() {{
        int slot = 27;
        for (Perk perk : Perk.values()) {
            if (slot == 31) slot++;
            put(perk, slot);
            slot++;
        }
    }};

    //MAIN MENU
    private Item mainMenuSkillItem;
    private Item showRanking;
    private Item mainMenuStatsItem;
    private Item mainMenuBack;
    private boolean mainMenuBackEnabled;
    private String mainMenuTitle;
    private int mainMenuSize;
    //TOP
    private Item topMenuSkillItem;
    private Item hideRanking;
    private Item topMenuStatsItem;
    private String topMenuTitle;
    private int topMenuSize;
    //SUBMENU
    private HashMap<SkillType, String> subMenuTitles;
    private List<Integer> subMenuSlots;
    private int subMenuSize;
    private Item subMenuInfoItem;
    private Item subMenuLockedItem;
    private Item subMenuUnlockedItem;
    private Item subMenuProgressItem;
    //RUNETABLE
    private Item runeInfoItem;
    private Item runeErrorItem;
    private Item runeButtonItem;
    private Item runeButtonToFuseItem;
    private Item runeTableGlassItem;
    private String runeTableTitle;
    private int runeTableMenuSize;
    //RUNESGUI
    private String runesTitle;
    private int runesSize;
    //PROFILEGUI
    private List<Item> profileGUIItems;
    private Item profileBackButton;
    private String profileTitle;
    private int profileMenuSize;
    //BUTTONS
    private Item background;
    private Item previousPage;
    private Item nextPage;
    private Item closeButton;
    private boolean backButtons;
    private Item multiplierItem;

    public Inventories(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        subMenuTitles = new HashMap<>();
        profileGUIItems = new ArrayList<>();

        //PROFILE
        profileTitle = getConfig().getString("profileGui.title");
        profileMenuSize = getConfig().getInt("profileGui.size");
        //RUNETABLE
        runeInfoItem = Utils.getItemFromConfig(getConfig(), "runeTableGUI.items.infoItem");
        runeErrorItem = Utils.getItemFromConfig(getConfig(), "runeTableGUI.items.errorItem");
        runeButtonItem = Utils.getItemFromConfig(getConfig(), "runeTableGUI.items.runeButton");
        runeButtonToFuseItem = Utils.getItemFromConfig(getConfig(), "runeTableGUI.items.runeButtonToFuse");
        runeTableGlassItem = Utils.getItemFromConfig(getConfig(), "runeTableGUI.items.runeTableGlass");
        runeTableTitle = getConfig().getString("runeTableGUI.title");
        runeTableMenuSize = getConfig().getInt("runeTableGUI.size");
        //RUNES
        runesTitle = getConfig().getString("runesGui.title");
        runesSize = getConfig().getInt("runesGui.size");
        //MAIN MENU
        mainMenuTitle = getConfig().getString("mainMenu.title");
        mainMenuSize = getConfig().getInt("mainMenu.size");
        mainMenuSkillItem = Utils.getItemFromConfig(getConfig(), "mainMenu.items.skillItem");
        showRanking = Utils.getItemFromConfig(getConfig(), "mainMenu.items.showranking");
        mainMenuStatsItem = Utils.getItemFromConfig(getConfig(), "mainMenu.items.statsItem");
        //TOP MENU
        topMenuTitle = getConfig().getString("topMenu.title");
        topMenuSize = getConfig().getInt("topMenu.size");
        topMenuSkillItem = Utils.getItemFromConfig(getConfig(), "topMenu.items.skillItem");
        hideRanking = Utils.getItemFromConfig(getConfig(), "topMenu.items.hideranking");
        topMenuStatsItem = Utils.getItemFromConfig(getConfig(), "topMenu.items.statsItem");
        //SUB MENU
        subMenuSize = getConfig().getInt("submenu.size");
        for (SkillType skillType : SkillType.values())
            subMenuTitles.put(skillType, getConfig().getString("submenu.titles." + skillType.toString()));
        subMenuSlots = getConfig().getIntegerList("submenu.slots");
        subMenuInfoItem = Utils.getItemFromConfig(getConfig(), "submenu.items.infoItem");
        subMenuLockedItem = Utils.getItemFromConfig(getConfig(), "submenu.items.lockedItem");
        subMenuLockedItem = Utils.getItemFromConfig(getConfig(), "submenu.items.lockedItem");
        subMenuUnlockedItem = Utils.getItemFromConfig(getConfig(), "submenu.items.unlockedItem");
        subMenuProgressItem = Utils.getItemFromConfig(getConfig(), "submenu.items.progressItem");
        //BUTTONS
        background = Utils.getItemFromConfig(getConfig(), "buttons.background");
        previousPage = Utils.getItemFromConfig(getConfig(), "buttons.previousPage");
        nextPage = Utils.getItemFromConfig(getConfig(), "buttons.nextPage");

        closeButton = Utils.getItemFromConfig(getConfig(), "buttons.closeButton");
        backButtons = getConfig().getBoolean("buttons.backButtons");
        multiplierItem = Utils.getItemFromConfig(getConfig(), "buttons.multiplierItem");

        mainMenuBack = Utils.getItemFromConfig(getConfig(), "main-menu-back");
        mainMenuBackEnabled = getConfig().getBoolean("main-menu-back.enabled");

        ConfigurationSection configurationSection = getConfig().getConfigurationSection("profileGui.items");
        if (configurationSection == null) return;
        for (String item : configurationSection.getKeys(false)) {
            if (item.equals("backButton"))
                profileBackButton = Utils.getItemFromConfig(getConfig(), "profileGui.items." + item);
            else
                profileGUIItems.add(Utils.getItemFromConfig(getConfig(), "profileGui.items." + item));
        }
    }
}
