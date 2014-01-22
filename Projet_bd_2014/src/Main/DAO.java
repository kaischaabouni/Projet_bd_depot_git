package Main;

import java.sql.Connection;

public abstract class DAO<Type> {

	// ouverture de la connexion
	Connection cn = Connexion.OpenConnexion();
	
	public abstract Type ShowList(long id);
	public abstract Type create(Type obj);
	public abstract Type update(Type obj);
	public abstract void delete(Type obj);
	
}
