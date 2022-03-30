package mc.ultimatecore.dragon.inventories;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.structures.DragonStructure;
import mc.ultimatecore.dragon.utils.InventoryUtils;
import mc.ultimatecore.dragon.utils.Placeholder;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class DragonGUI extends GUI {

    public DragonGUI(){
        super(1);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == 49) {
                player.closeInventory();
            }else if(e.getSlot() == HyperDragons.getInstance().getInventories().crystalsItem.slot){
                player.openInventory(new CrystalsGUI(1).getInventory());
            }else if(e.getSlot() == HyperDragons.getInstance().getInventories().altarItem.slot){
                player.openInventory(new AltarGUI(1).getInventory());
            }else if(e.getSlot() == HyperDragons.getInstance().getInventories().spawnItem.slot){
                Location spawn = HyperDragons.getInstance().getDragonManager().getDragonStructure().getSpawnLocation();
                if(spawn != null) player.teleport(spawn);
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperDragons.getInstance().getInventories().mainMenuSize, StringUtils.color(HyperDragons.getInstance().getInventories().mainMenuTitle));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().background));
        DragonStructure dragonStructure = HyperDragons.getInstance().getDragonManager().getDragonStructure();
        inventory.setItem(HyperDragons.getInstance().getInventories().crystalsItem.slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().crystalsItem, Collections.singletonList(
                new Placeholder("amount", String.valueOf(dragonStructure.getCrystals().size())))));
        inventory.setItem(HyperDragons.getInstance().getInventories().altarItem.slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().altarItem, Collections.singletonList(
                new Placeholder("amount", String.valueOf(dragonStructure.getAltars().size())))));
        inventory.setItem(HyperDragons.getInstance().getInventories().schematicItem.slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().schematicItem, Collections.singletonList(
                new Placeholder("schematic", HyperDragons.getInstance().getConfiguration().schematic))));
        inventory.setItem(HyperDragons.getInstance().getInventories().spawnItem.slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().spawnItem, Collections.singletonList(
                new Placeholder("location", Utils.getFormattedLocation(dragonStructure.getSpawnLocation())))));
        inventory.setItem(49, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().closeButton));
        return inventory;
    }
}
