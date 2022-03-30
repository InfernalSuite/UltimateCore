package com.infernalsuite.ultimatecore.collections.database;

import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.database.implementations.MySQLDatabase;
import com.infernalsuite.ultimatecore.collections.objects.PlayerCollections;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class SQLDatabase extends Database {
    
    public static final String COLLECTIONS_TABLE_NAME = "HyperCollections";
    public static final String COLLECTIONS_UUID_COLNAME = "UUID";
    
    public static final String[] ALL_TABLES = new String[]{
            COLLECTIONS_TABLE_NAME,
    };
    
    protected HyperCollections plugin;
    
    public SQLDatabase(HyperCollections plugin) {
        super(plugin);
        this.plugin = plugin;
    }
    
    @Override
    public void createTables() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> execute("CREATE TABLE IF NOT EXISTS " + COLLECTIONS_TABLE_NAME + "(UUID varchar(36) NOT NULL UNIQUE, Data LONGTEXT)"));
    }
    
    @Override
    public String getPlayerCollections(OfflinePlayer p) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + MySQLDatabase.COLLECTIONS_TABLE_NAME + " WHERE " + MySQLDatabase.COLLECTIONS_UUID_COLNAME + "=?")) {
            statement.setString(1, p.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getString("Data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void savePlayerCollections(OfflinePlayer p, PlayerCollections playerCollections) {
        this.execute("UPDATE " + MySQLDatabase.COLLECTIONS_TABLE_NAME + " SET Data =? WHERE " + MySQLDatabase.COLLECTIONS_UUID_COLNAME + "=?", plugin.getGson().toString(playerCollections), p.getUniqueId().toString());
    }
    
    @Override
    public void addIntoDatabase(OfflinePlayer player) {
        this.execute("INSERT IGNORE INTO " + MySQLDatabase.COLLECTIONS_TABLE_NAME + " VALUES(?,?)", player.getUniqueId().toString(), null);
    }
    
    @Override
    public Set<UUID> getAllPlayers() {
        Set<UUID> uuids = new HashSet<>();
        try (Connection con = this.hikari.getConnection(); ResultSet set = con.prepareStatement("SELECT " + MySQLDatabase.COLLECTIONS_UUID_COLNAME + " FROM " + MySQLDatabase.COLLECTIONS_TABLE_NAME).executeQuery()) {
            while (set.next())
                uuids.add(UUID.fromString(set.getString(MySQLDatabase.COLLECTIONS_UUID_COLNAME)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuids;
    }
}
