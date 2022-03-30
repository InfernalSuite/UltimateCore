package com.infernalsuite.ultimatecore.talismans.database.implementations;

import com.infernalsuite.ultimatecore.talismans.HyperTalismans;
import com.infernalsuite.ultimatecore.talismans.database.SQLDatabase;
import com.infernalsuite.ultimatecore.helper.database.Credentials;

public class MySQLDatabase extends SQLDatabase {
	public MySQLDatabase(HyperTalismans parent, Credentials credentials) {
		super(parent);
		this.connect(credentials);
	}
}