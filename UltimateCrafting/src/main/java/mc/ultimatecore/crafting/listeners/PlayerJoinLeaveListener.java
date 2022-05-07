package mc.ultimatecore.crafting.listeners;

import lombok.AllArgsConstructor;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.playerdata.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class PlayerJoinLeaveListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onLeave(PlayerKickEvent event) {
        try {
            final Player player = event.getPlayer();
            User user = this.plugin.getPlayerManager().createOrGetUser(player.getUniqueId());
            if(!player.getOpenInventory().getTopInventory().equals(user.getCraftingGUI().getInventory())) return;
            Inventory inventory = user.getCraftingGUI().getInventory();
            plugin.getInventories().craftingSlots.forEach(slot ->{
                if(inventory.getItem(slot) != null) player.getInventory().addItem(inventory.getItem(slot));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
