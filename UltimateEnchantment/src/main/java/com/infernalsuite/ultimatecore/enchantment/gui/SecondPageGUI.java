package com.infernalsuite.ultimatecore.enchantment.gui;

import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.enchantments.HyperEnchant;
import com.infernalsuite.ultimatecore.enchantment.managers.GUIManager;
import com.infernalsuite.ultimatecore.enchantment.object.EnchantObject;
import com.infernalsuite.ultimatecore.enchantment.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.enchantment.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SecondPageGUI extends GUI implements Listener {

    private final EnchantmentsPlugin plugin;

    private ItemStack itemStack;

    private final UUID uuid;

    private final GUIManager guiManager;

    private Map<Integer, Integer> slots;

    private final EnchantObject enchantObject;

    public SecondPageGUI(GUIManager guiManager, UUID uuid, ItemStack itemStack, int enchant) {
        super(EnchantmentsPlugin.getInstance().getInventories().mainMenuSize, EnchantmentsPlugin.getInstance().getInventories().mainMenuTitle, true);
        this.plugin = EnchantmentsPlugin.getInstance();
        this.itemStack = itemStack;
        this.uuid = uuid;
        this.guiManager = guiManager;
        this.slots = new HashMap<>();
        this.enchantObject = guiManager.getEnchantsMap().get(enchant);
        this.plugin.registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if(itemStack != null){
            Map<Integer, List<Integer>> levelSlots = plugin.getInventories().levelSlots;
            Map<Integer, Integer> toClickSlots = new HashMap<>();
            HyperEnchant hyperEnchant = enchantObject.getHyperEnchant();
            List<Integer> slots = hyperEnchant.getMaxLevel() > levelSlots.size() ? levelSlots.get(levelSlots.size()) : levelSlots.get(hyperEnchant.getMaxLevel());
            for(int i = 1; i<=hyperEnchant.getMaxLevel(); i++){
                try {
                    int slot = slots.get(i - 1);
                    if (hyperEnchant.isUseMoney()) {
                        setItem(slot,
                                InventoryUtils.makeItemHidden(plugin.getInventories().moneyEnchantBook,
                                        Utils.getMoneyPlaceholders(uuid, hyperEnchant, i, itemStack), hyperEnchant, itemStack));
                    }
                    else {
                        setItem(slot,
                                InventoryUtils.makeItemHidden(plugin.getInventories().levelEnchantBook,
                                        Utils.getEnchantPlaceholders(uuid, hyperEnchant, i, itemStack), hyperEnchant, itemStack));
                    }
                    toClickSlots.put(slot, i);
                }catch (IndexOutOfBoundsException e){
                    Bukkit.getLogger().warning("[UltimateEnchants] Enchantment "+hyperEnchant.getEnchantmentName()+" bypass 'levelSlots' option in inventories.yml, add more values to show all levels.");
                }
            }
            this.slots = toClickSlots;
        }
        setItem(plugin.getInventories().previousPage.slot, InventoryUtils.makeItem(plugin.getInventories().previousPage));
        setItem(plugin.getInventories().closeButton.slot, InventoryUtils.makeItem(plugin.getInventories().closeButton));
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getInventory().equals(getInventory())){
            Bukkit.getScheduler().runTaskLater(EnchantmentsPlugin.getInstance(), () -> {
                if(itemStack != null)
                    e.getPlayer().getInventory().addItem(itemStack);
            }, 3L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getWhoClicked();
        if(e.getClickedInventory().equals(getInventory())){
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                int slot = e.getSlot();
                if(slot == 49) {
                    player.closeInventory();
                }else if(e.getSlot() == plugin.getInventories().previousPage.slot){
                    ItemStack newItem = itemStack.clone();
                    itemStack = null;
                    player.openInventory(new EnchantingGUI(guiManager.getBokShelfPower(), newItem).getInventory());
                }else {
                    if(e.getSlot() == EnchantmentsPlugin.getInstance().getInventories().previousPage.slot) {
                        player.openInventory(new EnchantingGUI(guiManager.getBokShelfPower(), itemStack).getInventory());
                    }else{
                        if(slots.containsKey(e.getSlot())){
                            enchantObject.setLevel(slots.get(slot));
                            ItemStack newItem = guiManager.enchantItem(player, itemStack.clone(), enchantObject);
                            if(newItem != null){
                                itemStack = null;
                                player.openInventory(new EnchantingGUI(guiManager.getBokShelfPower(), newItem).getInventory());
                            }

                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        try {
            if(getInventory() == null || guiManager == null) return;
            Player p = event.getPlayer();
            Inventory inventory = getInventory();
            if(!p.getOpenInventory().getTopInventory().equals(inventory)) return;
            if(itemStack != null)
                p.getInventory().addItem(itemStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
