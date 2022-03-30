package com.infernalsuite.ultimatecore.helper.objects.messages;

import com.infernalsuite.ultimatecore.helper.utils.StringUtils;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class ConsoleMessage {
    
    public void sendMessage(String message, MessageType messageType) {
        if (messageType == MessageType.COLORED)
            Bukkit.getConsoleSender().sendMessage(StringUtils.color(message));
        else if (messageType == MessageType.LOG)
            Bukkit.getLogger().log(Level.ALL, StringUtils.color(message));
        else
            Bukkit.getLogger().warning(StringUtils.color(message));
    }
}
