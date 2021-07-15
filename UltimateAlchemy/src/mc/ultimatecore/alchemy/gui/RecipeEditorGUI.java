package mc.ultimatecore.alchemy.gui;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.alchemy.HyperAlchemy;
import mc.ultimatecore.alchemy.enums.BrewingSlots;
import mc.ultimatecore.alchemy.enums.EditorMode;
import mc.ultimatecore.alchemy.objects.AlchemyRecipe;
import mc.ultimatecore.alchemy.utils.InventoryUtils;
import mc.ultimatecore.alchemy.utils.Placeholder;
import mc.ultimatecore.alchemy.utils.StringUtils;
import mc.ultimatecore.alchemy.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RecipeEditorGUI implements NormalGUI, Listener {

    private final AlchemyRecipe alchemyRecipe;

    private Inventory inventory;

    private final Map<UUID, EditorMode> editingMode;

    public RecipeEditorGUI(AlchemyRecipe alchemyRecipe) {
        this.alchemyRecipe = alchemyRecipe;
        this.editingMode = new HashMap<>();
        setupInventory();
        HyperAlchemy.getInstance().registerListeners(this);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(!e.getInventory().equals(getInventory())) return;
        Player player = (Player) e.getPlayer();
        saveRecipe(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getClickedInventory().equals(getInventory())) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            UUID uuid = player.getUniqueId();
            if (e.getSlot() == HyperAlchemy.getInstance().getInventories().closeButton.slot) {
                player.closeInventory();
            } else if (e.getSlot() == 13 || e.getSlot() == 22 || e.getSlot() == 31) {
                if (e.getCursor() != null && !e.getCursor().getType().equals(Material.AIR)){
                    getInventory().setItem(e.getSlot(), e.getCursor());
                    e.setCursor(null);
                }
            }else if(e.getSlot() == 20){
                editingMode.put(uuid, EditorMode.TIME);
                player.closeInventory();
                player.sendMessage(StringUtils.color("&a► You are editing time for &f"+alchemyRecipe.getName()+"&a type &fstop &ato leave"));
            }else if(e.getSlot() == 24){
                editingMode.put(uuid, EditorMode.PERMISSION);
                player.closeInventory();
                player.sendMessage(StringUtils.color("&a► You are editing permission for &f"+alchemyRecipe.getName()+"&a type &fstop &ato leave"));
            } else if (e.getSlot() == 49) {
                player.closeInventory();
                saveRecipe(player);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if(editingMode.containsKey(player.getUniqueId())){
            UUID uuid = player.getUniqueId();
            e.setCancelled(true);
            String message = e.getMessage();
            if(message.contains("stop") || message.contains("leave")){
                editingMode.remove(uuid);
                Utils.openGUISync(player, getInventory());
                return;
            }
            if(message.contains(" ")){
                player.sendMessage(StringUtils.color("&cInvalid Characters!"));
                return;
            }
            if(editingMode.get(uuid) == EditorMode.PERMISSION){
                alchemyRecipe.setPermission(message);
            }else{
                try {
                    int time = Integer.parseInt(message);
                    alchemyRecipe.setTime(time);
                }catch (NumberFormatException exception){
                    player.sendMessage(StringUtils.color("&cInvalid Numbers!"));
                    return;
                }
            }
            editingMode.remove(uuid);
            Utils.openGUISync(player, getInventory());
        }

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        inventory.setItem(20, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().timeEditItem, Collections.singletonList(new Placeholder("time", String.valueOf(alchemyRecipe.getTime())))));
        inventory.setItem(24, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().permissionEditItem, Collections.singletonList(new Placeholder("permission", alchemyRecipe.getPermission() != null ? alchemyRecipe.getPermission() : "&cNONE"))));
        return this.inventory;
    }

    private void setupInventory(){
        Inventory inventory = Bukkit.createInventory(this, HyperAlchemy.getInstance().getInventories().mainMenuSize, StringUtils.color(HyperAlchemy.getInstance().getInventories().mainMenuTitle));
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().background));
        for(Integer slot : BrewingSlots.CREATOR_DECORATION_SLOTS.getSlots())
            inventory.setItem(slot, XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem());
        inventory.setItem(13, alchemyRecipe.getInputItem() == null ? InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().inputItem) : alchemyRecipe.getInputItem());
        inventory.setItem(22, alchemyRecipe.getFuelItem() == null ? InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().fuelItem) : alchemyRecipe.getFuelItem());
        inventory.setItem(31, alchemyRecipe.getOutputItem() == null ? InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().outPutItem) : alchemyRecipe.getOutputItem());

        inventory.setItem(49, InventoryUtils.makeItem(HyperAlchemy.getInstance().getInventories().saveRecipeItem));
        this.inventory = inventory;
    }

    private boolean saveRecipe(Player player){
        alchemyRecipe.setInputItem(getInventory().getItem(13));
        alchemyRecipe.setFuelItem(getInventory().getItem(22));
        alchemyRecipe.setOutputItem(getInventory().getItem(31));
        HyperAlchemy.getInstance().getBrewingRecipes().addRecipe(alchemyRecipe);
        if(!editingMode.containsKey(player.getUniqueId()))
            player.sendMessage(StringUtils.color("&a► You has saved successfully recipe " + alchemyRecipe.getName()));
        return true;
    }
}

