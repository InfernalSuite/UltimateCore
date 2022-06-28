package mc.ultimatecore.collections.gui;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.configs.Inventories;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.objects.PlayerCollection;
import mc.ultimatecore.collections.utils.InventoryUtils;
import mc.ultimatecore.collections.utils.StringUtils;
import mc.ultimatecore.collections.utils.Utils;
import mc.ultimatecore.helper.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LevelsMenu implements GUI {

    private final UUID uuid;

    private final Collection collection;
    private final Pageable pageable;
    private final Inventories inventories;

    public LevelsMenu(UUID uuid, Collection collection, Pageable pageable) {
        this.uuid = uuid;
        this.collection = collection;
        this.pageable = pageable;
        inventories = HyperCollections.getInstance().getInventories();
    }

    public Collection getCollection() {
        return this.collection;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == inventories.getPreviousPage().slot) {
                player.openInventory(new SubMenusGUI(uuid, getCollection().getCategory()).getInventory());
                return;
            }
            if (e.getSlot() == inventories.getCloseButton().slot) {
                player.closeInventory();
                return;
            }
            if (e.getSlot() == inventories.getItem("nextPage").slot) {
                openNextPage(player);
                return;
            }
            if (e.getSlot() == inventories.getItem("previousPage").slot) {
                openLastPage(player);
                return;
            }
        }
    }

    private void openLastPage(Player player) {
        if(pageable.getPage() <= 0) return;
        openPage(player, pageable.getPage() - 1);
    }

    private void openNextPage(Player player) {
        if(pageable.getPage() >= pageable.getMaxPages(collection.getMaxLevel())-1) return;
        openPage(player, pageable.getPage() + 1);
    }

    private void openPage(Player player, int page) {
        player.openInventory(new LevelsMenu(player.getUniqueId(), collection, Pageables.of(page, pageable.getItemsPerPage())).getInventory());
    }
    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, inventories.getLevelsMenuSize(), StringUtils.color(inventories.getLevelsMenuTitle().replace("%collection_name%", collection.getName())));
        fillInventory(inventory);
        return inventory;
    }

    private void fillInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, InventoryUtils.makeItem(inventories.getBackground()));
        }
        PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(uuid);
        int level = pageable.getOffset();
        int currentLevel = playerCollection.getLevel(collection.getKey());

        for (Integer slot : inventories.getLevelsMenuSlots()) {
            level++;
            if (collection.getMaxLevel() < level && level - pageable.getOffset() < pageable.getItemsPerPage()) break;
            if (currentLevel == level - 1) {
                inventory.setItem(slot, InventoryUtils.makeItem(inventories.getItem("progressItem"), Utils.getCollectionPlaceholders(uuid, collection, level), level, collection, false));
                continue;
            }
            if (currentLevel >= level) {
                inventory.setItem(slot, InventoryUtils.makeItem(inventories.getItem("unlockedItem"), Utils.getCollectionPlaceholders(uuid, collection, level), level, collection, false));
                continue;
            }
            inventory.setItem(slot, InventoryUtils.makeItem(inventories.getItem("lockedItem"), Utils.getCollectionPlaceholders(uuid, collection, level), level, collection, false));
        }

        inventory.setItem(inventories.getItem("infoItem").slot, InventoryUtils.makeItem(inventories.getItem("infoItem"), Utils.getCollectionPlaceholders(uuid, collection), level, collection, true));

        if(pageable.getPage() > 0) {
            inventory.setItem(inventories.getItem("previousPage").slot, InventoryUtils.makeItem(inventories.getItem("previousPage")));
        }
        if(pageable.getPage() < pageable.getMaxPages(collection.getMaxLevel())-1) {
            inventory.setItem(inventories.getItem("nextPage").slot, InventoryUtils.makeItem(inventories.getItem("nextPage")));
        }

        if (inventories.isBackButtons())
            inventory.setItem(inventories.getPreviousPage().slot, InventoryUtils.makeItem(inventories.getPreviousPage()));
        inventory.setItem(inventories.getCloseButton().slot, InventoryUtils.makeItem(inventories.getCloseButton()));
    }
}
