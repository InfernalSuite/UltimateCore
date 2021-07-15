package mc.ultimatecore.reforge.listeners;

import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try {
            final Player player = event.getPlayer();
            final User user = User.getUser(player);
            user.name = player.getName();
        } catch (Exception e) {
            HyperReforge.getInstance().sendErrorMessage(e);
        }
    }
}
