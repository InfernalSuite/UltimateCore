package com.infernalsuite.ultimatecore.dragon.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.structures.DragonAltar;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.dragon.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private final HyperDragons plugin;

    @EventHandler
    public void onUseTool(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof EnderCrystal)) return;
        ItemStack itemStack = e.getPlayer().getItemInHand();
        if(itemStack == null || itemStack.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(itemStack);
        if(!nbtItem.hasKey("dragonTool")) return;
        e.getRightClicked().remove();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack itemStack = player.getItemInHand();
        if (!Utils.itemIsKey(itemStack)) return;
        e.setCancelled(true);
        if(plugin.getDragonManager().isActive()){
            player.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("errorInProgress").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
            return;
        }
        if(plugin.getDragonManager().getEditorMode().contains(uuid)){
            player.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("errorEditor").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
            return;
        }
        manageAltars(player, e, itemStack);
    }

    private void manageAltars(Player p, PlayerInteractEvent e, ItemStack itemStack) {
        Block block = e.getClickedBlock();
        assert block != null;
        Optional<DragonAltar> dragonAltar = plugin.getDragonManager().getDragonStructure().getAltar(block.getLocation());
        if (!dragonAltar.isPresent()) return;
        if (dragonAltar.get().isInUse()) {
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("alreadyPlaced").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
            return;
        }
        if(itemStack.getAmount() == 1) {
            p.setItemInHand(null);
        }else {
            itemStack.setAmount(itemStack.getAmount() - 1);
            p.setItemInHand(itemStack);
        }
        dragonAltar.get().setInUse(true);
        int current = (int) plugin.getDragonManager().getDragonStructure().getAltars().stream().filter(DragonAltar::isInUse).count();
        int total = plugin.getDragonManager().getDragonStructure().getAltars().size();
        p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("placedEye")
                .replace("%current%", String.valueOf(current))
                .replace("%total%", String.valueOf(total))
                .replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        if(current < total) return;
        plugin.getDragonManager().getDragonStructure().getAltars().forEach(dragonAltar1 -> dragonAltar1.setInUse(false));
        p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("ritualCompleted")
                .replace("%current%", String.valueOf(current))
                .replace("%total%", String.valueOf(total))
                .replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        plugin.getDragonManager().start();

    }
}