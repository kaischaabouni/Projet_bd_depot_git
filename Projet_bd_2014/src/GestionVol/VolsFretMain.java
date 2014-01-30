
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

public class VolsFretMain {
    
	
	public void CreateVol() {
		
		//debut
		System.out.println("===> Ajouter un nouveau vol Fret\n");
		
		Table TabAvions = new Table(4);
		Table TabPilotes = new Table(5);
		ResultSet resultat;
		List NumAvionFList = new ArrayList();
		List NumPersoPList = new ArrayList();
		List NumPersoPRetenu = new ArrayList();
		float VolumeMax = 0, PoidsMax = 0;
		int NbrPilotes = 0;
		String ModeleAvion = null;
		
		//creation Data Access Object
		VolsFretDAO VFDAO = new VolsFretDAO();
				
		//creation objet vol passager
		VolsFret VolF = new VolsFret();
		
		//Numéro de vol
		System.out.println("Veuillez choisir le numéro du vol :");
		VolF.setNumVolF(LectureClavier.lireChaine());	
		
		//Origine
		System.out.println("Veuillez choisir l'origine du vol :");
		VolF.setOrigine(LectureClavier.lireChaine());
		
		//destination 
		System.out.println("La destination :");
		VolF.setDestination(LectureClavier.lireChaine());
		
		//Date
		System.out.println("La date (YYYY-MM-DD) :");
		VolF.setDateVolF(java.sql.Date.valueOf(LectureClavier.lireChaine()));
		
		//heure
		System.out.println("L'heure GMT :");
		VolF.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
			
		//Duree
		VolF.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				
		//Distance
		VolF.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				
		//avion 
		System.out.println("liste des avions disponibles :");
				
		// recupération de la liste a partir de la base de donnees
		// avion disponible + rayon d'action > distance de vol 
		resultat = VFDAO.ListAvionsDisponible();
			
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
		while(!NumAvionFList.contains(VolF.getNumAvionF())){
			VolF.setNumAvionF(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
		}
				
		//volume et poids d'avion
		ResultSet InfoAvion = VFDAO.InfosAvion(VolF.getNumAvionF());
		try { InfoAvion.next(); 
			  VolumeMax = InfoAvion.getFloat("VolumeMax");
			  PoidsMax  = InfoAvion.getFloat("PoidsMax");
			  NbrPilotes = InfoAvion.getInt("NbPilotes");
		      ModeleAvion = InfoAvion.getString("Modele");
		} catch (SQLException e) { System.out.println("erreur exception afficher infos d'avion choisie"); }
				
		//volume min
		VolF.setVolumeMin(LectureClavier.lireEntier("Veuillez choisir un volume min (entre 1 et "+VolumeMax+" supporter par l'avion choisie) pour ce vol : "));
				
		//poids min
		VolF.setPoidsMin(LectureClavier.lireEntier("Veuillez choisir un poids min (entre 1 et "+PoidsMax+" supporter par l'avion choisie) pour ce vol : "));
				
		//pilotes disponibles et selon le modele d'avion choisie
		System.out.println("liste des pilotes disponibles :");
				
		//recupération de la liste a partir de la base de donnees
		ResultSet ResultatPilotes = VFDAO.ListPilotesDisponible();
				
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
		VolF.setAffectationP(NumPersoPRetenu);
					
		//pas de hottesses dans vol fret
				
		//ajouter vol dans la base de donnees
		VFDAO.create(VolF);
				
		//fin
	    System.out.println("****************************************************\n");
	}

	
	
	public void ShowListeVols() {
		
		//debut
		System.out.println("===> Consulter la liste des vol \n");
				
		//creation Data Access Object
		VolsFretDAO VFDAO = new VolsFretDAO();
						
		// recupération de la liste a partir de la base de donnees
		ResultSet resultat = VFDAO.ShowList();
		
		Table TabVolsF = new Table(11);
		ResultSet ResultatPilotes = null;
    	String Pilotes = null;
		
		try{
			TabVolsF.addCell("NumVolF");      TabVolsF.addCell("DateVolF");  
			TabVolsF.addCell("HeureDepGMT");  TabVolsF.addCell("Origine"); 
			TabVolsF.addCell("Destination");  TabVolsF.addCell("Duree");
			TabVolsF.addCell("Distance");     TabVolsF.addCell("VolumeMin");
			TabVolsF.addCell("PoidsMin");     TabVolsF.addCell("NumAvionF");
			TabVolsF.addCell("Termine");      //TabVolsF.addCell("Pilotes");
		    
		    while(resultat.next()){ 
				
		    	TabVolsF.addCell(resultat.getString("NumVolF"));
		    	TabVolsF.addCell(resultat.getString("DateVol"));
		    	TabVolsF.addCell(resultat.getString("HeureDep"));
		    	TabVolsF.addCell(resultat.getString("Origine"));
		    	TabVolsF.addCell(resultat.getString("Destination"));
		    	TabVolsF.addCell(resultat.getString("Duree"));
		    	TabVolsF.addCell(resultat.getString("Distance"));
		    	TabVolsF.addCell(resultat.getString("VolumeMin"));
		    	TabVolsF.addCell(resultat.getString("PoidsMin"));
		    	TabVolsF.addCell(resultat.getString("NumAvionF"));
		    	
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
		float VolumeMax = 0, PoidsMax = 0;
		int NbrPilotes = 0;
		String ModeleAvion = null;
		
		
		//afficher la liste des vols
		ShowListeVols();
		
		//creation Data Access Object
		VolsFretDAO VFDAO = new VolsFretDAO();
						
		//creation objet vol passager
		VolsFret VolF = new VolsFret();
		
		System.out.println("Entrer le numéro de vol que vous voulez changer : ");
		String NumVolF = LectureClavier.lireChaine();
		
		//recupération dee donnees du vol
		ResultSet InfoVolF = VFDAO.InfosVolF(NumVolF);
		try { InfoVolF.next(); 
		      VolF.setNumVolF(NumVolF);
		      VolF.setDateVolF(InfoVolF.getDate("DateVolF"));
		      VolF.setOrigine(InfoVolF.getString("Origine"));
		      VolF.setDestination(InfoVolF.getString("Destination"));
	       	  VolF.setHeureDepGMT(InfoVolF.getTimestamp("HeureDepGMT"));
	       	  VolF.setDuree(InfoVolF.getInt("Duree"));
		      VolF.setDistance(InfoVolF.getInt("Distance"));
		      VolF.setVolumeMin(InfoVolF.getFloat("VolumeMin"));
		      VolF.setPoidsMin(InfoVolF.getFloat("PoidsMin"));
		      VolF.setNumAvionF(InfoVolF.getInt("NumAvionF"));
		      VolF.setTerminer(InfoVolF.getBoolean("Terminer"));
		      
		      //affectation pilotes
		      ResultSet InfoPilotesAffecter = VFDAO.InfoPilotesAffecter(VolF.getNumVolF(), VolF.getDateVolF());
				try { ResultSet InfoPilotes = null;
				      List ListPilotes = new ArrayList();
				      while(InfoPilotes.next()){
				         ListPilotes.add(InfoPilotesAffecter.getInt("NumPersoP"));
				      }
				      VolF.setAffectationP(ListPilotes);
				}catch(Exception e){ System.out.println("erreur exception liste pilotes affecter"); }
		      
		    
		} catch (SQLException e) {} //System.out.println("erreur exception infos du vol choisie"+e.getMessage()); }
		
		
		String Saisie = null;
		int SaisieInt = 0;
		
		//Numéro de vol
		System.out.println("Le numéro du vol : "+ VolF.getNumVolF());
			    
		//Origine
		System.out.println("L'origine : "+ VolF.getOrigine());
		Saisie = LectureClavier.lireChaine();
		if(Saisie != "") VolF.setOrigine(Saisie);
		
		//destination 
		System.out.println("La distination : "+ VolF.getDestination());
		Saisie = LectureClavier.lireChaine();
		if(Saisie != "") VolF.setDestination(Saisie);
		
		//Date
		System.out.println("La date : "+ VolF.getDateVolF());
		
		//heure
		System.out.println("L'Heure GMT : "+ VolF.getHeureDepGMT());
		Saisie = LectureClavier.lireChaine();
		//if(Saisie != "") VolF.setHeureDepGMT(java.sql.Timestamp.valueOf(Saisie));
		
		//Duree
		System.out.println("La durée (Min) : "+ VolF.getDuree());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolF.setDuree(SaisieInt);
		
		
		//Distance
		System.out.println("La distance (KM) : "+ VolF.getDistance());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolF.setDistance(SaisieInt);
		
		//avion 
		System.out.println("liste des avions disponibles :");
				
		// recupération de la liste a partir de la base de donnees
		// avion disponible + rayon d'action > distance de vol 
		resultat = VFDAO.ListAvionsDisponible();
			
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
		System.out.println("L'avion : "+ VolF.getNumAvionF());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolF.setNumAvionF(SaisieInt);
		
		//volume et poids d'avion
		ResultSet InfoAvion = VFDAO.InfosAvion(VolF.getNumAvionF());
		try { InfoAvion.next(); 
			  VolumeMax = InfoAvion.getFloat("VolumeMax");
			  PoidsMax  = InfoAvion.getFloat("PoidsMax");
			  NbrPilotes = InfoAvion.getInt("NbPilotes");
			  ModeleAvion = InfoAvion.getString("Modele");
		} catch (SQLException e) { System.out.println("erreur exception afficher infos d'avion choisie"); }
				
				
		//volume min
		System.out.println("Le volume : "+ VolF.getVolumeMin());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolF.setVolumeMin(SaisieInt);
		
		//poids min
		System.out.println("Le poids (Kg) : "+ VolF.getPoidsMin());
		SaisieInt = LectureClavier.lireEntier("");
		if(SaisieInt != 0) VolF.setPoidsMin(SaisieInt);
		
		//pilotes disponibles et selon le modele d'avion choisie
		System.out.println("liste des pilotes disponibles :");
				
		//recupération de la liste a partir de la base de donnees
		ResultSet ResultatPilotes = VFDAO.ListPilotesDisponible();
				
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
		System.out.println("Les pilotes : "+VolF.getAffectationP());
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
						VolF.setAffectationP(NumPersoPRetenu);
					}
		
		
		//pas de hottesses dans vol fret
		
		//ajouter vol dans la base de donnees
		VFDAO.update(VolF);
				
		//fin
	    System.out.println("****************************************************\n");
		
	}

	
	
	public void DeleteVol() {
		
		//debut
		System.out.println("===> Supprimer un vol \n");
				
		//afficher la liste des vols
		ShowListeVols();
						
		//creation Data Access Object
		VolsFretDAO VFDAO = new VolsFretDAO();
		
		//creation objet vol passager
		VolsFret VolF = new VolsFret();
		
		System.out.println("Entrer le numéro de vol que vous voulez supprimer : ");
		VolF.setNumVolF(LectureClavier.lireChaine());
		        
		VFDAO.delete(VolF);
				
		//fin
		System.out.println("****************************************************\n");
		
	}

	
	
	public void ConfirmVol() {
		
		//debut
		System.out.println("===> Confirmer la fin d'un vol \n");
		
		//afficher la liste des vols
		ShowListeVols();
				
		//creation Data Access Object
		VolsFretDAO VFDAO = new VolsFretDAO();
        
		System.out.println("Entrer le numéro de vol que vous voulez valider : ");
		String NumVolF = LectureClavier.lireChaine();
        
		VFDAO.ValiderVolF(NumVolF);
		
		//fin
	    System.out.println("****************************************************\n");
		
	}

}
