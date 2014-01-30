
package GestionVol;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.nocrala.tools.texttablefmt.Table;

import Main.DAO;
import Main.LectureClavier;

public class VolsPassagerMain {
    
	
	public void CreateVol() {
		
		//debut
		System.out.println("===> Ajouter un nouveau vol Passager\n");
		
		Table TabAvions = new Table(4);
		Table TabPilotes = new Table(5);
		Table TabHotesses = new Table(5);
		ResultSet resultat;
		List NumAvionPList = new ArrayList();
		List NumPersoPList = new ArrayList();
		List NumPersoHList = new ArrayList();
		List NumPersoPRetenu = new ArrayList();
		List NumPersoHRetenu = new ArrayList();
		int Nb1Cl = 0, Nb2Cl = 0;
		int NbrPilotes = 0, NbrHotesses = 0;
		String ModeleAvion = null;
		
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
				
		//creation objet vol passager
		VolsPassager VolP = new VolsPassager();
		
		//Numéro de vol
		System.out.println("Veuillez choisir le numéro du vol :");
		VolP.setNumVolP(LectureClavier.lireChaine());	
		
		//Origine
		System.out.println("Veuillez choisir l'origine du vol :");
		VolP.setOrigine(LectureClavier.lireChaine());
		
		//destination 
		System.out.println("La destination :");
		VolP.setDestination(LectureClavier.lireChaine());
		
		//Date
		System.out.println("La date (YYYY-MM-DD) :");
		VolP.setDateVolP(java.sql.Date.valueOf(LectureClavier.lireChaine()));
		
		//heure
		System.out.println("L'heure GMT :");
		VolP.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
			
		//Duree
		VolP.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				
		//Distance
		VolP.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				
		//avion 
		System.out.println("liste des avions disponibles :");
				
		// recupération de la liste a partir de la base de donnees
		// avion disponible + rayon d'action > distance de vol 
		resultat = VPDAO.ListAvionsDisponible();
			
		try{
				TabAvions.addCell("NumAvionP");    TabAvions.addCell("Modele");  TabAvions.addCell("Nb1Cl");
				TabAvions.addCell("Nb2Cl"); 
				  
				  while(resultat.next()){ 
				      
					  NumAvionPList.add(resultat.getInt("NumAvionP"));
					  TabAvions.addCell(resultat.getString("NumAvionP"));
					  TabAvions.addCell(resultat.getString("Modele"));
					  TabAvions.addCell(resultat.getString("Nb1Cl"));
					  TabAvions.addCell(resultat.getString("Nb2Cl"));
					  				 
				  }
		}catch(Exception e){ System.out.println("erreur exception afficher liste des avions disponibles"); }
				
		//creation de tableau d'affichage
		System.out.println(TabAvions.render());
				
		//test si l'entier entrer existe dans la table des avions deja afficher
		while(!NumAvionPList.contains(VolP.getNumAvionP())){
			VolP.setNumAvionP(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
		}
				
		//volume et poids d'avion
		ResultSet InfoAvion = VPDAO.InfosAvion(VolP.getNumAvionP());
		try { InfoAvion.next(); 
			  Nb1Cl = InfoAvion.getInt("Nb1ClMin");
			  Nb2Cl  = InfoAvion.getInt("Nb2ClMin");
			  NbrPilotes = InfoAvion.getInt("NbPilotes");
			  NbrHotesses = (Nb1Cl + Nb2Cl) /20;
		      ModeleAvion = InfoAvion.getString("Modele");
		} catch (SQLException e) { System.out.println("erreur exception afficher infos d'avion choisie"); }
				
		//Nb1Cl min
		VolP.setNb1ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 1 (entre 1 et "+Nb1Cl+" supporter par l'avion choisie) pour ce vol : "));
				
		//Nb2Cl min
		VolP.setNb2ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 2 (entre 1 et "+Nb2Cl+" supporter par l'avion choisie) pour ce vol : "));
				
		//pilotes disponibles et selon le modele d'avion choisie
		System.out.println("liste des pilotes disponibles :");
				
		//recupération de la liste a partir de la base de donnees
		ResultSet ResultatPilotes = VPDAO.ListPilotesDisponible();
				
		try{
				TabPilotes.addCell("NumPersoP");    TabPilotes.addCell("NomP");              
				TabPilotes.addCell("PrenomP");      TabPilotes.addCell("Pays");        
				TabPilotes.addCell("NbHeuresVolTotal");  

				  while(ResultatPilotes.next()){ 
				      
					  NumPersoPList.add(ResultatPilotes.getInt("NumPersoP"));
					  TabPilotes.addCell(ResultatPilotes.getString("NumPersoP"));
					  TabPilotes.addCell(ResultatPilotes.getString("NomP"));
					  TabPilotes.addCell(ResultatPilotes.getString("PrenomP"));
					  TabPilotes.addCell(ResultatPilotes.getString("Pays"));
					  TabPilotes.addCell(ResultatPilotes.getString("NbHeuresVolTotal"));
					  				 
				  }
		}catch(Exception e){ System.out.println("erreur exception afficher liste des pilotes"); }
				
		//creation de tableau d'affichage
		System.out.println(TabPilotes.render());
				
		//plusieurs pilotes
		System.out.println("Vous avez choisie une avion du modele "+ModeleAvion+" qui necessite "+NbrPilotes+" pilotes");
				
		//lecture du num entré
		for (int i = 0; i < NbrPilotes; i++) {
						
						int Num = 0;
						Num = LectureClavier.lireEntier("Pilote "+i+" : ");
						
						//interdire d'ajouter le meme pilote deux fois
						//accepter juste les numeros afficher dans le tableau
						while(!NumPersoPList.contains(Num)){
							Num = LectureClavier.lireEntier("Pilote ici "+i+" : ");
						}
						
						if(NumPersoPList.contains(Num)) NumPersoPRetenu.add(Num);
						
		}
					
		//ajouter pilote
		VolP.setAffectationP(NumPersoPRetenu);
					
		//hottesses disponibles et selon le modele d'avion choisie
		System.out.println("liste des hotesses disponibles :");
						
		//recupération de la liste a partir de la base de donnees
		ResultSet ResultatHotesses = VPDAO.ListHotessesDisponible();
						
				try{
						TabHotesses.addCell("NumPersoH");    TabHotesses.addCell("NomH");              
						TabHotesses.addCell("PrenomH");      TabHotesses.addCell("Pays");        
						TabHotesses.addCell("NbHeuresVolTotal");  

						  while(ResultatHotesses.next()){ 
						      
							  
							NumPersoHList.add(ResultatHotesses.getInt("NumPersoH"));
							  TabHotesses.addCell(ResultatHotesses.getString("NumPersoH"));
							  TabHotesses.addCell(ResultatHotesses.getString("NomH"));
							  TabHotesses.addCell(ResultatHotesses.getString("PrenomH"));
							  TabHotesses.addCell(ResultatHotesses.getString("Pays"));
							  TabHotesses.addCell(ResultatHotesses.getString("NbHeuresVolTotal"));
							  				 
						  }
				}catch(Exception e){ System.out.println("erreur exception afficher liste des hotesses"); }
						
				//creation de tableau d'affichage
				System.out.println(TabHotesses.render());
						
				//plusieurs pilotes
				System.out.println("Vous avez choisie une avion du modele "+ModeleAvion+" qui necessite "+NbrHotesses+" hotesses");
						
				//lecture du num entré
				for (int i = 0; i < NbrHotesses; i++) {
								
								int Num = 0;
								Num = LectureClavier.lireEntier("Hotesse "+i+" : ");
								
								//interdire d'ajouter le meme pilote deux fois
								//accepter juste les numeros afficher dans le tableau
								while(!NumPersoHList.contains(Num)){
									Num = LectureClavier.lireEntier("Hotesse ici "+i+" : ");
								}
								
								if(NumPersoHList.contains(Num)) NumPersoHRetenu.add(Num);
								
				}
							
				//ajouter pilote
				VolP.setAffectationH(NumPersoHRetenu);
		
		
				
		//ajouter vol dans la base de donnees
		VPDAO.create(VolP);
				
		//fin
	    System.out.println("****************************************************\n");
	}

	
	
	public void ShowListeVols() {
		
		//debut
		System.out.println("===> Consulter la liste des vol \n");
				
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
						
		// recupération de la liste a partir de la base de donnees
		ResultSet resultat = VPDAO.ShowList();
		
		Table TabVolsF = new Table(11);
		ResultSet ResultatPilotes = null;
    	String Pilotes = null;
		
		try{
			TabVolsF.addCell("NumVolP");      TabVolsF.addCell("DateVolP");  
			TabVolsF.addCell("HeureDepGMT");  TabVolsF.addCell("Origine"); 
			TabVolsF.addCell("Destination");  TabVolsF.addCell("Duree");
			TabVolsF.addCell("Distance");     TabVolsF.addCell("Nb1Cl");
			TabVolsF.addCell("Nb2Cl");        TabVolsF.addCell("NumAvionP");
			TabVolsF.addCell("Termine");      //TabVolsF.addCell("Pilotes");
		    
		    while(resultat.next()){ 
				
		    	TabVolsF.addCell(resultat.getString("NumVolP"));
		    	TabVolsF.addCell(resultat.getString("DateVol"));
		    	TabVolsF.addCell(resultat.getString("HeureDep"));
		    	TabVolsF.addCell(resultat.getString("Origine"));
		    	TabVolsF.addCell(resultat.getString("Destination"));
		    	TabVolsF.addCell(resultat.getString("Duree"));
		    	TabVolsF.addCell(resultat.getString("Distance"));
		    	TabVolsF.addCell(resultat.getString("Nb1ClMin"));
		    	TabVolsF.addCell(resultat.getString("Nb2ClMin"));
		    	TabVolsF.addCell(resultat.getString("NumAvionP"));
		    	
		    	String Termine = "Non";
				if(resultat.getString("Termine").equals("N")) Termine = "Non";
		    	else if(resultat.getString("Termine").equals("O")) Termine = "Oui";
		    	TabVolsF.addCell(Termine);
		    	
		    	// recupération de la liste des pilotes affectes a ce vol
		    	/*System.out.println("test");
		    	try{
		    		ResultatPilotes = VFDAO.ListPilotesAffecter(resultat.getString("NumVolF"), resultat.getDate("DateVolF"));
					System.out.println("size "+ResultatPilotes.getFetchSize()); 
		    		while(ResultatPilotes.next()){ 
						  Pilotes = Pilotes+"("+ResultatPilotes.getString("NumPersoP")+","
					  	           + ""+ResultatPilotes.getString("NomP")+","
						           + ""+ResultatPilotes.getString("PrenomP")+") "; 
					  } 
				}catch(Exception e){ System.out.println("erreur exception liste des pilotes d'un vol"); }
		    
		    	TabVolsF.addCell(Pilotes);
		    	*/
			}
		}catch(Exception e){ System.out.println("erreur exception liste des vols"); }
			
		//creation de tableau d'affichage
		System.out.println(TabVolsF.render());
		
		//fin
		System.out.println("****************************************************\n");
		
	}

	
	
	public void EditVol() {
		
		//debut
		System.out.println("===> Modifier un vol \n");
		
		Table TabAvions = new Table(4);
		Table TabPilotes = new Table(5);
		ResultSet resultat = null;
		List NumAvionFList = new ArrayList();
		List NumPersoPList = new ArrayList();
		List NumPersoPRetenu = new ArrayList();
		int Nb1Cl = 0, Nb2Cl = 0;
		int NbrPilotes = 0;
		String ModeleAvion = null;
		
		
		//afficher la liste des vols
		ShowListeVols();
		
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
						
		//creation objet vol passager
		VolsPassager VolP = new VolsPassager();
		
		System.out.println("Entrer le numéro de vol que vous voulez changer : ");
		String NumVolP = LectureClavier.lireChaine();
		
		//recupération dee donnees du vol
		ResultSet InfoVolP = VPDAO.InfosVolP(NumVolP);
		try { InfoVolP.next(); 
		      VolP.setNumVolP(NumVolP);
		      VolP.setDateVolP(InfoVolP.getDate("DateVolP"));
		      VolP.setOrigine(InfoVolP.getString("Origine"));
		      VolP.setDestination(InfoVolP.getString("Destination"));
	       	  VolP.setHeureDepGMT(InfoVolP.getTimestamp("HeureDepGMT"));
	       	  VolP.setDuree(InfoVolP.getInt("Duree"));
		      VolP.setDistance(InfoVolP.getInt("Distance"));
		      VolP.setNb2ClMin(InfoVolP.getInt("Nb1ClMin"));
		      VolP.setNb1ClMin(InfoVolP.getInt("Nb2ClMin"));
		      VolP.setNumAvionP(InfoVolP.getInt("NumAvionP"));
		      VolP.setTerminer(InfoVolP.getBoolean("Terminer"));
		      
		      //affectation pilotes
		      ResultSet InfoPilotesAffecter = VPDAO.InfoPilotesAffecter(VolP.getNumVolP(), VolP.getDateVolP());
				try { ResultSet InfoPilotes = null;
				      List ListPilotes = new ArrayList();
				      while(InfoPilotes.next()){
				         ListPilotes.add(InfoPilotesAffecter.getInt("NumPersoP"));
				      }
				      VolP.setAffectationP(ListPilotes);
				}catch(Exception e){ System.out.println("erreur exception liste pilotes affecter"); }
		      
		    
		} catch (SQLException e) {} //System.out.println("erreur exception infos du vol choisie"+e.getMessage()); }
		
		
		String Saisie = null;
		int SaisieInt = 0;
		
		//Numéro de vol
		System.out.println("Le numéro du vol : "+ VolP.getNumVolP());
			    
		//Origine
		System.out.println("L'origine : "+ VolP.getOrigine());
		Saisie = LectureClavier.lireChaine();
		if(Saisie != "") VolP.setOrigine(Saisie);
		
		//destination 
		System.out.println("La distination : "+ VolP.getDestination());
		Saisie = LectureClavier.lireChaine();
		if(Saisie != "") VolP.setDestination(Saisie);
		
		//Date
		System.out.println("La date : "+ VolP.getDateVolP());
		
		//heure
		System.out.println("L'Heure GMT : "+ VolP.getHeureDepGMT());
		Saisie = LectureClavier.lireChaine();
		//if(Saisie != "") VolF.setHeureDepGMT(java.sql.Timestamp.valueOf(Saisie));
		
		//Duree
		System.out.println("La durée (Min) : "+ VolP.getDuree());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolP.setDuree(SaisieInt);
		
		
		//Distance
		System.out.println("La distance (KM) : "+ VolP.getDistance());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolP.setDistance(SaisieInt);
		
		//avion 
		System.out.println("liste des avions disponibles :");
				
		// recupération de la liste a partir de la base de donnees
		// avion disponible + rayon d'action > distance de vol 
		resultat = VPDAO.ListAvionsDisponible();
			
		try{
				TabAvions.addCell("NumAvionF");    TabAvions.addCell("Modele");  TabAvions.addCell("VolumeMax");
				TabAvions.addCell("PoidsMax"); 
				  
				  while(resultat.next()){ 
				      
					  NumAvionFList.add(resultat.getInt("NumAvionF"));
					  TabAvions.addCell(resultat.getString("NumAvionF"));
					  TabAvions.addCell(resultat.getString("Modele"));
					  TabAvions.addCell(resultat.getString("VolumeMax"));
					  TabAvions.addCell(resultat.getString("PoidsMax"));
					  				 
				  }
		}catch(Exception e){ System.out.println("erreur exception afficher liste des avions disponibles"); }
				
		//creation de tableau d'affichage
		System.out.println(TabAvions.render());
				
		//test si l'entier entrer existe dans la table des avions deja afficher
		System.out.println("L'avion : "+ VolP.getNumAvionP());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolP.setNumAvionP(SaisieInt);
		
		//volume et poids d'avion
		ResultSet InfoAvion = VPDAO.InfosAvion(VolP.getNumAvionP());
		try { InfoAvion.next(); 
			  Nb1Cl = InfoAvion.getInt("Nb1Cl");
			  Nb2Cl  = InfoAvion.getInt("Nb2Cl");
			  NbrPilotes = InfoAvion.getInt("NbPilotes");
			  ModeleAvion = InfoAvion.getString("Modele");
		} catch (SQLException e) { System.out.println("erreur exception afficher infos d'avion choisie"); }
				
				
		//volume min
		System.out.println("Les places 1 : "+ VolP.getNb1ClMin());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolP.setNb1ClMin(SaisieInt);
		
		//poids min
		System.out.println("Les places 2 : "+ VolP.getNb2ClMin());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolP.setNb2ClMin(SaisieInt);
		
		//pilotes disponibles et selon le modele d'avion choisie
		System.out.println("liste des pilotes disponibles :");
				
		//recupération de la liste a partir de la base de donnees
		ResultSet ResultatPilotes = VPDAO.ListPilotesDisponible();
				
				try{
				TabPilotes.addCell("NumPersoP");    TabPilotes.addCell("NomP");              
				TabPilotes.addCell("PrenomP");      TabPilotes.addCell("Pays");        
				TabPilotes.addCell("NbHeuresVolTotal");  

				  while(ResultatPilotes.next()){ 
				      
					  NumPersoPList.add(ResultatPilotes.getInt("NumPersoP"));
					  TabPilotes.addCell(ResultatPilotes.getString("NumPersoP"));
					  TabPilotes.addCell(ResultatPilotes.getString("NomP"));
					  TabPilotes.addCell(ResultatPilotes.getString("PrenomP"));
					  TabPilotes.addCell(ResultatPilotes.getString("Pays"));
					  TabPilotes.addCell(ResultatPilotes.getString("NbHeuresVolTotal"));
					  				 
				  }
				}catch(Exception e){ System.out.println("erreur exception afficher liste des pilotes"); }
				
		//creation de tableau d'affichage
		System.out.println(TabPilotes.render());
				
		//plusieurs pilotes
		System.out.println("Vous avez choisie une avion du modele "+ModeleAvion+" qui necessite "+NbrPilotes+" pilotes");
				
		//lecture du num entré
		System.out.println("Les pilotes : "+VolP.getAffectationP());
					if(LectureClavier.lireChaine() != ""){
						for (int i = 0; i < NbrPilotes; i++) {
							
							int Num = 0;
							Num = LectureClavier.lireEntier("Pilote "+i+" : ");
							
							//interdire d'ajouter le meme pilote deux fois
							//accepter juste les numeros afficher dans le tableau
							while(!NumPersoPList.contains(Num)){
								Num = LectureClavier.lireEntier("Pilote ici "+i+" : ");
							}
							
							if(NumPersoPList.contains(Num)) NumPersoPRetenu.add(Num);
							
						}
						
						//ajouter pilote
						VolP.setAffectationP(NumPersoPRetenu);
					}
		
		
		//pas de hottesses dans vol fret
		
		//ajouter vol dans la base de donnees
		VPDAO.update(VolP);
				
		//fin
	    System.out.println("****************************************************\n");
		
	}

	
	
	public void DeleteVol() {
		
		//debut
		System.out.println("===> Supprimer un vol \n");
				
		//afficher la liste des vols
		ShowListeVols();
						
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
		
		//creation objet vol passager
		VolsPassager VolP = new VolsPassager();
		
		System.out.println("Entrer le numéro de vol que vous voulez supprimer : ");
		VolP.setNumVolP(LectureClavier.lireChaine());
		        
		VPDAO.delete(VolP);
				
		//fin
		System.out.println("****************************************************\n");
		
	}

	
	
	public void ConfirmVol() {
		
		//debut
		System.out.println("===> Confirmer la fin d'un vol \n");
		
		//afficher la liste des vols
		ShowListeVols();
				
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
        
		System.out.println("Entrer le numéro de vol que vous voulez valider : ");
		String NumVolP = LectureClavier.lireChaine();
        
		VPDAO.ValiderVolP(NumVolP);
		
		//fin
	    System.out.println("****************************************************\n");
		
	}

}
