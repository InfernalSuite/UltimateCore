package mc.ultimatecore.crafting.nms;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.*;

public class v1_12_R1 implements VanillaCraftingSource {

    @Override
    public Recipe getRecipe(org.bukkit.inventory.ItemStack[] craftingMatrix, World world) {
        Container container = new Container() {
            public InventoryView getBukkitView() {
                return null;
            }

            public boolean canUse(EntityHuman entityhuman) {
                return false;
            }
        };

        InventoryCrafting crafting = new InventoryCrafting(container, 3, 3);
        for (int i = 0; i < craftingMatrix.length; ++i) {
            crafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        ItemStack itemStack = CraftItemStack.asBukkitCopy(CraftingManager.craft(crafting, ((CraftWorld) world).getHandle()));
        return () -> itemStack;
    }

    @Override
    public ItemStack[] getRemainingItemsForCrafting(ItemStack[] craftingMatrix, World world) {
        Container container = new Container() {
            public InventoryView getBukkitView() {
                return null;
            }

            public boolean canUse(EntityHuman entityhuman) {
                return false;
            }
        };

        InventoryCrafting crafting = new InventoryCrafting(container, 3, 3);
        for (int i = 0; i < craftingMatrix.length; ++i) {
            crafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        net.minecraft.server.v1_12_R1.ItemStack[] nmsItems = CraftingManager.c(crafting, ((CraftWorld) world).getHandle()).toArray(new net.minecraft.server.v1_12_R1.ItemStack[0]);
        ItemStack[] bukkitItems = new ItemStack[nmsItems.length];
        for (int i = 0; i < nmsItems.length; i++) {
            bukkitItems[i] = CraftItemStack.asBukkitCopy(nmsItems[i]);
        }

        return bukkitItems;
    }

}