package com.infernalsuite.ultimatecore.dragon.inventories;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.managers.SetupManager;
import com.infernalsuite.ultimatecore.dragon.objects.guardian.DragonGuardian;
import com.infernalsuite.ultimatecore.dragon.objects.guardian.GuardianArmor;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IGuardian;
import com.infernalsuite.ultimatecore.dragon.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.dragon.utils.Placeholder;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class GuardianGUI extends GUI {

    private final DragonGuardian ultimateItem;

    private final HyperDragons plugin = HyperDragons.getInstance();

    public GuardianGUI(IGuardian ultimateItem) {
        super(1);
        this.ultimateItem = (DragonGuardian) ultimateItem;
    }

    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == 49) {
                player.closeInventory();
            }else if(e.getSlot() == 48) {
                player.openInventory(new AllGuardiansGUI(1).getInventory());
            }else if(e.getSlot() == 10){
                player.openInventory(new EquipEditGUI(player, ultimateItem, EquipEditGUI.Equip.HELMET).getInventory());
            }else if(e.getSlot() == 12){
                player.openInventory(new EquipEditGUI(player, ultimateItem, EquipEditGUI.Equip.CHESTPLATE).getInventory());
            }else if(e.getSlot() == 14){
                player.openInventory(new EquipEditGUI(player, ultimateItem, EquipEditGUI.Equip.LEGGINGS).getInventory());
            }else if(e.getSlot() == 16){
                player.openInventory(new EquipEditGUI(player, ultimateItem, EquipEditGUI.Equip.BOOTS).getInventory());
            }else if(e.getSlot() == 29) {
                player.sendMessage(StringUtils.color("&a► Type a mob type in chat to set the as new mob! Type &fstop &ato cancel the slot change."));
                plugin.getSetupManager().setSetupMode(player.getUniqueId(), ultimateItem, SetupManager.EditType.MOB);
                player.closeInventory();
            }else if(e.getSlot() == 33){
                player.sendMessage(StringUtils.color("&a► Type a number type in chat to set the as new health! Type &fstop &ato cancel the slot change."));
                plugin.getSetupManager().setSetupMode(player.getUniqueId(), ultimateItem, SetupManager.EditType.HEALTH);
                player.closeInventory();
            }else if(e.getSlot() == 31){
                player.sendMessage(StringUtils.color("&a► Type a number type in chat to set the as new displayname! Type &fstop &ato cancel the slot change."));
                plugin.getSetupManager().setSetupMode(player.getUniqueId(), ultimateItem, SetupManager.EditType.DISPLAYNAME);
                player.closeInventory();
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Item Editor"));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(plugin.getInventories().background));
        GuardianArmor guardianArmor = ultimateItem.getGuardianArmor();

        inventory.setItem(10, InventoryUtils.makeItem(plugin.getInventories().helmet, guardianArmor.getHelmet()));
        inventory.setItem(12, InventoryUtils.makeItem(plugin.getInventories().chestplate, guardianArmor.getChestplate()));
        inventory.setItem(14, InventoryUtils.makeItem(plugin.getInventories().leggings, guardianArmor.getLeggings()));
        inventory.setItem(16, InventoryUtils.makeItem(plugin.getInventories().boots, guardianArmor.getBoots()));
        inventory.setItem(29, InventoryUtils.makeItem(plugin.getInventories().type, Collections.singletonList(new Placeholder("type", String.valueOf(ultimateItem.getEntity())))));
        inventory.setItem(31, InventoryUtils.makeItem(plugin.getInventories().displayname, Collections.singletonList(new Placeholder("displayname", String.valueOf(ultimateItem.getDisplayName())))));
        inventory.setItem(33, InventoryUtils.makeItem(plugin.getInventories().health, Collections.singletonList(new Placeholder("health", String.valueOf(ultimateItem.getHealth())))));

        inventory.setItem(48, InventoryUtils.makeItem(plugin.getInventories().previousPage));
        inventory.setItem(49, InventoryUtils.makeItem(plugin.getInventories().closeButton));

        return inventory;
    }
}
