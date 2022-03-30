package com.infernalsuite.ultimatecore.souls.database;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public abstract class SQLDatabase extends Database {
    
    public final String tableName;
    public final String columnName;
    
    protected HyperSouls plugin;
    
    public SQLDatabase(HyperSouls plugin, String tableName) {
        super(plugin);
        this.plugin = plugin;
        this.tableName = tableName;
        this.columnName = "UUID";
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
    
    //PLAYER METHODS
    
    @Override
    public void addIntoPlayerDatabase(OfflinePlayer offlinePlayer) {
        this.execute("INSERT IGNORE INTO " + tableName + " VALUES(?,?,?)", offlinePlayer.getUniqueId().toString(), "", 0);
    }
    
    @Override
    public String getPlayerSouls(OfflinePlayer offlinePlayer) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + tableName + " WHERE " + columnName + "=?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next())
                    return set.getString("souls");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @Override
    public void setSouls(OfflinePlayer offlinePlayer, String playerSouls) {
        this.execute("UPDATE " + tableName + " SET souls =? WHERE " + columnName + "=?", playerSouls, offlinePlayer.getUniqueId().toString());
    }
    
    @Override
    public int getExchanged(OfflinePlayer offlinePlayer) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + tableName + " WHERE " + columnName + "=?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next())
                    return set.getInt("exchanged");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public void setExchanged(OfflinePlayer offlinePlayer, int exchanged) {
        this.execute("UPDATE " + tableName + " SET exchanged =? WHERE " + columnName + "=?", exchanged, offlinePlayer.getUniqueId().toString());
    }
    
    @Override
    public void createTables() {
    
    }
}
