package com.infernalsuite.ultimatecore.collections.listeners.addons;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.utils.Utils;
import me.jet315.minions.events.MinerBlockBreakEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class JetMinionsListener implements Listener {
    
    private final HyperCollections plugin;
    
    @EventHandler(priority = EventPriority.LOW)
    public void jetMinionsEvent(MinerBlockBreakEvent e) {
        if (e.getMinion() == null || e.getMinion().getPlayerUUID() == null || e.getBlock() == null)
            return;
        Player player = Bukkit.getPlayer(e.getMinion().getPlayerUUID());
        for (ItemStack item : e.getBlock().getDrops()) {
            if (item == null || item.getType() == Material.AIR) continue;
            String key = Utils.getKey(XMaterial.matchXMaterial(item).toString());
            if (!plugin.getCollections().collectionExist(key)) return;
            plugin.getCollectionsManager().addXP(player, key, item.getAmount());
        }
    }
}
