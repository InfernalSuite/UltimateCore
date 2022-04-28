package mc.ultimatecore.crafting.nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.*;

public class v1_8_R3 implements VanillaCraftingSource {

    @Override
    public Recipe getRecipe(org.bukkit.inventory.ItemStack[] craftingMatrix, World world) {
        Container container = new Container() {
            public InventoryView getBukkitView() {
                return null;
            }

            @Override
            public boolean a(EntityHuman entityHuman) {
                return false;
            }
        };

        InventoryCrafting crafting = new InventoryCrafting(container, 3, 3);
        for (int i = 0; i < craftingMatrix.length; ++i) {
            crafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        ItemStack itemStack = CraftItemStack.asBukkitCopy(CraftingManager.getInstance().craft(crafting, ((CraftWorld) world).getHandle()));
        return () -> itemStack;
    }

    @Override
    public ItemStack[] getRemainingItemsForCrafting(ItemStack[] craftingMatrix, World world) {
        Container container = new Container() {
            public InventoryView getBukkitView() {
                return null;
            }

            @Override
            public boolean a(EntityHuman entityHuman) {
                return false;
            }
        };

        InventoryCrafting crafting = new InventoryCrafting(container, 3, 3);
        for (int i = 0; i < craftingMatrix.length; ++i) {
            crafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        net.minecraft.server.v1_8_R3.ItemStack[] nmsItems = CraftingManager.getInstance().b(crafting, ((CraftWorld) world).getHandle());
        ItemStack[] bukkitItems = new ItemStack[nmsItems.length];
        for (int i = 0; i < nmsItems.length; i++) {
            bukkitItems[i] = CraftItemStack.asBukkitCopy(nmsItems[i]);
        }

        return bukkitItems;
    }
}