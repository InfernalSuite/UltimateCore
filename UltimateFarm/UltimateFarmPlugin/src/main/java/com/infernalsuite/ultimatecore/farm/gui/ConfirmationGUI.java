package com.infernalsuite.ultimatecore.farm.gui;

import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.objects.HyperRegion;
import com.infernalsuite.ultimatecore.farm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ConfirmationGUI implements GUI {

    private final String regionName;

    private final String key;

    private final HyperRegions plugin = HyperRegions.getInstance();

    public ConfirmationGUI(String key, String regionName) {
        this.key = key;
        this.regionName = regionName;
    }

    @Override
    public @NotNull
    Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, Utils.color("&8Confirm"));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, Utils.makeItem(plugin.getInventories().background));
        inventory.setItem(12, Utils.makeItemHidden(plugin.getInventories().confirmDelete));
        inventory.setItem(14, Utils.makeItemHidden(plugin.getInventories().cancelDelete));
        return inventory;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if (e.getSlot() == 12) {
                    HyperRegion farmRegion = plugin.getFarmManager().hyperRegions.get(key);
                    if (farmRegion.getRegions().remove(regionName))
                        player.sendMessage(Utils.color(plugin.getMessages().regionRemoved.replace("%region%", regionName).replace("%region_type%", key).replace("%prefix%", HyperRegions.getInstance().getConfiguration().prefix)));
                } else if (e.getSlot() != 14) {
                    return;
                }
                player.openInventory(new AllCropsGUI(1).getInventory());
            }
        }
    }
}
