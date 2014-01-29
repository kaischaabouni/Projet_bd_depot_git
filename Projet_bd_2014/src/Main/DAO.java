package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class DAO<Type> {
    
	//ouverture de connexion
	protected Connection cn = Connexion.OpenConnexion();
	
	public abstract ResultSet ShowList();
	public abstract void create(Type obj) throws SQLException;
	public abstract void update(Type obj);
	public abstract void delete(Type obj);
	
}
