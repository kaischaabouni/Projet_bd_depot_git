
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
		
		boolean erreur = false;
		String[] AllMsg = {"Default","Default message d'erreur"};
		Table TabAvions = new Table(4);
		Table TabPilotes = new Table(5);
		ResultSet resultat;
		List NumAvionPList = new ArrayList();
		List NumPersoPList = new ArrayList();
		List NumPersoPRetenu = new ArrayList();
		int Nb1Cl = 0, Nb2Cl = 0;
		int NbrPilotes = 0,  NbrHotesses = 0;
		String ModeleAvion = null;
		
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
				
		//creation objet vol passager
		VolsPassager VolP = new VolsPassager();
		
		
		do{
		  try{
			    //Numéro de vol
				if(VolP.getNumVolP() == null){
					System.out.println("Veuillez choisir le numéro du vol :");
					VolP.setNumVolP(LectureClavier.lireChaine());	
				}else{
					System.out.println("Le numéro du vol : "+ VolP.getNumVolP());
				}
				
				//Origine
				if(VolP.getOrigine() == null){
				    System.out.println("Veuillez choisir l'origine du vol :");
				    VolP.setOrigine(LectureClavier.lireChaine());
				}else{
					System.out.println("L'origine : "+ VolP.getOrigine());
				}
				
				//destination 
				if(VolP.getDestination() == null){
					System.out.println("La destination :");
					VolP.setDestination(LectureClavier.lireChaine());
				}else{
					System.out.println("La distination : "+ VolP.getDestination());
				}
				
				//Date
				if(VolP.getDateVolP() == null){
					System.out.println("La date (YYYY-MM-DD) :");
					VolP.setDateVolP(java.sql.Date.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("La date : "+ VolP.getDateVolP());
				}
				
				//heure
				if(VolP.getHeureDepGMT() == null){
					System.out.println("L'heure GMT :");
					VolP.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("L'Heure GMT : "+ VolP.getHeureDepGMT());
				}
				
				//Duree
				if(VolP.getDuree() == 0){
					VolP.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				}else{
					System.out.println("La durée (Min) : "+ VolP.getDuree());
				}
				
				//Distance
				if(VolP.getDistance() == 0){
					VolP.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				}else{
					System.out.println("La distance (KM) : "+ VolP.getDistance());
				}
				
				
				//avion 
				System.out.println("liste des avions disponibles :");
				
				// recupération de la liste a partir de la base de donnees
				// avion disponible + rayon d'action > distance de vol 
				resultat = VPDAO.ListAvionsDisponible();
			
				try{
				TabAvions.addCell("NumAvionP");    TabAvions.addCell("Modele");  
				TabAvions.addCell("Nb1Cl");        TabAvions.addCell("Nb2Cl"); 
				  
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
				if(VolP.getNumAvionP() == 0){
					while(!NumAvionPList.contains(VolP.getNumAvionP())){
						VolP.setNumAvionP(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
					}
				}else{
					System.out.println("L'avion : "+ VolP.getNumAvionP());
				}
				
				
				//Nombre de places d'avion
				ResultSet InfoAvion = VPDAO.InfosAvion(VolP.getNumAvionP());
				try { InfoAvion.next(); 
				      Nb1Cl = InfoAvion.getInt("Nb1Cl");
				      Nb2Cl  = InfoAvion.getInt("Nb2Cl");
				      NbrPilotes = InfoAvion.getInt("NbPilotes");
				      ModeleAvion = InfoAvion.getString("Modele");
				      NbrHotesses = (Nb1Cl + Nb2Cl)/20; 
				    } catch (SQLException e) { System.out.println("erreur exception afficher infos d'avion choisie"); }
				
				
				//Nombre de places 1 min
				if(VolP.getNb1ClMin() == 0){
					VolP.setNb1ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 1 min (entre 1 et "+Nb1Cl+" places dans l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le nombre des places 1 : "+ VolP.getNb1ClMin());
				}
				
				//Nombre de places 2 min
				if(VolP.getNb2ClMin() == 0){
					VolP.setNb2ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 2 min (entre 1 et "+Nb2Cl+" places dans l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le nombre des places 2 : "+ VolP.getNb2ClMin());
				}
				
				
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
				if(VolP.getAffectationP().isEmpty()){
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
					
				}else{
					System.out.println("Les pilotes : "+VolP.getAffectationP());	
				}
				
						
				//hottesses
				
				
				//ajouter vol dans la base de donnees
				VPDAO.create(VolP);
				erreur = false;
				
			
		  }catch(Exception e){
			  
			  //recuperation du message d'erreur
			  String Msg = e.getMessage();
			  if(Msg != null) AllMsg = Msg.split(",");
			  else { AllMsg[0] = ""; AllMsg[1]= ""; }
			  
			  //afficher le message d'erreur et initialiser l'attribut
			  switch (AllMsg[0]) {
			     
			     case "Destination": // destination != origine
				     System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setDestination("");
				 break;
				 
			     case "NumVolP": // deja un vol avec le meme numero et la meme date
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setDateVolP(null);	
				 break;
				 
			     case "Date": // date vol > hier ou aujourd hui
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setDateVolP(null);	
				 break;
				 
			     case "Nb1ClMin": // Nb1ClMin < Nb1Cl 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setNb1ClMin(0);	
				 break;
				 
			     case "Nb2ClMin": // Nb2ClMin < Nb2Cl 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setNb2ClMin(0);	
				 break;

			     default:
			    	 System.out.println("Erreur default : "+Msg);
			     return;
			  }
			  
			  erreur = true;
		  }
		}while(erreur == true);
		
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
		
		Table TabVolsP = new Table(12);
			
		try{
			TabVolsP.addCell("NumVolP");      TabVolsP.addCell("DateVolP");  
			TabVolsP.addCell("HeureDepGMT");  TabVolsP.addCell("Origine"); 
			TabVolsP.addCell("Destination");  TabVolsP.addCell("Duree");
			TabVolsP.addCell("Distance");     TabVolsP.addCell("Nb1ClMin");
			TabVolsP.addCell("Nb2ClMin");     TabVolsP.addCell("NumAvionP");
			TabVolsP.addCell("Termine");      TabVolsP.addCell("Pilotes");
		    
		    while(resultat.next()){ 
				      
		    	TabVolsP.addCell(resultat.getString("NumVolP"));
		    	TabVolsP.addCell(resultat.getString("DateVol"));
		    	TabVolsP.addCell(resultat.getString("HeureDep"));
		    	TabVolsP.addCell(resultat.getString("Origine"));
		    	TabVolsP.addCell(resultat.getString("Destination"));
		    	TabVolsP.addCell(resultat.getString("Duree"));
		    	TabVolsP.addCell(resultat.getString("Distance"));
		    	TabVolsP.addCell(resultat.getString("Nb1ClMin"));
		    	TabVolsP.addCell(resultat.getString("Nb2ClMin"));
		    	TabVolsP.addCell(resultat.getString("NumAvionP"));
		    	
		    	String Termine;
				if(resultat.getString("Termine") == "N") Termine = "Non";
		    	else Termine = "Oui";
		    	TabVolsP.addCell(Termine);
		    	
		    	// recupération de la liste des pilotes affectes a ce vol
		    	ResultSet ResultatPilotes = VPDAO.ListPilotesAffecter(resultat.getInt("NumVolP"), resultat.getString("DateVolP"));
				String Pilotes = null;
				try{
				  while(ResultatPilotes.next()){ 
					  Pilotes = Pilotes+"("+ResultatPilotes.getString("NumPersoP")+","
						  	           + ""+ResultatPilotes.getString("NomP")+","
							           + ""+ResultatPilotes.getString("PrenomP")+") "; 
				  }
				  TabVolsP.addCell(Pilotes);
				}catch(Exception e){ System.out.println("erreur exception"); }
				
			}
		}catch(Exception e){ System.out.println("erreur exception"); }
			
		//creation de tableau d'affichage
		System.out.println(TabVolsP.render());
		
		
		//fin
		System.out.println("****************************************************\n");
		
	}

	
	
	public void EditVol() {
		
		//debut
		System.out.println("===> Modifier un vol \n");
		
		boolean erreur = false;
		String[] AllMsg = {"Default","Default message d'erreur"};
		Table TabAvions = new Table(4);
		Table TabPilotes = new Table(5);
		ResultSet resultat = null;
		List NumAvionPList = new ArrayList();
		List NumPersoPList = new ArrayList();
		List NumPersoPRetenu = new ArrayList();
		int Nb1Cl = 0, Nb2Cl = 0;
		int NbrPilotes = 0, NbrHotesses = 0;
		String ModeleAvion = null;
		
		
		//afficher la liste des vols
		ShowListeVols();
		
		//creation Data Access Object
		VolsPassagerDAO VPDAO = new VolsPassagerDAO();
						
		//creation objet vol passager
		VolsPassager VolP = new VolsPassager();
		
		int NumVolP = LectureClavier.lireEntier("Entrer le numéro de vol que vous voulez changer : ");
		
		//recupération dee donnees du vol
		ResultSet InfoVolP = VPDAO.InfosVolP(NumVolP);
		try { InfoVolP.next(); 
		      VolP.setNumVolP(InfoVolP.getString("NumVolP"));
		      //VolP.setDateVolP(java.sql.Date.valueOf(InfoVolP.getString("DateVolP")));
		      VolP.setOrigine(InfoVolP.getString("Origine"));
		      VolP.setDestination(InfoVolP.getString("Destination"));
	       	  VolP.setHeureDepGMT(java.sql.Timestamp.valueOf(InfoVolP.getString("HeurDepGMT")));
		      VolP.setDuree(InfoVolP.getInt("Duree"));
		      VolP.setDistance(InfoVolP.getInt("Distance"));
		      VolP.setNb1ClMin(InfoVolP.getInt("Nb1ClMin"));
		      VolP.setNb2ClMin(InfoVolP.getInt("Nb2ClMin"));
		      VolP.setNumAvionP(InfoVolP.getInt("NumAvionP"));
		      VolP.setTerminer(InfoVolP.getBoolean("NumAvionF"));
		      
		      //affectation pilotes
		      ResultSet InfoPilotesAffecter = VPDAO.InfoPilotesAffecter(VolP.getNumVolP(), VolP.getDateVolP());
				try { ResultSet InfoPilotes = null;
				      List ListPilotes = new ArrayList();
				      while(InfoPilotes.next()){
				         ListPilotes.add(InfoPilotesAffecter.getInt("NumPersoP"));
				      }
				      VolP.setAffectationP(ListPilotes);
				}catch(Exception e){ System.out.println("erreur exception liste pilotes affecter"); }
		    
		} catch (SQLException e) { System.out.println("erreur exception infos du vol choisie"); }
		
		
		VolP.toString();
		
		
		do{
		  try{
			    //Numéro de vol
			    System.out.println("Le numéro du vol : "+ VolP.getNumVolP());
			    /*
			    if(VolF.getNumVolF() == null){
					System.out.println("Veuillez choisir le numéro du vol :");
					VolF.setNumVolF(LectureClavier.lireChaine());	
				}else{
					System.out.println("Le numéro du vol : "+ VolF.getNumVolF());
					if(LectureClavier.lireChaine() != "") VolF.setNumVolF(LectureClavier.lireChaine());
				}
				*/
			    
				//Origine
				if(VolP.getOrigine() == null){
				    System.out.println("Veuillez choisir l'origine du vol :");
				    VolP.setOrigine(LectureClavier.lireChaine());
				}else{
					System.out.println("L'origine : "+ VolP.getOrigine());
					if(LectureClavier.lireChaine() != "") VolP.setOrigine(LectureClavier.lireChaine());
				}
				
				//destination 
				if(VolP.getDestination() == null){
					System.out.println("La destination :");
					VolP.setDestination(LectureClavier.lireChaine());
				}else{
					System.out.println("La distination : "+ VolP.getDestination());
					if(LectureClavier.lireChaine() != "") VolP.setDestination(LectureClavier.lireChaine());
				}
				
				//Date
				System.out.println("La date : "+ VolP.getDateVolP());
				/*
				if(VolF.getDateVolF() == null){
					System.out.println("La date (YYYY-MM-DD) :");
					VolF.setDateVolF(java.sql.Date.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("La date : "+ VolF.getDateVolF());
					if(LectureClavier.lireChaine() != "") VolF.setDateVolF(java.sql.Date.valueOf(LectureClavier.lireChaine()));
				}
				*/
				//heure
				if(VolP.getHeureDepGMT() == null){
					System.out.println("L'heure GMT :");
					VolP.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("L'Heure GMT : "+ VolP.getHeureDepGMT());
					if(LectureClavier.lireChaine() != "") VolP.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
				}
				
				//Duree
				if(VolP.getDuree() == 0){
					VolP.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				}else{
					System.out.println("La durée (Min) : "+ VolP.getDuree());
					if(LectureClavier.lireChaine() != "") VolP.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				}
				
				//Distance
				if(VolP.getDistance() == 0){
					VolP.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				}else{
					System.out.println("La distance (KM) : "+ VolP.getDistance());
					if(LectureClavier.lireChaine() != "") VolP.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				}
				
				
				//avion 
				System.out.println("liste des avions disponibles :");
				
				// recupération de la liste a partir de la base de donnees
				// avion disponible + rayon d'action > distance de vol 
				resultat = VPDAO.ListAvionsDisponible();
			
				try{
				TabAvions.addCell("NumAvionP");    TabAvions.addCell("Modele");  TabAvions.addCell("Nb1ClMin");
				TabAvions.addCell("Nb2ClMin"); 
				  
				  while(resultat.next()){ 
				      
					  NumAvionPList.add(resultat.getInt("NumAvionP"));
					  TabAvions.addCell(resultat.getString("NumAvionP"));
					  TabAvions.addCell(resultat.getString("Modele"));
					  TabAvions.addCell(resultat.getString("Nb1ClMin"));
					  TabAvions.addCell(resultat.getString("Nb2ClMin"));
					  				 
				  }
				}catch(Exception e){ System.out.println("erreur exception afficher liste des avions disponibles"); }
				
				//creation de tableau d'affichage
				System.out.println(TabAvions.render());
				
				//test si l'entier entrer existe dans la table des avions deja afficher
				if(VolP.getNumAvionP() == 0){
					while(!NumAvionPList.contains(VolP.getNumAvionP())){
						VolP.setNumAvionP(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
					}
				}else{
					System.out.println("L'avion : "+ VolP.getNumAvionP());
					if(LectureClavier.lireChaine() != "") VolP.setNumAvionP(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
				}
				
				
				//Infos d'avion
				ResultSet InfoAvion = VPDAO.InfosAvion(VolP.getNumAvionP());
				try { InfoAvion.next(); 
				      Nb1Cl = InfoAvion.getInt("Nb1Cl");
				      Nb2Cl = InfoAvion.getInt("Nb2Cl");
				      NbrPilotes = InfoAvion.getInt("NbPilotes");
				      ModeleAvion = InfoAvion.getString("Modele");
				      NbrHotesses = (Nb1Cl + Nb2Cl)/20;
				    } catch (SQLException e) { System.out.println("erreur exception afficher infos d'avion choisie"); }
				
				
				//Nombre de places 1
				if(VolP.getNb1ClMin() == 0){
					VolP.setNb1ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 1 min (entre 1 et "+Nb1Cl+" places dans l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le nombre de places 1 : "+ VolP.getNb1ClMin());
					if(LectureClavier.lireChaine() != "") VolP.setNb1ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 1 min (entre 1 et "+Nb1Cl+" places dans l'avion choisie) pour ce vol : "));
				}
				
				//Nombre de places 2
				if(VolP.getNb2ClMin() == 0){
					VolP.setNb2ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 2 min (entre 1 et "+Nb1Cl+" places dans l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le nombre de places 2 : "+ VolP.getNb2ClMin());
					if(LectureClavier.lireChaine() != "") VolP.setNb2ClMin(LectureClavier.lireEntier("Veuillez choisir le nombre de places 2 min (entre 1 et "+Nb1Cl+" places dans l'avion choisie) pour ce vol : "));
				}
				
				
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
				if(VolP.getAffectationP().isEmpty()){
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
					
				}else{
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
				}
				
						
				//pas de hottesses dans vol fret
				
				//ajouter vol dans la base de donnees
				VPDAO.update(VolP);
				erreur = false;
				
			
		  }catch(Exception e){
			  
			  //recuperation du message d'erreur
			  String Msg = e.getMessage();
			  if(Msg != null) AllMsg = Msg.split(",");
			  else { AllMsg[0] = ""; AllMsg[1]= ""; }
			  
			//afficher le message d'erreur et initialiser l'attribut
			  switch (AllMsg[0]) {
			     
			     case "Destination": // destination != origine
				     System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setDestination("");
				 break;
				 
			     case "NumVolP": // deja un vol avec le meme numero et la meme date
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setDateVolP(null);	
				 break;
				 
			     case "Date": // date vol > hier ou aujourd hui
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setDateVolP(null);	
				 break;
				 
			     case "Nb1ClMin": // Nb1ClMin < Nb1Cl 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setNb1ClMin(0);	
				 break;
				 
			     case "Nb2ClMin": // Nb2ClMin < Nb2Cl 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolP.setNb2ClMin(0);	
				 break;

			     default:
			    	 System.out.println("Erreur default : "+Msg);
			     return;
			  }
			  
			  erreur = true;
		  }
		}while(erreur == true);
		
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
								
		int NumVolP = LectureClavier.lireEntier("Entrer le numéro de vol que vous voulez valider : ");
        
		VPDAO.ValiderVolP(NumVolP);
		
		//fin
	    System.out.println("****************************************************\n");
		
	}

}

