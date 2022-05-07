package mc.ultimatecore.crafting.gui.crafting;

import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.api.events.*;
import mc.ultimatecore.crafting.configs.*;
import mc.ultimatecore.crafting.objects.*;
import mc.ultimatecore.crafting.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CraftingGUIManager {

    private final Player player;
    private final Inventory inventory;

    private final HyperCrafting plugin;
    private final List<Integer> validSlots;

    @Nullable
    private CraftingRecipe activeRecipe = null;
    private boolean isVanilla = false;

    private final Map<Integer, CraftingRecipe> recipeSlots = new HashMap<>();

    public CraftingGUIManager(Player player, Inventory inventory, HyperCrafting plugin) {
        this.player = player;
        this.plugin = plugin;
        this.inventory = inventory;
        this.validSlots = new ArrayList<>(plugin.getInventories().craftingSlots);
    }

    public void updateRecipeSlots() {
        // Clear previous recipe slots
        recipeSlots.clear();
        for (int slot : plugin.getInventories().availableRecipesSlots) {
            inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().background));
        }

        List<Integer> slots = plugin.getInventories().availableRecipesSlots;
        if (plugin.getConfiguration().showAvailableRecipes && player.hasPermission("hypercrafting.autorecipes")) {
            List<CraftingRecipe> craftingRecipes = getQuickCraftingRecipes(player, slots.size());

            int index = 0;
            for (Integer slot : slots) {
                try {
                    CraftingRecipe craftingRecipe = craftingRecipes.get(index);
                    recipeSlots.put(slot, craftingRecipe);
                    inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().previewCraftItem, craftingRecipe.getResult(), "autoRecipes"));
                } catch (IndexOutOfBoundsException e) {
                }
                index++;
            }
        }

    }

    // Get valid recipes that the player can currently craft.
    private List<CraftingRecipe> getQuickCraftingRecipes(Player player, int amount) {
        List<CraftingRecipe> craftingRecipes = new ArrayList<>();
        for (CraftingRecipe craftingRecipe : plugin.getCraftingRecipes().getRecipes()) {
            if (craftingRecipes.size() > amount) {
                break;
            }
            if (!craftingRecipe.hasPermission(player)) {
                continue;
            }
            if (!hasEnoughItemsForRecipe(player, craftingRecipe)) {
                continue;
            }
            craftingRecipes.add(craftingRecipe);
        }
        return craftingRecipes;
    }

    // Updates the craft status which includes the result item and status glass panes on bottom
    public void updateCraftStatus() {
        CraftingRecipe recipe = this.activeRecipe;
        Inventories inventoryConfig = plugin.getInventories();

        boolean isSuccess = false;
        ItemStack item;

        // Check if the player has a valid recipe
        if (recipe != null && !recipe.isOverrideRecipe()) {
            boolean hasItems = this.checkRecipe(recipe);
            boolean hasPermission = recipe.hasPermission(player);

            if (!hasItems) {
                item = InventoryUtils.makeItem(inventoryConfig.recipeNotFound);
            } else if (!hasPermission) {
                item = InventoryUtils.makeItem(inventoryConfig.recipeNoPermission);
            } else {
                isSuccess = true;
                item = InventoryUtils.makeItem(inventoryConfig.previewCraftItem, recipe.getResult().clone(), "temporalCraft");
            }
        } else {
            item = InventoryUtils.makeItem((plugin.getInventories()).recipeNotFound);
        }


        inventory.setItem(CraftingGUI.RESULT_SLOT, item);

        // Update status glass panes
        ItemStack itemStack = InventoryUtils.makeItem(isSuccess ? inventoryConfig.successItem : inventoryConfig.unsuccessItem);
        for (int slot : plugin.getInventories().successSlots) {
            inventory.setItem(slot, itemStack);
        }
    }

    void updateActiveRecipe() {
        HashMap<Integer, ItemStack> items = this.getCraftingItems();
        this.activeRecipe = plugin.getCraftingRecipes().matchRecipe(player, items).orElse(null); // Update recipe
        this.isVanilla = false;
        if (this.activeRecipe == null) {
            ItemStack[] itemArray = new ItemStack[9];
            for (Map.Entry<Integer, ItemStack> item : items.entrySet()) {
                itemArray[item.getKey()] = item.getValue();
            }

            Recipe recipe = this.plugin.getVanillaSource().getRecipe(itemArray, player.getWorld());
            if (recipe != null) {
                this.activeRecipe = new CraftingRecipe("Vanilla", recipe.getResult(), this.plugin);
                this.isVanilla = true;
            }
        }
    }

    public void updateMenu() {
        this.updateActiveRecipe();
        this.updateRecipeSlots();
        this.updateCraftStatus();
    }

    public void quickCraft(CraftingRecipe recipe, boolean onCursor) {
        craft(onCursor, true, recipe);
    }

    public void normalCraft(boolean onCursor) {
        craft(onCursor, false, this.activeRecipe);
    }

    private void craft(boolean onCursor, boolean quickCraft, CraftingRecipe recipe) {
        if (recipe == null) {
            return;
        }

        // If using normal crafting, remove items from the crafting inventory
        if (isVanilla) {
            ItemStack[] itemArray = new ItemStack[9];
            for (Map.Entry<Integer, ItemStack> item : this.getCraftingItems().entrySet()) {
                itemArray[item.getKey()] = item.getValue();
            }

            ItemStack[] items = this.plugin.getVanillaSource().getRemainingItemsForCrafting(itemArray, player.getWorld());
            updateVanillaRecipeCrafingItems(items);
        } else {
            if (!quickCraft) {
                this.removeRecipeItems(recipe);
            } else {
                removePlayerItems(recipe);
            }
        }

        ItemStack result = recipe.getResult().clone();
        Bukkit.getServer().getPluginManager().callEvent(new HyperCraftEvent(player, result));
        if (onCursor) {
            // Attempt to stack items on the cursor
            ItemStack newStack = InventoryUtils.stackitem(result, player.getItemOnCursor());
            if (newStack != null) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        player.setItemOnCursor(newStack);
                    }
                }.runTask(plugin);
            }
        } else {
            player.getInventory().addItem(result).forEach((k, v) -> player.getWorld().dropItemNaturally(player.getLocation(), v));
        }

        this.updateActiveRecipe();
        if (quickCraft) {
            this.updateRecipeSlots();
        }
    }

    private void removePlayerItems(CraftingRecipe recipe) {
        for (ItemStack costItem : recipe.getRecipeItems().values()) {
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
    }
    // Validation methods

    private boolean hasEnoughItemsForRecipe(Player player, CraftingRecipe craftingRecipe) {
        List<ItemStack> playerItems = Arrays.asList(player.getInventory().getStorageContents());

        for (ItemStack itemStack : craftingRecipe.getRecipeItems().values()) {
            // If the quantity is lesser than the required recipe, false!
            if (Utils.getItemQuantity(playerItems, itemStack) < itemStack.getAmount()) {
                return false;
            }
        }

        return true;
    }

    private void updateVanillaRecipeCrafingItems(ItemStack[] itemStacks) {
        int mappedRecipeSlot = 0;
        for (Integer slot : validSlots) {
            ItemStack originalSlot = inventory.getItem(slot);
            ItemStack leftoverSlot = itemStacks[mappedRecipeSlot];
            if (!InventoryUtils.isEmpty(originalSlot)) {
                originalSlot.setAmount(originalSlot.getAmount() - 1);
                this.inventory.setItem(slot, originalSlot);
            }

            if (!InventoryUtils.isEmpty(leftoverSlot)) {
                if (InventoryUtils.isEmpty(originalSlot)) {
                    this.inventory.setItem(slot, leftoverSlot);
                } else if (originalSlot.equals(leftoverSlot)) {
                    leftoverSlot.setAmount(originalSlot.getAmount() + leftoverSlot.getAmount());
                    this.inventory.setItem(slot, leftoverSlot);
                }
            }

            mappedRecipeSlot++;
        }

    }

    private HashMap<Integer, ItemStack> getCraftingItems() {
        int mappedRecipeSlot = 0;
        HashMap<Integer, ItemStack> itemStackMap = new HashMap<>();
        for (Integer slot : validSlots) {
            ItemStack itemStack = inventory.getItem(slot);
            if (!InventoryUtils.isEmpty(itemStack)) {
                itemStackMap.put(mappedRecipeSlot, itemStack);
            }

            mappedRecipeSlot++;
        }

        return itemStackMap;
    }

    // It is important to note that these methods do not
    // check the type of item. Just the item counts.
    // Avoid recalculating the entire recipe!

    private boolean checkRecipe(CraftingRecipe craftingRecipe) {
        Map<Integer, ItemStack> recipeItems = craftingRecipe.getRecipeItems();
        int suceeded = 0;
        int mappedRecipeSlot = 0;

        for (Integer slot : validSlots) {
            ItemStack itemStack = inventory.getItem(slot);
            // Is there an item in that slot?
            // Is that item even in a valid recipe slot?
            if (!InventoryUtils.isEmpty(itemStack) && recipeItems.containsKey(mappedRecipeSlot)) {
                ItemStack recipeItem = recipeItems.get(mappedRecipeSlot);

                if (itemStack.getAmount() >= recipeItem.getAmount()) {
                    suceeded++;
                }
            }
            mappedRecipeSlot++;
        }

        // Is the amount of items that match the recipe good
        return recipeItems.size() == suceeded;
    }

    private void removeRecipeItems(CraftingRecipe craftingRecipe) {
        int mappedRecipeSlot = 0;

        for (Integer slot : validSlots) {
            ItemStack itemStack = inventory.getItem(slot);

            // Is there an item in that slot?
            // Is that item even in a valid recipe slot?
            if (!InventoryUtils.isEmpty(itemStack) && craftingRecipe.getRecipeItems().containsKey(mappedRecipeSlot)) {

                ItemStack recipeItem = craftingRecipe.getRecipeItems().get(mappedRecipeSlot);
                // If the items match amount, just remove the item
                ItemStack newItem;
                if (itemStack.getAmount() == recipeItem.getAmount()) {
                    newItem = null;
                } else {
                    newItem = itemStack.clone();
                    newItem.setAmount(newItem.getAmount() - recipeItem.getAmount());
                }

                inventory.setItem(slot, newItem);
            }
            mappedRecipeSlot++;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasActiveRecipe() {
        return this.activeRecipe != null;
    }

    public CraftingRecipe getRecipeSlot(int slot) {
        return this.recipeSlots.get(slot);
    }

    public boolean canQuickCraft(int slot) {
        return getRecipeSlot(slot) != null;
    }

    @Nullable
    public ItemStack getResultItem() {
        if (this.activeRecipe == null) {
            return null;
        }

        return this.activeRecipe.getResult().clone();
    }
}
