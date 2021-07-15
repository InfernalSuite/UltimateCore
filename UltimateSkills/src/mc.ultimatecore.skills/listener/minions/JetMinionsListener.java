package mc.ultimatecore.skills.listener.minions;

import lombok.AllArgsConstructor;
import mc.ultimatecore.skills.HyperSkills;
import me.jet315.minions.events.MinerBlockBreakEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class JetMinionsListener implements Listener {

    private final HyperSkills plugin;

    @EventHandler(priority = EventPriority.LOW)
    public void jetMinionsEvent(MinerBlockBreakEvent e) {
        if (e.getMinion() == null || e.getMinion().getPlayer() == null || e.getBlock() == null)
            return;
        Player player = e.getMinion().getPlayer();
        for (ItemStack item : e.getBlock().getDrops()) {
            if(item == null || item.getType() == Material.AIR) continue;
            plugin.getSkillManager().manageBlockPoints(player, e.getBlock(), item.getType(), false);
        }
    }
}
