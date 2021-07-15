package mc.ultimatecore.talismans.listener;

import lombok.AllArgsConstructor;
import mc.ultimatecore.talismans.HyperTalismans;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerJoinLeaveListener implements Listener {
    private final HyperTalismans plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getUserManager().addIntoTable(e.getPlayer());
        plugin.getUserManager().loadPlayerData(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getUserManager().savePlayerData(e.getPlayer(), true, true);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        plugin.getUserManager().savePlayerData(e.getPlayer(), true, true);
    }

}
