package mc.ultimatecore.crafting.playerdata;

import lombok.*;
import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.gui.crafting.CraftingGUI;
import mc.ultimatecore.crafting.gui.recipeeditor.RecipeCreatorGUI;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.function.*;

@Data
public class User {

    @Getter
    private UUID uuid;
    @Getter @Setter
    private CraftingGUI craftingGUI;
    @Getter @Setter
    private RecipeCreatorGUI recipeCreatorGUI;

    public User(UUID uuid, HyperCrafting plugin) {
        this.uuid = uuid;
        if (Bukkit.getPlayer(uuid) != null) {
            this.craftingGUI = new CraftingGUI(Bukkit.getPlayer(uuid), plugin);
        }
    }
}
