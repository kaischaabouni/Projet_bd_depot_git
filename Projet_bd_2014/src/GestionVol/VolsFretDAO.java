package GestionVol;

import Main.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VolsFretDAO extends DAO<VolsFretDAO>{
    
	ResultSet resultat;
	
	public ResultSet ShowList() {
		
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from volfret ....");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
			
	}

	public void create(VolsFretDAO obj) {
	}

	public void update(VolsFretDAO obj) {
	}

	public void delete(VolsFretDAO obj) {
		
	}

}
