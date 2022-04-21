package mc.ultimatecore.helper;

import lombok.*;
import mc.ultimatecore.helper.objects.messages.ConsoleMessage;
import mc.ultimatecore.helper.objects.messages.MessageType;
import mc.ultimatecore.helper.v1_8_R3.*;
import mc.ultimatecore.helper.v1_18_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.*;
import java.util.Arrays;

public class UltimatePlugin extends JavaPlugin {

    @Getter
    private VersionHook versionHook;
    
    @Override
    public void onLoad() {
        super.onLoad();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if(Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null
                || Bukkit.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") != null
                || Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit") != null) {
            this.versionHook = setupVersionHook();
        }
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
        return UltimatePlugin.getPlugin(UltimatePlugin.class);
    }
    
    public String getPluginName() {
        return getDescription().getName();
    }

    private VersionHook setupVersionHook() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return (VersionHook) Class.forName("mc.ultimatecore.helper." + version + ".VersionHookImpl").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return null;
        }
    }
}
