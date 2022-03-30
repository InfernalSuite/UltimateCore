package com.infernalsuite.ultimatecore.collections.database.implementations;

import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SQLiteDatabase extends SQLDatabase {
    
    public SQLiteDatabase(HyperCollections plugin, Credentials credentials) {
        super(plugin);
        this.plugin.getLogger().info("Using SQLite (local) database.");
        this.connect(credentials);
    }
    
    @Override
    public void createTables() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> execute("CREATE TABLE IF NOT EXISTS " + COLLECTIONS_TABLE_NAME + "(UUID varchar(36) NOT NULL UNIQUE, Data LONGTEXT)"));
    }
    
    @Override
    public void addIntoDatabase(OfflinePlayer player) {
        this.execute("INSERT OR IGNORE INTO " + MySQLDatabase.COLLECTIONS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), null);
    }
}
