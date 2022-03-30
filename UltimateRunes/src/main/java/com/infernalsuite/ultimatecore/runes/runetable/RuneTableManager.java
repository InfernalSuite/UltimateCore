package com.infernalsuite.ultimatecore.runes.runetable;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.runes.enums.RuneState;
import com.infernalsuite.ultimatecore.runes.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.runes.utils.Placeholder;
import com.infernalsuite.ultimatecore.runes.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.runes.HyperRunes;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;

@Getter
public class RuneTableManager {

    private final Inventory inventory;

    private final UUID uuid;

    private ItemStack firstItem;

    private ItemStack secondItem;

    private RuneState runeState;

    @Setter
    private boolean fusing;

    @Setter
    private boolean itemToPickup;

    private int taskID;

    public RuneTableManager(Inventory inventory, UUID uuid){
        this.inventory = inventory;
        this.uuid = uuid;
        this.fusing = false;
        this.itemToPickup = false;
    }

    public void checkRuneTable(){
        final RuneTableManager instance = this;
        if(fusing) return;
        Bukkit.getScheduler().runTaskLater(HyperRunes.getInstance(), () -> {
            firstItem = inventory.getItem(19);
            secondItem = inventory.getItem(25);
            inventory.setItem(13, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getRuneButtonItem()));
            if(!itemToPickup)
                inventory.setItem(31, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getRuneInfoItem()));
            if(firstItem != null && secondItem != null){
                runeState = Utils.manageRunes(instance);
                if(runeState == RuneState.NO_ERROR_ITEMS || runeState == RuneState.NO_ERROR_RUNES){
                    paintGlass(PaintSlots.ALL_SLOTS, true);
                    inventory.setItem(13, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getRuneButtonToFuseItem(), Arrays.asList(
                            new Placeholder("success_chance", String.valueOf(Utils.getSuccesChance(secondItem))), new Placeholder("fail_chance", String.valueOf(100 - Utils.getSuccesChance(secondItem))))));
                    inventory.setItem(31, getDoneItem());
                }else{
                    paintGlass(PaintSlots.ALL_SLOTS, false);
                    inventory.setItem(31, InventoryUtils.makeItem(HyperRunes.getInstance().getInventories().getRuneErrorItem(), runeState));
                }
            }else{
                runeState = null;
                paintGlass(PaintSlots.ALL_SLOTS, false);
                if(firstItem != null)
                    paintGlass(PaintSlots.ABOVE_LEFT, true);
                if(secondItem != null)
                    paintGlass(PaintSlots.ABOVE_RIGHT, true);
            }
        }, 0L);
    }

    private void paintGlass(PaintSlots paintSlots, boolean colored) {
        ItemStack glass = colored ? XMaterial.PURPLE_STAINED_GLASS_PANE.parseItem() : XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
        for (Integer slot : paintSlots.getSlots())
            inventory.setItem(slot, glass);
    }

    public void fuseItems(){
        this.fusing = true;
        inventory.setItem(31, XMaterial.AIR.parseItem());
        this.taskID = new BukkitRunnable() {
            int index = 0;
            int rounds = 0;
            XMaterial glass = XMaterial.PINK_STAINED_GLASS_PANE;
            public void run() {
                if(rounds < 5) {
                    inventory.setItem(PaintSlots.EFFECT_ONE.getSlots()[index], glass.parseItem());
                    inventory.setItem(PaintSlots.EFFECT_TWO.getSlots()[index], glass.parseItem());
                    index++;
                    if(index == 8){
                        index = 0;
                        rounds++;
                        glass = glass == XMaterial.PINK_STAINED_GLASS_PANE ? XMaterial.PURPLE_STAINED_GLASS_PANE : XMaterial.PINK_STAINED_GLASS_PANE;
                    }
                }else {
                    getItem();
                    checkRuneTable();
                }
            }
        }.runTaskTimer(HyperRunes.getInstance() , 0L, 2L).getTaskId();
    }

    public void getItem(){
        Bukkit.getScheduler().cancelTask(taskID);
        if(!fusing) return;
        ItemStack itemStack = getDoneItem();
        inventory.setItem(19, XMaterial.AIR.parseItem());
        inventory.setItem(25, XMaterial.AIR.parseItem());
        fusing = false;
        new BukkitRunnable() {
            public void run() {
                itemToPickup = true;
                inventory.setItem(31, itemStack);
            }
        }.runTaskLater(HyperRunes.getInstance(), 0);

    }

    private ItemStack getDoneItem(){
        return runeState == RuneState.NO_ERROR_ITEMS ? Utils.getRunedItem(firstItem, secondItem) : Utils.getNewRune(firstItem);
    }

}
