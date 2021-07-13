package mc.ultimatecore.souls.listeners;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.api.events.AllSoulsFoundEvent;
import mc.ultimatecore.souls.api.events.FirstSoulFoundEvent;
import mc.ultimatecore.souls.api.events.SoulFoundEvent;
import mc.ultimatecore.souls.objects.PlayerSouls;
import mc.ultimatecore.souls.objects.Soul;
import mc.ultimatecore.souls.utils.StringUtils;
import mc.ultimatecore.souls.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@AllArgsConstructor
public class SoulCollectListener implements Listener {
    
    private final HyperSouls plugin;
    
    @EventHandler
    public void soulCollect(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock() == null) return;
        try {
            if (XMaterial.getVersion() != 8) {
                if (!e.getHand().equals(org.bukkit.inventory.EquipmentSlot.HAND)) return;
            }
        } catch (Exception ignored) {
        
        }
        Soul soul = plugin.getSouls().getSoulByLocation(e.getClickedBlock().getLocation());
        if (soul == null) return;
        Player player = e.getPlayer();
        PlayerSouls playerSouls = plugin.getDatabaseManager().getSoulsData(player);
        if (!playerSouls.hasSoul(soul.getId())) {
            if (playerSouls.getAmount() == 0) {
                Bukkit.getServer().getPluginManager().callEvent(new FirstSoulFoundEvent(player, soul));
                player.sendMessage(StringUtils.color(plugin.getMessages().getMessage("firstSoulFound")));
                Utils.playSound(player, plugin.getConfiguration().firstSoulFoundSound);
            }
            Bukkit.getServer().getPluginManager().callEvent(new SoulFoundEvent(player, soul));
            player.sendMessage(StringUtils.color(plugin.getMessages().getMessage("soulFound")));
            Utils.playSound(player, plugin.getConfiguration().soulFoundSound);
            playerSouls.addSoul(soul.getId());
            Utils.playParticle(player, soul);
            Utils.giveMoney(player, soul);
            soul.getCommandRewards().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
            if (playerSouls.getAmount() != HyperSouls.getInstance().getSouls().souls.size()) return;
            if (HyperSouls.getInstance().getConfiguration().allSoulsFound_Reward != null) {
                Bukkit.getServer().getPluginManager().callEvent(new AllSoulsFoundEvent(player, soul));
                HyperSouls.getInstance().getConfiguration().allSoulsFound_Reward.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
                player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("allSoulsFound")));
            }
        } else {
            player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("alreadyFound")));
        }
    }
}
