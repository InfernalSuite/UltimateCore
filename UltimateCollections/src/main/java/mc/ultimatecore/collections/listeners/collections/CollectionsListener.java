package mc.ultimatecore.collections.listeners.collections;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import mc.ultimatecore.collections.*;
import mc.ultimatecore.collections.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
public class CollectionsListener implements Listener {

    private final HyperCollections plugin;

    private final HashMap<UUID, Long> countdown = new HashMap<>();

    @EventHandler
    public void onChest(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (e.getBlock().getState() instanceof org.bukkit.inventory.InventoryHolder) {
            this.countdown.put(p.getUniqueId(), System.currentTimeMillis() + 150L);
            setMetadataAround(p);
        }
        if (e.getBlock().hasMetadata(Constants.PLACED_BLOCK_KEY))
            setMetadataAround(p);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Location l = e.getEntity().getLocation();
            for (Entity ent : l.getWorld().getNearbyEntities(l, 5.0D, 5.0D, 5.0D)) {
                if (ent instanceof Player) {
                    Player k = (Player) ent;
                    this.countdown.put(k.getUniqueId(), System.currentTimeMillis() + 150L);
                }
            }
            setMetadataAround(e.getEntity());
        }
    }

    @EventHandler
    public void onFrame(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.ItemFrame)
            preventDupe(e.getEntity().getLocation());
    }

    @EventHandler
    public void onEnter(VehicleDestroyEvent e) {
        if (e.getAttacker() instanceof Player && e.getVehicle() instanceof org.bukkit.inventory.InventoryHolder)
            setMetadataAround(e.getVehicle());
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        Item bt = e.getItemDrop();
        setMetadata(bt);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.getBlock().setMetadata(Constants.PLACED_BLOCK_KEY, new FixedMetadataValue(this.plugin, "UUID"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickUp(PlayerPickupItemEvent e) {
        if (e.isCancelled()) return;
        ItemStack item = e.getItem().getItemStack();
        Player p = e.getPlayer();
        if (!checkHas(e.getItem())) return;
        if (this.countdown.getOrDefault(p.getUniqueId(), 0L) > System.currentTimeMillis()) {
            e.setCancelled(true);
            return;
        }
        this.countdown.remove(p.getUniqueId());
        String key = Utils.getKey(XMaterial.matchXMaterial(item).toString());
        if (plugin.getConfiguration().isDebug()) Bukkit.getConsoleSender().sendMessage(key);
        if (!plugin.getCollections().collectionExist(key)) return;
        Player player = e.getPlayer();
        plugin.getCollectionsManager().addXP(player, key, item.getAmount());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onExtractItem(FurnaceExtractEvent e) {
        String key = Utils.getKey(XMaterial.matchXMaterial(e.getItemType()).toString());
        if (!HyperCollections.getInstance().getCollections().collectionExist(key)) return;
        Player player = e.getPlayer();
        plugin.getCollectionsManager().addXP(player, key, e.getItemAmount());
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        preventDupe(e.getBlock().getLocation());
    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent e) {
        preventDupe(e.getBlock().getLocation());
    }

    private void preventDupe(Location block) {
        for (Entity entity : block.getWorld().getNearbyEntities(block, 3.0D, 3.0D, 3.0D)) {
            if (entity instanceof Player) {
                Player p = (Player) entity;
                this.countdown.put(p.getUniqueId(), System.currentTimeMillis() + 150L);
            }
        }
        setMetadataAround(block);
    }

    public void setMetadataAround(Entity p) {
        setMetadataAround(p.getLocation());
    }

    public void setMetadataAround(final Location l) {
        (new BukkitRunnable() {
            public void run() {
                for (Entity item : l.getWorld().getNearbyEntities(l, 5.0D, 5.0D, 5.0D)) {
                    if (item instanceof Item) {
                        Item i = (Item) item;
                        i.setMetadata(Constants.PLACED_BLOCK_KEY, new FixedMetadataValue(plugin, "UUID"));
                    }
                }
                cancel();
            }
        }).runTaskLater(this.plugin, 1L);
    }

    public void setMetadata(Item i) {
        i.setMetadata(Constants.PLACED_BLOCK_KEY, new FixedMetadataValue(plugin, "UUID"));
    }

    public boolean checkHas(Item i) {
        return !i.hasMetadata(Constants.PLACED_BLOCK_KEY);
    }

}
