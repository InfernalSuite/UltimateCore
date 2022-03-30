package mc.ultimatecore.souls.gui;

import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.objects.Soul;
import mc.ultimatecore.souls.utils.InventoryUtils;
import mc.ultimatecore.souls.utils.Placeholder;
import mc.ultimatecore.souls.utils.StringUtils;
import mc.ultimatecore.souls.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;
import java.util.Optional;

public class AddCommandGUI extends GUI implements Listener {
    
    private int soulID;
    
    private Player player;
    
    public AddCommandGUI() {
        super(27, HyperSouls.getInstance().getInventories().addMoneyGUITitle);
        HyperSouls.getInstance().registerListeners(this);
        this.soulID = 0;
    }
    
    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
        if (soulID != -1 && !soul.isPresent()) return;
        if (soulID == -1) {
            setItem(0, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().commandGUIaddCommand));
        } else {
            int slot = 0;
            for (String command : soul.get().getCommandRewards()) {
                setItem(slot, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().commandGUIcurrentCommand,
                        Collections.singletonList(new Placeholder("command", command))));
                slot++;
            }
            setItem(soul.get().getCommandRewards().size(), InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().commandGUIaddCommand));
        }
        setItem(21, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().previousPage));
        setItem(22, InventoryUtils.makeItem(HyperSouls.getInstance().getInventories().closeButton));
        
        
    }
    
    public void changeSettings(Player player, int newID) {
        this.player = player;
        this.soulID = newID;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                if (e.getSlot() == 21) {
                    player.closeInventory();
                    player.openInventory(HyperSouls.getInstance().getSoulEditGUI().getInventory());
                } else if (e.getSlot() == 22) {
                    player.closeInventory();
                } else {
                    if (soulID == -1) {
                        if (e.getSlot() == 0) {
                            player.closeInventory();
                            player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("editorMode").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                            this.player = player;
                        }
                    } else {
                        Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
                        if (!soul.isPresent()) return;
                        if (e.getSlot() == soul.get().getCommandRewards().size()) {
                            player.closeInventory();
                            player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("editorMode").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix)));
                            this.player = player;
                        } else if (e.getSlot() < soul.get().getCommandRewards().size()) {
                            if (e.getClick() == ClickType.RIGHT) {
                                soul.get().getCommandRewards().remove(e.getSlot());
                                getInventory().clear();
                            }
                        }
                    }
                }
                
            }
        }
    }
    
    @EventHandler
    public void checkChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (this.player != null && player == this.player) {
            event.setCancelled(true);
            String message = event.getMessage();
            if (message.contains("leave") || message.contains("exit")) {
                this.player = null;
                Utils.openGUIAsync(player, getInventory());
            } else {
                if (soulID == -1) {
                    for (Soul soul : HyperSouls.getInstance().getSouls().souls.values())
                        soul.addCommandReward(message);
                    player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("addedCommand").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%command%", message)));
                } else {
                    Optional<Soul> soul = HyperSouls.getInstance().getSouls().getSoulByID(soulID);
                    soul.ifPresent(value -> value.addCommandReward(message));
                    player.sendMessage(StringUtils.color(HyperSouls.getInstance().getMessages().getMessage("addedCommand").replace("%prefix%", HyperSouls.getInstance().getConfiguration().prefix).replace("%command%", message)));
                }
                Utils.openGUIAsync(player, getInventory());
                this.player = null;
            }
            this.player = null;
        }
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (event.getPlayer() == this.player) this.player = null;
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerKickEvent event) {
        if (event.getPlayer() == this.player) this.player = null;
    }
    
    
}
