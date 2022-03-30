package com.infernalsuite.ultimatecore.alchemy.gui;


import com.infernalsuite.ultimatecore.alchemy.HyperAlchemy;
import com.infernalsuite.ultimatecore.alchemy.objects.AlchemyRecipe;
import com.infernalsuite.ultimatecore.alchemy.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.alchemy.utils.Placeholder;
import com.infernalsuite.ultimatecore.alchemy.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AllRecipesGUI implements NormalGUI {

    private final int page;

    private final Map<Integer, AlchemyRecipe> recipeSlots;

    private final boolean hasNext;

    public AllRecipesGUI(int page) {
        this.page = page;
        this.recipeSlots = new HashMap<>();
        this.hasNext = HyperAlchemy.getInstance().getBrewingRecipes().getRecipes().size() > 45 * page;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Recipe Editor"));
        for(Integer slot : Arrays.asList(45,46,47,48,49,50,51,52,53))
            inventory.setItem(slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().background));
        List<AlchemyRecipe> recipes = new ArrayList<>(HyperAlchemy.getInstance().getBrewingRecipes().getRecipes().values());
        int slot = 0;
        int i = 45 * (this.page - 1);
        if(recipes.size() > 0){
            while (slot < 45) {
                if (recipes.size() > i && i >= 0) {
                    AlchemyRecipe recipe = recipes.get(i);
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().recipeItemMenu, Collections.singletonList(new Placeholder("recipe_name", recipe.getName()))));
                    this.recipeSlots.put(slot, recipe);
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (this.page > 1)
            inventory.setItem(45, InventoryUtils.makeItem((HyperAlchemy.getInstance().getInventories()).previousPage));
        if (recipes.size() > 45 * page)
            inventory.setItem(53, InventoryUtils.makeItem((HyperAlchemy.getInstance().getInventories()).nextPage));
        inventory.setItem(HyperAlchemy.getInstance().getInventories().closeButton.slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().closeButton));
        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == 49) {
            player.closeInventory();
        } else if (slot == getInventory().getSize() - 1 && e.getCurrentItem() != null && hasNext) {
            player.openInventory(new AllRecipesGUI(page + 1).getInventory());
        } else if (slot == getInventory().getSize() - 9 && e.getCurrentItem() != null && page > 1) {
            player.openInventory(new AllRecipesGUI(page - 1).getInventory());
        } else {
            if (recipeSlots.containsKey(slot)) {
                AlchemyRecipe craftingRecipe = recipeSlots.get(slot);
                if (e.getClick() == ClickType.LEFT) {
                    player.openInventory(craftingRecipe.getRecipeCreatorGUI().getInventory());
                } else if (e.getClick() == ClickType.RIGHT) {
                    HyperAlchemy.getInstance().getBrewingRecipes().removeRecipe(craftingRecipe);
                    getInventory().clear();
                }
            }
        }
    }
}
