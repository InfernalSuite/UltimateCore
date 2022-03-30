package com.infernalsuite.ultimatecore.talismans.managers;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.objects.InventoryTalisman;
import com.infernalsuite.ultimatecore.talismans.objects.PlayerTalismans;
import lombok.Getter;
import com.infernalsuite.ultimatecore.talismans.api.events.PlayerEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class TalismanManager implements Listener {
    @Getter
    private final Map<UUID, PlayerTalismans> playerTalismans = new HashMap<>();
    private final Map<UUID, InventoryTalisman> talismans = new HashMap<>();
    @Getter
    private final Set<UUID> usingRegion = new HashSet<>();

    public TalismanManager(HyperTalismans plugin) {
        plugin.registerListeners(this);
        loadAllPlayers();
    }

    @EventHandler
    private void onJoin(PlayerEnterEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        playerTalismans.put(uuid, new PlayerTalismans(e.getPlayer()));
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!playerTalismans.containsKey(uuid)) return;
        playerTalismans.get(uuid).stop();
        playerTalismans.remove(uuid);
    }

    @EventHandler
    private void onLeave(PlayerKickEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!playerTalismans.containsKey(uuid)) return;
        playerTalismans.get(uuid).stop();
        playerTalismans.remove(uuid);
    }

    private void loadAllPlayers(){
        Bukkit.getOnlinePlayers().forEach(player -> playerTalismans.put(player.getUniqueId(), new PlayerTalismans(player)));
    }

    public InventoryTalisman getTalisman(UUID uuid){
        if(talismans.containsKey(uuid))
            return talismans.get(uuid);
        return null;
    }

    public void register(UUID uuid, InventoryTalisman inventoryTalisman){
        if(!talismans.containsKey(uuid))
            talismans.put(uuid, inventoryTalisman);
    }
}
