package mc.ultimatecore.skills.listener;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.*;
import mc.ultimatecore.helper.utils.*;
import mc.ultimatecore.skills.*;
import mc.ultimatecore.skills.configs.*;
import org.bukkit.*;
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

@Data
public class BlockPlaceListener implements Listener {

    private final Config config;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

        Player player = e.getPlayer();
        boolean needsCreative = config.isByPassPlaceRequireCreative();
        String permission = config.getByPassPlaceCheckPermission();
        if(player.hasPermission(permission) && (!needsCreative || player.getGameMode() == GameMode.CREATIVE)) return;

        Block bl = e.getBlock();

        if(!BlockUtils.needsPlacementTagging(bl)) return;

        bl.setMetadata(Constants.PLACED_BLOCK_KEY, new FixedMetadataValue(HyperSkills.getInstance(), "UUID"));
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
