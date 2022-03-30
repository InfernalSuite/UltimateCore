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
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


public class EnchantingGUI extends GUI implements Listener {

    private final GUIManager guiManager;

    private final EnchantmentsPlugin plugin;

    public EnchantingGUI(int bookShelfPower, ItemStack itemStack) {
        super(EnchantmentsPlugin.getInstance().getInventories().mainMenuSize, EnchantmentsPlugin.getInstance().getInventories().mainMenuTitle, false);
        this.plugin = EnchantmentsPlugin.getInstance();
        this.guiManager = new GUIManager(getInventory(), bookShelfPower);
        if(itemStack != null) setItem(19, itemStack);
        this.plugin.registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        setItem(plugin.getInventories().closeButton.slot, InventoryUtils.makeItem(plugin.getInventories().closeButton));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getInventory().equals(getInventory())){
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if(guiManager.getToEnchantItem() != null){
                    e.getPlayer().getInventory().addItem(guiManager.getToEnchantItem());
                    guiManager.setToEnchantItem(null);
                }
            }, 3L);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if(!e.getInventory().equals(getInventory())) return;
        guiManager.checkEnchantmentTable();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getWhoClicked();
        if(e.getClickedInventory().equals(getInventory())){
            if (e.getCurrentItem() != null) {
                int slot = e.getSlot();
                if(slot == 49) {
                    player.closeInventory();
                    e.setCancelled(true);
                }else{
                    if (slot != 19) {
                        e.setCancelled(true);
                        int page = guiManager.getBooksPage();
                        if (slot == plugin.getInventories().backPageButton.slot && page > 1) {
                            guiManager.setBooksPage(page - 1);
                        } else if (slot == plugin.getInventories().nextPageButton.slot && e.getCurrentItem().getType().equals(InventoryUtils.makeItem(plugin.getInventories().nextPageButton).getType())) {
                            guiManager.setBooksPage(page + 1);
                        } else {
                            Map<Integer, EnchantObject> hyperEnchants = guiManager.getEnchantsMap();
                            if (hyperEnchants.size() < 1) return;
                            if (!hyperEnchants.containsKey(slot)) return;
                            HyperEnchant hyperEnchant = hyperEnchants.get(slot).getHyperEnchant();
                            if (guiManager.getBokShelfPower() >= hyperEnchant.getRequiredBookShelf()) {
                                ItemStack newItem = guiManager.getToEnchantItem().clone();
                                guiManager.setToEnchantItem(null);
                                player.openInventory(new SecondPageGUI(guiManager, player.getUniqueId(), newItem, slot).getInventory());
                                return;
                            } else {
                                player.sendMessage(Utils.color(EnchantmentsPlugin.getInstance().getMessages().getMessage("notEnoughPower")));
                            }
                        }
                    }
                }
            }
        }
        guiManager.checkEnchantmentTable();
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (!e.getInventory().equals(getInventory())) return;
        guiManager.checkEnchantmentTable();
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        try {
            if(getInventory() == null || guiManager == null) return;
            Player p = event.getPlayer();
            Inventory inventory = getInventory();
            if(!p.getOpenInventory().getTopInventory().equals(inventory)) return;
            if(guiManager.getToEnchantItem() != null)
                p.getInventory().addItem(guiManager.getToEnchantItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
