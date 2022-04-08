package mc.ultimatecore.helper;

import com.infernalsuite.ultimatecore.common.storage.*;
import lombok.*;
import mc.ultimatecore.helper.objects.messages.ConsoleMessage;
import mc.ultimatecore.helper.objects.messages.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class UltimatePlugin extends JavaPlugin {

    @Getter
    private Storage storage;
    
    @Override
    public void onLoad() {
        super.onLoad();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Bukkit.getOnlinePlayers().forEach(Player::closeInventory);
    }

    public boolean isPlayerOnline(UUID uuid) {
        Player player = getServer().getPlayer(uuid);
        return player != null && player.isOnline();
    }
    
    protected void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
    
    public void sendConsoleMessage(String message, MessageType messageType) {
        new ConsoleMessage().sendMessage("&e[" + getDescription().getName() + "] " + message, messageType);
    }
    
    public static UltimatePlugin getInstance() {
        return UltimatePlugin.getPlugin(UltimatePlugin.class);
    }
    
    public String getPluginName() {
        return getDescription().getName();
    }
}
