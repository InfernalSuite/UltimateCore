package mc.ultimatecore.skills.gui;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.Item;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.utils.InventoryUtils;
import mc.ultimatecore.skills.utils.Placeholder;
import mc.ultimatecore.skills.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttributeEditGUI implements GUI {

    private final UltimateItem ultimateItem;

    private final Map<Integer, Ability> abilityMap;

    private final Map<Integer, Perk> perksMap;

    public AttributeEditGUI(UltimateItem ultimateItem) {
        this.ultimateItem = ultimateItem;
        this.abilityMap = new HashMap<>();
        this.perksMap = new HashMap<>();
    }

    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == 49) {
                player.closeInventory();
            }else if(e.getSlot() == HyperSkills.getInstance().getInventories().armorItem.slot){
                if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
                ultimateItem.setItemStack(e.getCurrentItem());
                player.openInventory(new AttributeEditGUI(ultimateItem).getInventory());
            }else if(e.getSlot() == 45){
                player.openInventory(new ArmorEditGUI(ultimateItem).getInventory());
            }else{
                if(abilityMap.containsKey(e.getSlot())) {
                    player.sendMessage(StringUtils.color("&a► Type a number in chat to set the as new amount! Type &fstop &ato cancel the slot change."));
                    HyperSkills.getInstance().getUltimateItems().setSetupMode(player.getUniqueId(), ultimateItem.getId(), abilityMap.get(e.getSlot()).toString());
                    player.closeInventory();
                }else if(perksMap.containsKey(e.getSlot())) {
                    player.sendMessage(StringUtils.color("&a► Type a number in chat to set the as new amount! Type &fstop &ato cancel the slot change."));
                    HyperSkills.getInstance().getUltimateItems().setSetupMode(player.getUniqueId(), ultimateItem.getId(), perksMap.get(e.getSlot()).toString());
                    player.closeInventory();
                }
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, StringUtils.color("&8Attribute Editor"));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getBackground()));
        for (Integer slot : HyperSkills.getInstance().getInventories().armorDecorationSlots)
            inventory.setItem(slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().armorDecoration));
        inventory.setItem(HyperSkills.getInstance().getInventories().armorItem.slot, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().armorItem, Collections.singletonList(new Placeholder("item_id", ultimateItem.getId())), ultimateItem));
        //Abilities
        for(Ability ability : Ability.values()) {
            Item item = HyperSkills.getInstance().getInventories().attributeItem;
            item.headData = ability.getTexture();
            inventory.setItem(HyperSkills.getInstance().getInventories().attributesMap.get(ability), InventoryUtils.makeItem(item, Arrays.asList(new Placeholder("attribute_amount", String.valueOf(ultimateItem.getAbility(ability))), new Placeholder("attribute_name", ability.toString()))));
            abilityMap.put(HyperSkills.getInstance().getInventories().attributesMap.get(ability), ability);
        }
        //Perks
        for(Perk ability : Perk.values()) {
            Item item = HyperSkills.getInstance().getInventories().attributeItem;
            item.headData = ability.getTexture();
            inventory.setItem(HyperSkills.getInstance().getInventories().perksMap.get(ability), InventoryUtils.makeItem(item, Arrays.asList(new Placeholder("attribute_amount", String.valueOf(ultimateItem.getPerk(ability))), new Placeholder("attribute_name", ability.toString()))));
            perksMap.put(HyperSkills.getInstance().getInventories().perksMap.get(ability), ability);
        }
        inventory.setItem(49, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getCloseButton()));
        inventory.setItem(45, InventoryUtils.makeItem(HyperSkills.getInstance().getInventories().getPreviousPage()));
        return inventory;
    }
}
