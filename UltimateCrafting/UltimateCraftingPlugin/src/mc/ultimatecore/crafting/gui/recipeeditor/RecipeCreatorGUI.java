package mc.ultimatecore.crafting.gui.recipeeditor;

import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.GUI;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class RecipeCreatorGUI extends GUI implements Listener {

    private final CraftingRecipe craftingRecipe;

    public RecipeCreatorGUI(CraftingRecipe craftingRecipe) {
        super(craftingRecipe.getName());
        this.craftingRecipe = craftingRecipe;
        HyperCrafting.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if(e.getSlot() == 49) {
                    player.closeInventory();
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory() == null || !e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getPlayer();
        if (isEmpty() || craftingRecipe.getResult() == null || craftingRecipe.getResult().getType() == Material.AIR){
            if(HyperCrafting.getInstance().getCraftingRecipes().isCreated(craftingRecipe.getName()))
                Utils.openGUISync(player, craftingRecipe.getIndividualRecipeGUI().getInventory());
            else
                player.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("recipeCanceled").replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
            return;
        }
        CraftingRecipe newCraftingRecipe = craftingRecipe;
        if(HyperCrafting.getInstance().getCraftingRecipes().isCreated(craftingRecipe.getName())) {
            Utils.openGUISync(player, craftingRecipe.getIndividualRecipeGUI().getInventory());
            HyperCrafting.getInstance().getCraftingRecipes().removeRecipe(craftingRecipe);
        }else {
            player.sendMessage(Utils.color(HyperCrafting.getInstance().getMessages().getMessage("recipeCreated").replace("%recipe_name%", newCraftingRecipe.getName()).replace("%prefix%", HyperCrafting.getInstance().getConfiguration().prefix)));
        }
        createRecipe(newCraftingRecipe, getInventory());
        HyperCrafting.getInstance().getCraftingRecipes().addRecipe(newCraftingRecipe);
        HyperCrafting.getInstance().getCraftingRecipes().save();
        //newCraftingRecipe.registerRecipe();
    }

    public void createRecipe(CraftingRecipe craftingRecipe, Inventory inventory){
        craftingRecipe.getRecipeItems().clear();
        for(int i = 0; i<=8; i++){
            if(inventory.getItem(i) != null && !inventory.getItem(i).getType().equals(Material.AIR)){
                craftingRecipe.getRecipeItems().put(i, inventory.getItem(i));
            }
        }
    }

    private boolean isEmpty(){
        for(int i = 1 ; i<=8; i++){
            if(getInventory().getItem(i) != null)
                return false;
        }
        return true;
    }

}
