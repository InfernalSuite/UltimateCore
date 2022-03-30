package com.infernalsuite.ultimatecore.crafting.managers;

import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.api.events.HyperCraftEvent;
import com.infernalsuite.ultimatecore.crafting.objects.AutoReturn;
import com.infernalsuite.ultimatecore.crafting.objects.CraftingRecipe;
import com.infernalsuite.ultimatecore.crafting.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CraftingGUIManager {
    
    private final List<Integer> craftingSlots;
    
    @Getter
    @Setter
    private InventoryView inventoryView;
    
    private final Inventory inventory;
    
    @Getter
    private final Map<Integer, String> recipeSlots;
    
    @Getter
    private final Map<Integer, CraftingRecipe> autoRecipes;
    
    @Getter
    private ItemStack displayItem;
    
    private final HyperCrafting plugin;
    
    public CraftingGUIManager(Inventory inventory, InventoryView inventoryView) {
        this.plugin = HyperCrafting.getInstance();
        this.inventory = inventory;
        this.craftingSlots = new ArrayList<>(plugin.getInventories().craftingSlots);
        this.recipeSlots = new HashMap<>();
        this.autoRecipes = new HashMap<>();
        this.inventoryView = inventoryView;
    }
    
    public void init(Player player) {
        recipeSlots.clear();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (inventoryView == null || inventory == null) return;
            for (int i = 1; i <= craftingSlots.size(); i++)
                inventoryView.setItem(i, inventory.getItem(craftingSlots.get(i - 1)));
            if (plugin.getConfiguration().showAvailableRecipes && player.hasPermission("hypercrafting.autorecipes")) {
                List<CraftingRecipe> craftingRecipes = getAvailableRecipes(player, plugin.getInventories().availableRecipesSlots.size());
                int index = 0;
                for (Integer slot : plugin.getInventories().availableRecipesSlots) {
                    try {
                        CraftingRecipe craftingRecipe = craftingRecipes.get(index);
                        inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().previewCraftItem, craftingRecipe.getResult(), "autoRecipes"));
                        recipeSlots.put(slot, craftingRecipe.getName());
                    } catch (IndexOutOfBoundsException e) {
                        inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().background));
                    }
                    index++;
                }
            } else {
                plugin.getInventories().availableRecipesSlots.forEach(slot -> inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().background)));
            }
            ItemStack viewItem = inventoryView.getItem(0);
            Optional<CraftingRecipe> craftingRecipe = plugin.getCraftingRecipes().matchRecipe(player, getItemStackMap());
            if (craftingRecipe.isPresent() && !craftingRecipe.get().isOverrideRecipe()) {
                if (!checkRecipe(craftingRecipe.get())) {
                    inventory.setItem(23, InventoryUtils.makeItem(plugin.getInventories().recipeNotFound));
                    colorizeSlots(false);
                    return;
                }
                if (!craftingRecipe.get().hasPermission(player)) {
                    inventory.setItem(23, InventoryUtils.makeItem(plugin.getInventories().recipeNoPermission));
                    colorizeSlots(false);
                    return;
                }
                colorizeSlots(true);
                displayItem = craftingRecipe.get().getResult();
                inventory.setItem(23, InventoryUtils.makeItem(plugin.getInventories().previewCraftItem, craftingRecipe.get().getResult(), "temporalCraft"));
            } else {
                if (viewItem != null && viewItem.getType() != Material.AIR && !plugin.getBlackList().itemIsBlackListed(viewItem)) {
                    // Fixing usage of custom items for vanilla items
                    if (getItemStackMap().values().stream().anyMatch(itemStack -> itemStack.hasItemMeta() || (itemStack.getItemMeta() != null && (itemStack.getItemMeta().hasEnchants() || itemStack.getItemMeta().hasDisplayName() || itemStack.getItemMeta().hasLore()))))
                        return;
                    
                    displayItem = viewItem;
                    inventory.setItem(23, InventoryUtils.makeItem(plugin.getInventories().previewCraftItem, viewItem, "temporalCraft"));
                    colorizeSlots(true);
                } else {
                    inventory.setItem(23, null);
                    inventory.setItem(23, InventoryUtils.makeItem((plugin.getInventories()).recipeNotFound));
                    colorizeSlots(false);
                }
            }
        }, 0L);
    }
    
    public AutoReturn makeAutoCraft(Player player, CraftingRecipe craftingRecipe) {
        if (reachStackSize(player, craftingRecipe.getResult().clone())) {
            init(player);
            return AutoReturn.REACH_STACK;
        }
        if (!playerCanCraft(player, craftingRecipe)) {
            init(player);
            return AutoReturn.NO_ITEMS;
        } else {
            removeCraftItems(player, craftingRecipe);
            init(player);
            return AutoReturn.SUCCESS;
        }
    }
    
    private boolean reachStackSize(Player player, ItemStack itemStack) {
        ItemStack cursor = player.getItemOnCursor();
        return cursor != null && cursor.getType() != Material.AIR && cursor.isSimilar(itemStack) && cursor.getAmount() + itemStack.getAmount() > itemStack.getMaxStackSize();
    }
    
    private void removeCraftItems(Player player, CraftingRecipe craftingRecipe) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (ItemStack costItem : craftingRecipe.getRecipeItems().values()) {
                ItemStack[] playerContents = player.getInventory().getContents();
                try {
                    for (ItemStack itemStack : playerContents) {
                        if (itemStack != null && itemStack.isSimilar(costItem)) {
                            ItemStack toRemoveItem = costItem.clone();
                            if (toRemoveItem.getAmount() > itemStack.getMaxStackSize()) {
                                for (int i = 0; i < toRemoveItem.getAmount(); i++) {
                                    player.getInventory().removeItem(toRemoveItem);
                                }
                            } else {
                                toRemoveItem.setAmount(costItem.getAmount());
                                player.getInventory().removeItem(toRemoveItem);
                            }
                            break;
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, 0L);
        
    }
    
    private List<CraftingRecipe> getAvailableRecipes(Player player, int amount) {
        List<CraftingRecipe> craftingRecipes = new ArrayList<>();
        for (CraftingRecipe craftingRecipe : plugin.getCraftingRecipes().getRecipes()) {
            if (craftingRecipes.size() > amount) break;
            if (!craftingRecipe.hasPermission(player)) continue;
            if (!playerCanCraft(player, craftingRecipe)) continue;
            craftingRecipes.add(craftingRecipe);
        }
        return craftingRecipes;
    }
    
    private boolean playerCanCraft(Player player, CraftingRecipe craftingRecipe) {
        Collection<ItemStack> recipeItems = craftingRecipe.getRecipeItems().values();
        List<ItemStack> playerItems = new ArrayList<>();
        for (ItemStack itemStack : player.getInventory().getContents())
            if (itemStack != null)
                playerItems.add(itemStack.clone());
        for (ItemStack itemStack : recipeItems) {
            if (itemStack != null && Utils.getItemQuantity(playerItems, itemStack) < Utils.getItemQuantity(recipeItems, itemStack))
                return false;
        }
        return true;
    }
    
    private HashMap<Integer, ItemStack> getItemStackMap() {
        int mapSlot = 0;
        HashMap<Integer, ItemStack> itemStackMap = new HashMap<>();
        for (Integer slot : craftingSlots) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack != null && itemStack.getType() != Material.AIR)
                itemStackMap.put(mapSlot, itemStack);
            mapSlot++;
        }
        return itemStackMap;
    }
    
    public void removeItem(Player player, ItemStack itemStack, boolean shift) {
        if (itemStack == null) return;
        Optional<CraftingRecipe> craftingRecipe = plugin.getCraftingRecipes().getRecipeByItem(itemStack);
//        ItemStack cursor = player.getItemOnCursor();
//        if(cursor.isSimilar(itemStack) && cursor.getAmount() + itemStack.getAmount() > itemStack.getMaxStackSize() && !shift)
//            return;
//        if(cursor.getType() != Material.AIR && !cursor.isSimilar(itemStack))
//            return;
//        if(cursor.isSimilar(itemStack))
//            itemStack.setAmount(itemStack.getAmount() + cursor.getAmount());
        
        if (craftingRecipe.isPresent())
            removeRecipeItems(craftingRecipe.get());
        else
            removeRecipeItems();
        if (!shift) {
            Bukkit.getServer().getPluginManager().callEvent(new HyperCraftEvent(player, itemStack));
            player.getInventory().addItem(itemStack).forEach((k, v) -> player.getWorld().dropItemNaturally(player.getLocation(), v));
//            Bukkit.getScheduler().runTask(plugin, () -> player.setItemOnCursor(itemStack));
        }
        inventory.setItem(23, null);
        init(player);
    }
    
    
    public int getMinItemQuantity(ItemStack itemStack) {
        if (itemStack == null) return 1;
        Optional<CraftingRecipe> craftingRecipe = plugin.getCraftingRecipes().getRecipeByItem(itemStack);
        
        int quantity = 64;
        for (int i = 1; i <= craftingSlots.size(); i++) {
            ItemStack item = inventory.getItem(craftingSlots.get(i - 1));
            if (item == null) continue;
            if (item.getAmount() < quantity)
                quantity = item.getAmount();
        }
        if (craftingRecipe.isPresent())
            return quantity / craftingRecipe.get().getMaxItemQuantity();
        return quantity;
    }
    
    
    private void colorizeSlots(boolean success) {
        ItemStack itemStack = InventoryUtils.makeItem(success ? plugin.getInventories().succesItem : plugin.getInventories().unsuccesItem);
        for (Integer slot : plugin.getInventories().successSlots) {
            inventory.setItem(slot, null);
            inventory.setItem(slot, itemStack);
        }
        
    }
    
    private boolean checkRecipe(CraftingRecipe craftingRecipe) {
        int toReach = craftingRecipe.getRecipeItems().size();
        int init = 0;
        int recipeSlot = 0;
        for (Integer slot : craftingSlots) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack != null && itemStack.getType() != Material.AIR && craftingRecipe.getRecipeItems().containsKey(recipeSlot)) {
                ItemStack recipeItem = craftingRecipe.getRecipeItems().get(recipeSlot);
                if (itemStack.getAmount() >= recipeItem.getAmount())
                    init++;
            }
            recipeSlot++;
        }
        return toReach == init;
    }
    
    private void removeRecipeItems(CraftingRecipe craftingRecipe) {
        int recipeSlot = 0;
        for (Integer slot : craftingSlots) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack != null && itemStack.getType() != Material.AIR && craftingRecipe.getRecipeItems().containsKey(recipeSlot)) {
                ItemStack recipeItem = craftingRecipe.getRecipeItems().get(recipeSlot).clone();
                if (itemStack.getAmount() == recipeItem.getAmount()) {
                    inventory.setItem(slot, null);
                } else {
                    ItemStack newItem = itemStack.clone();
                    newItem.setAmount(newItem.getAmount() - recipeItem.getAmount());
                    inventory.setItem(slot, newItem);
                }
            }
            recipeSlot++;
        }
    }
    
    private void removeRecipeItems() {
        for (Integer slot : craftingSlots) {
            ItemStack item = inventory.getItem(slot);
            if (!Utils.itemIsNull(item)) {
                ItemStack itemStack = item.clone();
                if (itemStack.getAmount() == 1) {
                    inventory.setItem(slot, null);
                } else {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    inventory.setItem(slot, itemStack);
                }
            }
        }
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public void remove() {
        inventoryView.getPlayer().remove();
    }
    
    public void add(InventoryView inventoryView) {
        this.inventoryView = inventoryView;
    }
    
}
