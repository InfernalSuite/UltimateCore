package com.infernalsuite.ultimatecore.pets.database;

import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.objects.PetData;
import com.infernalsuite.ultimatecore.pets.objects.Tier;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public abstract class SQLDatabase extends Database {
    
    protected static final String PETS_TABLE_NAME = "Pets";
    protected static final String PETS_UUID_COLNAME = "UUID";
    
    protected final String playersTable;
    protected static final String PLAYERS_UUID_COLNAME = "UUID";
    
    protected HyperPets plugin;
    
    public SQLDatabase(HyperPets plugin, String playersTable) {
        super(plugin);
        this.plugin = plugin;
        this.playersTable = playersTable;
    }
    
    @Override
    public void createTables() {
    }
    
    @Override
    public CompletableFuture<Void> createTablesAsync() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            execute("CREATE TABLE IF NOT EXISTS " + PETS_TABLE_NAME + "(UUID bigint, pet_name varchar(10), pet_level int, pet_xp double, pet_tier varchar(10), primary key (UUID))");
            execute("CREATE TABLE IF NOT EXISTS " + playersTable + "(UUID varchar(36) NOT NULL UNIQUE, spawned_id bigint, inventory_pets varchar(50), primary key (UUID))");
            future.complete(null);
        });
        return future;
    }
    
    @Override
    public void addIntoPetsDatabase(PetData petData) {
        this.execute("INSERT IGNORE INTO " + PETS_TABLE_NAME + " VALUES(?,?,?,?,?)", petData.getPetUUID().toString(), petData.getPetName(), petData.getLevel(), petData.getXp(), petData.getTier().getName());
    }
    
    
    @Override
    public String getPetName(Integer petUUID) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + PETS_TABLE_NAME + " WHERE " + PETS_UUID_COLNAME + "=?")) {
            statement.setString(1, petUUID.toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getString("pet_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @Override
    public Integer getPetLevel(Integer petUUID) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + PETS_TABLE_NAME + " WHERE " + PETS_UUID_COLNAME + "=?")) {
            statement.setString(1, petUUID.toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getInt("pet_level");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public Tier getPetTier(Integer petUUID) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + PETS_TABLE_NAME + " WHERE " + PETS_UUID_COLNAME + "=?")) {
            statement.setString(1, petUUID.toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return HyperPets.getInstance().getTiers().getTier(set.getString("pet_tier"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Double getPetXP(Integer petUUID) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + PETS_TABLE_NAME + " WHERE " + PETS_UUID_COLNAME + "=?")) {
            statement.setString(1, petUUID.toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getDouble("pet_xp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0d;
    }
    
    
    @Override
    public void setPetLevel(Integer petUUID, int level) {
        this.execute("UPDATE " + PETS_TABLE_NAME + " SET pet_level =? WHERE " + PETS_UUID_COLNAME + "=?", level, petUUID.toString());
    }
    
    @Override
    public void setPetXP(Integer petUUID, Double xp) {
        this.execute("UPDATE " + PETS_TABLE_NAME + " SET pet_xp =? WHERE " + PETS_UUID_COLNAME + "=?", xp, petUUID.toString());
    }
    
    @Override
    public void setPetTier(Integer petUUID, String tier) {
        this.execute("UPDATE " + PETS_TABLE_NAME + " SET pet_tier =? WHERE " + PETS_UUID_COLNAME + "=?", tier, petUUID.toString());
    }
    
    @Override
    public void setPetName(Integer petUUID, String name) {
        this.execute("UPDATE " + PETS_TABLE_NAME + " SET pet_name =? WHERE " + PETS_UUID_COLNAME + "=?", name, petUUID.toString());
    }
    
    //PLAYER METHODS
    
    @Override
    public void addIntoPlayerDatabase(OfflinePlayer offlinePlayer) {
        this.execute("INSERT IGNORE INTO " + playersTable + " VALUES(?,?,?)", offlinePlayer.getUniqueId().toString(), -1, "");
    }
    
    @Override
    public Integer getSpawnedID(OfflinePlayer offlinePlayer) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + playersTable + " WHERE " + PLAYERS_UUID_COLNAME + "=?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getInt("spawned_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    @Override
    public String getInventoryPets(OfflinePlayer offlinePlayer) {
        try (Connection con = this.hikari.getConnection(); PreparedStatement statement = con.prepareStatement("SELECT * FROM " + playersTable + " WHERE " + PLAYERS_UUID_COLNAME + "=?")) {
            statement.setString(1, offlinePlayer.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getString("inventory_pets");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @Override
    public void setInventoryPets(OfflinePlayer offlinePlayer, String petsID) {
        this.execute("UPDATE " + playersTable + " SET inventory_pets =? WHERE " + PLAYERS_UUID_COLNAME + "=?", petsID, offlinePlayer.getUniqueId().toString());
    }
    
    @Override
    public void setSpawnedID(OfflinePlayer offlinePlayer, Integer petUUID) {
        this.execute("UPDATE " + playersTable + " SET spawned_id =? WHERE " + PLAYERS_UUID_COLNAME + "=?", petUUID, offlinePlayer.getUniqueId().toString());
    }
    
    @Override
    public List<Integer> getPetsID() {
        List<Integer> ids = new ArrayList<>();
        try (Connection con = this.hikari.getConnection(); ResultSet set = con.prepareStatement("SELECT " + PETS_UUID_COLNAME + " FROM " + PETS_TABLE_NAME + " ORDER BY " + PETS_UUID_COLNAME + " DESC LIMIT 100000").executeQuery()) {
            while (set.next()) {
                ids.add(set.getInt(PETS_UUID_COLNAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(ids);
        return ids;
    }
}
