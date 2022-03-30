package com.infernalsuite.ultimatecore.crafting.gui.recipeeditor;


import com.infernalsuite.ultimatecore.crafting.HyperCrafting;
import com.infernalsuite.ultimatecore.crafting.gui.GUI;
import com.infernalsuite.ultimatecore.crafting.gui.RecipePreviewGUI;
import com.infernalsuite.ultimatecore.crafting.objects.Category;
import com.infernalsuite.ultimatecore.crafting.objects.CraftingRecipe;
import com.infernalsuite.ultimatecore.crafting.objects.EditType;
import com.infernalsuite.ultimatecore.crafting.playerdata.User;
import com.infernalsuite.ultimatecore.crafting.utils.InventoryUtils;
import com.infernalsuite.ultimatecore.crafting.utils.Placeholder;
import com.infernalsuite.ultimatecore.crafting.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class IndividualRecipeGUI extends GUI implements Listener {

    private final CraftingRecipe recipe;

    private final Map<UUID, EditType> editMap;

    public IndividualRecipeGUI(CraftingRecipe craftingRecipe) {
        super(45, "&8Recipe Settings", new HashSet<>(Arrays.asList(37,38,39,41,42,43,44)));
        this.recipe = craftingRecipe;
        this.editMap = new HashMap<>();
        HyperCrafting.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if(recipe == null) return;
        String permission = recipe.getPermission() == null || recipe.getPermission().equalsIgnoreCase("") ? "&cNONE" : recipe.getPermission();
        setItem(HyperCrafting.getInstance().getInventories().permissionEditItem.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().permissionEditItem, Collections.singletonList(new Placeholder("permission", permission))));
        setItem(HyperCrafting.getInstance().getInventories().itemsEditItem.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().itemsEditItem));
        setItem(HyperCrafting.getInstance().getInventories().itemPreview.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().itemPreview));

        setItem(HyperCrafting.getInstance().getInventories().recipesSlot.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().recipesSlot, Collections.singletonList(new Placeholder("slot", String.valueOf(recipe.getSlot())))));
        setItem(HyperCrafting.getInstance().getInventories().recipesCategory.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().recipesCategory, Collections.singletonList(new Placeholder("category", String.valueOf(recipe.getCategory())))));
        setItem(HyperCrafting.getInstance().getInventories().overrideRecipe.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().overrideRecipe, Collections.singletonList(new Placeholder("isOverride", String.valueOf(recipe.isOverrideRecipe())))));

        setItem(HyperCrafting.getInstance().getInventories().recipesPage.slot, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().recipesPage, Collections.singletonList(new Placeholder("page", String.valueOf(recipe.getPage())))));

        setItem(getInventory().getSize() - 9, InventoryUtils.makeItem((HyperCrafting.getInstance().getInventories()).previousPage));
        setItem(40, InventoryUtils.makeItem(HyperCrafting.getInstance().getInventories().closeButton));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())){
            return;
        }
        int slot = e.getSlot();
        e.setCancelled(true);
        User user = User.getUser(player);
        if(slot == 40) {
            player.closeInventory();
        } else if (slot == getInventory().getSize() - 9 && e.getCurrentItem() != null) {
            player.openInventory(new AllRecipesGUI(1).getInventory());
        } else if (slot == HyperCrafting.getInstance().getInventories().itemsEditItem.slot) {
            player.openInventory(user.getRecipeCreatorGUI(recipe).getInventory());
        } else if (slot == HyperCrafting.getInstance().getInventories().itemPreview.slot) {
            new RecipePreviewGUI(HyperCrafting.getInstance(), recipe).openInventory(player);
        }else if(slot == HyperCrafting.getInstance().getInventories().overrideRecipe.slot){
            recipe.setOverrideRecipe(!recipe.isOverrideRecipe());
            player.openInventory(new IndividualRecipeGUI(recipe).getInventory());
        } else if (slot == HyperCrafting.getInstance().getInventories().permissionEditItem.slot) {
            player.sendMessage(Utils.color("&a• You are editing permission for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.PERMISSION);
        } else if (slot == HyperCrafting.getInstance().getInventories().recipesSlot.slot) {
            player.sendMessage(Utils.color("&a• You are editing slot for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.SLOT);
        } else if (slot == HyperCrafting.getInstance().getInventories().recipesPage.slot) {
            player.sendMessage(Utils.color("&a• You are editing page for &f"+ recipe.getName()+"&a type &fstop &ato leave"));
            player.closeInventory();
            this.editMap.put(player.getUniqueId(), EditType.PAGE);
        } else if (slot == HyperCrafting.getInstance().getInventories().recipesCategory.slot) {
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
                Utils.openGUISync(player, getInventory());
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
                Category category = HyperCrafting.getInstance().getCategories().getCategory(message);
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
            Utils.openGUISync(player, getInventory());
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
