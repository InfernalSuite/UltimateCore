package mc.ultimatecore.skills.gui;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.utils.InventoryUtils;
import mc.ultimatecore.skills.utils.Placeholder;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class AllArmorsGUI implements GUI {

    private final int page;

    private final HashMap<Integer, UltimateItem> recipeSlots;

    public AllArmorsGUI(int page) {
        this.page = page;
        this.recipeSlots = new HashMap<>();
    }

    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot == 49) {
            player.closeInventory();
        } else if (slot == 53 && e.getCurrentItem() != null) {
            player.openInventory(new AllArmorsGUI(page + 1).getInventory());
        } else if (slot == 53 - 9 && e.getCurrentItem() != null) {
            player.openInventory(new AllArmorsGUI(page - 1).getInventory());
        } else {
            if (recipeSlots.containsKey(slot)) {
                if(e.isRightClick())
                    player.openInventory(new ConfirmGUI(recipeSlots.get(slot)).getInventory());
                else
                    player.openInventory(new ArmorEditGUI(recipeSlots.get(slot)).getInventory());
            }
        }

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Item Editor"));
        for (Integer slot : Arrays.asList(45, 46, 47, 48, 50, 51 ,52, 53))
            inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getBackground()));
        List<UltimateItem> recipes = new ArrayList<>(HyperSkills.getInstance().getUltimateItems().ultimateItems.values());
        int slot = 0;
        int i = 28 * (this.page - 1);
        if(recipes.size() > 0){
            while (slot < 28) {
                if (recipes.size() > i && i >= 0) {
                    UltimateItem recipe = recipes.get(i);
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().allArmorItem, Collections.singletonList(new Placeholder("item_id", recipe.getId())), recipe));
                    this.recipeSlots.put(slot, recipe);
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (this.page > 1)
            inventory.setItem(54 - 9, InventoryUtils.makeItem((HyperSkills.getInstance().getInventories()).getPreviousPage()));
        if (recipes.size() > 28 * this.page)
            inventory.setItem(54 - 1, InventoryUtils.makeItem((HyperSkills.getInstance().getInventories()).getNextPage()));

        inventory.setItem(HyperSkills.getInstance().getInventories().getCloseButton().slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getCloseButton()));
        return inventory;
    }
}
