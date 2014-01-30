package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	private static String User     = "shimabue";
	private static String Password = "bd2013";
	private static String ConnUrl  = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:ufrima";
	private static Connection Conn = null;
	
	
	//pour s'assurer qu'une seule instance du classe puisse être instancier (Singleton) 
	private Connexion(){}
	
	//GetInstance
	public static Connection OpenConnexion(){
        
		if(Conn == null){
		    try{
			    // Enregistrement du driver Oracle
	  	        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  	     	    
	  	    
	  	        // Etablissement de la connection
	  	        Conn = DriverManager.getConnection(ConnUrl, User, Password);
	  	      
			}catch(SQLException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return Conn;
	}
	
	
	
	public static void CloseConnexion() {
		try { 
		     Conn.close();
		     Conn = null;
		} catch (Exception ignore) {}
	}
	
	
	
}
