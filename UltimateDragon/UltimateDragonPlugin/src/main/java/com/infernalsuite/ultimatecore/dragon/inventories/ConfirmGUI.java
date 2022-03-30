package com.infernalsuite.ultimatecore.dragon.inventories;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IGuardian;
import com.infernalsuite.ultimatecore.dragon.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


public class ConfirmGUI extends GUI {

    private final IGuardian ultimateItem;

    public ConfirmGUI(IGuardian ultimateItem) {
        super(1);
        this.ultimateItem = ultimateItem;
    }

    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == 11) {
            HyperDragons.getInstance().getDragonGuardians().remove(ultimateItem.getID());
            player.openInventory(new AllGuardiansGUI(1).getInventory());
        } else if (slot == 15) {
            player.openInventory(new AllGuardiansGUI(1).getInventory());
        }

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, StringUtils.color("&8Confirm"));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().background));
        inventory.setItem(11, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().confirm));
        inventory.setItem(15, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().cancel));
        return inventory;
    }
}
