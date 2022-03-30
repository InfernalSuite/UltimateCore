package mc.ultimatecore.skills.listener.perks;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.Random;

public class EnchantingPerks implements Listener {
    @EventHandler
    public void playerPickUpOrb(PlayerExpChangeEvent e) {
        if(e.getAmount() > 0) {
            duplyReward(e.getPlayer(), e.getAmount());
        }
        return;
    }

    private void duplyReward(Player p, int oldXP) {
        int r = new Random().nextInt(100);
        int percentage = (int) HyperSkills.getInstance().getApi().getTotalPerk(p.getUniqueId(), Perk.Exp_Chance);
        if (r < percentage)
            p.giveExp((percentage * oldXP) / 100);
    }

}
