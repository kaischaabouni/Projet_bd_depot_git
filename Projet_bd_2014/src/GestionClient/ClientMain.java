package GestionClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.nocrala.tools.texttablefmt.Table;
import Main.DAO;
import Main.LectureClavier;


public class ClientMain {
    
	public void ShowListeClients() {
		
		//debut
		System.out.println("===> Consulter la liste des clients \n");
		
		//creation Data Access Object
		DAO<ClientDAO> CLDAO = new ClientDAO();	
		ResultSet resultat = CLDAO.ShowList();
		Table t = new Table(7);
		
		try{
		  t.addCell("NumClient");    t.addCell("Nom");  t.addCell("Prenom");
		  t.addCell("Passeport");    t.addCell("Adresse");  t.addCell("Pays");
		  t.addCell("Points");   
		  
		  while(resultat.next()){ 
		      
			  t.addCell(resultat.getString("NumClient"));
			  t.addCell(resultat.getString("NomC"));
			  t.addCell(resultat.getString("PrenomC"));
			  t.addCell(resultat.getString("Passeport"));
			  t.addCell(resultat.getString("Numero")+" "+ resultat.getString("Rue")+" "+ resultat.getString("CP")+" "+ resultat.getString("Ville"));
			  t.addCell(resultat.getString("Pays"));
			  t.addCell(resultat.getString("Points"));
			  				 
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println(t.render());        
		
		//fin
		System.out.println("****************************************************\n");
		
	}

	
	public void ShowListeVols() {
		
		//debut
		System.out.println("===> Consulter la liste des réservations d'un client \n");
		
		System.out.println("===> Liste des clients \n");
		
		
		DAO<ClientDAO> CLDAO = new ClientDAO();	
		ResultSet resultat = CLDAO.ShowList();
		Table t = new Table(7);
		
		try{
		  t.addCell("NumClient");    t.addCell("Nom");  t.addCell("Prenom");
		  t.addCell("Passeport");    t.addCell("Adresse");  t.addCell("Pays");
		  t.addCell("Points");   
		  
		  while(resultat.next()){ 
		      
			  t.addCell(resultat.getString("NumClient"));
			  t.addCell(resultat.getString("NomC"));
			  t.addCell(resultat.getString("PrenomC"));
			  t.addCell(resultat.getString("Passeport"));
			  t.addCell(resultat.getString("Numero")+" "+ resultat.getString("Rue")+" "+ resultat.getString("CP")+" "+ resultat.getString("Ville"));
			  t.addCell(resultat.getString("Pays"));
			  t.addCell(resultat.getString("Points"));
			  				 
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println(t.render());
		
		int NumClient = LectureClavier.lireEntier("===> Veuillez entrer le numéro de client : \n");
		
		
		ResultSet ResultatVolF = ((ClientDAO) CLDAO).ShowListVolPassager(NumClient);
		ResultSet ResultatVolP = ((ClientDAO) CLDAO).ShowListVolFret(NumClient);
		
		
		//....
		System.out.println("Liste des réservations des vol Passager : \n");
		
        Table TabPassager = new Table(9);
		
		try{
			
		//v.NumVolP, v.DateVolP, v.Origine, v.Destination, v.HeurDepGMT, rp.*	
		  TabPassager.addCell("NumVolP");      TabPassager.addCell("DateVolP");    TabPassager.addCell("Origine");
		  TabPassager.addCell("Destination");  TabPassager.addCell("HeurDepGMT");  TabPassager.addCell("NumPlace");
		  TabPassager.addCell("NumResa");      TabPassager.addCell("Prix");        TabPassager.addCell("DateResa");
		  
		  while(ResultatVolP.next()){ 
		      
			  TabPassager.addCell(resultat.getString("NumVolP"));
			  TabPassager.addCell(resultat.getString("DateVolP"));
			  TabPassager.addCell(resultat.getString("Origine"));
			  TabPassager.addCell(resultat.getString("Destination"));
			  TabPassager.addCell(resultat.getString("HeurDepGMT"));
			  TabPassager.addCell(resultat.getString("NumPlace"));
			  TabPassager.addCell(resultat.getString("NumResa"));
			  TabPassager.addCell(resultat.getString("Prix"));
			  TabPassager.addCell(resultat.getString("DateResa"));
			  				 
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println(TabPassager.render());
		System.out.println("\n");
		
		
		//....
		System.out.println("Liste des réservations des vol Fret : \n");
		
        Table TabFret = new Table(10);
		
		try{
			
		//v.NumVolP, v.DateVolP, v.Origine, v.Destination, v.HeurDepGMT, rp.*	
		  TabFret.addCell("NumVolF");      TabFret.addCell("DateVolF");    TabFret.addCell("Origine");
		  TabFret.addCell("Destination");  TabFret.addCell("HeurDepGMT");  TabFret.addCell("Volume");
		  TabFret.addCell("Poids");
		  TabFret.addCell("NumResa");      TabFret.addCell("Prix");        TabFret.addCell("DateResa");
		  
		  while(ResultatVolF.next()){ 
		      
			  TabFret.addCell(resultat.getString("NumVolF"));
			  TabFret.addCell(resultat.getString("DateVolF"));
			  TabFret.addCell(resultat.getString("Origine"));
			  TabFret.addCell(resultat.getString("Destination"));
			  TabFret.addCell(resultat.getString("HeurDepGMT"));
			  TabFret.addCell(resultat.getString("Volume"));
			  TabFret.addCell(resultat.getString("Poids"));
			  TabFret.addCell(resultat.getString("NumResa"));
			  TabFret.addCell(resultat.getString("Prix"));
			  TabFret.addCell(resultat.getString("DateResa"));
			  				 
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println(TabFret.render());
		System.out.println("\n");
		
		
				
		//fin
		System.out.println("****************************************************\n");
		
	}

}
