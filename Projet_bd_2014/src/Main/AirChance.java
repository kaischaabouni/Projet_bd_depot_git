package Main;

import GestionVol.VolsFretMain;
import GestionVol.VolsPassagerMain;
import GestionPersonnel.HotesseMain;
import GestionPersonnel.PiloteMain;
import GestionReservation.ReservationFretMain;
import GestionReservation.ReservationPassagerMain;
import GestionClient.ClientMain;

public class AirChance{

	public static void main(String[] args) {
		
		AfficherMenu();
		
		
		String action;
	    boolean exit = false;
	        
	    while(!exit) {
	  	    	
	  	    	action = LectureClavier.lireChaine(); 
	  	    	switch(action) {
	  	    		 
	  	    	    // gestion vols Passagers
	  	    	    case "1.1" : case "1.2" : case "1.3" : case "1.4" : case "1.5" :
	  	    	    	VolsPassagerMain VolPass = new VolsPassagerMain();
	  	    	    	if(action.equals("1.1")) VolPass.ShowListeVols();  //Consulter la liste des volsP
	  	    	    	if(action.equals("1.2")) VolPass.CreateVol();      //Ajouter un nouveau volP 
	  	    	    	if(action.equals("1.3")) VolPass.EditVol();        //Modifier un volP
	  	    	    	if(action.equals("1.4")) VolPass.DeleteVol();      //Supprimer un volP
	  	    	    	if(action.equals("1.5")) VolPass.ConfirmVol();     //Confirmer la fin d'un volP
	  	    	    break;
	  	    	
	  	    	    
	  	    	    // gestion vols Fret
	  	    	    case "2.1" : case "2.2" : case "2.3" : case "2.4" : case "2.5" :
	  	    	    	VolsFretMain VolFret = new VolsFretMain();
	  	    	    	if(action.equals("2.1")) VolFret.ShowListeVols(); //Consulter la liste des volsF
	  	    	    	if(action.equals("2.2")) VolFret.CreateVol();     //Ajouter un nouveau volF
	  	    	    	if(action.equals("2.3")) VolFret.EditVol();       //Modifier un volF
	  	    	    	if(action.equals("2.4")) VolFret.DeleteVol();     //Supprimer un volF
	  	    	    	if(action.equals("2.5")) VolFret.ConfirmVol();    //Confirmer la fin d'un volF
	  	    	    break;
	  	    	    
	  	    	    
	  	    	    // gestion Réservations
	  	    	    case "3.1" : case "3.2" : case "3.3" : case "3.4" : 
	  	    	    	ReservationFretMain ResaFret = new ReservationFretMain();
	  	    	    	if(action.equals("3.1")) ResaFret.ShowListeReservations(); //Consulter les réservations
	  	    	    	if(action.equals("3.2")) ResaFret.CreateReservation();     //Ajouter une nouvelle réservation
	  	    	    	if(action.equals("3.3")) ResaFret.EditReservation();       //Modifier une réservation
	  	    	    	if(action.equals("3.4")) ResaFret.DeleteReservation();     //Supprimer une réservation
	  	    	    break;
	  	    	    
	  	    	    // gestion Réservations
	  	    	    case "4.1" : case "4.2" : case "4.3" : case "4.4" : 
	  	    	    	ReservationPassagerMain ResaPass = new ReservationPassagerMain();
	  	    	    	if(action.equals("4.1")) ResaPass.ShowListeReservations(); //Consulter les réservations
	  	    	    	if(action.equals("4.2")) ResaPass.CreateReservation();     //Ajouter une nouvelle réservation
	  	    	    	if(action.equals("4.3")) ResaPass.EditReservation();       //Modifier une réservation
	  	    	    	if(action.equals("4.4")) ResaPass.DeleteReservation();     //Supprimer une réservation
	  	    	    break;
	  	    	    
	  	    	    // gestion Personnel
	  	    	    case "5.1" : case "5.2" : case "5.3" : case "5.4" : 
	  	    	    	PiloteMain pilote = new PiloteMain();
	  	    	    	HotesseMain hotesse = new HotesseMain();

	  	    	    	if(action.equals("5.1")) pilote.CreatePilote(); //Consulter les réservations
	  	    	    	if(action.equals("5.2")) pilote.DeletePilote();     //Ajouter une nouvelle réservation
	  	    	    	if(action.equals("5.3")) hotesse.CreateHotesse();       //Modifier une réservation
	  	    	    	if(action.equals("5.4")) hotesse.DeleteHotesse();     //Supprimer une réservation
	  	    	    break;
	  	    	    
	  	    	    // gestion Clients
	  	    	    case "6.1" : case "6.2" :  
	  	    	    	ClientMain Client = new ClientMain();
	  	    	    	if(action.equals("6.1")) Client.ShowListeClients(); //Consulter la liste des clients
	  	    	    	if(action.equals("6.2")) Client.ShowListeVols();    //Consulter la liste des vols d'un client
	  	    	    break;
	  	    	    
	  	    	    
	  	    		//Initialiser et peupler la base de données
	  	    		case "9" :  break;
	  	    		
	  	    	    //quitter
	  	    	    case "0" : exit = true; break;
	  	    	    
	  	    		//default
	  	    		default : 
	  	    			System.out.println("=> choix incorrect"); 
	  	    			System.out.println("****************************************************\n");
	  	    			
	  	    	}
	  	    } 	    

	    
	  	    // fermeture de la connexion !!!
	  	    Connexion.CloseConnexion();
	  	    
	  	    System.out.println("au revoir");

	}

	
	
