package mc.ultimatecore.dragon.inventories;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.structures.DragonAltar;
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

import java.util.*;

public class AltarGUI extends GUI{

    private final Map<Integer, Integer> slots = new HashMap<>();

    public AltarGUI(int page) {
        super(page);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == 49) {
            player.closeInventory();
        } else if (slot == 48) {
            player.openInventory(new DragonGUI().getInventory());
        } else if (slot == getInventory().getSize() - 1 && e.getCurrentItem() != null) {
            player.openInventory(new AltarGUI(page + 1).getInventory());
        } else if (slot == getInventory().getSize() - 9 && e.getCurrentItem() != null) {
            player.openInventory(new AltarGUI(page - 1).getInventory());
        } else {
            if (slots.containsKey(slot)) {
                try {
                    List<DragonAltar> recipes = new ArrayList<>(HyperDragons.getInstance().getDragonManager().getDragonStructure().getAltars());
                    DragonAltar toRemove = recipes.get(slots.get(slot));
                    if(e.isRightClick()){
                        HyperDragons.getInstance().getDragonManager().getDragonStructure().getAltars().remove(toRemove);
                        player.openInventory(new AltarGUI(page).getInventory());
                        return;
                    }
                    player.closeInventory();
                    player.teleport(toRemove.getLocation());
                }catch (Exception ignored){ }
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Dragon Altars"));
        for (Integer slot : Arrays.asList(45, 46, 47, 48, 50, 51 ,52, 53))
            inventory.setItem(slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().background));
        List<DragonAltar> recipes = new ArrayList<>(HyperDragons.getInstance().getDragonManager().getDragonStructure().getAltars());
        int slot = 0;
        int i = 28 * (this.page - 1);
        if(recipes.size() > 0){
            while (slot < 28) {
                if (recipes.size() > i && i >= 0) {
                    Location recipe = recipes.get(i).getLocation();
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().altarLocation, Arrays.asList(
                            new Placeholder("location", Utils.getFormattedLocation(recipe)),
                            new Placeholder("id", String.valueOf(i))
                            )));
                    this.slots.put(slot, i);
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (this.page > 1)
            inventory.setItem(getInventory().getSize() - 9, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().previousPage));
        if (recipes.size() > 28 * this.page)
            inventory.setItem(getInventory().getSize() - 1, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().nextPage));
        inventory.setItem(48, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().backMainMenu));
        inventory.setItem(49, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().closeButton));
        return inventory;
    }
}
