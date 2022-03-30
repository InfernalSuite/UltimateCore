package mc.ultimatecore.alchemy.managers;

import lombok.Getter;
import mc.ultimatecore.alchemy.gui.BrewingGUI;
import org.bukkit.Location;

import java.util.HashMap;

public class BrewingStandManager {

    @Getter
    private final HashMap<Location, BrewingGUI> brewingStandItems = new HashMap<>();

}