	private static void AfficherMenu() {
	   
		System.out.println("****************** AIR CHANCE ***********************");
		System.out.println("*****************************************************\n");
		System.out.println("************* Gestion Vols Passagers ****************");
		System.out.println("1.1 : Consulter la liste des vols");
		System.out.println("1.2 : Ajouter un nouveau vol");
		System.out.println("1.3 : Modifier un vol");
		System.out.println("1.4 : Supprimer un vol");
		System.out.println("1.5 : Confirmer la fin d'un vol");
		System.out.println("*****************************************************\n");
		System.out.println("*************** Gestion Vols Fret *******************");
		System.out.println("2.1 : Consulter la liste des vols");
		System.out.println("2.2 : Ajouter un nouveau vol");
		System.out.println("2.3 : Modifier un vol");
		System.out.println("2.4 : Supprimer un vol");
		System.out.println("2.5 : Confirmer la fin d'un vol");
		System.out.println("*****************************************************\n");
		System.out.println("******** Gestion Réservations de Fret ***************");
		System.out.println("3.1 : Consulter les réservations");
		System.out.println("3.2 : Ajouter une nouvelle réservation");
		System.out.println("3.3 : Modifier une réservation");
		System.out.println("3.4 : Supprimer une réservation");
		System.out.println("*****************************************************\n");
		System.out.println("******** Gestion Réservations de Passager ************");
		System.out.println("4.1 : Consulter les réservations");
		System.out.println("4.2 : Ajouter une nouvelle réservation");
		System.out.println("4.3 : Modifier une réservation");
		System.out.println("4.4 : Supprimer une réservation");
		System.out.println("*****************************************************\n");
		System.out.println("******** Gestion Réservations de Personnel ************");
		System.out.println("5.1 : Ajouter un pilote");
		System.out.println("5.2 : Supprimer un pilote");
		System.out.println("5.3 : Ajouter une hotesse");
		System.out.println("5.4 : Supprimer une hotesse");
		System.out.println("****************************************************\n");
		System.out.println("**************** Gestion Clients *******************");
		System.out.println("6.1 : Consulter la liste des clients");
		System.out.println("6.2 : Consulter la liste des réservations d'un client");
		System.out.println("****************************************************\n");
		System.out.println("9 : Initialiser et peupler la base de données");
		System.out.println("0 : Quitter");
		
	}

}
