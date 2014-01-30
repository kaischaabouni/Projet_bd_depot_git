package GestionReservation;

import java.sql.ResultSet;

import org.nocrala.tools.texttablefmt.Table;

import GestionClient.ClientDAO;
import GestionVol.VolsPassager;
import GestionVol.VolsPassagerDAO;
import Main.DAO;
import Main.LectureClavier;

public class ReservationPassagerMain {

	public void ShowListeReservations() {
		
		//debut
		System.out.println("===> Consulter les r�servations \n");
		
		//creation Data Access Object
		DAO<ReservationPassagerDAO> CLDAO = new ReservationPassagerDAO();	
		ResultSet resultat = CLDAO.ShowList();
		Table t = new Table(5);
		
		try{
		  t.addCell("NumVolP");    t.addCell("DateVolP");  t.addCell("NumPlace");
		  t.addCell("NumResa");    t.addCell("Prix");   
		  
		  while(resultat.next()){ 
		      
			  t.addCell(resultat.getString("NumVolP"));
			  t.addCell(resultat.getString("DateVolP"));
			  t.addCell(resultat.getString("NumPlace"));
			  t.addCell(resultat.getString("NumResa"));
			  t.addCell(resultat.getString("Prix"));
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println(t.render());        
		
		//fin
		System.out.println("****************************************************\n");
	}

	public void CreateReservation() {
		
		int NumClient;
		int NumVol;

		String dateVolP;
		int NumPlace;
		
		String villeDepart;
		String villeDestination;

		//debut
		System.out.println("===> Ajouter une nouvelle r�servation \n");
		
		//creation Data Access Object
		ReservationPassagerDAO reservationPassager = new ReservationPassagerDAO();
		DAO<ReservationDAO> reservation = new ReservationDAO();
		
		//creation objet res
		Reservation resa = new Reservation();
		ReservationPassager resap = new ReservationPassager();
		
		// chercher un vol ad�quat
		System.out.println("Saisir la ville de d�part. \n");
		villeDepart = LectureClavier.lireChaine();
		
		System.out.println("Saisir la ville de destination. \n");
		villeDestination = LectureClavier.lireChaine();
		
		
		System.out.println("Saisir la date de d�part. (dd-mm-aaaa) \n");
		dateVolP = LectureClavier.lireChaine();
		
		ResultSet resultat = ReservationDAO.chercherVol(villeDepart, villeDestination, dateVolP);
		
		Table t = new Table(8);
		
		try{
		  t.addCell("NumVolP");    t.addCell("DateVolP");  t.addCell("Origine");
		  t.addCell("Destination");    t.addCell("HeureDepGMT");   
		  t.addCell("Duree");   t.addCell("Distance");  t.addCell("NumAvionP");
		  while(resultat.next()){ 
		      
			  t.addCell(resultat.getString("NumVolP"));
			  t.addCell(resultat.getString("DateVolP"));
			  t.addCell(resultat.getString("Origine"));
			  t.addCell(resultat.getString("Destination"));
			  t.addCell(resultat.getString("HeureDepGMT"));
			  t.addCell(resultat.getString("Duree"));
			  t.addCell(resultat.getString("Distance"));
			  t.addCell(resultat.getString("NumAvionP"));
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println("Vols disponnible � cette date : \n "+ t.render());
		
		System.out.println("Saisir le num�ro du vol souhait�. \n");
		NumVol = Integer.parseInt(LectureClavier.lireChaine());
			
		System.out.println("Entrez votre num�ro de client. \n");
		NumClient =  Integer.parseInt(LectureClavier.lireChaine());
		
		ResultSet resultat2 = ReservationDAO.chercherPlaceLibre(NumVol, dateVolP);
		Table t2 = new Table(5);
		
		try{
		  t2.addCell("NumVolP");    t2.addCell("DateVolP");  t2.addCell("NumPlace");
		  t2.addCell("NumResa");    t2.addCell("Prix"); 
		  while(resultat2.next()){ 
			  
			  t2.addCell(resultat.getString("NumVolP"));
			  t2.addCell(resultat.getString("DateVolP"));
			  t2.addCell(resultat.getString("NumPlace"));
			  t2.addCell(resultat.getString("NumResa"));
			  t2.addCell(resultat.getString("Prix"));
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println("Places disponnibles pour ce vol : \n "+ t2.render());
		
		
		System.out.println("Choisissez votre num�ro de place. \n");
		NumPlace =  Integer.parseInt(LectureClavier.lireChaine());

		
		resap.setDateVolP(dateVolP);
		resap.setNumPlace(NumClient);
		
		reservationPassager.create(resap);
		
	    //fin
		System.out.println("****************************************************\n");
		
	}

	public void EditReservation() {
		
		//debut
		System.out.println("===> Modifier une r�servation \n");
				
		//....
		System.out.println("suite de fonction");
				
		//fin
		System.out.println("****************************************************\n");
		
	}

	public void DeleteReservation() {
		
		//debut
		System.out.println("===> Supprimer une r�servation \n");
				
		//....
		System.out.println("suite de fonction");
				
		//fin
		System.out.println("****************************************************\n");
		
	}
	

}
