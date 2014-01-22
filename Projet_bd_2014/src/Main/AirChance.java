package Main;

import GestionVol.VolsFretMain;
import GestionVol.VolsPassagerMain;
import GestionReservation.ReservationMain;
import GestionClient.ClientMain;

public class AirChance{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
	  	    	    	ReservationMain Resa = new ReservationMain();
	  	    	    	if(action.equals("3.1")) Resa.ShowListeReservations(); //Consulter les réservations
	  	    	    	if(action.equals("3.2")) Resa.CreateReservation();     //Ajouter une nouvelle réservation
	  	    	    	if(action.equals("3.3")) Resa.EditReservation();       //Modifier une réservation
	  	    	    	if(action.equals("3.4")) Resa.DeleteReservation();     //Supprimer une réservation
	  	    	    break;
	  	    	    
	  	    	    
	  	    	    // gestion Clients
	  	    	    case "4.1" : case "4.2" :  
	  	    	    	ClientMain Client = new ClientMain();
	  	    	    	if(action.equals("4.1")) Client.ShowListeClients(); //Consulter la liste des clients
	  	    	    	if(action.equals("4.2")) Client.ShowListeVols();    //Consulter la liste des vols d'un client
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
		System.out.println("**************** Gestion Réservations ***************");
		System.out.println("3.1 : Consulter les réservations");
		System.out.println("3.2 : Ajouter une nouvelle réservation");
		System.out.println("3.3 : Modifier une réservation");
		System.out.println("3.4 : Supprimer une réservation");
		System.out.println("****************************************************\n");
		System.out.println("**************** Gestion Clients *******************");
		System.out.println("4.1 : Consulter la liste des clients");
		System.out.println("4.2 : Consulter la liste des vols d'un client");
		System.out.println("****************************************************\n");
		System.out.println("9 : Initialiser et peupler la base de données");
		System.out.println("0 : Quitter");
		
	}

}
