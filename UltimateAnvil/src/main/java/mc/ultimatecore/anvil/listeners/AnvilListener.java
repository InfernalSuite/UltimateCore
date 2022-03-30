package mc.ultimatecore.anvil.listeners;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.managers.User;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AnvilListener implements Listener {
    @EventHandler
    public void onOpenEnchantmentTable(PlayerInteractEvent e) {
        if (!HyperAnvil.getInstance().getConfiguration().setAsDefaultAnvil) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block bl = e.getClickedBlock();
        if (bl == null || (!bl.getType().equals(XMaterial.ANVIL.parseMaterial()) && !bl.getType().equals(XMaterial.DAMAGED_ANVIL.parseMaterial()) && !bl.getType().equals(XMaterial.CHIPPED_ANVIL.parseMaterial())))
            return;
        e.setCancelled(true);
        Player p = e.getPlayer();
        User user = User.getUser(p);
        p.openInventory(user.getAnvilGUI().getInventory());
    }

}
