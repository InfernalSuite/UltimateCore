package mc.ultimatecore.alchemy.managers;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.api.events.HyperBrewEvent;
import mc.ultimatecore.alchemy.enums.BrewingSlots;
import mc.ultimatecore.alchemy.objects.AlchemyRecipe;
import mc.ultimatecore.alchemy.utils.InventoryUtils;
import mc.ultimatecore.alchemy.utils.Placeholder;
import mc.ultimatecore.alchemy.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
public class AlchemyGUIManager {
    @Getter
    private final Inventory inventory;

    private final Location location;

    @Getter @Setter
    private boolean brewing;

    private int taskID;

    private int timeID;

    private double time;

    private UUID uuid;

    public void init(Player player){
        final AlchemyGUIManager instance = this;
        Bukkit.getScheduler().runTaskLater(HyperAlchemy.getInstance(), () -> {
            updateItems();
            AlchemyRecipe alchemyRecipe = Utils.getAlchemyRecipe(instance.getInventory(), player);
            if(alchemyRecipe == null){
                if(brewing)
                    cancelEffect();
                setDecorationItems(InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().normalItem));
            }else{
                if(brewing) return;
                brewing = true;
                startCountdown(alchemyRecipe.getTime());
                makeEffect();
            }
        }, 0L);
        if(player != null)
            uuid = player.getUniqueId();
    }

    private void cancelEffect(){
        brewing = false;
        Bukkit.getScheduler().cancelTask(taskID);
        Bukkit.getScheduler().cancelTask(timeID);
        setDecorationItems(InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().normalItem));
    }

    public void startCountdown(int count){
        this.time = count;
        this.timeID = new BukkitRunnable() {
            public void run() {
                if(time > 0) {
                    time-=0.1;
                    for(Integer slot : BrewingSlots.EFFECT_SLOTS.getSlots())
                        inventory.setItem(slot, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().brewingItem, Collections.singletonList(new Placeholder("brewing_time", String.valueOf(Utils.round(time)))), inventory.getItem(slot).getType()));
                }
            }
        }.runTaskTimer(HyperAlchemy.getInstance() , 0L, 2L).getTaskId();
    }

    private void makeEffect(){
        this.brewing = true;
        setDecorationItems(XMaterial.YELLOW_STAINED_GLASS_PANE.parseItem());
        this.taskID = new BukkitRunnable() {
            int index = 0;
            XMaterial glass = XMaterial.ORANGE_STAINED_GLASS_PANE;
            public void run() {
                if(time > 0) {
                    inventory.setItem(BrewingSlots.EFFECT_ONE.getSlots().get(index), InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().brewingItem, Collections.singletonList(new Placeholder("brewing_time", String.valueOf(Utils.round(time)))), glass));
                    inventory.setItem(BrewingSlots.EFFECT_TWO.getSlots().get(index), InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().brewingItem, Collections.singletonList(new Placeholder("brewing_time", String.valueOf(Utils.round(time)))), glass));
                    index++;
                    if(index == 5){
                        index = 0;
                        glass = glass == XMaterial.ORANGE_STAINED_GLASS_PANE ? XMaterial.YELLOW_STAINED_GLASS_PANE : XMaterial.ORANGE_STAINED_GLASS_PANE;
                    }
                }else {
                    cancelEffect();
                    setNewItems();
                }
            }
        }.runTaskTimer(HyperAlchemy.getInstance() , 0L, 4L).getTaskId();
    }

    private void updateItems(){
        BrewingStand brewingStand = getBrewingStand();
        if(brewingStand == null) return;
        HashMap<Integer, Integer> relationalSlots = HyperAlchemy.getInstance().getInventories().relationSlots;
        for(Integer slot : relationalSlots.keySet()){
            ItemStack itemStack = inventory.getItem(relationalSlots.get(slot));
            brewingStand.getInventory().setItem(slot, itemStack == null ? XMaterial.AIR.parseItem() : itemStack);
        }
    }

    private void setDecorationItems(ItemStack item){
        for(Integer slot : BrewingSlots.EFFECT_SLOTS.getSlots())
            inventory.setItem(slot, item);
    }

    public BrewingStand getBrewingStand(){
        if(location == null) return null;
        Block bl = location.getBlock();
        if(!bl.getType().equals(Material.BREWING_STAND)) return null;
        return (BrewingStand) bl.getState();
    }

    public void setNewItems(){
        AlchemyRecipe alchemyRecipe = Utils.getAlchemyRecipe(getInventory());
        if(alchemyRecipe == null) return;
        ItemStack itemStack = inventory.getItem(13);
        ItemStack alchemyInput = alchemyRecipe.getInputItem();
        //ALCHEMY ITEMS
        for(Integer slot : BrewingSlots.BOTTLE_SLOTS.getSlots()){
            ItemStack inputItem = inventory.getItem(slot);
            if(inputItem != null && inputItem.getType() != Material.AIR)
                if(inputItem.isSimilar(alchemyInput))
                    inventory.setItem(slot, alchemyRecipe.getOutputItem());
        }
        //FUEL ITEM
        ItemStack fuelItem = alchemyRecipe.getFuelItem();
        if(itemStack != null && itemStack.getType() != Material.AIR){
            if(itemStack.getAmount() - fuelItem.getAmount() <= 1){
                inventory.setItem(13, null);
            }else{
                itemStack.setAmount(itemStack.getAmount() - fuelItem.getAmount());
                inventory.setItem(13, itemStack);
            }
        }
        if(uuid != null && Bukkit.getPlayer(uuid) != null)
            Bukkit.getServer().getPluginManager().callEvent(new HyperBrewEvent(Bukkit.getPlayer(uuid), alchemyRecipe));
        updateItems();
    }


}
