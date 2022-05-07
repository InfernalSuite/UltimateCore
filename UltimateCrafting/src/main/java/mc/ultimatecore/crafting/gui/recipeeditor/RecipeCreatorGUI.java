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

    private final HyperCrafting plugin;

    private final CraftingRecipe craftingRecipe;

    public RecipeCreatorGUI(CraftingRecipe craftingRecipe, HyperCrafting plugin) {
        super(craftingRecipe.getName(), plugin);
        this.plugin = plugin;
        this.craftingRecipe = craftingRecipe;
        plugin.registerListeners(this);
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
        if (!e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getPlayer();
        if (isEmpty() || craftingRecipe.getResult().getType() == Material.AIR){
            if(this.plugin.getCraftingRecipes().isCreated(craftingRecipe.getName()))
                Utils.openGUISync(player, craftingRecipe.getIndividualRecipeGUI().getInventory(), this.plugin);
            else
                player.sendMessage(Utils.color(this.plugin.getMessages().getMessage("recipeCanceled").replace("%prefix%", this.plugin.getConfiguration().prefix)));
            return;
        }
        CraftingRecipe newCraftingRecipe = craftingRecipe;
        if(this.plugin.getCraftingRecipes().isCreated(craftingRecipe.getName())) {
            Utils.openGUISync(player, craftingRecipe.getIndividualRecipeGUI().getInventory(), this.plugin);
            this.plugin.getCraftingRecipes().removeRecipe(craftingRecipe);
        }else {
            player.sendMessage(Utils.color(this.plugin.getMessages().getMessage("recipeCreated").replace("%recipe_name%", newCraftingRecipe.getName()).replace("%prefix%", this.plugin.getConfiguration().prefix)));
        }
        createRecipe(newCraftingRecipe, getInventory());
        this.plugin.getCraftingRecipes().addRecipe(newCraftingRecipe);
        this.plugin.getCraftingRecipes().save();
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
