package com.infernalsuite.ultimatecore.farm.gui;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.farm.HyperRegions;
import com.infernalsuite.ultimatecore.farm.objects.HyperRegion;
import com.infernalsuite.ultimatecore.farm.objects.RegenTime;
import com.infernalsuite.ultimatecore.farm.objects.blocks.ChanceBlock;
import com.infernalsuite.ultimatecore.farm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class SettingsGUI implements GUI {

    private final String key;

    private final HyperRegions plugin = HyperRegions.getInstance();

    @Override
    public @NotNull
    Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, Utils.color("&8Edit Time"));
        for (Integer slot : plugin.getInventories().regionSlots)
            inventory.setItem(slot, Utils.makeItem(plugin.getInventories().regionBackground));
        HyperRegion farmRegion = plugin.getFarmManager().hyperRegions.get(key);
        RegenTime regenTime = farmRegion.getRegenTime();
        inventory.setItem(13, Utils.makeItem(plugin.getInventories().changeTime, Arrays.asList(new Utils.Placeholder("min_time", String.valueOf(regenTime.getMin())), new Utils.Placeholder("max_time", String.valueOf(regenTime.getMin())))));

        inventory.setItem(20, Utils.makeItem(plugin.getInventories().blockBroken, Collections.singletonList(new Utils.Placeholder("block_type", farmRegion.getBlockBroken().getMaterial())), farmRegion.getBlockBroken()));

        inventory.setItem(21, Utils.makeItem(plugin.getInventories().nextPhase));

        inventory.setItem(22, Utils.makeItem(plugin.getInventories().blockWhile, Collections.singletonList(new Utils.Placeholder("block_type", farmRegion.getBlockWhileRegen().getMaterial())), farmRegion.getBlockWhileRegen()));

        inventory.setItem(23, Utils.makeItem(plugin.getInventories().nextPhase));

        Optional<ChanceBlock> regionBlock = farmRegion.getChanceBlocks().getHighestBlock();
        if(regionBlock.isPresent())
            inventory.setItem(24, Utils.makeItem(plugin.getInventories().blocksAfter
                    , Collections.singletonList(new Utils.Placeholder("block_type", regionBlock.get().getMaterial())), regionBlock.get()));
        else
            inventory.setItem(24, Utils.makeItem(plugin.getInventories().blocksAfter
                    , Collections.singletonList(new Utils.Placeholder("block_type", "AIR")), null));

        inventory.setItem(31, Utils.makeItem(plugin.getInventories().isTripleBlock, Collections.singletonList(new Utils.Placeholder("status", String.valueOf(farmRegion.isTripleBlock())))));

        inventory.setItem(49, Utils.makeItem(plugin.getInventories().closeButton));
        inventory.setItem(48, Utils.makeItem(plugin.getInventories().previousPage));
        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (e.getSlot() == getInventory().getSize() - 5) {
                player.closeInventory();
            } else if (e.getSlot() == getInventory().getSize() - 6) {
                player.openInventory(new AllCropsGUI(1).getInventory());
            }
        }
    }
}