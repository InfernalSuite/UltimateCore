package mc.ultimatecore.crafting.gui;

import lombok.Getter;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.api.events.HyperCraftEvent;
import mc.ultimatecore.crafting.managers.CraftingGUIManager;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.objects.AutoReturn;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Optional;

public class CraftingGUI extends GUI implements Listener {

    @Getter
    private final CraftingGUIManager guiManager;

    private final HyperCrafting plugin;

    public CraftingGUI(Player player, HyperCrafting plugin) {
        super(plugin.getInventories().mainMenuSize, plugin.getInventories().mainMenuTitle, plugin.getInventories().decorationSlots);
        this.plugin = plugin;
        InventoryView view = plugin.getNms().getInventoryView(player, "Special Table", player.getWorld(), player.getLocation(), new FixedMetadataValue(plugin, "UUID"));
        guiManager = new CraftingGUIManager(getInventory(), view);
        guiManager.init(player);
        plugin.registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        setItem(plugin.getInventories().closeButton.slot, InventoryUtils.makeItem(plugin.getInventories().closeButton));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Inventory inventory = e.getInventory();
        if (!inventory.equals(getInventory())) return;
        Player player = (Player) e.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Integer slot : plugin.getInventories().craftingSlots){
                ItemStack itemStack = inventory.getItem(slot);
                if(itemStack != null && !itemStack.getType().equals(Material.AIR)){
                    player.getInventory().addItem(itemStack);
                    getInventory().setItem(slot, null);
                }
            }
            guiManager.init(player);
        }, 2L);


    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        Player player = (Player) e.getWhoClicked();
        guiManager.init(player);
    }

    @EventHandler
    public void onInventoryDrag(InventoryOpenEvent e) {
        Inventory inventory = e.getInventory();
        if (!inventory.equals(getInventory())) return;
        Player player = (Player) e.getPlayer();
        if (guiManager.getInventoryView() == null)
            guiManager.setInventoryView(plugin.getNms().getInventoryView(player, "Special Table", player.getWorld(), player.getLocation(), new FixedMetadataValue(plugin, "UUID")));
        guiManager.init(player);
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
        if(e.getInventory().equals(getInventory()) && !e.getClickedInventory().equals(getInventory())){
            guiManager.init(player);
            return;
        }
        if(slot == plugin.getInventories().closeButton.slot) {
            e.setCancelled(true);
            player.closeInventory();
            return;
        }
        ItemStack current = e.getCurrentItem();
        if (!plugin.getInventories().craftingSlots.contains(slot) && slot != 23 || Utils.matchType(current, plugin.getInventories().recipeNotFound.material) ||
                Utils.matchType(current, plugin.getInventories().recipeNoPermission.material)) {
            e.setCancelled(true);
            if (guiManager.getRecipeSlots().containsKey(slot) && plugin.getConfiguration().showAvailableRecipes
                    && player.hasPermission("hypercrafting.autorecipes") && e.getClick().isLeftClick()) {
                ItemStack cursor = e.getCursor();
                Optional<CraftingRecipe> craftingRecipe = plugin.getCraftingRecipes().getRecipeByName(guiManager.getRecipeSlots().get(slot));
                if (craftingRecipe.isPresent()){
                    ItemStack clicked = craftingRecipe.get().getResult().clone();
                    if (cursor != null && cursor.getType() != Material.AIR && !cursor.isSimilar(clicked) || e.isShiftClick() || e.isRightClick())
                        return;
                    AutoReturn autoReturn = guiManager.makeAutoCraft(player, craftingRecipe.get());
                    if (autoReturn == AutoReturn.SUCCESS) {
                        if (cursor == null || cursor.getType() == Material.AIR) {
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.setItemOnCursor(clicked), 1);
                        } else {
                            ItemStack finalItem = clicked.clone();
                            finalItem.setAmount(cursor.getAmount() + finalItem.getAmount());
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.setItemOnCursor(finalItem), 1);
                        }
                    }else if (autoReturn == AutoReturn.NO_ITEMS) {
                        player.sendMessage(Utils.color(plugin.getMessages().getMessage("noItems")));
                    }
                }
            }
            return;
        }
        ItemStack itemStack = guiManager.getDisplayItem();
        ItemStack cursor = e.getCursor();
        if(slot == 23){
            e.setCancelled(true);
            if(!checkItems(itemStack, cursor)) return;
            if(!Utils.itemIsNull(itemStack) && itemStack.getType() == Material.BARRIER) return;
            if(e.isShiftClick()){
                if(!reachStackSize(cursor, itemStack)){
                    Bukkit.getServer().getPluginManager().callEvent(new HyperCraftEvent(player, e.getCurrentItem()));
                    ItemStack newItem = itemStack.clone();
                    int maxQuantity = guiManager.getMinItemQuantity(newItem);
                    for(int i = 0; i<maxQuantity; i++)
                        Bukkit.getScheduler().runTaskLater(plugin, () -> guiManager.removeItem(player, itemStack, true), 1L);
                    if(maxQuantity > newItem.getMaxStackSize()){
                        for(int i = 0; i<maxQuantity; i++)
                            player.getInventory().addItem(newItem);
                    }else{
                        newItem.setAmount(newItem.getAmount() * maxQuantity);
                        player.getInventory().addItem(newItem);
                    }
                }
            }else{
                Bukkit.getScheduler().runTaskLater(plugin, () -> guiManager.removeItem(player, itemStack, false), 1L);
            }
        }
        guiManager.init(player);
    }

    private boolean checkItems(ItemStack itemStack, ItemStack cursor){
        return Utils.itemIsNull(cursor) || !Utils.itemIsNull(cursor) && cursor.isSimilar(itemStack);
    }

    private boolean reachStackSize(ItemStack cursor, ItemStack secondItem){
        if(cursor == null || cursor.getType().equals(Material.AIR))
            return false;
        return secondItem != null && cursor.getAmount() + secondItem.getAmount() >= cursor.getMaxStackSize();
    }
}
