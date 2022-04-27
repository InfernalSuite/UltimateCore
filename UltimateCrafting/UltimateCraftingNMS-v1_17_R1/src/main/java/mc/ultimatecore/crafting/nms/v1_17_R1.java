package mc.ultimatecore.crafting.nms;

import net.minecraft.server.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.*;
import org.bukkit.craftbukkit.v1_17_R1.inventory.*;
import org.bukkit.inventory.*;

import java.util.*;

public class v1_17_R1 implements VanillaCraftingSource {

    @Override
    public Recipe getRecipe(ItemStack[] craftingMatrix, World world) {
        Container container = new Container(null, -1) {
            public InventoryView getBukkitView() {
                return null;
            }

            public boolean canUse(EntityHuman entityhuman) {
                return false;
            }
        };

        InventoryCrafting inventoryCrafting = new InventoryCrafting(container, 3, 3);
        return this.getNMSRecipe(craftingMatrix, inventoryCrafting, (CraftWorld) world).map(IRecipe::toBukkitRecipe).orElse(null);
    }

    private Optional<RecipeCrafting> getNMSRecipe(ItemStack[] craftingMatrix, InventoryCrafting inventoryCrafting, CraftWorld world) {
        for (int i = 0; i < craftingMatrix.length; ++i) {
            inventoryCrafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        return MinecraftServer.getServer().getCraftingManager().craft(Recipes.a, inventoryCrafting, world.getHandle());
    }

    @Override
    public ItemStack[] getRemainingItemsForCrafting(ItemStack[] craftingMatrix, World world) {
        Container container = new Container(null, -1) {
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

        net.minecraft.world.item.ItemStack[] nmsItems = MinecraftServer.getServer().getCraftingManager().c(Recipes.a, crafting, ((CraftWorld) world).getHandle()).toArray(new net.minecraft.world.item.ItemStack[0]);
        ItemStack[] bukkitItems = new ItemStack[nmsItems.length];
        for (int i = 0; i < nmsItems.length; i++) {
            bukkitItems[i] = CraftItemStack.asBukkitCopy(nmsItems[i]);
        }

        return bukkitItems;
    }

}