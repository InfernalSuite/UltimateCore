package mc.ultimatecore.crafting.gui;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.Category;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.objects.StatsMap;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Placeholder;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryRecipeGUI extends SimpleGUI {

    private final HyperCrafting plugin;

    private final Category category;

    private final int page;

    private boolean nextPage;

    private final Map<Integer, CraftingRecipe> slots = new HashMap<>();

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if (slot == plugin.getInventories().bookCloseButton.slot) {
            player.closeInventory();
        }else if (slot == plugin.getInventories().categoryBackMainMenuButton.slot) {
            if (plugin.getInventories().categoryBackMainMenuButton.enabled && plugin.getInventories().categoryBackMainMenuButton.command != null)
                player.performCommand(plugin.getInventories().categoryBackMainMenuButton.command);
        }else if (slots.containsKey(e.getSlot())) {
            CraftingRecipe craftingRecipe = slots.get(e.getSlot());
            if(hasPermission(player, craftingRecipe))
                new RecipePreviewGUI(plugin, slots.get(e.getSlot())).openInventory(player);
            else
                player.sendMessage(Utils.color(plugin.getMessages().getMessage("recipeLocked")));
        }else if(e.getSlot() == plugin.getInventories().categoryBackPage.slot && page > 1){
            new CategoryRecipeGUI(plugin, category, page - 1).openInventory(player);
        }else if(slot == plugin.getInventories().categoryNextPage.slot && nextPage){
            new CategoryRecipeGUI(plugin, category, page + 1).openInventory(player);
        }
    }

    @Override
    public void openInventory(Player player) {
        List<CraftingRecipe> recipes = plugin.getCraftingRecipes().getRecipes().stream()
                .filter(craftingRecipe -> craftingRecipe.getCategory() != null && craftingRecipe.getCategory().equals(category.getKey()))
                .collect(Collectors.toList());
        List<Integer> pages = recipes.stream().map(CraftingRecipe::getPage).collect(Collectors.toList());
        int maxPage = pages.isEmpty() ? 1 : Collections.max(pages);
        Inventory inventory = Bukkit.createInventory(this, plugin.getInventories().categorySize,
                Utils.color(plugin.getInventories().categoryTitle
                        .replace("%category%", String.valueOf(category.getDisplayName()))
                        .replace("%max_page%", String.valueOf(maxPage))
                        .replace("%page%", String.valueOf(page))));
        for (Integer slot : plugin.getInventories().categoryDecorationSlots)
            inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().categoryDecoration));


        StatsMap str = getStatsMap(player);
        inventory.setItem(plugin.getInventories().categoryGUIItem.slot, InventoryUtils.makeItem(plugin.getInventories().categoryGUIItem, Utils.getRecipeBookPlaceholders(category, str), category));
        for(CraftingRecipe recipe : recipes){
            if(recipe == null) continue;
            if(recipe.getPage() == page){
                int slot = recipe.getSlot();
                if(hasPermission(player, recipe))
                    inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().recipeUnlocked, recipe.getResult(), "locked"));
                else
                    inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().recipeLocked));
                this.slots.put(slot, recipe);
            }else if(recipe.getPage() > page){
                nextPage = true;
            }
        }
        if (page > 1)
            inventory.setItem(plugin.getInventories().categoryBackPage.slot, InventoryUtils.makeItem(plugin.getInventories().categoryBackPage, Collections.singletonList(new Placeholder("previous_page", String.valueOf(page - 1)))));
        if(nextPage)
            inventory.setItem(plugin.getInventories().categoryNextPage.slot, InventoryUtils.makeItem(plugin.getInventories().categoryNextPage, Collections.singletonList(new Placeholder("next_page", String.valueOf(page + 1)))));
        if(plugin.getInventories().categoryBackMainMenuButton.enabled)
            inventory.setItem(plugin.getInventories().categoryBackMainMenuButton.slot, InventoryUtils.makeItem(plugin.getInventories().categoryBackMainMenuButton));
        inventory.setItem(plugin.getInventories().categoryCloseButton.slot, InventoryUtils.makeItem(plugin.getInventories().categoryCloseButton));

        player.openInventory(inventory);

    }

    private StatsMap getStatsMap(Player player){
        Map<String, Integer> unlocked = new HashMap<>();
        Map<String, Integer> total = new HashMap<>();
        for(CraftingRecipe craftingRecipe : plugin.getCraftingRecipes().getRecipes())
            if(craftingRecipe.getCategory() != null){
                total.put(craftingRecipe.getCategory(), total.getOrDefault(craftingRecipe.getCategory(), 0)+1);
                if(hasPermission(player, craftingRecipe))
                    unlocked.put(craftingRecipe.getCategory(), unlocked.getOrDefault(craftingRecipe.getCategory(), 0)+1);
            }
        return new StatsMap(unlocked, total);
    }

    private boolean hasPermission(Player player, CraftingRecipe craftingRecipe){
        return craftingRecipe.getPermission() == null || craftingRecipe.getPermission().equals("") || player.hasPermission(craftingRecipe.getPermission());
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
