package com.infernalsuite.ultimatecore.collections.database.implementations;

import com.infernalsuite.ultimatecore.collections.HyperCollections;
import com.infernalsuite.ultimatecore.collections.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;

public class MySQLDatabase extends SQLDatabase {
    
    public MySQLDatabase(HyperCollections parent, Credentials credentials) {
        super(parent);
        this.connect(credentials);
    }
}