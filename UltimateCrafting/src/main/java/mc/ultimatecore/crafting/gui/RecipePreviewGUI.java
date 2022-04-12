package mc.ultimatecore.crafting.gui;


import lombok.AllArgsConstructor;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.Category;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Placeholder;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

@AllArgsConstructor
public class RecipePreviewGUI implements SimpleGUI {
    
    private final HyperCrafting plugin;
    
    private final CraftingRecipe craftingRecipe;
    
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == plugin.getInventories().closeButton.slot) {
            player.closeInventory();
        } else if (slot == plugin.getInventories().previewbackPageButton.slot && plugin.getInventories().previewbackPageButton.enabled) {
            if (craftingRecipe.getCategory() == null) return;
            Category category = plugin.getCategories().getCategory(craftingRecipe.getCategory());
            if (category == null) return;
            new CategoryRecipeGUI(plugin, category, 1).openInventory(player);
        }
    }
    
    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(this, plugin.getInventories().recipePreviewSize, Utils.color(plugin.getInventories().recipePreviewTitle));
        for (Integer slot : plugin.getInventories().recipePreviewDecoration)
            inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().background));
        inventory.setItem(plugin.getInventories().previewDecorationItem.slot, InventoryUtils.makeItem(plugin.getInventories().previewDecorationItem));
        inventory.setItem(plugin.getInventories().resultSlot, craftingRecipe.getResult().clone());
        inventory.setItem(plugin.getInventories().closeButton.slot, InventoryUtils.makeItem(plugin.getInventories().closeButton));
        Category category = plugin.getCategories().getCategory(craftingRecipe.getCategory() != null ? craftingRecipe.getCategory() : "");
        inventory.setItem(plugin.getInventories().previewbackPageButton.slot, InventoryUtils.makeItem(plugin.getInventories().previewbackPageButton, Collections.singletonList(new Placeholder("category", category != null ? category.getDisplayName() : "NONE"))));
        for (int i = 0; i < 9; i++)
            if (craftingRecipe.getRecipeItems().containsKey(i))
                inventory.setItem(plugin.getInventories().recipePreviewSlots.get(i), craftingRecipe.getRecipeItems().get(i));
        player.openInventory(inventory);
    }
    
    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
