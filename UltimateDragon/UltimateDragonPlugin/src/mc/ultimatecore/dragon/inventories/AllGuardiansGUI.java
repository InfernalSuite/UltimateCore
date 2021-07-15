package mc.ultimatecore.dragon.inventories;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.implementations.IGuardian;
import mc.ultimatecore.dragon.utils.InventoryUtils;
import mc.ultimatecore.dragon.utils.Placeholder;
import mc.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class AllGuardiansGUI extends GUI {

    private final Map<Integer, IGuardian> recipeSlots;

    public AllGuardiansGUI(int page) {
        super(page);
        this.recipeSlots = new HashMap<>();
    }

    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == 49) {
            player.closeInventory();
        } else if (slot == 53 && e.getCurrentItem() != null) {
            player.openInventory(new AllGuardiansGUI(page + 1).getInventory());
        } else if (slot == 53 - 9 && e.getCurrentItem() != null) {
            player.openInventory(new AllGuardiansGUI(page - 1).getInventory());
        } else {
            if (recipeSlots.containsKey(slot)) {
                if(e.isRightClick())
                    player.openInventory(new ConfirmGUI(recipeSlots.get(slot)).getInventory());
                else
                    player.openInventory(new GuardianGUI(recipeSlots.get(slot)).getInventory());
            }
        }

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Item Editor"));
        for (Integer slot : Arrays.asList(45, 46, 47, 48, 50, 51 ,52, 53))
            inventory.setItem(slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().background));
        List<IGuardian> recipes = new ArrayList<>(HyperDragons.getInstance().getDragonGuardians().getGuardianMap().values());
        int slot = 0;
        int i = 28 * (this.page - 1);
        if(recipes.size() > 0){
            while (slot < 28) {
                if (recipes.size() > i && i >= 0) {
                    IGuardian recipe = recipes.get(i);
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().guardian, Collections.singletonList(new Placeholder("id", recipe.getID()))));
                    this.recipeSlots.put(slot, recipe);
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (this.page > 1)
            inventory.setItem(45, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().previousPage));
        if (recipes.size() > 28 * this.page)
            inventory.setItem(53, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().nextPage));

        inventory.setItem(49, InventoryUtils.makeItem(HyperDragons.getInstance().getInventories().closeButton));
        return inventory;
    }
}
