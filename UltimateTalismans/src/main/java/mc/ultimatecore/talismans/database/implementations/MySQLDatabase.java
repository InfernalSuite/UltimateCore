package mc.ultimatecore.talismans.database.implementations;

import mc.ultimatecore.helper.database.Credentials;
import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.database.SQLDatabase;

public class MySQLDatabase extends SQLDatabase {
	public MySQLDatabase(HyperTalismans parent, Credentials credentials) {
		super(parent);
		this.connect(credentials);
	}
}