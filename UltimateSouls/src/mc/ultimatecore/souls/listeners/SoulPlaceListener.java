package mc.ultimatecore.souls.listeners;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.objects.Soul;
import mc.ultimatecore.souls.utils.StringUtils;
import mc.ultimatecore.souls.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SoulPlaceListener implements Listener {
    
    @EventHandler
    public void soulPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("souls.place") && !player.isOp()) return;
        Block bl = e.getBlock();
        ItemStack itemStack = e.getItemInHand();
        if (!Utils.hasName(itemStack, HyperSouls.getInstance().getMessages().getMessage("soulToPlaceName"))) return;
        Location location = bl.getLocation();
        if (HyperSouls.getInstance().getSouls().getSoulByLocation(location) != null) return;
        int id = HyperSouls.getInstance().getSouls().getNextID();
        Soul soul = new Soul(id, location, new ArrayList<>(), 0, null);
        HyperSouls.getInstance().getSouls().souls.put(location, soul);
        player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("soulPlaced")
                                                       .replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)
                                                       .replace("%soul_id%", String.valueOf(id))));
    }
    
    
}
