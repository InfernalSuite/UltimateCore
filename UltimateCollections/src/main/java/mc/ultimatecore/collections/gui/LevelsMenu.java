package mc.ultimatecore.collections.gui;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.configs.Inventories;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.objects.PlayerCollection;
import mc.ultimatecore.collections.utils.InventoryUtils;
import mc.ultimatecore.collections.utils.StringUtils;
import mc.ultimatecore.collections.utils.Utils;
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
    
    public LevelsMenu(UUID uuid, Collection collection) {
        this.uuid = uuid;
        this.collection = collection;
    }
    
    public Collection getCollection() {
        return this.collection;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == HyperCollections.getInstance().getInventories().getPreviousPage().slot)
                player.openInventory(new SubMenusGUI(uuid, getCollection().getCategory()).getInventory());
            else if (e.getSlot() == HyperCollections.getInstance().getInventories().getCloseButton().slot)
                player.closeInventory();
        }
    }
    
    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperCollections.getInstance().getInventories().getLevelsMenuSize(), StringUtils.color(HyperCollections.getInstance().getInventories().getLevelsMenuTitle().replace("%collection_name%", collection.getName())));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getBackground()));
        PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(uuid);
        int i = 0;
        int level = playerCollection.getLevel(collection.getKey());
        Inventories inventories = HyperCollections.getInstance().getInventories();
        for (Integer slot : inventories.getLevelsMenuSlots()) {
            i++;
            if (collection.getMaxLevel() < i) break;
            if (level == i - 1) {
                inventory.setItem(slot, InventoryUtils.makeItem(inventories.getItem("progressItem"), Utils.getCollectionPlaceholders(uuid, collection, i), i, collection, false));
                continue;
            }
            if (level >= i) {
                inventory.setItem(slot, InventoryUtils.makeItem(inventories.getItem("unlockedItem"), Utils.getCollectionPlaceholders(uuid, collection, i), i, collection, false));
                continue;
            }
            inventory.setItem(slot, InventoryUtils.makeItem(inventories.getItem("lockedItem"), Utils.getCollectionPlaceholders(uuid, collection, i), i, collection, false));
        }
        inventory.setItem(inventories.getItem("infoItem").slot, InventoryUtils.makeItem(inventories.getItem("infoItem"), Utils.getCollectionPlaceholders(uuid, collection), i, collection, true));
        if (inventories.isBackButtons())
            inventory.setItem(inventories.getPreviousPage().slot, InventoryUtils.makeItem(inventories.getPreviousPage()));
        inventory.setItem(inventories.getCloseButton().slot, InventoryUtils.makeItem(inventories.getCloseButton()));
        return inventory;
    }
}
