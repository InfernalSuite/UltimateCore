package com.infernalsuite.ultimatecore.souls.database.implementations;

import com.infernalsuite.ultimatecore.souls.HyperSouls;
import com.infernalsuite.ultimatecore.souls.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;

public class MySQLDatabase extends SQLDatabase {
    
    public MySQLDatabase(HyperSouls parent, Credentials credentials, String name) {
        super(parent, name);
        this.connect(credentials);
    }
}