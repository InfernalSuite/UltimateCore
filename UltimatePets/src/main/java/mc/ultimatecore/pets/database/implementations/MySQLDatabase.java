package mc.ultimatecore.pets.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.pets.HyperPets;
import mc.ultimatecore.pets.database.SQLDatabase;

public class MySQLDatabase extends SQLDatabase {

	public MySQLDatabase(HyperPets parent, Credentials credentials) {
		super(parent, "Players_Pets");
		this.connect(credentials);
	}
}