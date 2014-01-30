package GestionReservation;

import java.sql.ResultSet;

import org.nocrala.tools.texttablefmt.Table;

import Main.DAO;

public class ReservationFretMain {

	public void ShowListeReservations() {
		
		//debut
		System.out.println("===> Consulter les réservations \n");
		
		//creation Data Access Object
		DAO<ReservationFretDAO> CLDAO = new ReservationFretDAO();	
		ResultSet resultat = CLDAO.ShowList();
		Table t = new Table(6);
		
		try{
		  t.addCell("NumVolF");    t.addCell("DateVolF");  t.addCell("NumResa");
		  t.addCell("Volume");    t.addCell("Poids");   t.addCell("Prix"); 
		  
		  while(resultat.next()){ 			  
			  t.addCell(resultat.getString("NumVolF"));
			  t.addCell(resultat.getString("DateVolF"));
			  t.addCell(resultat.getString("NumResa"));
			  t.addCell(resultat.getString("Volume"));
			  t.addCell(resultat.getString("Poids"));
			  t.addCell(resultat.getString("Prix"));

		  }
		}catch(Exception e){ 			System.out.println("ERROR ! \n Code d'erreur");
		System.out.println("Message d'erreur : "+e.getMessage());}
		
		
		//creation de tableau d'affichage
		System.out.println(t.render());        
		
		//fin
		System.out.println("****************************************************\n");
		
		
	}

	public void CreateReservation() {
		
		//debut
		System.out.println("===> Ajouter une nouvelle réservation \n");
				
		//....
	    System.out.println("suite de fonction");
				
	    //fin
		System.out.println("****************************************************\n");
		
	}

	public void EditReservation() {
		
		//debut
		System.out.println("===> Modifier une réservation \n");
				
		//....
		System.out.println("suite de fonction");
				
		//fin
		System.out.println("****************************************************\n");
		
	}

	public void DeleteReservation() {
		
		//debut
		System.out.println("===> Supprimer une réservation \n");
				
		//....
		System.out.println("suite de fonction");
				
		//fin
		System.out.println("****************************************************\n");
		
	}
	

}
