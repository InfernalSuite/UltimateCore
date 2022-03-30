package com.infernalsuite.ultimatecore.pets.database.implementations;

import com.infernalsuite.ultimatecore.helper.database.Credentials;
import com.infernalsuite.ultimatecore.pets.HyperPets;
import com.infernalsuite.ultimatecore.pets.database.SQLDatabase;

public class MySQLDatabase extends SQLDatabase {

	public MySQLDatabase(HyperPets parent, Credentials credentials) {
		super(parent, "Players_Pets");
		this.connect(credentials);
	}
}