package com.infernalsuite.ultimatecore.dragon.inventories;

import com.infernalsuite.ultimatecore.dragon.objects.guardian.DragonGuardian;
import com.infernalsuite.ultimatecore.dragon.objects.guardian.GuardianArmor;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EquipEditGUI extends GUI {
    private final DragonGuardian dragonGuardian;
    private final Equip equip;
    private final ItemStack[] itemStacks;

    public EquipEditGUI(Player player, DragonGuardian dragonGuardian, Equip equip) {
        super(1);
        this.dragonGuardian = dragonGuardian;
        this.equip = equip;
        this.itemStacks = player.getInventory().getContents();
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item != null){
            GuardianArmor guardianArmor = dragonGuardian.getGuardianArmor();
            if(equip.equals(Equip.HELMET))
                guardianArmor.setHelmet(item.clone());
            else if(equip.equals(Equip.CHESTPLATE))
                guardianArmor.setChestplate(item.clone());
            else if(equip.equals(Equip.LEGGINGS))
                guardianArmor.setLeggings(item.clone());
            else if(equip.equals(Equip.BOOTS))
                guardianArmor.setBoots(item.clone());
            else
                return;
            event.getWhoClicked().openInventory(new GuardianGUI(dragonGuardian).getInventory());
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        player.openInventory(new GuardianGUI(dragonGuardian).getInventory());
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 45, StringUtils.color("&8Select an Item"));
        if(itemStacks != null) inventory.setContents(itemStacks);
        return inventory;
    }

    enum Equip{
        LEGGINGS,
        CHESTPLATE,
        HELMET,
        BOOTS
    }
}
