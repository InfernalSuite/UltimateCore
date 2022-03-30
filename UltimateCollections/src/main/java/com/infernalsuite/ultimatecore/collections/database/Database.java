package com.infernalsuite.ultimatecore.collections.database;

import com.infernalsuite.ultimatecore.collections.objects.PlayerCollections;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.implementations.DatabaseImpl;
import org.bukkit.OfflinePlayer;

import java.util.Set;
import java.util.UUID;

public abstract class Database extends DatabaseImpl {
    
    public Database(UltimatePlugin plugin) {
        super(plugin);
    }
    
    public abstract Set<UUID> getAllPlayers();
    
    public abstract String getPlayerCollections(OfflinePlayer player);
    
    public abstract void savePlayerCollections(OfflinePlayer player, PlayerCollections playerCollections);
    
    public abstract void addIntoDatabase(OfflinePlayer player);
    
}
