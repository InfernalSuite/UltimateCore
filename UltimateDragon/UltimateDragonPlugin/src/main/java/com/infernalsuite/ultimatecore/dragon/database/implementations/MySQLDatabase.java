package com.infernalsuite.ultimatecore.dragon.database.implementations;

import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;

public class MySQLDatabase extends SQLDatabase {
	public MySQLDatabase(HyperDragons parent, Credentials credentials) {
		super(parent);
		this.connect(credentials);
	}
}