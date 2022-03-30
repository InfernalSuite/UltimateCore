package com.infernalsuite.ultimatecore.talismans.configs;

import com.infernalsuite.ultimatecore.talismans.Item;
import com.infernalsuite.ultimatecore.talismans.utils.Utils;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;

import java.util.HashSet;
import java.util.Set;

public class Inventories extends YAMLFile {
    public Set<Integer> decorationSlots;
    public Item background;
    public Item closeButton;
    public int bagSize;
    public String bagTitle;

    public Inventories(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        bagSize = getConfig().getInt("talismansBag.size");
        bagTitle = getConfig().getString("talismansBag.title");

        decorationSlots = new HashSet<>(getConfig().getIntegerList("talismansBag.decoration_slots"));
        background = Utils.getItemFromConfig(getConfig(), "talismansBag.items.decoration");
        closeButton = Utils.getItemFromConfig(getConfig(), "talismansBag.items.close_button");
    }
}
