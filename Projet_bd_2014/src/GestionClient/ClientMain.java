package GestionClient;

import java.sql.ResultSet;
import java.sql.SQLException;

import Main.DAO;

public class ClientMain {

	public void ShowListeClients() {
		
		//debut
		System.out.println("===> Consulter la liste des clients \n");
		
		//creation Data Access Object
		DAO<ClientDAO> CLDAO = new ClientDAO();
				
		//creation objet vol passager
		//Client Cl = new Client();
		
		ResultSet resultat = CLDAO.ShowList();
		
		try{
		  while(resultat.next()){ 
		     System.out.println("Num client = " +resultat.getInt("NumClient")
		    					+ ", NOM = " + resultat.getString("NomC"));
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
				
		//fin
		System.out.println("****************************************************\n");
		
	}

	public void ShowListeVols() {
		
		//debut
		System.out.println("===> Consulter la liste des vols d'un client \n");
				
		//....
		System.out.println("suite de fonction");
				
		//fin
		System.out.println("****************************************************\n");
		
	}

}
