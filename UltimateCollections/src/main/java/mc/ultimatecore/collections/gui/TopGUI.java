package mc.ultimatecore.collections.gui;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.Item;
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

@RequiredArgsConstructor
public class TopGUI implements GUI {
    
    private final UUID uuid;
    
    private final HyperCollections plugin = HyperCollections.getInstance();
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == HyperCollections.getInstance().getInventories().getCloseButton().slot) {
            player.closeInventory();
        } else if (e.getSlot() == HyperCollections.getInstance().getInventories().getTopMenuItem().slot) {
            player.openInventory(new MainMenuGUI(player.getUniqueId()).getInventory());
        } else if (e.getSlot() == HyperCollections.getInstance().getInventories().getMainMenuBack().slot && HyperCollections.getInstance().getInventories().isMainMenuBackEnabled()) {
            player.closeInventory();
            String command = plugin.getInventories().getMainMenuBack().command;
            if (command.contains("%player%"))
                Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())), 3);
            else
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.performCommand(command), 3);
        } else {
            for (Item item : HyperCollections.getInstance().getInventories().getTopMenu()) {
                if (item.slot == e.getSlot()) {
                    player.closeInventory();
                    if (item.command != null) {
                        Bukkit.getServer().dispatchCommand(e.getWhoClicked(), item.command);
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperCollections.getInstance().getInventories().getTopMenuSize(), StringUtils.color(HyperCollections.getInstance().getInventories().getTopMenuTitle()));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getBackground()));
        if (HyperCollections.getInstance().getInventories().isMainMenuBackEnabled())
            inventory.setItem(HyperCollections.getInstance().getInventories().getMainMenuBack().slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getMainMenuBack()));
        for (Item item : HyperCollections.getInstance().getInventories().getTopMenu())
            inventory.setItem(item.slot, InventoryUtils.makeItem(item, Utils.getTopPlaceholders(uuid, item.category)));
        inventory.setItem(HyperCollections.getInstance().getInventories().getTopMenuItem().slot, InventoryUtils.makeItem(HyperCollections.getInstance().getInventories().getTopMenuItem(), Utils.getGlobalPlaceHolders(uuid)));
        inventory.setItem((HyperCollections.getInstance().getInventories()).getCloseButton().slot, InventoryUtils.makeItem((HyperCollections.getInstance().getInventories()).getCloseButton()));
        return inventory;
    }
}
