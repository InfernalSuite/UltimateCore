package com.infernalsuite.ultimatecore.helper.database.implementations;

import com.zaxxer.hikari.HikariConfig;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.database.SQL;

import java.io.File;
import java.io.IOException;

public class SQLite extends SQL {
    
    private static final String FILE_NAME = "playerdata.db";
    private final String filePath;
    
    public SQLite(UltimatePlugin plugin) {
        super(plugin);
        this.filePath = plugin.getDataFolder().getPath() + File.separator + FILE_NAME;
        createDBFile();
    }
    
    public HikariConfig getDatabase() {
        final HikariConfig hikari = new HikariConfig();
        hikari.setPoolName(plugin.getPluginName() + "-" + POOL_COUNTER.getAndIncrement());
        hikari.setDriverClassName("org.sqlite.JDBC");
        hikari.setJdbcUrl("jdbc:sqlite:" + this.filePath);
        hikari.setConnectionTestQuery("SELECT 1");
        hikari.setMinimumIdle(MINIMUM_IDLE);
        hikari.setMaxLifetime(MAX_LIFETIME);
        hikari.setConnectionTimeout(CONNECTION_TIMEOUT);
        hikari.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        hikari.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);
        return hikari;
    }
    
    private void createDBFile() {
        File yourFile = new File(this.filePath);
        try {
            yourFile.createNewFile();
        } catch (IOException e) {
            plugin.getLogger().warning(String.format("Unable to create %s", FILE_NAME));
            e.printStackTrace();
        }
    }
}
