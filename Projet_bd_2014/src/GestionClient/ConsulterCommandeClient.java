package gestionClient;
import gestionPlanning.LectureClavier;

public class ConsulterCommandeClient {
	// On affiche la liste des clients existants � dans la base de donn�es
	
	
	
	public static void ConsulterClient() {
		
		
	System.out.println("*** Afficher tous les commandes clients dans la base : ***");
		for (Client a : Client.selectionClient()) {
				System.out.println(a);
		}
		Integer idClient = LectureClavier.lireEntier("ID du client qui souhaite consulter ses commandes : ");
		Client.AfficherCommande(idClient);
	}
}
			

