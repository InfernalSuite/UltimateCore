package com.infernalsuite.ultimatecore.enchantment.listener;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.playerdata.User;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchantingTableListener implements Listener {
    @EventHandler
    public void onOpenEnchantmentTable(PlayerInteractEvent e){
        if(!EnchantmentsPlugin.getInstance().getConfiguration().setAsDefaultEnchantingTable) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block bl = e.getClickedBlock();
        if(bl == null || !bl.getType().equals(XMaterial.ENCHANTING_TABLE.parseMaterial())) return;
        String world = e.getPlayer().getWorld().getName();
        if(EnchantmentsPlugin.getInstance().getConfiguration().disabledWorlds.contains(world)) return;
        e.setCancelled(true);
        Player player = e.getPlayer();
        User user = User.getUser(player);
        player.openInventory(user.getMainMenu(Utils.getBookShelfPower(bl), true, null).getInventory());
    }

}
