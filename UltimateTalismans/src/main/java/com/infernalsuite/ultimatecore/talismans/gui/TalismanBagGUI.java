package com.infernalsuite.ultimatecore.talismans.gui;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.utils.StringUtils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import com.infernalsuite.ultimatecore.talismans.objects.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TalismanBagGUI extends GUI implements Listener {

    private final UUID uuid;

    public TalismanBagGUI(UUID uuid, HyperTalismans plugin) {
        super(HyperTalismans.getInstance().getInventories().bagSize, StringUtils.color(plugin.getInventories().bagTitle), plugin);
        this.uuid = uuid;
        plugin.registerListeners(this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if(e.getClickedInventory().equals(getInventory())) {
            if (slot == plugin.getInventories().closeButton.slot) {
                e.setCancelled(true);
                player.closeInventory();
            } else if (plugin.getInventories().decorationSlots.contains(slot)) {
                e.setCancelled(true);
            }else{
                ItemStack itemStack = e.getCursor();
                if(itemStack == null || itemStack.getType() == Material.AIR) return;
                NBTItem nbtItem = new NBTItem(itemStack);
                if(!nbtItem.hasKey("uc_talisman")){
                    player.sendMessage(StringUtils.color(plugin.getMessages().getMessage("noTalisman")));
                    e.setCancelled(true);
                }
            }
        }else {
            ItemStack itemStack = e.getCurrentItem();
            if (itemStack == null || itemStack.getType() == Material.AIR) return;
            NBTItem nbtItem = new NBTItem(itemStack);
            if (!nbtItem.hasKey("uc_talisman")) {
                player.sendMessage(StringUtils.color(plugin.getMessages().getMessage("noTalisman")));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(!e.getInventory().equals(getInventory())) return;
        List<String> talismans = new ArrayList<>();
        for (ItemStack itemStack : getInventory().getContents()) {
            if(itemStack == null || itemStack.getType().equals(Material.AIR)) continue;
            NBTItem nbtItem = new NBTItem(itemStack);
            if(!nbtItem.hasKey("uc_talisman")) continue;
            String name = nbtItem.getString("uc_talisman_name");
            talismans.add(name);
        }
        Player player = (Player) e.getPlayer();
        plugin.getUserManager().getBagTalismans(player.getUniqueId()).setTalismans(talismans);
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if(!e.getInventory().equals(getInventory())) return;
        getInventory().clear();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::addItems);
    }

    @Override
    public void addItems() {
        super.addItems();
        int i = 0;
        List<String> talismans = this.plugin.getUserManager().getBagTalismans(uuid).getTalismans();
        for(String name : talismans){
            Talisman talisman = plugin.getTalismans().getTalisman(name);
            if(talisman == null) continue;
            setItem(i, talisman.getItem());
            i++;
        }
    }


}