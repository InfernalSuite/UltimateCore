package com.infernalsuite.ultimatecore.crafting.listeners;

import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.playerdata.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class PlayerJoinLeaveListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onLeave(PlayerKickEvent event) {
        try {
            final Player player = event.getPlayer();
            User user = User.getUser(player);
            if(!player.getOpenInventory().getTopInventory().equals(user.getMainMenu().getInventory())) return;
            Inventory inventory = user.getMainMenu().getInventory();
            plugin.getInventories().craftingSlots.forEach(slot ->{
                if(inventory.getItem(slot) != null) player.getInventory().addItem(inventory.getItem(slot));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
