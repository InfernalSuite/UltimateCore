package com.infernalsuite.ultimatecore.pets.configs;

import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.Item;
import com.infernalsuite.ultimatecore.pets.utils.Utils;

import java.util.List;

public class Inventories extends YAMLFile {
    //PETS Menu
    public int petsGUISize;
    public String petsGUITitle;
    public List<Integer> petsGUIExcludedSlots;
    public List<Integer> petSlots;
    public Item petItemInGUI;
    public Item petInfo;
    public Item convertItemEnabled;
    public Item convertItemDisabled;
    public Item nextPage;
    public Item previousPage;
    public Item background;
    public Item closeButton;
    public Item mainMenuBack;
    public boolean mainMenuBackEnabled;
    //
    public Item hidePetsEnabled;
    public Item hidePetsDisabled;

    public Inventories(HyperPets hyperPets, String name, boolean defaults, boolean save) {
        super(hyperPets, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        this.loadDefaults();
    }

    private void loadDefaults(){
        //------------------------------------------------//
        petsGUISize = getConfig().getInt("petsGUI.size");
        petsGUITitle = getConfig().getString("petsGUI.title");
        petsGUIExcludedSlots = getConfig().getIntegerList("petsGUI.excludedSlots");
        petSlots = getConfig().getIntegerList("petsGUI.petSlots");
        petItemInGUI = Utils.getItemFromConfig(getConfig(), "petsGUI.items.petItemInGUI");
        petInfo = Utils.getItemFromConfig(getConfig(), "petsGUI.items.petInfo");
        convertItemEnabled = Utils.getItemFromConfig(getConfig(), "petsGUI.items.convertItemEnabled");
        convertItemDisabled = Utils.getItemFromConfig(getConfig(), "petsGUI.items.convertItemDisabled");
        nextPage = Utils.getItemFromConfig(getConfig(), "buttons.nextPage");
        previousPage = Utils.getItemFromConfig(getConfig(), "buttons.previousPage");
        background = Utils.getItemFromConfig(getConfig(), "buttons.background");
        closeButton = Utils.getItemFromConfig(getConfig(), "buttons.closeButton");
        mainMenuBack = Utils.getItemFromConfig(getConfig(), "main-menu-back");
        mainMenuBackEnabled = getConfig().getBoolean("main-menu-back.enabled");
        hidePetsEnabled = Utils.getItemFromConfig(getConfig(), "petsGUI.items.hidePetsEnabled");
        hidePetsDisabled = Utils.getItemFromConfig(getConfig(), "petsGUI.items.hidePetsDisabled");
        //------------------------------------------------//
    }

}
