package com.infernalsuite.ultimatecore.helper.utils;

import com.cryptomorin.xseries.XMaterial;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.objects.hyper.HyperBlock;
import com.infernalsuite.ultimatecore.helper.objects.messages.MessageType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class CompatMaterial {
    
    private static final boolean LEGACY;
    
    static {
        LEGACY = XMaterial.getVersion() > 13;
    }
    
    public static boolean matchItem(ItemStack itemStack, String item) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;
        String key = itemStack.getType().toString();
        if (LEGACY) {
            return key.equals(item);
        } else {
            try {
                XMaterial material = XMaterial.valueOf(item);
                String legacy = material.getLegacy().length > 0 ? material.getLegacy()[0] : material.toString();
                if (key.equals(legacy) && itemStack.getData().getData() == material.getData())
                    return true;
            } catch (Exception e) {
                UltimatePlugin.getInstance().sendConsoleMessage("&cError unknown item " + key + "!", MessageType.COLORED);
            }
            return false;
        }
    }
    
    public static boolean matchBlock(Block block, HyperBlock hyperBlock) {
        if (block == null || block.getType() == Material.AIR) return false;
        String key = block.getType().toString();
        if (LEGACY) {
            return key.equals(hyperBlock.getKey()) && block.getData() == hyperBlock.getData();
        } else {
            try {
                XMaterial material = XMaterial.valueOf(hyperBlock.getKey());
                String legacyBlock = material.getLegacy().length > 0 ? material.getLegacy()[0] : material.toString();
                if (key.equals(legacyBlock) && block.getData() == material.getData())
                    return true;
            } catch (Exception e) {
                UltimatePlugin.getInstance().sendConsoleMessage("&cError unknown item " + key + "!", MessageType.COLORED);
            }
            return false;
        }
    }
}
