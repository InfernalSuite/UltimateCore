package mc.ultimatecore.helper;

import mc.ultimatecore.helper.objects.messages.ConsoleMessage;
import mc.ultimatecore.helper.objects.messages.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;


public class UltimatePlugin extends JavaPlugin {
    
    private static UltimatePlugin INSTANCE;
    
    @Override
    public void onLoad() {
        super.onLoad();
        INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Bukkit.getOnlinePlayers().forEach(Player::closeInventory);
    }
    
    
    protected void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
    
    public void sendConsoleMessage(String message, MessageType messageType) {
        new ConsoleMessage().sendMessage("&e[" + getDescription().getName() + "] " + message, messageType);
    }
    
    public static UltimatePlugin getInstance() {
        return INSTANCE;
    }
    
    public String getPluginName() {
        return getDescription().getName();
    }
}
