package mc.ultimatecore.skills.listener.skills;

import lombok.AllArgsConstructor;
import mc.ultimatecore.farm.events.HyperBlockBreakEvent;
import mc.ultimatecore.skills.HyperSkills;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class HyperRegionListener implements Listener {
    
    private final HyperSkills plugin;
    
    @EventHandler
    public void onBreakHyper(HyperBlockBreakEvent e) {
        Block bl = e.getBlock();
        Material mat = bl.getType();
        Player player = e.getPlayer();
        try {
            if (bl.hasMetadata("COLLECTED")) return;
            plugin.getSkillManager().manageBlockPoints(player, bl, mat, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
