package mc.ultimatecore.crafting.listeners;

import lombok.RequiredArgsConstructor;
import mc.ultimatecore.crafting.HyperCrafting;
import mc.ultimatecore.crafting.api.events.HyperCraftEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class CommandListener implements Listener {

    private final HyperCrafting plugin;

    @EventHandler
    public void onBrew(HyperCraftEvent event){
        Player player = event.getPlayer();
        if(player == null) return;
        plugin.getCommands().commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
    }
}
