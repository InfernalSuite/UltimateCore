package com.infernalsuite.ultimatecore.talismans.listener;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.objects.PlayerTalismans;
import com.infernalsuite.ultimatecore.talismans.objects.TalismanType;
import com.infernalsuite.ultimatecore.talismans.objects.implementations.HealingTalisman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Optional;

public class HealthListener  implements Listener {

    @EventHandler
    public void onGainhHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            PlayerTalismans playerTalismans = HyperTalismans.getInstance().getTalismanManager().getPlayerTalismans().get(p.getUniqueId());
            if(playerTalismans == null) return;
            Optional<String> talisman = playerTalismans.hasTalisman(TalismanType.HEALING);
            if(!talisman.isPresent()) return;
            HealingTalisman healingTalisman = (HealingTalisman) HyperTalismans.getInstance().getTalismans().getTalismans().get(talisman.get());
            if(healingTalisman == null) return;
            double amount = e.getAmount();
            if(healingTalisman.getPercentage() < 1) return;
            double toAdd = (e.getAmount() * healingTalisman.getPercentage()) / 100;
            double newAmount = amount + toAdd;
            if(newAmount > p.getMaxHealth()) return;
            e.setAmount(amount + toAdd);
        }
    }
}
