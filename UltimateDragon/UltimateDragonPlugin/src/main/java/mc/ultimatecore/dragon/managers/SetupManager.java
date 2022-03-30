package mc.ultimatecore.dragon.managers;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.inventories.GuardianGUI;
import mc.ultimatecore.dragon.objects.guardian.DragonGuardian;
import mc.ultimatecore.dragon.objects.structures.DragonAltar;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SetupManager implements Listener {

    private final Map<UUID, Map<DragonGuardian, EditType>> editSettings = new HashMap<>();

    private final HyperDragons plugin;

    public SetupManager(HyperDragons plugin){
        this.plugin = plugin;
        plugin.registerListeners(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!plugin.getDragonManager().getEditorMode().contains(uuid)) return;
        if(manageAltars(player, e)) e.setCancelled(true);
    }

    @EventHandler
    public void checkChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        Map<DragonGuardian, EditType> setupMode = editSettings.getOrDefault(uuid, null);
        if (setupMode != null && !setupMode.isEmpty()) {
            Optional<DragonGuardian> key = setupMode.keySet().stream().findFirst();
            EditType edit = setupMode.get(key.get());
            event.setCancelled(true);
            String message = event.getMessage();
            DragonGuardian ultimateItem = key.get();
            if(message.contains("leave") || message.contains("exit") || message.contains("stop")){
                openGUIAsync(player, ultimateItem);
            }else{
                try{
                    if (edit == EditType.HEALTH) {
                        double manaCost = Double.parseDouble(message);
                        ultimateItem.setHealth(manaCost);
                    }else if(edit == EditType.MOB) {
                        EntityType entityType = EntityType.valueOf(message);
                        entityType.name();
                        ultimateItem.setEntity(message);
                    }else if(edit == EditType.DISPLAYNAME) {
                        ultimateItem.setDisplayName(message);
                    }
                    editSettings.remove(uuid);
                    openGUIAsync(player, ultimateItem);
                } catch (NumberFormatException e) {
                    player.sendMessage(StringUtils.color(plugin.getMessages().getMessage("invalidNumber").replace("%prefix%", plugin.getConfiguration().prefix)));
                } catch (IllegalArgumentException e){
                    player.sendMessage(StringUtils.color("%prefix% &cInvalid Mob!".replace("%prefix%", plugin.getConfiguration().prefix)));
                }
            }

        }
    }

    public void openGUIAsync(Player player, DragonGuardian ultimateItem){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.openInventory(new GuardianGUI(ultimateItem).getInventory()),3L);
    }

    private boolean manageAltars(Player p, PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) return false;
        Location location = e.getClickedBlock().getLocation();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && plugin.getDragonManager().getDragonStructure().removeAltar(location))
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("altarRemoved").replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        else if(e.getAction() == Action.LEFT_CLICK_BLOCK && plugin.getDragonManager().getDragonStructure().addAltar(new DragonAltar(location, false)))
            p.sendMessage(StringUtils.color(HyperDragons.getInstance().getMessages().getMessage("altarSet").replace("%location%", Utils.getFormattedLocation(location)).replace("%prefix%", HyperDragons.getInstance().getConfiguration().prefix)));
        return true;
    }

    public void setSetupMode(UUID uuid, DragonGuardian key, EditType editType){
        editSettings.put(uuid, new HashMap<DragonGuardian, EditType>(){{
            put(key, editType);
        }});
    }

    public enum EditType{
        HEALTH,
        MOB,
        DISPLAYNAME
    }
}
