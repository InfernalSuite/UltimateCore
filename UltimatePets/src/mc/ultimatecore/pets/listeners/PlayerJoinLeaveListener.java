package mc.ultimatecore.pets.listeners;

import lombok.AllArgsConstructor;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.objects.PlayerPet;
import mc.ultimatecore.pets.playerdata.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerJoinLeaveListener implements Listener {

    private final HyperPets plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try {
            plugin.getUserManager().addIntoTable(event.getPlayer());
            plugin.getUserManager().loadPlayerData(event.getPlayer());

        } catch (Exception e) {
            plugin.sendErrorMessage(e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        try {
            final Player player = event.getPlayer();
            final User user = plugin.getUserManager().getUser(player);
            PlayerPet playerPet = user.getPlayerPet();
            if(playerPet != null){
                plugin.getPetsManager().savePetData(playerPet.getPetData().getPetUUID(), true);
                playerPet.removePet(true);
            }
            plugin.getUserManager().savePlayerData(event.getPlayer());
        } catch (Exception e) {
            plugin.sendErrorMessage(e);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        try {
            final Player player = event.getPlayer();
            final User user = plugin.getUserManager().getUser(player);
            PlayerPet playerPet = user.getPlayerPet();
            if(playerPet != null){
                plugin.getPetsManager().savePetData(playerPet.getPetData().getPetUUID(), true);
                playerPet.removePet(true);
            }
            plugin.getUserManager().savePlayerData(event.getPlayer());
        } catch (Exception e) {
            plugin.sendErrorMessage(e);
        }
    }
}
