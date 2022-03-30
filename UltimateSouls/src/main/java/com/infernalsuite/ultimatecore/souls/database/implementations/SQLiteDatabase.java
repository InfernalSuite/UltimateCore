package com.infernalsuite.ultimatecore.souls.database.implementations;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.CompletableFuture;

public class SQLiteDatabase extends SQLDatabase {
    
    public SQLiteDatabase(HyperSouls plugin, Credentials credentials) {
        super(plugin, "Players");
        this.plugin.getLogger().info("Using SQLite (local) database.");
        this.connect(credentials);
    }
    
    @Override
    public CompletableFuture<Void> createTablesAsync() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            execute("CREATE TABLE IF NOT EXISTS " + tableName + "(UUID varchar(36) NOT NULL UNIQUE, souls text, exchanged bigint, primary key (UUID))");
            future.complete(null);
        });
        return future;
    }
    
    @Override
    public void addIntoPlayerDatabase(OfflinePlayer offlinePlayer) {
        this.execute("INSERT OR IGNORE INTO " + tableName + " VALUES(?,?,?)", offlinePlayer.getUniqueId().toString(), "", 0);
    }
}
