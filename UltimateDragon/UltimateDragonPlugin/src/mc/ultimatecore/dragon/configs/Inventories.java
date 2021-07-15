package mc.ultimatecore.dragon.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.dragon.utils.Item;

import java.util.Arrays;
import java.util.Collections;

public class Inventories {

    public String mainMenuTitle = "&8Dragon Settings";

    public int mainMenuSize = 54;

    public Item schematicItem = new Item(XMaterial.PAPER, 25, 1, "&aCurrent Schematic", Arrays.asList("", "&e► Schematic: %schematic%"));

    public Item closeButton = new Item(XMaterial.BARRIER, 1, "&cClose", Collections.singletonList("&7Click to close this menu."));

    public Item previousPage = new Item(XMaterial.ARROW, 1, "&ePrevious Page", Collections.singletonList("&7Click to go back."));

    public Item backMainMenu = new Item(XMaterial.ARROW, 49, 1, "&eBack", Collections.singletonList("&7Click to go Main Menu."));

    public Item nextPage = new Item(XMaterial.ARROW, 1, "&eNext Page", Collections.singletonList("&7Click to go to the next page."));

    public Item background = new Item(XMaterial.BLACK_STAINED_GLASS_PANE, 1, "", Collections.emptyList());

    public Item spawnItem = new Item(XMaterial.DRAGON_HEAD, 4, 1, "&aDragon Spawn", Arrays.asList("", "&eLocation: &6%location%", "", "&e► Click to teleport"));

    public Item crystalsItem = new Item(XMaterial.PLAYER_HEAD, 19, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc0ODEzOWVlNmNmZjc0NGM3ZTg5NTkzZjZiOTBkNDYwNDRkMDdlZTVlNjM4MjhmYmU5NTMxZmI2NmRmOWI4ZiJ9fX0=", 1, "&aCrystals", Arrays.asList("", "&eAmount: &6%amount%", "", "&e► Click to view"));

    public Item altarItem = new Item(XMaterial.PLAYER_HEAD, 22, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ2ODRlM2U3ODkwY2FmN2QxMzc2MmVhMTllYjE0YzU5NDBiODhmZDdmMDc3ZDgxZTZlZmZiNGY2ZGYxNmMyNiJ9fX0=", 1, "&aAltars", Arrays.asList("", "&eAmount: &6%amount%", "", "&e► Click to view"));

    public Item crystalsLocation = new Item(XMaterial.PLAYER_HEAD, 19, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc0ODEzOWVlNmNmZjc0NGM3ZTg5NTkzZjZiOTBkNDYwNDRkMDdlZTVlNjM4MjhmYmU5NTMxZmI2NmRmOWI4ZiJ9fX0=", 1, "&aCrystal #%id%", Arrays.asList("", "&eLocation: &6%location%", "", "&e► Left-Click to teleport", "&e► Right-Click to remove"));

    public Item altarLocation = new Item(XMaterial.PLAYER_HEAD, 22, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ2ODRlM2U3ODkwY2FmN2QxMzc2MmVhMTllYjE0YzU5NDBiODhmZDdmMDc3ZDgxZTZlZmZiNGY2ZGYxNmMyNiJ9fX0=", 1, "&aAltar #%id%", Arrays.asList("", "&eLocation: &6%location%", "", "&e► Left-Click to teleport", "&e► Right-Click to remove"));

    public Item guardian = new Item(XMaterial.ZOMBIE_HEAD, 22, 1, "&aGuardian #%id%", Arrays.asList("", "&e► Left-Click to edit", "&e► Right-Click to remove"));

    public Item cancel = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&cCancel", Arrays.asList(
            "", "&7 ► Click to cancel"));

    public Item confirm = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&aConfirm", Arrays.asList(
            "", "&7 ► Click to confirm"));


    public Item chestplate = new Item(XMaterial.DIAMOND_CHESTPLATE, 1, "&aEdit Chestplate", Arrays.asList(
            "", "&7 ► Click to change"));

    public Item helmet = new Item(XMaterial.DIAMOND_HELMET, 1, "&aEdit Helmet", Arrays.asList(
            "", "&7 ► Click to change"));

    public Item leggings = new Item(XMaterial.DIAMOND_LEGGINGS, 1, "&aEdit Leggings", Arrays.asList(
            "", "&7 ► Click to change"));

    public Item boots = new Item(XMaterial.DIAMOND_BOOTS, 1, "&aEdit Boots", Arrays.asList(
            "", "&7 ► Click to change"));

    public Item health = new Item(XMaterial.APPLE, 1, "&aHealth: %health%", Arrays.asList(
            "", "&7 ► Click to change"));

    public Item type = new Item(XMaterial.ZOMBIE_HEAD, 22, 1, "&aMob Type", Arrays.asList("", "&eType: &6%type%", "", "&e► Click to change"));

    public Item displayname = new Item(XMaterial.NAME_TAG, 22, 1, "&aDisplayName", Arrays.asList("", "&eDisplayname: &6%displayname%", "", "&e► Click to change"));

}
