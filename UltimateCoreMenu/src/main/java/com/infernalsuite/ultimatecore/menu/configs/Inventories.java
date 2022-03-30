package com.infernalsuite.ultimatecore.menu.configs;

import com.infernalsuite.ultimatecore.menu.HyperCore;
import com.infernalsuite.ultimatecore.menu.Item;
import com.infernalsuite.ultimatecore.menu.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

public class Inventories extends YAMLFile{

    //MAIN MENU
    public String mainMenuTitle;
    public int mainMenuSize;

    public Item closeButton;

    public Set<Integer> mainMenuExcludedSlots;

    public Item background;

    public Set<Item> items;

    public Inventories(HyperCore hyperCore, String name) {
        super(hyperCore, name);
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
        mainMenuSize = getConfig().get().getInt("mainMenu.size");
        mainMenuTitle = getConfig().get().getString("mainMenu.title");

        mainMenuExcludedSlots = new HashSet<>(getConfig().get().getIntegerList("mainMenu.excludedSlots"));

        //buttons
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");

        //options mc.ultimatecore.gui

        ConfigurationSection section = getConfig().get().getConfigurationSection("mainMenu.items");
        if(section == null) return;
        items = new HashSet<>();
        section.getKeys(false).forEach(key -> items.add(Utils.getItemFromConfig(getConfig().get(), "mainMenu.items."+key)));
    }
}
