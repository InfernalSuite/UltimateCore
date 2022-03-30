package com.infernalsuite.ultimatecore.alchemy.managers;

import lombok.Getter;
import com.infernalsuite.ultimatecore.alchemy.gui.BrewingGUI;
import org.bukkit.Location;

import java.util.HashMap;

public class BrewingStandManager {

    @Getter
    private final HashMap<Location, BrewingGUI> brewingStandItems = new HashMap<>();

}
