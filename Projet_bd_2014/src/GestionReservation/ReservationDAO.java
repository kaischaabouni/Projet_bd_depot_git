package GestionReservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.DAO;

public class ReservationDAO extends DAO<ReservationDAO>{

    ResultSet resultat;
	
	public ResultSet ShowList() {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from reservation ....");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;	
	}

	
	public void create(ReservationDAO obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("insert into reservation ....");
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

	
	public void update(ReservationDAO obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("update reservation ....");
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

	
	public void delete(ReservationDAO obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("delete from reservation ....");
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

}
