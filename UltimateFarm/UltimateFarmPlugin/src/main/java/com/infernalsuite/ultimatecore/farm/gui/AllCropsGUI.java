package com.infernalsuite.ultimatecore.farm.gui;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.configs.Inventories.Item;
import com.infernalsuite.ultimatecore.farm.objects.HyperRegion;
import com.infernalsuite.ultimatecore.farm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@RequiredArgsConstructor
public class AllCropsGUI implements GUI {

    private final Map<Integer, String> farmSlots = new HashMap<>();

    private final int page;

    private final HyperRegions plugin = HyperRegions.getInstance();

    @Override
    public @NotNull
    Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, Utils.color("&8Select Type"));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, Utils.makeItem(plugin.getInventories().background));
        List<String> regions = new ArrayList<>(plugin.getFarmManager().hyperRegions.keySet());
        int slot = 0;
        int i = 45 * (page - 1);
        Item item = new Item(plugin.getInventories().mainMenuItem);
        while (slot < 45) {
            if (regions.size() > i && i >= 0) {
                HyperRegion farmRegion = plugin.getFarmManager().hyperRegions.get(regions.get(i));
                inventory.setItem(slot, Utils.makeItemHidden(item, Collections.singletonList(new Utils.Placeholder("region_type", Utils.getFormattedName(regions.get(i)))), farmRegion.getTexture()));
                farmSlots.put(slot, regions.get(i));
                slot++;
                i++;
            } else {
                inventory.setItem(slot, Utils.makeItemHidden(plugin.getInventories().background));
                slot++;
            }
        }
        if (page > 1)
            inventory.setItem(plugin.getInventories().previousPage.slot, Utils.makeItem(plugin.getInventories().previousPage));
        inventory.setItem(plugin.getInventories().nextPage.slot, Utils.makeItem(plugin.getInventories().nextPage));

        inventory.setItem(plugin.getInventories().closeButton.slot, Utils.makeItem(plugin.getInventories().closeButton));
        return inventory;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == plugin.getInventories().closeButton.slot) {
                player.closeInventory();
            } else if (e.getSlot() == plugin.getInventories().previousPage.slot && page > 1) {
                player.openInventory(new AllCropsGUI(page - 1).getInventory());
            } else if (e.getSlot() == plugin.getInventories().nextPage.slot) {
                player.openInventory(new AllCropsGUI(page + 1).getInventory());
            } else {
                for (Integer slot : farmSlots.keySet()) {
                    if (slot == e.getSlot()) {
                        String key = farmSlots.get(slot);
                        player.openInventory(new SettingsGUI(key).getInventory());
                    }
                }
            }
        }
    }
}
