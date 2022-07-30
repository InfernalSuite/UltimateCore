package mc.ultimatecore.helper.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@AllArgsConstructor
public class Credentials {
    
    private final String host, databaseName, userName, password;
    private final DatabaseType databaseType;
    private final int port;
    
    public static Credentials fromConfig(FileConfiguration config) {
        String host = config.getString("mysql.host");
        String dbName = config.getString("mysql.database");
        String userName = config.getString("mysql.username");
        String password = config.getString("mysql.password");
        int port = config.getInt("mysql.port");
        DatabaseType databaseType = DatabaseType.valueOf(config.getString("database_type"));
        
        Validate.notNull(host);
        Validate.notNull(dbName);
        Validate.notNull(userName);
        Validate.notNull(password);
        return new Credentials(host, dbName, userName, password, databaseType, port);
    }
}
