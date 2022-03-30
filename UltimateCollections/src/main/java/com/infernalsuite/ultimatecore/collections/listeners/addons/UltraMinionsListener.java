package com.infernalsuite.ultimatecore.collections.listeners.addons;

import com.cryptomorin.xseries.XMaterial;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionCollectEvent;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class UltraMinionsListener implements Listener {
    
    private final HyperCollections plugin;
    
    @EventHandler(priority = EventPriority.LOW)
    public void ultraMinionsEvent(MinionCollectEvent e) {
        if (e.getItems().isEmpty() || e.getPlayer() == null) return;
        for (ItemStack item : e.getItems().keySet()) {
            if (item == null || item.getType() == Material.AIR) continue;
            Player player = e.getPlayer();
            String key = Utils.getKey(XMaterial.matchXMaterial(item).toString());
            if (!plugin.getCollections().collectionExist(key)) return;
            plugin.getCollectionsManager().addXP(player, key, item.getAmount());
        }
    }
}
