package mc.ultimatecore.crafting.playerdata;

import lombok.*;
import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.gui.crafting.CraftingGUI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;

import java.util.*;

@Data
public class User {

    @Getter
    private UUID uuid;
    @Getter @Setter
    private CraftingGUI craftingGUI;

    public User(UUID uuid, HyperCrafting plugin) {
        this.uuid = uuid;
        if (Bukkit.getPlayer(uuid) != null) {
            this.craftingGUI = new CraftingGUI(Bukkit.getPlayer(uuid), plugin);
        }
    }
}
