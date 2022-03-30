package mc.ultimatecore.skills.listener;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.skills.HyperSkills;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if(player.isOp()) return;
        Block bl = e.getBlock();
        if (XMaterial.isNewVersion()) {
            if (bl.getBlockData() instanceof Ageable && !bl.getType().toString().contains("SUGAR_CANE"))
                return;
        } else {
            if (bl.getState().getData() instanceof Crops)
                return;
            if (bl.getState().getData() instanceof NetherWarts)
                return;
        }
        bl.setMetadata("COLLECTED", new FixedMetadataValue(HyperSkills.getInstance(), "UUID"));
    }

    @EventHandler
    public void onRunePlace(BlockPlaceEvent e){
        ItemStack itemStack = e.getItemInHand();
        if(itemStack.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return;
        NBTItem nbtItem = new NBTItem(itemStack);
        if(nbtItem.hasKey("runeLevel"))
            e.setCancelled(true);
    }
}
