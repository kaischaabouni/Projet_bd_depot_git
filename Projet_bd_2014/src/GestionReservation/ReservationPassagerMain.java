package GestionReservation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.nocrala.tools.texttablefmt.Table;

import GestionClient.ClientDAO;
import GestionVol.VolsPassager;
import GestionVol.VolsPassagerDAO;
import Main.DAO;
import Main.LectureClavier;

public class ReservationPassagerMain {

	public void ShowListeReservations() {
		
		//debut
		System.out.println("===> Consulter les réservations \n");
		
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
		System.out.println("===> Ajouter une nouvelle réservation \n");
		
		//creation Data Access Object
		ReservationPassagerDAO reservationPassager = new ReservationPassagerDAO();
		DAO<ReservationDAO> reservation = new ReservationDAO();
		
		//creation objet res
		Reservation resa = new Reservation();
		ReservationPassager resap = new ReservationPassager();
		
		// chercher un vol adéquat
		System.out.println("Saisir la ville de départ. \n");
		villeDepart = LectureClavier.lireChaine();
		
		System.out.println("Saisir la ville de destination. \n");
		villeDestination = LectureClavier.lireChaine();
		
		
		System.out.println("Saisir la date de départ. (dd-mm-aaaa) \n");
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
		System.out.println("Vols disponnible à cette date : \n "+ t.render());
		
		System.out.println("Saisir le numéro du vol souhaité. \n");
		NumVol = Integer.parseInt(LectureClavier.lireChaine());
			
		System.out.println("Entrez votre numéro de client. \n");
		NumClient =  Integer.parseInt(LectureClavier.lireChaine());
		
		ResultSet resultat2 = ReservationDAO.chercherPlaceLibre(NumVol, dateVolP);
		Table t2 = new Table(4);
		
		try{
		  t2.addCell("NumAvionP");    t2.addCell("NumPlace");  t2.addCell("Classe");
		  t2.addCell("Position");     
		  
		  while(resultat2.next()){ 
			  
			  t2.addCell(resultat2.getString("NumAvionP"));
			  t2.addCell(resultat2.getString("NumPlace"));
			  t2.addCell(resultat2.getString("Classe"));
			  t2.addCell(resultat2.getString("Position"));
		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
		
		//creation de tableau d'affichage
		System.out.println("Places disponnibles pour ce vol : \n "+ t2.render());
		
		
		System.out.println("Choisissez votre numéro de place. \n");
		NumPlace =  Integer.parseInt(LectureClavier.lireChaine());

		resap.setNumVolP(NumVol);
		resap.setDateVolP(dateVolP);
		resap.setNumPlace(NumPlace);
		resap.setNumResa(NumClient);
		resap.setPrix(800);

		resa.setNumClient(NumClient);
		resa.setDateResa(dateVolP);
		
		ReservationDAO.create(resa);



		try {
			reservationPassager.create(resap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
