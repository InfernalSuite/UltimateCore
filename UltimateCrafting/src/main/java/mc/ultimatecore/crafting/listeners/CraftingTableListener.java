package mc.ultimatecore.crafting.listeners;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.playerdata.User;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@AllArgsConstructor
public class CraftingTableListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onOpenEnchantmentTable(PlayerInteractEvent event){
        if(!plugin.getConfiguration().setAsDefaultCraftingTable) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block bl = event.getClickedBlock();
        if(bl == null || !bl.getType().equals(XMaterial.CRAFTING_TABLE.parseMaterial())) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        User user = this.plugin.getPlayerManager().createOrGetUser(player.getUniqueId());
        user.getCraftingGUI().openInventory(player);
    }

}
