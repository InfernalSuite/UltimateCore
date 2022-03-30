package com.infernalsuite.ultimatecore.helper.database.implementations;

import com.zaxxer.hikari.HikariConfig;
import com.infernalsuite.ultimatecore.helper.UltimatePlugin;
import com.infernalsuite.ultimatecore.helper.database.Credentials;
import com.infernalsuite.ultimatecore.helper.database.SQL;

public class MySQL extends SQL {
    
    public MySQL(UltimatePlugin plugin) {
        super(plugin);
    }
    
    public HikariConfig getDatabase(Credentials credentials) {
        final HikariConfig hikari = new HikariConfig();
        hikari.setPoolName(plugin.getPluginName() + "-" + POOL_COUNTER.getAndIncrement());
        hikari.setJdbcUrl("jdbc:mysql://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName());
        hikari.setConnectionTestQuery("SELECT 1");
        hikari.setUsername(credentials.getUserName());
        hikari.setPassword(credentials.getPassword());
        hikari.setMinimumIdle(MINIMUM_IDLE);
        hikari.setMaxLifetime(MAX_LIFETIME);
        hikari.setConnectionTimeout(CONNECTION_TIMEOUT);
        hikari.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        hikari.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);
        return hikari;
    }
}
