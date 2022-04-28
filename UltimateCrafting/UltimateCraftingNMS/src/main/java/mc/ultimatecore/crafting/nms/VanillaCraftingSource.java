package mc.ultimatecore.crafting.nms;

import org.bukkit.*;
import org.bukkit.inventory.*;

public interface VanillaCraftingSource {

    Recipe getRecipe(ItemStack[] craftingMatrix, World world);

    ItemStack[] getRemainingItemsForCrafting(ItemStack[] craftingMatrix, World world);
}