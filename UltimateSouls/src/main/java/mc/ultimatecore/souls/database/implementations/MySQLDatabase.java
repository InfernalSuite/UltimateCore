package mc.ultimatecore.souls.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.souls.HyperSouls;
import mc.ultimatecore.souls.database.SQLDatabase;

public class MySQLDatabase extends SQLDatabase {
    
    public MySQLDatabase(HyperSouls parent, Credentials credentials, String name) {
        super(parent, name);
        this.connect(credentials);
    }
}