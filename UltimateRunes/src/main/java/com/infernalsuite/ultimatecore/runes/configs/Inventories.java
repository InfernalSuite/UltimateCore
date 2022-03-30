package com.infernalsuite.ultimatecore.runes.configs;

import com.infernalsuite.ultimatecore.runes.utils.Utils;
import lombok.Getter;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import com.infernalsuite.ultimatecore.runes.Item;

@Getter
public class Inventories extends YAMLFile{

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
    //BUTTONS
    private Item background;
    private Item previousPage;
    private Item closeButton;
    private boolean backButtons;

    public Inventories(HyperRunes hyperSkills, String name) {
        super(hyperSkills, name);
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
        runeInfoItem = Utils.getItemFromConfig(getConfig().get(), "runeTableGUI.items.infoItem");
        runeErrorItem = Utils.getItemFromConfig(getConfig().get(), "runeTableGUI.items.errorItem");
        runeButtonItem = Utils.getItemFromConfig(getConfig().get(), "runeTableGUI.items.runeButton");
        runeButtonToFuseItem = Utils.getItemFromConfig(getConfig().get(), "runeTableGUI.items.runeButtonToFuse");
        runeTableGlassItem = Utils.getItemFromConfig(getConfig().get(), "runeTableGUI.items.runeTableGlass");
        runeTableTitle = getConfig().get().getString("runeTableGUI.title");
        runeTableMenuSize = getConfig().get().getInt("runeTableGUI.size");
        //RUNES
        runesTitle = getConfig().get().getString("runesGui.title");
        runesSize = getConfig().get().getInt("runesGui.size");
        //BUTTONS
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");
        previousPage = Utils.getItemFromConfig(getConfig().get(), "buttons.previousPage");
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        backButtons = getConfig().get().getBoolean("buttons.backButtons");

    }
}
