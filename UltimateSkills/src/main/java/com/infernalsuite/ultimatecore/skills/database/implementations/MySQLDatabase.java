package com.infernalsuite.ultimatecore.skills.database.implementations;

import com.infernalsuite.ultimatecore.helper.database.Credentials;
import com.infernalsuite.ultimatecore.skills.database.SQLDatabase;
import com.infernalsuite.ultimatecore.skills.HyperSkills;

public class MySQLDatabase extends SQLDatabase {

	public MySQLDatabase(HyperSkills parent, Credentials credentials) {
		super(parent);
		this.connect(credentials);
	}
}