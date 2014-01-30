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
	
	public void create(ReservationPassager obj) throws SQLException {

        cn.setAutoCommit(true);
		try{
			Statement requete = cn.createStatement();
			requete.executeQuery("insert into ResaPassager "
				           + "values("+obj.getNumVolP()+","
				           + "to_date('"+obj.getDateVolP()+"','dd-mm-yyyy'),"
				           + ""+obj.getNumPlace()+","
				           + ""+obj.getNumResa()+","
				           + ""+obj.getPrix()+")");

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
