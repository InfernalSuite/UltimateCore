package mc.ultimatecore.dragon.database.implementations;

import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.database.SQLDatabase;
import mc.ultimatecore.helper.database.Credentials;

public class MySQLDatabase extends SQLDatabase {
	public MySQLDatabase(HyperDragons parent, Credentials credentials) {
		super(parent);
		this.connect(credentials);
	}
}