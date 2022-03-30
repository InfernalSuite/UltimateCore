package com.infernalsuite.ultimatecore.skills.gui;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.Skill;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import com.infernalsuite.ultimatecore.skills.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.skills.utils.Placeholder;
import com.infernalsuite.ultimatecore.skills.utils.StringUtils;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.UUID;

public class SubGUI implements GUI {

    private final UUID uuid;

    private final SkillType skillType;

    private final int page;

    private final double multiplier;

    public SubGUI(UUID uuid, SkillType skillType, int page, double multiplier) {
        this.uuid = uuid;
        this.page = page;
        this.skillType = skillType;
        this.multiplier = multiplier;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == HyperSkills.getInstance().getInventories().getPreviousPage().slot) {
                if (page == 1)
                    player.openInventory(new MainGUI(uuid).getInventory());
                else
                    player.openInventory(new SubGUI(uuid, skillType, page-1, multiplier).getInventory());
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().getNextPage().slot && HyperSkills.getInstance().getConfiguration().maxSkillLevel != 25 * page) {
                player.openInventory(new SubGUI(uuid, skillType, page+1, multiplier).getInventory());
            } else if (e.getSlot() == HyperSkills.getInstance().getInventories().getCloseButton().slot) {
                player.closeInventory();
            }else if(e.getSlot() == HyperSkills.getInstance().getInventories().getMultiplierItem().slot){
                player.closeInventory();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.valueOf(HyperSkills.getInstance().getInventories().getMultiplierItem().command).replace("%player%", player.getName()));
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, HyperSkills.getInstance().getInventories().getSubMenuSize(), StringUtils.color(HyperSkills.getInstance().getInventories().getSubMenuTitles().get(skillType)));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getBackground()));
        Skill skill = HyperSkills.getInstance().getSkills().getAllSkills().get(skillType);
        int i = (25 * page) - 25;
        int level = HyperSkills.getInstance().getSkillManager().getLevel(uuid, skillType);
        inventory.setItem(HyperSkills.getInstance().getInventories().getSubMenuInfoItem().slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getSubMenuInfoItem(), Utils.getSkillsPlaceHolders(uuid, skillType), skillType, level));
        for (Integer slot : HyperSkills.getInstance().getInventories().getSubMenuSlots()) {
            i++;
            if (level == i - 1) {
                inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getSubMenuProgressItem(), Utils.getSkillsPlaceHolders(uuid, skillType, (i-1)), skillType, i, i));
            }else {
                if (level >= i)
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getSubMenuUnlockedItem(), Utils.getSkillsPlaceHolders(uuid, skillType, i-1), skillType, i, i));
                else
                    inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getSubMenuLockedItem(), Utils.getSkillsPlaceHolders(uuid, skillType, i-1), skillType, i, i));
            }
        }
        if(skill.isMultiplierEnabled())
            inventory.setItem(HyperSkills.getInstance().getInventories().getMultiplierItem().slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getMultiplierItem(), Collections.singletonList(new Placeholder("multiplier", String.valueOf(multiplier)))));
        inventory.setItem(HyperSkills.getInstance().getInventories().getPreviousPage().slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getPreviousPage()));
        if (i != HyperSkills.getInstance().getConfiguration().maxSkillLevel || i < HyperSkills.getInstance().getConfiguration().maxSkillLevel)
            inventory.setItem(HyperSkills.getInstance().getInventories().getNextPage().slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getNextPage()));
        inventory.setItem((HyperSkills.getInstance().getInventories()).getCloseButton().slot, InventoryUtils.makeItem((HyperSkills.getInstance().getInventories()).getCloseButton()));
        return inventory;
    }
}
