package com.infernalsuite.ultimatecore.skills.managers;

import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.api.events.PlayerEnterEvent;
import com.infernalsuite.ultimatecore.skills.objects.abilities.MMOSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HealthManager implements Listener {
    private final Map<UUID, MMOSettings> players = new HashMap<>();

    public HealthManager(HyperSkills plugin) {
        plugin.registerListeners(this);
        loadAllPlayers();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerEnterEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        players.put(uuid, new MMOSettings(e.getPlayer()));
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!players.containsKey(uuid)) return;
        players.get(uuid).stop();
        players.remove(uuid);
    }

    @EventHandler
    private void onLeave(PlayerKickEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!players.containsKey(uuid)) return;
        players.get(uuid).stop();
        players.remove(uuid);
    }

    private void loadAllPlayers(){
        Bukkit.getOnlinePlayers().forEach(player -> players.put(player.getUniqueId(), new MMOSettings(player)));
    }
}
