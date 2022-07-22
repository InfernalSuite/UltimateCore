package mc.ultimatecore.crafting.nms;

import net.minecraft.server.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R1.*;
import org.bukkit.craftbukkit.v1_19_R1.inventory.*;
import org.bukkit.inventory.*;

public class v1_19_R1 implements VanillaCraftingSource {

    @Override
    public Recipe getRecipe(ItemStack[] craftingMatrix, World world) {
        return Bukkit.getServer().getCraftingRecipe(craftingMatrix, world);
    }

    @Override
    public ItemStack[] getRemainingItemsForCrafting(ItemStack[] craftingMatrix, World world) {
        Container container = new Container(null, -1) {
            public InventoryView getBukkitView() {
                return null;
            }

            @Override
            public net.minecraft.world.item.ItemStack a(EntityHuman entityHuman, int i) {
                return null;
            }

            @Override
            public boolean a(EntityHuman entityHuman) {
                return false;
            }

        };

        InventoryCrafting crafting = new InventoryCrafting(container, 3, 3);
        for (int i = 0; i < craftingMatrix.length; ++i) {
            crafting.b(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        net.minecraft.world.item.ItemStack[] nmsItems = MinecraftServer.getServer().aE().c(Recipes.a, crafting, ((CraftWorld) world).getHandle()).toArray(new net.minecraft.world.item.ItemStack[0]);
        ItemStack[] bukkitItems = new ItemStack[nmsItems.length];
        for (int i = 0; i < nmsItems.length; i++) {
            bukkitItems[i] = CraftItemStack.asBukkitCopy(nmsItems[i]);
        }

        return bukkitItems;
    }

}