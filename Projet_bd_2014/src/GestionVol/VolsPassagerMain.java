package GestionVol;

import java.sql.ResultSet;

import GestionClient.ClientDAO;
import Main.DAO;
import Main.LectureClavier;

public class VolsPassagerMain {

	
	public void CreateVol(){
		String DateVolP; 
		String Origine; 
		String Destination; 
		String HeureDepGMT;
		int Duree; 
		int Distance; 
		int Nb1ClMin; 
		int Nb2ClMin; 
		int NumAvionP;
		String Termine;

		
		//debut
		System.out.println("===> Créer un nouveau vol. \n");
		
		//creation Data Access Object
		DAO<VolsPassagerDAO> VPDAO = new VolsPassagerDAO();
		
		//creation objet vol passager
		VolsPassager VolsP = new VolsPassager();
		
		System.out.println("Saisir une date(dd/mm/aaaa). \n");
		DateVolP = LectureClavier.lireChaine(); 
		VolsP.setDateVolP(DateVolP);

		System.out.println("Saisir l'origine. \n");
		Origine = LectureClavier.lireChaine(); 
		VolsP.setOrigine(Origine);
		
		System.out.println("Saisir la destination. \n");
		Destination = LectureClavier.lireChaine(); 
		VolsP.setDestination(Destination);

		System.out.println("Saisir une Heure(hh/mm/ss). \n");
		HeureDepGMT = LectureClavier.lireChaine(); 
		VolsP.setHeurDepGMT(HeureDepGMT);

		System.out.println("Saisir la durée. \n");
		Duree = LectureClavier.lireEntier(LectureClavier.lireChaine()); 
		VolsP.setDuree(Duree);

		System.out.println("Saisir la distance. \n");
		Distance = LectureClavier.lireEntier(LectureClavier.lireChaine()); 
		VolsP.setDistance(Distance);

		System.out.println("Saisir le nombre de première classes. \n");
		Nb1ClMin = LectureClavier.lireEntier(LectureClavier.lireChaine());
		VolsP.setNb1ClMin(Nb1ClMin);

		System.out.println("Saisir le nombre de seconde classes. \n");
		Nb2ClMin = LectureClavier.lireEntier(LectureClavier.lireChaine());
		VolsP.setNb2ClMin(Nb2ClMin);

		System.out.println("Saisir le numéro de l'avion. \n");
		NumAvionP = LectureClavier.lireEntier(LectureClavier.lireChaine());
		VolsP.setNumAvionP(NumAvionP);

		System.out.println("Vol terminé (O ou N). \n");
		Termine = LectureClavier.lireChaine();
		VolsP.setTermine(Termine);

		//fin
		System.out.println("****************************************************\n");
		
		VolsP.toString();
		
	}
	
	

	public void ShowListeVols() {
		
		//debut
		System.out.println("===> Consulter la liste des Vols \n");
		
		//creation Data Access Object
		DAO<VolsPassagerDAO> CLDAO = new VolsPassagerDAO();
		
		ResultSet resultat = CLDAO.ShowList();
		
		try{
		  while(resultat.next()){ 
		     System.out.println("Num Vol = " +resultat.getInt("NumVolP")
		    					+ ", Origine = " + resultat.getString("Origine")
								+ ", destination = " + resultat.getString("Destination"));

		  }
		}catch(Exception e){ System.out.println("erreur exception"); }
		
				
		//fin
		System.out.println("****************************************************\n");
	}

	
	public void EditVol() {
		
		//debut
		System.out.println("===> Modifier un vol \n");
		
		//....
		System.out.println("suite de fonction");
				
		//fin
	    System.out.println("****************************************************\n");
	    
	}

	
	public void DeleteVol() {
		
		//debut
		System.out.println("===> Supprimer un vol \n");
		
		//....
		System.out.println("suite de fonction");
		
		//fin
		System.out.println("****************************************************\n");
	}

	
	public void ConfirmVol() {
		
		//debut
		System.out.println("===> Confirmer la fin d'un vol \n");
		
		//....
		System.out.println("suite de fonction");
		
		//fin
		System.out.println("****************************************************\n");
	}
	
	
}
