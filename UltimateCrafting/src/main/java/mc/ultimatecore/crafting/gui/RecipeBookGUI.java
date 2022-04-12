package mc.ultimatecore.crafting.gui;

import lombok.AllArgsConstructor;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.objects.Category;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.objects.StatsMap;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class RecipeBookGUI implements SimpleGUI {

    private final HyperCrafting plugin;

    private final Map<Integer, Category> slots = new HashMap<>();

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        if(e.getSlot() == plugin.getInventories().bookCloseButton.slot) {
            player.closeInventory();
        }else if(e.getSlot() == plugin.getInventories().backMainMenu.slot) {
            if (plugin.getInventories().backMainMenu.enabled && plugin.getInventories().backMainMenu.command != null)
                player.performCommand(plugin.getInventories().backMainMenu.command);
        }else if(slots.containsKey(e.getSlot())) {
            new CategoryRecipeGUI(plugin, slots.get(e.getSlot()), 1).openInventory(player);
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(this, plugin.getInventories().bookSize, Utils.color(plugin.getInventories().bookTitle));
        for (Integer slot : plugin.getInventories().bookSlots)
            inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().bookDecoration));
        StatsMap str = getStatsMap(player);
        plugin.getCategories().getAllCategories().forEach(category -> {
            inventory.setItem(category.getSlot(), InventoryUtils.makeItem(plugin.getInventories().categoryItem, Utils.getRecipeBookPlaceholders(category, str), category));
            slots.put(category.getSlot(), category);
        });
        inventory.setItem(plugin.getInventories().recipeBook.slot, InventoryUtils.makeItem(plugin.getInventories().recipeBook, Utils.getRecipeBookPlaceholders(str)));
        if(plugin.getInventories().backMainMenu.enabled)
            inventory.setItem(plugin.getInventories().backMainMenu.slot, InventoryUtils.makeItem(plugin.getInventories().backMainMenu));
        inventory.setItem(plugin.getInventories().bookCloseButton.slot, InventoryUtils.makeItem(plugin.getInventories().bookCloseButton));
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
