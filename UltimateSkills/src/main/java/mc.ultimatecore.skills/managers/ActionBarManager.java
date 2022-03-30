package mc.ultimatecore.skills.managers;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.api.events.PlayerEnterEvent;
import mc.ultimatecore.skills.objects.PlayerBar;
import mc.ultimatecore.skills.objects.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionBarManager implements Listener {
    private final Map<UUID, PlayerBar> actionBarCache = new HashMap<>();

    public ActionBarManager(HyperSkills plugin) {
        plugin.registerListeners(this);
        loadAllPlayers();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onJoin(PlayerEnterEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        actionBarCache.put(uuid, new PlayerBar(e.getPlayer()));
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!actionBarCache.containsKey(uuid)) return;
        actionBarCache.get(uuid).stop();
        actionBarCache.remove(uuid);
    }

    @EventHandler
    private void onLeave(PlayerKickEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!actionBarCache.containsKey(uuid)) return;
        actionBarCache.get(uuid).stop();
        actionBarCache.remove(uuid);
    }

    public void sendXPActionBar(Player player, SkillType skillType, double amount){
        UUID uuid = player.getUniqueId();
        if(!actionBarCache.containsKey(uuid)) return;
        actionBarCache.get(uuid).sendXPActionBar(player, skillType, amount);
    }

    private void loadAllPlayers(){
        Bukkit.getOnlinePlayers().forEach(player -> actionBarCache.put(player.getUniqueId(), new PlayerBar(player)));
    }
}
