package GestionReservation;

import Main.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationPassagerDAO extends DAO<ReservationPassagerDAO>{

    ResultSet resultat;
	
	public ResultSet ShowList() {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from ResaPassager");
						
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;	
	}

	
	public void create(ReservationPassager obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("insert into ResPassager values( '',"
														+obj.getDateVolP()+","
														+obj.getNumPlace()+","
														+obj.getNumResa()+","
														+obj.getPrix()+")");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

	
	public void update(ReservationPassagerDAO obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("update reservation passager ....");
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

	
	public void delete(ReservationPassagerDAO obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("delete from reservation passager ....");
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}


	@Override
	public void create(ReservationPassagerDAO obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
