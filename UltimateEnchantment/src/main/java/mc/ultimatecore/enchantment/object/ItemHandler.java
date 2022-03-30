package mc.ultimatecore.enchantment.object;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

public class ItemHandler {
    private BukkitTask bukkitTask;

    public void start(Player player){
        /*if(bukkitTask != null) return;
        bukkitTask = (new BukkitRunnable() {
            public void run() {
                if(!check(player))
                    stop();
            }
        }).runTaskTimerAsynchronously(EnchantmentsPlugin.getInstance(), 0L, 20L);*/
    }

    private boolean check(Player player){
        if(player == null || !player.isOnline()) return false;
        ItemStack itemStack = player.getItemInHand();
        if(itemStack == null || itemStack.getType() == Material.AIR);
        Map<HyperEnchant, Integer> enchants = EnchantmentsPlugin.getInstance().getHyperEnchantments().getItemEnchantments(itemStack);
        if(enchants.size() < 1) return true;
        ItemStack newItem = itemStack.clone();
        for(HyperEnchant hyperEnchant : enchants.keySet())
            newItem = EnchantmentsPlugin.getInstance().getEnchantsHandler().addEnchantment(itemStack, enchants.get(hyperEnchant), false, hyperEnchant);
        player.setItemInHand(newItem);
        return true;
    }

    public void stop(){
        /*if(bukkitTask != null)
            bukkitTask.cancel();*/
    }
}
