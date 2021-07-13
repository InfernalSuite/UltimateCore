package mc.ultimatecore.souls.listeners;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.objects.PlayerSouls;
import mc.ultimatecore.souls.objects.Soul;
import mc.ultimatecore.souls.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SoulRemoveListener implements Listener {
    
    @EventHandler
    public void soulPlace(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("souls.remove") || !player.isOp()) return;
        Block bl = e.getBlock();
        Location location = bl.getLocation();
        Soul soul = HyperSouls.getInstance().getSouls().getSoulByLocation(location);
        if (soul == null) return;
        int id = soul.getId();
        if (HyperSouls.getInstance().getSouls().removeSoul(soul)) {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                PlayerSouls playerSouls = HyperSouls.getInstance().getDatabaseManager().getSoulsData(player1);
                if (playerSouls.hasSoul(id))
                    playerSouls.removeSoul(id);
            }
            player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("succesfullyRemoved").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%soul_id%", String.valueOf(soul.getId()))));
        }
    }
    
}
