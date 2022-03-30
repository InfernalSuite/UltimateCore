package com.infernalsuite.ultimatecore.souls.gui;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.souls.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.souls.utils.Placeholder;
import com.infernalsuite.ultimatecore.souls.utils.StringUtils;
import com.infernalsuite.ultimatecore.souls.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

@AllArgsConstructor
public class TiaGUI implements SimpleGUI {
    
    private final HyperSouls plugin;
    
    private final UUID uuid;
    
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == plugin.getInventories().tiaClose.slot)
            player.closeInventory();
        else if (e.getSlot() != plugin.getInventories().tiaClaim.slot)
            return;
        plugin.getTiaSystem().claimRewards(player);
        player.closeInventory();
        Utils.playSound(player, plugin.getConfiguration().tiaClaimSound);
    }
    
    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, plugin.getInventories().tiaSize, StringUtils.color(plugin.getInventories().tiaTitle));
        for (Integer slot : plugin.getInventories().tiaSlots)
            inventory.setItem(slot, InventoryUtils.makeItem(plugin.getInventories().tiaDecoration));
        int toExchange = plugin.getTiaSystem().getToExchange(uuid);
        int toClaim = plugin.getTiaSystem().getRequired();
        String placeholder = toExchange >= toClaim ? plugin.getMessages().getMessage("claimPlaceholder") : plugin.getMessages().getMessage("noSoulsPlaceholder");
        inventory.setItem(plugin.getInventories().tiaClaim.slot, InventoryUtils.makeItem(plugin.getInventories().tiaClaim,
                Arrays.asList(new Placeholder("to_exchange", toExchange + ""), new Placeholder("to_claim", toClaim + ""), new Placeholder("exchange_placeholder", placeholder)
                )));
        inventory.setItem(plugin.getInventories().tiaClose.slot, InventoryUtils.makeItem(plugin.getInventories().tiaClose));
        return inventory;
    }
}
