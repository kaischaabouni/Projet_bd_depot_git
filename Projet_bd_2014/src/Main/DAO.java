package Main;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class DAO<Type> {

	// ouverture de la connexion
	protected Connection cn = Connexion.OpenConnexion();
	
	public abstract ResultSet ShowList();
	public abstract Type create(Type obj);
	public abstract Type update(Type obj);
	public abstract void delete(Type obj);
	
}
