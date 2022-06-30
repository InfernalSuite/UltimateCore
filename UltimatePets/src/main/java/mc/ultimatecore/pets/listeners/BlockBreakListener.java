package mc.ultimatecore.pets.listeners;

import lombok.AllArgsConstructor;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.playerdata.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@AllArgsConstructor
public class BlockBreakListener implements Listener {

    private final HyperPets plugin;

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if(e.isCancelled()) return;
        Player player = e.getPlayer();
        User user = plugin.getUserManager().getUser(player);
        if(user.getPlayerPet() == null) return;
        if(plugin.getConfiguration().blockBreakXP <= 0.0D) return;
        plugin.getPetsLeveller().addXP(player, plugin.getConfiguration().blockBreakXP);
    }




}
