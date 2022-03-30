package mc.ultimatecore.farm.listeners;

import lombok.AllArgsConstructor;
import mc.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.events.HyperBlockBreakEvent;
import mc.ultimatecore.farm.guardians.Guardian;
import mc.ultimatecore.farm.objects.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CropBreakListener implements Listener {

    private final HyperRegions plugin;

    @EventHandler(priority = EventPriority.LOW)
    public void onCropBreakLow(BlockBreakEvent e) {
        if (plugin.getConfiguration().priorityLevel == 1) execute(e);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCropBreakNormal(BlockBreakEvent e) {
        if (plugin.getConfiguration().priorityLevel == 2) execute(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCropBreakHigh(BlockBreakEvent e) {
        if (plugin.getConfiguration().priorityLevel == 3) execute(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCropBreakHighest(BlockBreakEvent e) {
        if (plugin.getConfiguration().priorityLevel == 4) execute(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCropBreakMonitor(BlockBreakEvent e) {
        if (plugin.getConfiguration().priorityLevel == 5) execute(e);
    }

    private void execute(BlockBreakEvent e) {
        if (e.isCancelled() && !plugin.getConfiguration().byPassWorldGuard) return;
        Block bl = e.getBlock();
        Material material = bl.getType();
        String key = material.toString().toUpperCase();
        WrappedBlockData wrappedBlockData = new WrappedBlockData(bl);
        if (plugin.getConfiguration().debug) Bukkit.getConsoleSender().sendMessage("Material: " + key + " | Id: " + wrappedBlockData.getLegacyData());
        if (plugin.getFarmManager().regenBlock(bl, key, wrappedBlockData)) {
            HyperBlockBreakEvent event = new HyperBlockBreakEvent(e.getPlayer(), bl);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (e.isCancelled()) {
                if (!plugin.getConfiguration().byPassWorldGuard) return;
                e.setCancelled(false);
                e.getBlock().breakNaturally();
            }
        }
    }

    @EventHandler
    public void armorInteract(PlayerArmorStandManipulateEvent e) {
        String name = e.getRightClicked().getCustomName();
        if (name == null) return;
        if (name.contains("guardian_"))
            e.setCancelled(true);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        plugin.getGuardians().getGuardians().stream()
                .filter(Guardian::isNotEnabled)
                .forEach(Guardian::enable);
    }

    @EventHandler
    public void unload(ChunkUnloadEvent e) {
        List<String> names = Arrays.stream(e.getChunk().getEntities())
                .filter(entity -> entity instanceof ArmorStand)
                .filter(entity -> entity.getName() != null && entity.getName().contains("guardian_"))
                .map(Entity::getName)
                .collect(Collectors.toList());
        if (names.isEmpty()) return;
        names.forEach(name -> plugin.getGuardians().getGuardian(name.replace("guardian_", "")).ifPresent(Guardian::remove));
    }
}
