package com.infernalsuite.ultimatecore.runes.listeners.runes;

import com.infernalsuite.ultimatecore.runes.managers.User;
import com.infernalsuite.ultimatecore.runes.runetable.RuneTableStructure;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RuneTableListener implements Listener {

    @EventHandler
    public void onPlaceRuneTable(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!player.isOp() || !player.hasPermission("hyperrunes.runetable.place")) return;
        ItemStack item = e.getItem();
        if(item == null || item.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(item);
        if(!nbtItem.hasKey("runeStructure")) return;
        if(e.getClickedBlock() == null) return;
        RuneTableStructure.place(e.getClickedBlock().getLocation().clone());
    }

    @EventHandler
    public void onRuneTableClick(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        User user = User.getUser(player);
        if(e.getRightClicked() == null) return;
        String name = e.getRightClicked().getName();
        if(name == null) return;
        if(!name.contains("RuneTable")) return;
        e.setCancelled(true);
        if(player.hasPermission("hyperrunes.runetable.open"))
            player.openInventory(user.getRuneTable().getInventory());
    }

    @EventHandler
    public void onRuneRemove(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof ArmorStand)) return;
        Player player = e.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if(itemStack == null || itemStack.getType() != Material.ARROW) return;
        NBTItem nbtItem = new NBTItem(itemStack);
        if(!nbtItem.hasKey("runesRemove")) return;
        e.getRightClicked().getNearbyEntities(3, 3 ,3).stream()
                .filter(entity -> entity instanceof ArmorStand)
                .filter(entity -> entity.getName() != null && entity.getName().equals("RuneTable"))
                .forEach(Entity::remove);
        if(e.getRightClicked() != null)
            e.getRightClicked().remove();
    }
}
