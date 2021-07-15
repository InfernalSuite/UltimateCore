package mc.ultimatecore.skills.gui;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.utils.InventoryUtils;
import mc.ultimatecore.skills.utils.Placeholder;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmorEditGUI implements GUI {

    private final UltimateItem ultimateItem;

    public ArmorEditGUI(UltimateItem ultimateItem) {
        this.ultimateItem = ultimateItem;
    }

    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == 49) {
                player.closeInventory();
            } else if (e.isShiftClick()) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
                ultimateItem.setItemStack(e.getCurrentItem());
                player.openInventory(new ArmorEditGUI(ultimateItem).getInventory());
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().manaCost.slot) {
                player.sendMessage(StringUtils.color("&a► Type a number in chat to set the as new amount! Type &fstop &ato cancel the slot change."));
                HyperSkills.getInstance().getUltimateItems().setSetupMode(player.getUniqueId(), ultimateItem.getId(), "ManaCost");
                player.closeInventory();
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().displayName.slot) {
                player.sendMessage(StringUtils.color("&a► Type a name in chat to set the as new name! Type &fstop &ato cancel the slot change."));
                HyperSkills.getInstance().getUltimateItems().setSetupMode(player.getUniqueId(), ultimateItem.getId(), "Name");
                player.closeInventory();
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().giveItem.slot) {
                player.getInventory().addItem(ultimateItem.getItem());
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().loreItem.slot) {
                if(e.isLeftClick()){
                    player.sendMessage(StringUtils.color("&a► Type a new line in chat to set the add a lore line! Type &fstop &ato cancel the lore change."));
                    HyperSkills.getInstance().getUltimateItems().setSetupMode(player.getUniqueId(), ultimateItem.getId(), "Lore");
                    player.closeInventory();
                }else if(e.isRightClick()){
                    ultimateItem.setItemStack(getLoredItem());
                    player.openInventory(new ArmorEditGUI(ultimateItem).getInventory());
                }
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().glowItem.slot) {
                switchGlow();
                player.openInventory(new ArmorEditGUI(ultimateItem).getInventory());
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().effectInHand.slot) {
                ultimateItem.setEffectInHand(!ultimateItem.isEffectInHand());
                player.openInventory(new ArmorEditGUI(ultimateItem).getInventory());
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().attributes.slot) {
                player.openInventory(new AttributeEditGUI(ultimateItem).getInventory());
            } else if (e.getSlot() == 45) {
                player.openInventory(new AllArmorsGUI(1).getInventory());
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Item Editor"));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getBackground()));
        for (Integer slot : HyperSkills.getInstance().getInventories().armorDecorationSlots)
            inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().armorDecoration));
        inventory.setItem(HyperSkills.getInstance().getInventories().armorItem.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().armorItem, Collections.singletonList(new Placeholder("item_id", ultimateItem.getId())), ultimateItem));
        inventory.setItem(HyperSkills.getInstance().getInventories().manaCost.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().manaCost, Collections.singletonList(new Placeholder("mana_cost", String.valueOf(ultimateItem.getManaCost())))));
        inventory.setItem(HyperSkills.getInstance().getInventories().attributes.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().attributes));
        inventory.setItem(HyperSkills.getInstance().getInventories().giveItem.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().giveItem));
        inventory.setItem(HyperSkills.getInstance().getInventories().effectInHand.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().effectInHand, Collections.singletonList(new Placeholder("effect_hand", String.valueOf(ultimateItem.isEffectInHand())))));
        inventory.setItem(HyperSkills.getInstance().getInventories().displayName.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().displayName, Collections.singletonList(new Placeholder("displayName", String.valueOf(ultimateItem.getDisplayName())))));
        inventory.setItem(HyperSkills.getInstance().getInventories().loreItem.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().loreItem, new ArrayList<>(), ultimateItem));
        inventory.setItem(HyperSkills.getInstance().getInventories().glowItem.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().glowItem));

        inventory.setItem(45, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getPreviousPage()));

        return inventory;
    }

    private void switchGlow(){
        ItemStack newItem = ultimateItem.getItemStack();
        ItemMeta meta = newItem.getItemMeta();
        if(meta.getEnchants().containsKey(Enchantment.LURE)){
            meta.removeEnchant(Enchantment.LURE);
        }else {
            meta.addEnchant(Enchantment.LURE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        newItem.setItemMeta(meta);
        ultimateItem.setItemStack(newItem);
    }

    private ItemStack getLoredItem(){
        ItemStack newItem = ultimateItem.getItemStack();
        ItemMeta meta = newItem.getItemMeta();
        if(!meta.hasLore()) return newItem;
        List<String> lore = meta.getLore();
        lore.remove(lore.size() - 1);
        meta.setLore(lore);
        newItem.setItemMeta(meta);
        return newItem;
    }
}
