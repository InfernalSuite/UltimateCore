package com.infernalsuite.ultimatecore.crafting.listeners;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.playerdata.User;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@AllArgsConstructor
public class CraftingTableListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onOpenEnchantmentTable(PlayerInteractEvent e){
        if(!plugin.getConfiguration().setAsDefaultCraftingTable) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block bl = e.getClickedBlock();
        if(bl == null || !bl.getType().equals(XMaterial.CRAFTING_TABLE.parseMaterial())) return;
        e.setCancelled(true);
        Player p = e.getPlayer();
        User user = User.getUser(p);
        p.openInventory(user.getMainMenu().getInventory());
    }

}
