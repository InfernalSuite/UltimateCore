package mc.ultimatecore.enchantment.managers;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.Item;
import mc.ultimatecore.enchantment.api.events.HyperEnchantEvent;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.enchantment.enchantments.hooks.HyperAdvancedEnchantment;
import mc.ultimatecore.enchantment.enums.EnchantState;
import mc.ultimatecore.enchantment.object.EnchantObject;
import mc.ultimatecore.enchantment.utils.InventoryUtils;
import mc.ultimatecore.enchantment.utils.Placeholder;
import mc.ultimatecore.enchantment.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class GUIManager {

    private final Inventory inventory;

    @Getter @Setter
    private ItemStack toEnchantItem;

    private EnchantState enchantState;

    private final EnchantmentsPlugin plugin;

    @Getter
    private final Map<Integer, EnchantObject> enchantsMap;
    @Getter
    private final int bokShelfPower;
    @Getter @Setter
    private int booksPage;

    private final BukkitScheduler tasks;

    public GUIManager(Inventory inventory, int bokShelfPower){
        this.inventory = inventory;
        this.plugin = EnchantmentsPlugin.getInstance();
        this.enchantsMap = new HashMap<>();
        this.booksPage = 1;
        this.tasks = Bukkit.getScheduler();
        this.bokShelfPower = bokShelfPower;
    }

    public void checkEnchantmentTable(){
        //if(fusing) return;
        clearFirstPage();
        setupFirstPageItems();
        tasks.runTask(plugin, () -> {
                toEnchantItem = inventory.getItem(19);
                if(toEnchantItem != null && !toEnchantItem.getType().equals(Material.AIR)){
                    enchantState = Utils.manageEnchant(this);
                    clearFirstPage();
                    if(enchantState == EnchantState.READY_TO_ENCHANT)
                        setFirstPageBooks();
                    else
                        tasks.runTask(plugin, () -> inventory.setItem(23, InventoryUtils.makeItemHidden(plugin.getInventories().errorItem, enchantState)));
                }else{
                    clearFirstPage();
                    setupFirstPageItems();
                }
        });

    }

    private void setupFirstPageItems(){
        setItemSync(23, plugin.getInventories().emptyItem);
        setItemSync(plugin.getInventories().bookShelfItem.slot, plugin.getInventories().bookShelfItem, Collections.singletonList(new Placeholder("bookshelf_power", String.valueOf(bokShelfPower))));
        setItemSync(plugin.getInventories().enchantingTable.slot, plugin.getInventories().enchantingTable, Collections.singletonList(new Placeholder("bookshelf_power", String.valueOf(bokShelfPower))));
    }

    private void setFirstPageBooks(){
        List<HyperEnchant> hyperEnchants = plugin.getHyperEnchantments().getAvailableEnchantments(toEnchantItem);
        int slotsSize = plugin.getInventories().bookSlots.size();
        int slot = 0;
        int i = slotsSize * (this.booksPage - 1);
        if (hyperEnchants.size() > 0) {
            while (slot < slotsSize) {
                if (hyperEnchants.size() > i && i >= 0) {
                    HyperEnchant hyperEnchant = hyperEnchants.get(i);
                    int finalSlot = plugin.getInventories().bookSlots.get(slot);
                    if (bokShelfPower >= hyperEnchant.getRequiredBookShelf()) {
                        tasks.runTask(plugin, () -> inventory.setItem(finalSlot, InventoryUtils.makeItemHidden(plugin.getInventories().availableBook, Utils.getEnchantPlaceholders(hyperEnchant), hyperEnchant)));
                    } else {
                        tasks.runTask(plugin, () -> inventory.setItem(finalSlot, InventoryUtils.makeItemHidden(plugin.getInventories().notAvailableBook, Utils.getEnchantPlaceholders(hyperEnchant), hyperEnchant)));
                    }
                    enchantsMap.put(finalSlot, new EnchantObject(hyperEnchant));
                    slot++;
                    i++;
                    continue;
                }
                slot++;
            }
        }
        if (this.booksPage > 1)
            setItemSync(plugin.getInventories().backPageButton.slot, plugin.getInventories().backPageButton, Collections.singletonList(new Placeholder("previous_page", String.valueOf(booksPage - 1))));
        if (hyperEnchants.size() > slotsSize * booksPage)
            setItemSync(plugin.getInventories().nextPageButton.slot, plugin.getInventories().nextPageButton, Collections.singletonList(new Placeholder("next_page", String.valueOf(booksPage + 1))));
    }

    private void clearFirstPage() {
        ItemStack background = InventoryUtils.makeItemHidden(plugin.getInventories().background);
        //CLEARING BOOKS
        enchantsMap.forEach((slot, enchant) -> tasks.runTask(plugin, () -> inventory.setItem(slot, background)));
        //CLEARING BACK, NEXT BUTTON AND GRAY
        tasks.runTask(plugin, () -> inventory.setItem(plugin.getInventories().backPageButton.slot, background));
        tasks.runTask(plugin, () -> inventory.setItem(plugin.getInventories().nextPageButton.slot, background));
        tasks.runTask(plugin, () -> inventory.setItem(23, background));
        //CLEARING STORED ENCHANTS
        enchantsMap.clear();
    }

    public ItemStack enchantItem(Player player, ItemStack itemStack, EnchantObject enchantObject){
        if(enchantObject == null) return null;
        HyperEnchant hyperEnchant = enchantObject.getHyperEnchant();
        Optional<Enchantment> enchantment = hyperEnchant.hasEnchantmentConflicts(itemStack);
        if(!EnchantmentsPlugin.getInstance().getConfiguration().byPassEnchantmentRestrictions){
            if(enchantment.isPresent() && !plugin.getConfiguration().removeIncompatibleEnchants){
                player.sendMessage(Utils.color(plugin.getMessages().getMessage("incompatibleEnchantment").replace("%prefix%", plugin.getConfiguration().prefix)));
                Utils.playSound(player, "ENTITY_VILLAGER_NO");
                return null;
            }
        }
        int level = enchantObject.getLevel();
        if(!hyperEnchant.isUseMoney()){
            boolean isEnchanted = hyperEnchant.itemIsEnchanted(itemStack, level);
            int required = hyperEnchant.getRequiredLevel(level);
            int itemLevel = hyperEnchant.getItemLevel(itemStack);
            required = !isEnchanted ? itemLevel > 0 ? Utils.getNewRequiredLevel(required) : required : required;
            //required = itemLevel > 0 ? Utils.getNewRequiredLevel(required) : required;
            if(player.getLevel() >= required){
                if(level > itemLevel){
                    itemStack = plugin.getEnchantsHandler().addEnchantment(itemStack, level, false, hyperEnchant);
                    player.setLevel(player.getLevel() - required);
                    Utils.playSound(player, "ENTITY_EXPERIENCE_ORB_PICKUP");
                    Bukkit.getServer().getPluginManager().callEvent(new HyperEnchantEvent(player, required, 0, hyperEnchant, level, itemStack, hyperEnchant instanceof HyperAdvancedEnchantment));
                    return itemStack;
                }else{
                    player.sendMessage(Utils.color(plugin.getMessages().getMessage("alreadyState").replace("%prefix%", plugin.getConfiguration().prefix)));
                    Utils.playSound(player, "ENTITY_VILLAGER_NO");
                }
            }else{
                player.sendMessage(Utils.color(plugin.getMessages().getMessage("notEnoughState").replace("%prefix%", plugin.getConfiguration().prefix)));
                Utils.playSound(player, "ENTITY_VILLAGER_NO");
            }
        }else{
            try {
                Economy eco = EnchantmentsPlugin.getInstance().getEconomy();
                double currentMoney = eco.getBalance(player);
                double requiredMoney = hyperEnchant.getRequiredMoney(level);
                if(currentMoney >= requiredMoney){
                    itemStack = plugin.getEnchantsHandler().addEnchantment(itemStack, level, false, hyperEnchant);
                    eco.withdrawPlayer(player, requiredMoney);
                    Utils.playSound(player, "ENTITY_EXPERIENCE_ORB_PICKUP");
                    Bukkit.getServer().getPluginManager().callEvent(new HyperEnchantEvent(player, 0, requiredMoney, hyperEnchant, level, itemStack, hyperEnchant instanceof HyperAdvancedEnchantment));
                    return itemStack;
                }else{
                    player.sendMessage(Utils.color(plugin.getMessages().getMessage("notEnoughMoney").replace("%prefix%", plugin.getConfiguration().prefix)));
                    Utils.playSound(player, "ENTITY_VILLAGER_NO");
                }
            }catch (Exception e){
                Bukkit.getConsoleSender().sendMessage(Utils.color("&c[HyperEnchantments] Economy Plugin wasn't found"));
            }
        }
        return null;
    }

    private void setItemSync(int slot, Item item, List<Placeholder> placeholders){
        tasks.runTask(plugin, () -> inventory.setItem(slot, InventoryUtils.makeItem(item, placeholders)));
    }

    private void setItemSync(int slot, Item item){
        tasks.runTask(plugin, () -> inventory.setItem(slot, InventoryUtils.makeItem(item)));
    }
}
