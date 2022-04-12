package mc.ultimatecore.crafting.gui;

import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.api.events.*;
import mc.ultimatecore.crafting.configs.*;
import mc.ultimatecore.crafting.managers.*;
import mc.ultimatecore.crafting.objects.*;
import mc.ultimatecore.crafting.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

// TODO:
// Bugs:
// Crafting result incorrectly appears green at times (put any item in the slot)
// Moving items out doesn't update crafing menu
// Preview recipes disappears alot
//
public class CraftingGUI implements SimpleGUI {

    public static final int RESULT_SLOT = 23;

    private final Inventories inventoryConfig;

    private final CraftingGUIManager guiManager;
    private final Inventory inventory;

    public boolean isClosed = false;

    public CraftingGUI() {
        this.inventoryConfig = HyperCrafting.getInstance().getInventories();
        this.inventory = Bukkit.createInventory(this, this.inventoryConfig.mainMenuSize, Utils.color(this.inventoryConfig.mainMenuTitle));

        this.guiManager = new CraftingGUIManager(inventory);


        // Populate decoration slots
        for (int slot : this.inventoryConfig.decorationSlots) {
            if (slot == 23) {
                continue;
            }

            this.inventory.setItem(slot, InventoryUtils.makeItemHidden(this.inventoryConfig.background));
        }

        this.inventory.setItem(inventoryConfig.closeButton.slot, InventoryUtils.makeItem(inventoryConfig.closeButton));
    }


    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        isClosed = true;
        Player player = (Player) event.getPlayer();

        for (int slot : this.inventoryConfig.craftingSlots) {
            ItemStack itemStack = event.getInventory().getItem(slot);
            if (!InventoryUtils.isEmpty(itemStack)) {

                Map<Integer, ItemStack> extraItems = player.getInventory().addItem(itemStack); // Add items from the crafting menu
                if (!extraItems.isEmpty()) {
                    for (ItemStack extraItem : extraItems.values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), extraItem);
                    }
                }
            }
        }
    }

    @Override
    public void openInventory(Player player) {
        guiManager.init(player);
        player.openInventory(inventory);
    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        HyperCrafting hyperCrafting = HyperCrafting.getInstance();

        Bukkit.getScheduler().runTask(hyperCrafting, () -> {
            if (!isClosed) {
                guiManager.init((Player) event.getWhoClicked());
            }
        });
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();
        ClickType clickType = event.getClick();
        ItemStack cursor = event.getCursor();
        HyperCrafting hyperCrafting = HyperCrafting.getInstance();

        if (slot == this.inventoryConfig.closeButton.slot) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }

        event.setCancelled(true); // Stricly cancel on default

        if (this.inventoryConfig.craftingSlots.contains(slot)) {
            event.setCancelled(false);
        }

        // Handle crafting items by directly clicking on recipes
        Map<Integer, String> recipeMap = this.guiManager.getRecipeSlots();
        if (recipeMap.containsKey(slot) && hyperCrafting.getConfiguration().showAvailableRecipes && player.hasPermission("hypercrafting.autorecipes") && clickType.isLeftClick()) {
            Optional<CraftingRecipe> craftingRecipe = hyperCrafting.getCraftingRecipes().getRecipeByName(recipeMap.get(slot));

            if (craftingRecipe.isPresent()) {
                ItemStack clicked = craftingRecipe.get().getResult().clone();

                // Don't allow the player to pickup recipe slots if it's normally impossible
                if (cursor != null && cursor.getType() != Material.AIR && !cursor.isSimilar(clicked) || event.isShiftClick() || event.isRightClick()) {
                    return;
                }

                AutoReturn autoReturn = this.guiManager.makeAutoCraft(player, craftingRecipe.get());
                if (autoReturn == AutoReturn.SUCCESS) {
                    if (!InventoryUtils.isEmpty(cursor)) {
                        clicked.setAmount(cursor.getAmount() + clicked.getAmount());
                    }

                    Bukkit.getScheduler().runTask(hyperCrafting, () -> player.setItemOnCursor(clicked));
                } else if (autoReturn == AutoReturn.NO_ITEMS) {
                    player.sendMessage(Utils.color(hyperCrafting.getMessages().getMessage("noItems")));
                }
            } else {
                return; // If there is no crafting recipe, do nothing!
            }
        }

        ItemStack craftedItem = guiManager.getDisplayItem();
        if (slot == RESULT_SLOT) {
            // If recipe not found don't click
            if (Utils.matchType(craftedItem, this.inventoryConfig.recipeNotFound.material)) {
                return;
            }

            // If the player can't gather the item don't click
            if (!canHoldItem(craftedItem, cursor)) {
                return;
            }

            // TODO: Clean up this code
            if (event.isShiftClick()) {
                if (!reachStackSize(cursor, craftedItem)) {
                    Bukkit.getServer().getPluginManager().callEvent(new HyperCraftEvent(player, event.getCurrentItem()));
                    ItemStack newItem = craftedItem.clone();
                    int maxQuantity = guiManager.getMinItemQuantity(newItem);
                    for (int i = 0; i < maxQuantity; i++) {
                        guiManager.removeItem(player, craftedItem, true);
                    }

                    if (maxQuantity > newItem.getMaxStackSize()) {
                        for (int i = 0; i < maxQuantity; i++) {
                            player.getInventory().addItem(newItem);
                        }
                    } else {
                        newItem.setAmount(newItem.getAmount() * maxQuantity);
                        player.getInventory().addItem(newItem);
                    }
                }
            } else {
                guiManager.removeItem(player, craftedItem, false);
            }
        }

        // TODO: Don't rely on the bukkit inventory for state.
        // Store the needed items in a map for example, and have proper updating code.
        // (We have to do this because that code relies on items that are updated after this event)
        Bukkit.getScheduler().runTask(hyperCrafting, () -> {
            if (!isClosed) {
                guiManager.init(player);
            }
        });
    }

    private boolean canHoldItem(ItemStack itemStack, ItemStack cursor) {
        return Utils.itemIsNull(cursor) || !Utils.itemIsNull(cursor) && cursor.isSimilar(itemStack);
    }

    private boolean reachStackSize(ItemStack cursor, ItemStack secondItem) {
        if (cursor == null || cursor.getType().equals(Material.AIR))
            return false;
        return secondItem != null && cursor.getAmount() + secondItem.getAmount() >= cursor.getMaxStackSize();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
