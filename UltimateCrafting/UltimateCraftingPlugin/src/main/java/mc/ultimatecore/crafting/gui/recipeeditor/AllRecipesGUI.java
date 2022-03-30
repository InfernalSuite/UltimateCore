package mc.ultimatecore.crafting.gui.recipeeditor;


import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.SimpleGUI;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Placeholder;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AllRecipesGUI extends SimpleGUI {

    private final int page;

    private final Map<Integer, CraftingRecipe> recipeSlots;

    private final boolean hasNext;

    public AllRecipesGUI(int page) {
        this.page = page;
        this.recipeSlots = new HashMap<>();
        this.hasNext = HyperCrafting.getInstance().getCraftingRecipes().getRecipes().size() > 45 * page;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, Utils.color("&8Recipe Editor"));
        for (Integer slot : new HashSet<>(Arrays.asList(45,46,47,48,49,50,51,52,53)))
            inventory.setItem(slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().background));
        List<CraftingRecipe> recipes = HyperCrafting.getInstance().getCraftingRecipes().getRecipes();
        int slot = 0;
        int i = 45 * (page - 1);
        if(recipes.size() > 0){
            while (slot < 45) {
                if (recipes.size() > i && i >= 0) {
                    CraftingRecipe recipe = recipes.get(i);
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().recipeItemMenu, Collections.singletonList(new Placeholder("recipe_name", recipe.getName())), recipe.getResult().getType()));
                    this.recipeSlots.put(slot, recipe);
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (page > 1)
            inventory.setItem(45, InventoryUtils.makeItem((HyperCrafting.getInstance().getInventories()).previousPage));
        if (recipes.size() > 45 * page)
            inventory.setItem(53, InventoryUtils.makeItem((HyperCrafting.getInstance().getInventories()).nextPage));
        inventory.setItem(HyperCrafting.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().closeButton));
        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        if (slot == 49) {
            player.closeInventory();
        } else if (slot == getInventory().getSize() - 1 && e.getCurrentItem() != null && hasNext) {
            player.openInventory(new AllRecipesGUI(page + 1).getInventory());
        } else if (slot == getInventory().getSize() - 9 && e.getCurrentItem() != null && page > 1) {
            player.openInventory(new AllRecipesGUI(page - 1).getInventory());
        } else {
            if (recipeSlots.containsKey(slot)) {
                CraftingRecipe craftingRecipe = recipeSlots.get(slot);
                player.openInventory(craftingRecipe.getIndividualRecipeGUI().getInventory());
            }
        }
    }

    @Override
    public void openInventory(Player player) {

    }


}
