package mc.ultimatecore.reforge.managers;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.enums.PaintSlots;
import mc.ultimatecore.reforge.enums.ReforgeState;
import mc.ultimatecore.reforge.utils.InventoryUtils;
import mc.ultimatecore.reforge.utils.Placeholder;
import mc.ultimatecore.reforge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.UUID;

@Getter
public class GUIManager {

    private final Inventory inventory;

    private final UUID uuid;

    private ItemStack firstItem;

    private ReforgeState reforgeState;

    @Setter
    private boolean fusing;

    private final HyperReforge plugin;

    public GUIManager(Inventory inventory, UUID uuid){
        this.inventory = inventory;
        this.uuid = uuid;
        this.fusing = false;
        this.plugin = HyperReforge.getInstance();
    }

    public void updateReforge(){
        final GUIManager instance = this;
        if(fusing) return;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            firstItem = inventory.getItem(13);
            inventory.setItem(22, InventoryUtils.makeItem(plugin.getInventories().getEmptyAnvil()));
            if(firstItem != null){
                reforgeState = Utils.manageReforge(instance);
                if(reforgeState != ReforgeState.INCOMPATIBLE_ITEMS){
                    paintGlass(true);
                    inventory.setItem(22, InventoryUtils.makeItem(plugin.getInventories().getToReforgeAnvil(), Collections.singletonList(new Placeholder("cost", String.valueOf(getCost())))));
                }else{
                    paintGlass(false);
                    inventory.setItem(22, InventoryUtils.makeItem(plugin.getInventories().getErrorItem(), reforgeState));
                }
            }else{
                reforgeState = null;
                paintGlass(false);
            }
        }, 0L);
    }


    private void paintGlass(boolean enabled) {
        ItemStack itemStack = InventoryUtils.makeDecorationItem(enabled ? plugin.getInventories().getSuccessItem() : plugin.getInventories().getEmptyItem());
        for (Integer slot : PaintSlots.ALL_SLOTS.getSlots()) inventory.setItem(slot, itemStack);
    }

    public void fuseItems(Player player){
        this.fusing = true;
        getItem(player);
        updateReforge();
    }

    public void getItem(Player player){
        if(!fusing) return;
        ItemStack itemStack = getDoneItem();
        new BukkitRunnable() {
            public void run() {
                Utils.playSound(player, plugin.getConfiguration().anvilFuseSound);
                inventory.setItem(13, itemStack);
                fusing = false;
            }
        }.runTaskLater(plugin, 1);
    }

    public double getCost(){
        return Utils.getCost(this);
    }

    private ItemStack getDoneItem(){
        return Utils.getReforgedItem(this);
    }

}
