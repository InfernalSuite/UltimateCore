package com.infernalsuite.ultimatecore.helper.implementations;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.helper.objects.Placeholder;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemCreatorImpl {
    
    ItemStack makeItem(ItemStack item, int amount, String name, List<String> lore);
    
    ItemStack makeItem(XMaterial material, int amount, String name, List<String> lore);
    
    ItemStack makeItem(Item item);
    
    ItemStack makeItem(Item item, List<Placeholder> placeholders);
}
