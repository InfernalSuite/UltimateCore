package mc.ultimatecore.crafting.gui.recipeeditor;


import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.gui.GUI;
import mc.ultimatecore.crafting.gui.RecipePreviewGUI;
import mc.ultimatecore.crafting.objects.Category;
import mc.ultimatecore.crafting.objects.CraftingRecipe;
import mc.ultimatecore.crafting.objects.EditType;
import mc.ultimatecore.crafting.playerdata.User;
import mc.ultimatecore.crafting.utils.InventoryUtils;
import mc.ultimatecore.crafting.utils.Placeholder;
import mc.ultimatecore.crafting.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class IndividualRecipeGUI extends GUI implements Listener {

    private final HyperCrafting plugin;

    private final CraftingRecipe recipe;

    private final Map<UUID, EditType> editMap;

    public IndividualRecipeGUI(CraftingRecipe craftingRecipe, HyperCrafting plugin) {
        super(45, "&8Recipe Settings", new HashSet<>(Arrays.asList(37,38,39,41,42,43,44)), plugin);
        this.plugin = plugin;
        this.recipe = craftingRecipe;
        this.editMap = new HashMap<>();
        plugin.registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if(recipe == null) return;
        String permission = recipe.getPermission() == null || recipe.getPermission().equalsIgnoreCase("") ? "&cNONE" : recipe.getPermission();
        setItem(this.plugin.getInventories().permissionEditItem.slot, InventoryUtils.makeItem(this.plugin.getInventories().permissionEditItem, Collections.singletonList(new Placeholder("permission", permission))));
        setItem(this.plugin.getInventories().itemsEditItem.slot, InventoryUtils.makeItem(this.plugin.getInventories().itemsEditItem));
        setItem(this.plugin.getInventories().itemPreview.slot, InventoryUtils.makeItem(this.plugin.getInventories().itemPreview));

        setItem(this.plugin.getInventories().recipesSlot.slot, InventoryUtils.makeItem(this.plugin.getInventories().recipesSlot, Collections.singletonList(new Placeholder("slot", String.valueOf(recipe.getSlot())))));
        setItem(this.plugin.getInventories().recipesCategory.slot, InventoryUtils.makeItem(this.plugin.getInventories().recipesCategory, Collections.singletonList(new Placeholder("category", String.valueOf(recipe.getCategory())))));
        setItem(this.plugin.getInventories().overrideRecipe.slot, InventoryUtils.makeItem(this.plugin.getInventories().overrideRecipe, Collections.singletonList(new Placeholder("isOverride", String.valueOf(recipe.isOverrideRecipe())))));

        setItem(this.plugin.getInventories().recipesPage.slot, InventoryUtils.makeItem(this.plugin.getInventories().recipesPage, Collections.singletonList(new Placeholder("page", String.valueOf(recipe.getPage())))));

        setItem(getInventory().getSize() - 9, InventoryUtils.makeItem((this.plugin.getInventories()).previousPage));
        setItem(40, InventoryUtils.makeItem(this.plugin.getInventories().closeButton));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())){
            return;
        }
        int slot = e.getSlot();
        e.setCancelled(true);
        if(slot == 40) {
            player.closeInventory();
        } else if (slot == getInventory().getSize() - 9 && e.getCurrentItem() != null) {
            player.openInventory(new AllRecipesGUI(1, this.plugin).getInventory());
        } else if (slot == this.plugin.getInventories().itemsEditItem.slot) {
            player.openInventory(this.plugin.getPlayerManager().getRecipeCreatorGUI(recipe).getInventory());
        } else if (slot == this.plugin.getInventories().itemPreview.slot) {
            new RecipePreviewGUI(this.plugin, recipe).openInventory(player);
        }else if(slot == this.plugin.getInventories().overrideRecipe.slot){
            recipe.setOverrideRecipe(!recipe.isOverrideRecipe());
            player.openInventory(new IndividualRecipeGUI(recipe, this.plugin).getInventory());
        } else if (slot == this.plugin.getInventories().permissionEditItem.slot) {
            player.sendMessage(Utils.color("&a• You are editing permission for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.PERMISSION);
        } else if (slot == this.plugin.getInventories().recipesSlot.slot) {
            player.sendMessage(Utils.color("&a• You are editing slot for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.SLOT);
        } else if (slot == this.plugin.getInventories().recipesPage.slot) {
            player.sendMessage(Utils.color("&a• You are editing page for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.PAGE);
        } else if (slot == this.plugin.getInventories().recipesCategory.slot) {
            player.sendMessage(Utils.color("&a• You are editing category for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.CATEGORY);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if(editMap.containsKey(player.getUniqueId())){
            e.setCancelled(true);
            String message = e.getMessage();
            if(message.contains("stop")){
                this.editMap.remove(player.getUniqueId());
                Utils.openGUISync(player, getInventory(), this.plugin);
                return;
            }
            if(message.contains(" ")){
                player.sendMessage(Utils.color("&c• Invalid Characters!"));
                return;
            }
            EditType editType = editMap.get(player.getUniqueId());
            if(editType == EditType.PERMISSION) {
                recipe.setPermission(message);
            }else if(editType == EditType.CATEGORY){
                Category category = this.plugin.getCategories().getCategory(message);
                if(category == null){
                    player.sendMessage(Utils.color("&c• Invalid Category!"));
                    return;
                }
                recipe.setCategory(category.getKey());
            }else if(editType == EditType.SLOT){
                Integer slot = parseInt(message);
                if(slot == null){
                    player.sendMessage(Utils.color("&c• Invalid Numbers!"));
                    return;
                }
                recipe.setSlot(slot);
            }else if(editType == EditType.PAGE){
                Integer page = parseInt(message);
                if(page == null){
                    player.sendMessage(Utils.color("&c• Invalid Numbers!"));
                    return;
                }
                recipe.setPage(page);
            }
            this.editMap.remove(player.getUniqueId());
            Utils.openGUISync(player, getInventory(), this.plugin);
        }
    }

    private Integer parseInt(String str){
        try{
            return Integer.parseInt(str);
        }catch (NumberFormatException e){
            return null;
        }
    }

}
