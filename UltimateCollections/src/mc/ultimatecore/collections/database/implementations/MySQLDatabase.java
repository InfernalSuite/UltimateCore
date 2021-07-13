package mc.ultimatecore.collections.database.implementations;

import mc.ultimatecore.collections.HyperCollections;
import mc.ultimatecore.collections.database.SQLDatabase;
import mc.ultimatecore.helper.database.Credentials;

public class MySQLDatabase extends SQLDatabase {
    
    public MySQLDatabase(HyperCollections parent, Credentials credentials) {
        super(parent);
        this.connect(credentials);
    }
}