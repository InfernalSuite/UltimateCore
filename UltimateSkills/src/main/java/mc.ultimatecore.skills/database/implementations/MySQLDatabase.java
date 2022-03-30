package mc.ultimatecore.skills.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.database.SQLDatabase;

public class MySQLDatabase extends SQLDatabase {

	public MySQLDatabase(HyperSkills parent, Credentials credentials) {
		super(parent);
		this.connect(credentials);
	}
}