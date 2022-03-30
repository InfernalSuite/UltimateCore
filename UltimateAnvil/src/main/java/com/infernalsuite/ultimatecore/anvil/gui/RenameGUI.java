package com.infernalsuite.ultimatecore.anvil.gui;

import com.infernalsuite.ultimatecore.anvil.HyperAnvil;
import com.infernalsuite.ultimatecore.anvil.managers.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class RenameGUI extends GUI implements Listener {

    private final User user;

    public RenameGUI(User user) {
        super(HyperAnvil.getInstance().getInventories().getRenameMenuTitle());
        this.user = user;
        HyperAnvil.getInstance().registerListeners(this);
    }

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        Player player = (Player) e.getPlayer();

        player.openInventory(this.user.getAnvilGUI().getInventory());
        //if(e.getIte)
    }


}