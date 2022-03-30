package com.infernalsuite.ultimatecore.dragon.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@AllArgsConstructor
public class DatabaseCredentials {

    private final String host, databaseName, userName, password, databaseType;
    private final int port;

    public static DatabaseCredentials fromConfig(FileConfiguration config) {
		String host = config.getString("mysql.host");
		String dbName = config.getString("mysql.database");
		String userName = config.getString("mysql.username");
		String password = config.getString("mysql.password");
		int port = config.getInt("mysql.port");
		String databaseType = config.getString("database_type");
        Validate.notNull(host);
        Validate.notNull(dbName);
        Validate.notNull(userName);
        Validate.notNull(password);
        return new DatabaseCredentials(host,dbName,userName,password,databaseType,port);
    }

}
