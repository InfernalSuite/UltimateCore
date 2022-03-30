package com.infernalsuite.ultimatecore.skills.listener.skills;

import com.infernalsuite.ultimatecore.skills.objects.perks.Perk;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
public class BlockBreakListener implements Listener {

    private final HyperSkills plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void miningSpeed(PlayerInteractEvent e) {
        if(e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Block bl = e.getClickedBlock();
        Player player = e.getPlayer();
        if(bl == null) return;
        if (bl.hasMetadata("COLLECTED")) return;
        if(e.isCancelled()) return;
        double miningSpeed = plugin.getApi().getTotalPerk(player.getUniqueId(), Perk.Mining_Speed);
        if(miningSpeed < 1) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, Math.max((int) (miningSpeed * 0.002645), 1)));

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreakSkills(BlockBreakEvent e) {
        Block bl = e.getBlock();
        Player player = e.getPlayer();
        try {
            if (bl.hasMetadata("COLLECTED")) return;
            if(e.isCancelled()) return;
            Material mat = bl.getType();
            plugin.getSkillManager().manageBlockPoints(player, bl, mat, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void cutSheep(PlayerShearEntityEvent e) {
        if (e.getEntity() instanceof Sheep) plugin.getSkillManager().addXP(e.getPlayer().getUniqueId(), SkillType.Farming, plugin.getSkillPoints().farming_Cut_Wool);
    }
}
