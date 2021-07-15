package mc.ultimatecore.anvil.gui;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.api.events.AnvilUseEvent;
import mc.ultimatecore.anvil.enums.AnvilState;
import mc.ultimatecore.anvil.managers.AnvilGUIManager;
import mc.ultimatecore.anvil.managers.User;
import mc.ultimatecore.anvil.nms.AnvilGUI;
import mc.ultimatecore.anvil.utils.InventoryUtils;
import mc.ultimatecore.anvil.utils.StringUtils;
import mc.ultimatecore.anvil.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class HyperAnvilGUI extends GUI implements Listener {

    private final AnvilGUIManager guiManager;

    public HyperAnvilGUI(User user) {
        super(HyperAnvil.getInstance().getInventories().getAnvilMenuSize(), HyperAnvil.getInstance().getInventories().getAnvilMenuTitle(), new HashSet<>(Arrays.asList(29, 33, 13, 22, 20, 11, 12, 24, 15, 14)));
        this.guiManager = new AnvilGUIManager(getInventory(), UUID.fromString(user.player));
        guiManager.updateAnvil();
        HyperAnvil.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if (getInventory().getItem(13) == null)
            setItem(13, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getInfoItem()));

        if (getInventory().getItem(22) == null)
            setItem(22, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getCombineItemsNormal()));
        if (HyperAnvil.getInstance().getInventories().getRenameItem().enabled)
            setItem(HyperAnvil.getInstance().getInventories().getRenameItem().slot, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getRenameItem()));
        setItem(HyperAnvil.getInstance().getInventories().getCloseButton().slot, InventoryUtils.makeItem(HyperAnvil.getInstance().getInventories().getCloseButton()));

    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        Inventory inventory = e.getInventory();
        if(!inventory.equals(getInventory())) return;
        if(guiManager.isItemToPickup()){
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("itemToPickup")));
            return;
        }
        guiManager.updateAnvil();
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getInventory().equals(getInventory())){
            Player player = (Player) e.getPlayer();
            //item is fusing
            if(guiManager.getFirstItem() != null) player.getInventory().addItem(guiManager.getFirstItem());
            if(guiManager.getSecondItem() != null) player.getInventory().addItem(guiManager.getSecondItem());
            getInventory().setItem(29, XMaterial.AIR.parseItem());
            getInventory().setItem(33, XMaterial.AIR.parseItem());
            ItemStack itemStack = getInventory().getItem(13);
            if(guiManager.isFusing()) guiManager.setFusing(false);
            if(guiManager.isItemToPickup()){
                guiManager.setItemToPickup(false);
                player.getInventory().addItem(itemStack);
                getInventory().setItem(13, XMaterial.AIR.parseItem());
            }
            guiManager.updateAnvil();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory()) && !e.getInventory().equals(getInventory())) return;
            Player player = (Player) e.getWhoClicked();
            if(guiManager.isFusing()){
                e.setCancelled(true);
                return;
            }
            if(guiManager.isItemToPickup()){
                if(e.getClickedInventory().equals(getInventory()) && e.getSlot() == 13){
                    guiManager.setItemToPickup(false);
                    guiManager.updateAnvil();
                    return;
                }
                e.setCancelled(true);
                player.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("itemToPickup")));
                return;
            }

            if(e.getClickedInventory().equals(getInventory())){
                if (e.getSlot() == HyperAnvil.getInstance().getInventories().getCloseButton().slot) {
                    e.setCancelled(true);
                    player.closeInventory();
                }else if(e.getSlot() == HyperAnvil.getInstance().getInventories().getRenameItem().slot && HyperAnvil.getInstance().getInventories().getRenameItem().enabled){
                    player.closeInventory();
                    openAnvil(player);
                }else if (e.getSlot() != 29 && e.getSlot() != 33) {
                    if(e.getSlot() == 13) {
                        if(guiManager.isItemToPickup())
                            return;
                        e.setCancelled(true);
                        return;
                    }else{
                        e.setCancelled(true);
                        if(e.getSlot() == 22){
                            if(guiManager.getAnvilState() == AnvilState.NO_ERROR_TAG || guiManager.getAnvilState() == AnvilState.NO_ERROR_ITEMS || guiManager.getAnvilState() == AnvilState.NO_ERROR_BOOK){
                                if(guiManager.hasLevelCost(player)) {
                                    AnvilUseEvent event = new AnvilUseEvent(player, guiManager.getAnvilState());
                                    Bukkit.getServer().getPluginManager().callEvent(event);
                                    if (event.isCancelled())
                                        return;
                                    Utils.playSound(player, HyperAnvil.getInstance().getConfiguration().anvilFuseSound);
                                    player.setLevel(player.getLevel() - Utils.getCost(guiManager));
                                    guiManager.fuseItems();
                                }else{
                                    player.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("xpErrorMessage").replace("%prefix%", HyperAnvil.getInstance().getConfiguration().prefix)));
                                }
                            }
                            return;
                        }
                    }
                }
            }
            guiManager.updateAnvil();
    }

    @EventHandler
    public void onInventoryClose(InventoryClickEvent e){
        ItemStack current = e.getCurrentItem();
        if(!e.getInventory().getType().equals(InventoryType.ANVIL)) return;
        if(current == null || current.getType() != XMaterial.NAME_TAG.parseMaterial()) return;
        if(e.getSlot() == 2) {
            if(!Utils.isModifiedNameTag(current)) return;
            Player player = (Player) e.getWhoClicked();
            double level = player.getLevel();
            double cost = HyperAnvil.getInstance().getConfiguration().nameTagCreateCost;
            if (level < cost) {
                player.sendMessage(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("xpErrorMessage")));
            } else {
                player.setLevel((int) (level - cost));
                player.getInventory().addItem(HyperAnvil.getInstance().getConfiguration().allowColorsInNameTag ? Utils.getColoredNameTag(current) : current);
            }
        }
    }


    public void openAnvil(Player myPlayer){
        new AnvilGUI.Builder()
                .onComplete((player, text) -> AnvilGUI.Response.close())
                .onLeftInputClick(player -> player.getInventory().addItem())
                .onRightInputClick(player -> player.setFoodLevel(player.getFoodLevel()))
                .onClose(player -> player.setFoodLevel(player.getFoodLevel()))
                .itemLeft(Utils.getModifiedNameTag())
                .itemRight(XMaterial.AIR.parseItem())
                .text(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("defaultName")))
                .title(StringUtils.color(HyperAnvil.getInstance().getMessages().getMessage("renameTitle")
                        .replace("%cost%", String.valueOf(HyperAnvil.getInstance().getConfiguration().nameTagCreateCost))))
                .plugin(HyperAnvil.getInstance())
                .open(myPlayer);
    }

}
