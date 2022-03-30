package com.infernalsuite.ultimatecore.souls.database;

import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.implementations.DatabaseImpl;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.CompletableFuture;

public abstract class Database extends DatabaseImpl {
    
    public Database(UltimatePlugin plugin) {
        super(plugin);
    }
    
    //PET METHODS
    public abstract CompletableFuture<Void> createTablesAsync();
    
    //PLAYER METHODS
    
    public abstract void addIntoPlayerDatabase(OfflinePlayer offlinePlayer);
    
    public abstract void setSouls(OfflinePlayer offlinePlayer, String playerSouls);
    
    public abstract String getPlayerSouls(OfflinePlayer offlinePlayer);
    
    public abstract void setExchanged(OfflinePlayer offlinePlayer, int exchanged);
    
    public abstract int getExchanged(OfflinePlayer offlinePlayer);
    
}
