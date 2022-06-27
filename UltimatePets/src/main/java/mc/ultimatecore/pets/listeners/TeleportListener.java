package mc.ultimatecore.pets.listeners;


import lombok.*;
import mc.ultimatecore.pets.*;
import mc.ultimatecore.pets.managers.*;
import mc.ultimatecore.pets.objects.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerTeleportEvent;

@AllArgsConstructor
public class TeleportListener implements Listener {
    private final UserManager userManager;

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        var user = userManager.getUser(event.getPlayer().getUniqueId());

        // Ignore as data isn't loaded yet or pets aren't visible anyway
        if(user == null || user.isHidePets()) return;

        PlayerPet playerPet = user.getPlayerPet();

        // Ignore as player doesnt have a pet equipped
        if(playerPet == null) return;
        playerPet.removeStand();
        Bukkit.getScheduler().runTaskLater(HyperPets.getInstance(), playerPet::spawnStand, 3);
    }
}
