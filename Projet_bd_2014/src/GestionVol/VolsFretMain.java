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
		
		boolean erreur = false;
		String[] AllMsg = {"Default","Default message d'erreur"};
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
		
		
		do{
		  try{
			    //Numéro de vol
				if(VolF.getNumVolF() == null){
					System.out.println("Veuillez choisir le numéro du vol :");
					VolF.setNumVolF(LectureClavier.lireChaine());	
				}else{
					System.out.println("Le numéro du vol : "+ VolF.getNumVolF());
				}
				
				//Origine
				if(VolF.getOrigine() == null){
				    System.out.println("Veuillez choisir l'origine du vol :");
				    VolF.setOrigine(LectureClavier.lireChaine());
				}else{
					System.out.println("L'origine : "+ VolF.getOrigine());
				}
				
				//destination 
				if(VolF.getDestination() == null){
					System.out.println("La destination :");
					VolF.setDestination(LectureClavier.lireChaine());
				}else{
					System.out.println("La distination : "+ VolF.getDestination());
				}
				
				//Date
				if(VolF.getDateVolF() == null){
					System.out.println("La date (YYYY-MM-DD) :");
					VolF.setDateVolF(java.sql.Date.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("La date : "+ VolF.getDateVolF());
				}
				
				//heure
				if(VolF.getHeureDepGMT() == null){
					System.out.println("L'heure GMT :");
					VolF.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("L'Heure GMT : "+ VolF.getHeureDepGMT());
				}
				
				//Duree
				if(VolF.getDuree() == 0){
					VolF.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				}else{
					System.out.println("La durée (Min) : "+ VolF.getDuree());
				}
				
				//Distance
				if(VolF.getDistance() == 0){
					VolF.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				}else{
					System.out.println("La distance (KM) : "+ VolF.getDistance());
				}
				
				
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
				if(VolF.getNumAvionF() == 0){
					while(!NumAvionFList.contains(VolF.getNumAvionF())){
						VolF.setNumAvionF(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
					}
				}else{
					System.out.println("L'avion : "+ VolF.getNumAvionF());
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
				if(VolF.getVolumeMin() == 0){
					VolF.setVolumeMin(LectureClavier.lireEntier("Veuillez choisir un volume min (entre 1 et "+VolumeMax+" supporter par l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le volume : "+ VolF.getVolumeMin());
				}
				
				//poids min
				if(VolF.getPoidsMin() == 0){
					VolF.setPoidsMin(LectureClavier.lireEntier("Veuillez choisir un poids min (entre 1 et "+PoidsMax+" supporter par l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le poids (Kg) : "+ VolF.getPoidsMin());
				}
				
				
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
				if(VolF.getAffectationP().isEmpty()){
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
					
				}else{
					System.out.println("Les pilotes : "+VolF.getAffectationP());	
				}
				
						
				//pas de hottesses dans vol fret
				
				//ajouter vol dans la base de donnees
				VFDAO.create(VolF);
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
			    	 VolF.setDestination("");
				 break;
				 
			     case "NumVolF": // deja un vol avec le meme numero et la meme date
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setDateVolF(null);	
				 break;
				 
			     case "Date": // date vol > hier ou aujourd hui
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setDateVolF(null);	
				 break;
				 
			     case "Volume": // volume min < volume max 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setVolumeMin(0);	
				 break;
				 
			     case "Poids": // poids min < poids max 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setPoidsMin(0);	
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
		VolsFretDAO VFDAO = new VolsFretDAO();
						
		// recupération de la liste a partir de la base de donnees
		ResultSet resultat = VFDAO.ShowList();
		
		Table TabVolsF = new Table(12);
			
		try{
			TabVolsF.addCell("NumVolF");      TabVolsF.addCell("DateVolF");  
			TabVolsF.addCell("HeureDepGMT");  TabVolsF.addCell("Origine"); 
			TabVolsF.addCell("Destination");  TabVolsF.addCell("Duree");
			TabVolsF.addCell("Distance");     TabVolsF.addCell("VolumeMin");
			TabVolsF.addCell("PoidsMin");     TabVolsF.addCell("NumAvionF");
			TabVolsF.addCell("Termine");      TabVolsF.addCell("Pilotes");
		    
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
		    	
		    	String Termine;
				if(resultat.getString("Termine") == "N") Termine = "Non";
		    	else Termine = "Oui";
		    	TabVolsF.addCell(Termine);
		    	
		    	// recupération de la liste des pilotes affectes a ce vol
		    	ResultSet ResultatPilotes = VFDAO.ListPilotesAffecter(resultat.getInt("NumVolF"), resultat.getString("DateVolF"));
				String Pilotes = null;
				try{
				  while(ResultatPilotes.next()){ 
					  Pilotes = Pilotes+"("+ResultatPilotes.getString("NumPersoP")+","
						  	           + ""+ResultatPilotes.getString("NomP")+","
							           + ""+ResultatPilotes.getString("PrenomP")+") "; 
				  }
				  TabVolsF.addCell(Pilotes);
				}catch(Exception e){ System.out.println("erreur exception"); }
				
			}
		}catch(Exception e){ System.out.println("erreur exception"); }
			
		//creation de tableau d'affichage
		System.out.println(TabVolsF.render());
		
		
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
		
		//recupération dee donnees du vol
		ResultSet InfoVolF = VFDAO.InfosVolF(LectureClavier.lireEntier("Entrer le numéro de vol que vous voulez changer : "));
		try { InfoVolF.next(); 
		      VolF.setNumVolF(InfoVolF.getString("NumVolF"));
		      VolF.setDateVolF(java.sql.Date.valueOf(InfoVolF.getString("DateVolF")));
		      VolF.setOrigine(InfoVolF.getString("Origine"));
		      VolF.setDestination(InfoVolF.getString("Destination"));
	       	  VolF.setHeureDepGMT(java.sql.Timestamp.valueOf(InfoVolF.getString("HeurDepGMT")));
		      VolF.setDuree(InfoVolF.getInt("Duree"));
		      VolF.setDistance(InfoVolF.getInt("Distance"));
		      VolF.setVolumeMin(InfoVolF.getFloat("VolumeMin"));
		      VolF.setPoidsMin(InfoVolF.getFloat("PoidsMin"));
		      VolF.setNumAvionF(InfoVolF.getInt("NumAvionF"));
		      VolF.setTerminer(InfoVolF.getBoolean("NumAvionF"));
		      
		      //affectation pilotes
		      ResultSet InfoPilotesAffecter = VFDAO.InfoPilotesAffecter(VolF.getNumVolF(), VolF.getDateVolF());
				try { ResultSet InfoPilotes = null;
				      List ListPilotes = new ArrayList();
				      while(InfoPilotes.next()){
				         ListPilotes.add(InfoPilotesAffecter.getInt("NumPersoP"));
				      }
				      VolF.setAffectationP(ListPilotes);
				}catch(Exception e){ System.out.println("erreur exception liste pilotes affecter"); }
		    
		} catch (SQLException e) { System.out.println("erreur exception infos du vol choisie"); }
		
		
		do{
		  try{
			    //Numéro de vol
				if(VolF.getNumVolF() == null){
					System.out.println("Veuillez choisir le numéro du vol :");
					VolF.setNumVolF(LectureClavier.lireChaine());	
				}else{
					System.out.println("Le numéro du vol : "+ VolF.getNumVolF());
				}
				
				//Origine
				if(VolF.getOrigine() == null){
				    System.out.println("Veuillez choisir l'origine du vol :");
				    VolF.setOrigine(LectureClavier.lireChaine());
				}else{
					System.out.println("L'origine : "+ VolF.getOrigine());
				}
				
				//destination 
				if(VolF.getDestination() == null){
					System.out.println("La destination :");
					VolF.setDestination(LectureClavier.lireChaine());
				}else{
					System.out.println("La distination : "+ VolF.getDestination());
				}
				
				//Date
				if(VolF.getDateVolF() == null){
					System.out.println("La date (YYYY-MM-DD) :");
					VolF.setDateVolF(java.sql.Date.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("La date : "+ VolF.getDateVolF());
				}
				
				//heure
				if(VolF.getHeureDepGMT() == null){
					System.out.println("L'heure GMT :");
					VolF.setHeureDepGMT(java.sql.Timestamp.valueOf(LectureClavier.lireChaine()));
				}else{
					System.out.println("L'Heure GMT : "+ VolF.getHeureDepGMT());
				}
				
				//Duree
				if(VolF.getDuree() == 0){
					VolF.setDuree(LectureClavier.lireEntier("La durée (Min) :"));
				}else{
					System.out.println("La durée (Min) : "+ VolF.getDuree());
				}
				
				//Distance
				if(VolF.getDistance() == 0){
					VolF.setDistance(LectureClavier.lireEntier("La distance (Km) :"));
				}else{
					System.out.println("La distance (KM) : "+ VolF.getDistance());
				}
				
				
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
				if(VolF.getNumAvionF() == 0){
					while(!NumAvionFList.contains(VolF.getNumAvionF())){
						VolF.setNumAvionF(LectureClavier.lireEntier("Veuillez choisir un numéro d'avion : "));
					}
				}else{
					System.out.println("L'avion : "+ VolF.getNumAvionF());
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
				if(VolF.getVolumeMin() == 0){
					VolF.setVolumeMin(LectureClavier.lireEntier("Veuillez choisir un volume min (entre 1 et "+VolumeMax+" supporter par l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le volume : "+ VolF.getVolumeMin());
				}
				
				//poids min
				if(VolF.getPoidsMin() == 0){
					VolF.setPoidsMin(LectureClavier.lireEntier("Veuillez choisir un poids min (entre 1 et "+PoidsMax+" supporter par l'avion choisie) pour ce vol : "));
				}else{
					System.out.println("Le poids (Kg) : "+ VolF.getPoidsMin());
				}
				
				
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
				if(VolF.getAffectationP().isEmpty()){
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
					
				}else{
					System.out.println("Les pilotes : "+VolF.getAffectationP());	
				}
				
						
				//pas de hottesses dans vol fret
				
				//ajouter vol dans la base de donnees
				VFDAO.create(VolF);
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
			    	 VolF.setDestination("");
				 break;
				 
			     case "NumVolF": // deja un vol avec le meme numero et la meme date
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setDateVolF(null);	
				 break;
				 
			     case "Date": // date vol > hier ou aujourd hui
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setDateVolF(null);	
				 break;
				 
			     case "Volume": // volume min < volume max 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setVolumeMin(0);	
				 break;
				 
			     case "Poids": // poids min < poids max 
			    	 System.out.println("Erreur : "+AllMsg[1]);
			    	 VolF.setPoidsMin(0);	
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
		
        boolean erreur = false;
		
		do{
		  try{
			
			
			
			
			
			  erreur = false;
		  }catch(Exception e){
			  
			//recuperation du message d'erreur
			  String Msg = e.getMessage();
			  String[] AllMsg = null; 
			  if(Msg != null) AllMsg = Msg.split(",");
			  else { AllMsg[0] = ""; AllMsg[1]= ""; }
			  
			  //afficher le message d'erreur et initialiser l'attribut
			  switch (AllMsg[0]) {
			     case value:
				
				 break;

			     default:
				 break;
			  }
			  
			  erreur = true;
		  }
		}while(erreur = true);
		
		//fin
	    System.out.println("****************************************************\n");
		
	}

	
	
	public void ConfirmVol() {
		
		//debut
		System.out.println("===> Confirmer la fin d'un vol \n");
		
        boolean erreur = false;
		
		do{
		  try{
			
			
			
			
			
			  erreur = false;
		  }catch(Exception e){
			  
			//recuperation du message d'erreur
			  String Msg = e.getMessage();
			  String[] AllMsg = null; 
			  if(Msg != null) AllMsg = Msg.split(",");
			  else { AllMsg[0] = ""; AllMsg[1]= ""; }
			  
			  //afficher le message d'erreur et initialiser l'attribut
			  switch (AllMsg[0]) {
			     case value:
				
				 break;

			     default:
				 break;
			  }
			
			  erreur = true;
		  }
		}while(erreur = true);
		
		//fin
	    System.out.println("****************************************************\n");
		
	}

}
