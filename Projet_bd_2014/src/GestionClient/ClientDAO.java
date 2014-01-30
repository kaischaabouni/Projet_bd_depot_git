
package GestionClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.DAO;


public class ClientDAO extends DAO<ClientDAO>{
    
	ResultSet resultat;
	
	public ResultSet ShowList() {
		try{
        Statement requete = cn.createStatement();
		ResultSet resultat = requete.executeQuery("select * from client");
		
		return resultat;
		
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}

	
    public ResultSet ShowListVolPassager(int NumClient) {
		try{
        Statement requete = cn.createStatement();
		ResultSet resultat = requete.executeQuery(""
				+ "select v.*, rp.* "
				+ "from volspassager v, client c, reservation r, resapassager rp "
				+ "where c.NumClient = "+ NumClient +" "
				+ "and c.NumClient = r.NumClient "
				+ "and r.NumResa = rp.NumResa "
				+ "and rp.NumVolP = v.NumVolP");
		
		return resultat;
		
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}
    
    
    public ResultSet ShowListVolFret(int NumClient) {
		try{
        Statement requete = cn.createStatement();
		ResultSet resultat = requete.executeQuery(""
				+ "select v.*, rf.* "
				+ "from volsfret v, client c, reservation r, resafret rf "
				+ "where c.NumClient = "+ NumClient +" "
				+ "and c.NumClient = r.NumClient "
				+ "and r.NumResa = rf.NumResa "
				+ "and rf.NumVolF = v.NumVolF");
		
		return resultat;
		
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;	
	}
	
	
	public void create(ClientDAO obj) {}
	public void update(ClientDAO obj) {}
	public void delete(ClientDAO obj) {}

		
}
