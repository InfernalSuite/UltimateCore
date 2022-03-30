package com.infernalsuite.ultimatecore.skills.managers;

import com.infernalsuite.ultimatecore.skills.api.events.PlayerEnterEvent;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.abilities.PlayerSpeed;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpeedManager implements Listener {
    private final Map<UUID, PlayerSpeed> players = new HashMap<>();

    public SpeedManager(HyperSkills plugin) {
        plugin.registerListeners(this);
        loadAllPlayers();
    }

    @EventHandler
    private void onJoin(PlayerEnterEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        players.put(uuid, new PlayerSpeed(e.getPlayer()));
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
        Bukkit.getOnlinePlayers().forEach(player -> players.put(player.getUniqueId(), new PlayerSpeed(player)));
    }
}
