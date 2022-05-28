package mc.ultimatecore.collections.gui;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.objects.Category;
import mc.ultimatecore.collections.objects.Collection;
import mc.ultimatecore.collections.objects.PlayerCollection;
import mc.ultimatecore.collections.utils.InventoryUtils;
import mc.ultimatecore.collections.utils.Placeholder;
import mc.ultimatecore.collections.utils.StringUtils;
import mc.ultimatecore.collections.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class SubMenusGUI implements GUI {
    
    private final HashMap<Integer, Collection> menuItems = new HashMap<>();
    
    private final UUID uuid;
    
    private final Category category;
    
    public SubMenusGUI(UUID uuid, Category category) {
        this.uuid = uuid;
        this.category = category;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == HyperCollections.getInstance().getInventories().getPreviousPage().slot) {
                player.openInventory(new MainMenuGUI(uuid).getInventory());
            } else if (e.getSlot() == HyperCollections.getInstance().getInventories().getCloseButton().slot) {
                player.closeInventory();
            } else {
                if (menuItems.containsKey(e.getSlot())) {
                    Collection collection = menuItems.get(e.getSlot());
                    PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(player.getUniqueId());
                    if (playerCollection.getXP(collection.getKey()) > 0)
                        player.openInventory(new LevelsMenu(uuid, collection).getInventory());
                    else
                        player.sendMessage(Utils.color(HyperCollections.getInstance().getMessages().getMessage("collectionLocked")));
                }
            }
        }
    }
    
    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperCollections.getInstance().getInventories().getSubMenuSize(), StringUtils.color(HyperCollections.getInstance().getInventories().getSubMenuTitles().get(category.getName())));
        for (Integer i : HyperCollections.getInstance().getInventories().getSubMenuDecoration())
            inventory.setItem(i, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getBackground()));
        for (Collection collection : HyperCollections.getInstance().getCollections().collections.values()) {
            if (!collection.getCategory().equals(this.category)) continue;
            String key = collection.getKey();
            PlayerCollection playerCollection = HyperCollections.getInstance().getCollectionsManager().createOrGetUser(uuid);
            if (playerCollection.getXP(key) > 0) {
                inventory.setItem(collection.getSlot(), InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getSubMenuItems().get("unlockedItem"), Utils.getCollectionPlaceholders(uuid, collection), playerCollection.getLevel(key) + 1, collection, true));
            } else {
                inventory.setItem(collection.getSlot(), InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getSubMenuItems().get("lockedItem"), Collections.singletonList(new Placeholder("collection_name", collection.getName()))));
            }
            menuItems.put(collection.getSlot(), collection);
        }
        inventory.setItem(HyperCollections.getInstance().getInventories().getSubMenuItems().get(category.getName()).slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getSubMenuItems().get(category.getName()), Utils.getCategoriesPlaceholder(uuid, this.category)));
        if (HyperCollections.getInstance().getInventories().isBackButtons())
            inventory.setItem(HyperCollections.getInstance().getInventories().getPreviousPage().slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getPreviousPage()));
        inventory.setItem(HyperCollections.getInstance().getInventories().getCloseButton().slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getCloseButton()));
        return inventory;
    }
}
