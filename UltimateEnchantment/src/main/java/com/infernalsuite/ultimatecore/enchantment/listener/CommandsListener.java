package com.infernalsuite.ultimatecore.enchantment.listener;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.enchantment.EnchantmentsPlugin;
import com.infernalsuite.ultimatecore.enchantment.api.events.HyperEnchantEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class CommandsListener implements Listener {

    private final EnchantmentsPlugin plugin;

    @EventHandler
    public void onBrew(HyperEnchantEvent event){
        Player player = event.getPlayer();
        if(player == null) return;
        plugin.getCommands().commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
    }
}
