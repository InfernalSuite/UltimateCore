package mc.ultimatecore.anvil.listeners;

import mc.ultimatecore.anvil.HyperAnvil;
import mc.ultimatecore.anvil.managers.User;
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
            HyperAnvil.getInstance().sendErrorMessage(e);
        }
    }
}
