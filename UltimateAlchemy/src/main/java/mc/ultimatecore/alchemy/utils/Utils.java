package mc.ultimatecore.alchemy.utils;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.Item;
import mc.ultimatecore.alchemy.enums.BrewingSlots;
import mc.ultimatecore.alchemy.objects.AlchemyRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Utils {
    public static void openGUISync(Player player, Inventory inventory){
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperAlchemy.getInstance(), () -> player.openInventory(inventory),3L);
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path){
        Item item = new Item();
        if(yamlConfig.contains(path+".material")) item.material = XMaterial.valueOf(yamlConfig.getString(path+".material"));
        if(yamlConfig.contains(path+".title")) item.title = yamlConfig.getString(path+".title");
        if(yamlConfig.contains(path+".lore")) item.lore = yamlConfig.getStringList(path+".lore");
        if(yamlConfig.contains(path+".slot")) item.slot = yamlConfig.getInt(path+".slot");
        if(yamlConfig.contains(path+".command")) item.command = yamlConfig.getString(path+".command");
        if(yamlConfig.contains(path+".amount")) item.amount = yamlConfig.getInt(path+".amount");
        return item;
    }

    public static AlchemyRecipe getAlchemyRecipe(Inventory inventory, Player player){
        ItemStack fuelItem = inventory.getItem(13);
        if(fuelItem == null || fuelItem.getType() == Material.AIR) return null;
        if(player == null) return null;
        for(AlchemyRecipe alchemyRecipe : HyperAlchemy.getInstance().getBrewingRecipes().getRecipes().values()){
            if(alchemyRecipe.getPermission() != null && !player.hasPermission(alchemyRecipe.getPermission())) continue;
            if(!alchemyRecipe.getFuelItem().isSimilar(fuelItem)) continue;
            int equal = 0;
            int total = 0;
            for(Integer slot : BrewingSlots.BOTTLE_SLOTS.getSlots()){
                ItemStack inputItem = inventory.getItem(slot);
                if(inputItem != null && inputItem.getType() != Material.AIR)
                    if(inputItem.isSimilar(alchemyRecipe.getInputItem()))
                        equal++;
                    else total++;
            }
            if(equal > 0 && total == 0) return alchemyRecipe;
        }
        return null;
    }

    public static AlchemyRecipe getAlchemyRecipe(Inventory inventory){
        ItemStack fuelItem = inventory.getItem(13);
        if(fuelItem == null || fuelItem.getType() == Material.AIR) return null;
        for(AlchemyRecipe alchemyRecipe : HyperAlchemy.getInstance().getBrewingRecipes().getRecipes().values()){
            if(!alchemyRecipe.getFuelItem().isSimilar(fuelItem)) continue;
            int equal = 0;
            int total = 0;
            for(Integer slot : BrewingSlots.BOTTLE_SLOTS.getSlots()){
                ItemStack inputItem = inventory.getItem(slot);
                if(inputItem != null && inputItem.getType() != Material.AIR)
                    if(inputItem.isSimilar(alchemyRecipe.getInputItem()))
                        equal++;
                    else total++;
            }
            if(equal > 0 && total == 0) return alchemyRecipe;
        }
        return null;
    }

    public static Double round(double value){
        return Math.round(value * 10) / 10D;
    }

}
