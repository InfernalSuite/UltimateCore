package com.infernalsuite.ultimatecore.anvil.managers;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
import com.infernalsuite.ultimatecore.anvil.HyperAnvil;
import com.infernalsuite.ultimatecore.anvil.Item;
import com.infernalsuite.ultimatecore.anvil.enums.AnvilState;
import com.infernalsuite.ultimatecore.anvil.enums.PaintSlots;
import com.infernalsuite.ultimatecore.anvil.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.anvil.utils.Placeholder;
import com.infernalsuite.ultimatecore.anvil.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.UUID;

@Getter
public class AnvilGUIManager {

    private final Inventory inventory;

    private final UUID uuid;

    private ItemStack firstItem;

    private ItemStack secondItem;

    private AnvilState anvilState;

    @Setter
    private boolean fusing;

    @Setter
    private boolean itemToPickup;

    public AnvilGUIManager(Inventory inventory, UUID uuid){
        this.inventory = inventory;
        this.uuid = uuid;
        this.fusing = false;
        this.itemToPickup = false;
    }

    public void updateAnvil(){
        final AnvilGUIManager instance = this;
        if(fusing) return;
        Bukkit.getScheduler().runTaskLater(HyperAnvil.getInstance(), () -> {
            firstItem = inventory.getItem(29);
            secondItem = inventory.getItem(33);
            inventory.setItem(22, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getCombineItemsNormal()));
            if(!itemToPickup)
                inventory.setItem(13, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getInfoItem()));
            if(firstItem != null && secondItem != null){
                anvilState = Utils.manageAnvil(instance);
                if(anvilState == AnvilState.NO_ERROR_ITEMS || anvilState == AnvilState.NO_ERROR_BOOK || anvilState == AnvilState.NO_ERROR_TAG){
                    paintAllSlots(true);
                    inventory.setItem(22, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getCombineItemsToFuse(), Collections.singletonList(new Placeholder("cost_amount", String.valueOf(Utils.getCost(this))))));
                    inventory.setItem(13, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getItemPreview(), getDoneItem()));
                }else{
                    paintAllSlots(false);
                    inventory.setItem(13, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getErrorItem(), anvilState));
                }
            }else{
                anvilState = null;
                paintAllSlots(false);
                if(firstItem != null)
                    paintGlass(PaintSlots.ABOVE_LEFT, true, true);
                if(secondItem != null)
                    paintGlass(PaintSlots.ABOVE_RIGHT, false, true);
            }
        }, 0L);
    }

    private void paintGlass(PaintSlots paintSlots, boolean toUpgrade, boolean enabled) {
        Item item = toUpgrade ? enabled ? HyperAnvil.getInstance().getInventories().getItemToUpgradeSuccess() : HyperAnvil.getInstance().getInventories().getItemToUpgradeError()
                : enabled ? HyperAnvil.getInstance().getInventories().getItemToSacrificeSuccess() : HyperAnvil.getInstance().getInventories().getItemToSacrificeError();
        ItemStack itemStack = InventoryUtils.makeItem(item);
        for (Integer slot : paintSlots.getSlots())
            inventory.setItem(slot, itemStack);
    }

    private void paintGlass(boolean enabled) {
        Item item = enabled ? HyperAnvil.getInstance().getInventories().getItemToUpgradeSuccess() : HyperAnvil.getInstance().getInventories().getItemToUpgradeError();
        ItemStack itemStack = InventoryUtils.makeDecorationItem(item);
        for (Integer slot : PaintSlots.BELOW.getSlots()) inventory.setItem(slot, itemStack);
    }

    private void paintAllSlots(boolean colored) {
        paintGlass(PaintSlots.ABOVE_LEFT, true, colored);
        paintGlass(PaintSlots.ABOVE_RIGHT, false, colored);
        paintGlass(colored);
    }

    public void fuseItems(){
        this.fusing = true;
        inventory.setItem(31, XMaterial.AIR.parseItem());
        getItem();
        updateAnvil();
    }

    public void getItem(){
        if(!fusing) return;
        ItemStack itemStack = getDoneItem();
        inventory.setItem(29, XMaterial.AIR.parseItem());
        inventory.setItem(33, XMaterial.AIR.parseItem());
        fusing = false;
        new BukkitRunnable() {
            public void run() {
                itemToPickup = true;
                inventory.setItem(13, itemStack);
            }
        }.runTaskLater(HyperAnvil.getInstance(), 0);
    }

    public boolean hasLevelCost(Player player){
        return player.getLevel() >= Utils.getCost(this);
    }

    private ItemStack getDoneItem(){
        return Utils.getFusedItem(this);
    }

}
