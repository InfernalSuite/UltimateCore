package com.infernalsuite.ultimatecore.dragon.inventories;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.dragon.utils.Placeholder;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.dragon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CrystalsGUI extends GUI{

    private final Map<Integer, Integer> slots = new HashMap<>();

    public CrystalsGUI(int page) {
        super(page);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == 49) {
            player.closeInventory();
        } else if (slot == 48) {
            player.openInventory(new DragonGUI().getInventory());
        } else if (slot == getInventory().getSize() - 1 && e.getCurrentItem() != null) {
            player.openInventory(new CrystalsGUI(page + 1).getInventory());
        } else if (slot == getInventory().getSize() - 9 && e.getCurrentItem() != null) {
            player.openInventory(new CrystalsGUI(page - 1).getInventory());
        } else {
            if (slots.containsKey(slot)) {
                try {
                    List<Location> recipes = new ArrayList<>(HyperDragons.getInstance().getDragonManager().getDragonStructure().getCrystals());
                    Location toRemove = recipes.get(slots.get(slot));
                    if(e.isRightClick()){
                        HyperDragons.getInstance().getDragonManager().getDragonStructure().getCrystals().remove(toRemove);
                        player.openInventory(new CrystalsGUI(page).getInventory());
                        return;
                    }
                    player.closeInventory();
                    player.teleport(toRemove);
                }catch (Exception ignored){ }
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Dragon Crystals"));
        for (Integer slot : Arrays.asList(45, 46, 47, 48, 50, 51 ,52, 53))
            inventory.setItem(slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().background));
        List<Location> recipes = new ArrayList<>(HyperDragons.getInstance().getDragonManager().getDragonStructure().getCrystals());
        int slot = 0;
        int i = 28 * (this.page - 1);
        if(recipes.size() > 0){
            while (slot < 28) {
                if (recipes.size() > i && i >= 0) {
                    Location recipe = recipes.get(i);
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().crystalsLocation, Arrays.asList(
                            new Placeholder("location", Utils.getFormattedLocation(recipe)),
                            new Placeholder("id", String.valueOf(i))
                    )));
                    slots.put(slot, i);
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (this.page > 1)
            inventory.setItem(getInventory().getSize() - 9, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().previousPage));
        if (recipes.size() > 28 * this.page)
            inventory.setItem(getInventory().getSize() - 1, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().nextPage));
        inventory.setItem(48, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().backMainMenu));
        inventory.setItem(49, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().closeButton));
        return inventory;
    }
}
