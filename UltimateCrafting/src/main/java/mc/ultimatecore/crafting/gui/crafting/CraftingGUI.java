package mc.ultimatecore.crafting.gui.crafting;

import mc.ultimatecore.crafting.*;
import mc.ultimatecore.crafting.configs.*;
import mc.ultimatecore.crafting.gui.*;
import mc.ultimatecore.crafting.objects.*;
import mc.ultimatecore.crafting.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CraftingGUI implements SimpleGUI {

    public static final int RESULT_SLOT = 23;

    private final Inventories inventoryConfig;

    private final CraftingGUIManager guiManager;
    private final Inventory inventory;

    public boolean isClosed = false;

    public CraftingGUI(Player player) {
        this.inventoryConfig = HyperCrafting.getInstance().getInventories();
        this.inventory = Bukkit.createInventory(this, this.inventoryConfig.mainMenuSize, Utils.color(this.inventoryConfig.mainMenuTitle));
        this.guiManager = new CraftingGUIManager(player, inventory);

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
        guiManager.updateMenu();
        player.openInventory(inventory);
    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        HyperCrafting hyperCrafting = HyperCrafting.getInstance();

        Bukkit.getScheduler().runTask(hyperCrafting, () -> {
            if (!isClosed) {
                guiManager.updateMenu();
            }
        });
    }

    @Override
    public void onUpdatePlayerInventory(InventoryClickEvent event) {
        HyperCrafting hyperCrafting = HyperCrafting.getInstance();

        Bukkit.getScheduler().runTask(hyperCrafting, () -> {
            if (!isClosed) {
                guiManager.updateMenu();
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
        CraftingRecipe recipe = this.guiManager.getRecipeSlot(slot);
        if (recipe != null && hyperCrafting.getConfiguration().showAvailableRecipes && player.hasPermission("hypercrafting.autorecipes") && clickType.isLeftClick()) {
            // Don't allow the player to pickup recipe slots if it's normally impossible
            if (!InventoryUtils.canStackitem(recipe.getResult(), cursor)) {
                return;
            }

            if (event.isShiftClick()) {
                // Attempt to craft until there is no valid recipe
                while (this.guiManager.canQuickCraft(slot)) {
                    this.guiManager.quickCraft(recipe, false);
                }
            } else {
                this.guiManager.quickCraft(recipe, true);
            }

            return;
        }

        ItemStack craftedItem = this.guiManager.getResultItem();
        if (slot == RESULT_SLOT) {
            // If there is no result for the current recipe just cancel
            if (craftedItem == null) {
                return;
            }

            // If the player can't gather the item don't click
            if (!InventoryUtils.canStackitem(craftedItem, cursor)) {
                return;
            }

            if (event.isShiftClick()) {
                // Attempt to craft until there is no valid recipe
                while (this.guiManager.hasActiveRecipe()) {
                    this.guiManager.normalCraft(false);
                }
            } else {
                this.guiManager.normalCraft(true);
            }
        }

        // TODO: Don't rely on the bukkit inventory for state.
        // Store the needed items in a map for example, and have proper updating code.
        // (We have to do this because that code relies on items that are updated after this event)
        Bukkit.getScheduler().runTask(hyperCrafting, () -> {
            if (!isClosed) {
                guiManager.updateMenu();
            }
        });
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
