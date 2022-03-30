package mc.ultimatecore.helper.implementations;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.helper.database.DatabaseType;
import mc.ultimatecore.helper.database.implementations.MySQL;
import mc.ultimatecore.helper.database.implementations.SQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
public abstract class DatabaseImpl {
    
    protected HikariDataSource hikari;
    protected final UltimatePlugin plugin;
    
    public abstract void createTables();
    
    public void connect(Credentials credentials) {
        if (credentials.getDatabaseType() == DatabaseType.MYSQL)
            this.hikari = new HikariDataSource(new MySQL(plugin).getDatabase(credentials));
        else
            this.hikari = new HikariDataSource(new SQLite(plugin).getDatabase());
        this.createTables();
        
    }
    
    public void close() {
        if (this.hikari != null) {
            this.hikari.close();
            this.plugin.getLogger().info("Closing SQL Connection");
        }
    }
    
    public synchronized void execute(String sql, Object... replacements) {
        try (Connection c = this.hikari.getConnection(); PreparedStatement statement = c.prepareStatement(sql)) {
            if (replacements != null)
                for (int i = 0; i < replacements.length; i++)
                    statement.setObject(i + 1, replacements[i]);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
