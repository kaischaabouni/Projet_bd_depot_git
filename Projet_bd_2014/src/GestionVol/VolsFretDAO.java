
package GestionVol;

import Main.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class VolsFretDAO extends DAO<VolsFret>{
    
	ResultSet resultat;
	
	//afficher la liste des vols fret
	public ResultSet ShowList() {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select NumVolF, Origine, Destination, Duree, Distance, VolumeMin, PoidsMin, NumAvionF, Termine, "
					                                + "TO_CHAR(DateVolF, 'DD/MM/YYYY') as datevol, "
					                                + "to_char(HeureDepGMT,'HH24:MI:SS') as heuredep  from volsfret ");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;	
	}

	
	//ajouter un nouveau vol fret
	public void create(VolsFret obj){
		try{
	        Statement requete = cn.createStatement();
			requete.executeQuery("insert into volsfret "
					           + "values('"+obj.getNumVolF()+"',"
					           + "to_date('"+obj.getDateVolF()+"', 'yyyy-mm-dd'),"
					           + "'"+obj.getOrigine()+"',"
					           + "'"+obj.getDestination()+"',"
					           + "to_date('"+obj.getHeureDepGMT()+"', 'yyyy-mm-dd HH24:MI:SS'),"
					           + "'"+obj.getDuree()+"',"
					           + "'"+obj.getDistance()+"',"
					           + "'"+obj.getVolumeMin()+"',"
					           + "'"+obj.getPoidsMin()+"',"
					           + "'"+obj.getNumAvionF()+"',"
					           + "'N')");
			
			//parcourir la liste pour ajouter les pilotes
			Iterator it = obj.getAffectationP().iterator();
			while(it.hasNext()){
				requete.executeQuery("insert into affectationp "
			                       + "values('"+it.next()+"',"
				                   + "'"+obj.getNumVolF()+"',"
		                           + "to_date('"+obj.getDateVolF()+"', 'yyyy-mm-dd'))");
			
			}
			System.out.println("Vol ajouter. \n");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}	
	}

	
	//modifier un vol fret
	public void update(VolsFret obj) {
		try{
			Statement requete = cn.createStatement();
			requete.executeQuery("update volsfret "
					           + "set  Origine = '"+obj.getOrigine()+"', "
					           + "Destination = '"+obj.getDestination()+"',  "
					          // + "HeureDepGMT ='"+obj.getHeureDepGMT()+"', "
					           + "Duree = '"+obj.getDuree()+"', "
					           + "Distance = '"+obj.getDistance()+"', "
					           + "VolumeMin = '"+obj.getVolumeMin()+"', "
					           + "PoidsMin = '"+obj.getPoidsMin()+"', "
					           + "NumAvionF = '"+obj.getNumAvionF()+"' "
					           + "where NumVolF = '"+obj.getNumVolF()+"' ");//and DateVolF= '"+obj.getDateVolF()+"' ");
			
			//parcourir la liste pour ajouter les pilotes
			Iterator it = obj.getAffectationP().iterator();
			while(it.hasNext()){
				requete.executeQuery("insert into affectationp "
			                       + "values('"+it.next()+"',"
				                   + "'"+obj.getNumVolF()+"',"
		                           + "to_date('"+obj.getDateVolF()+"', 'yyyy-mm-dd'))");
			}
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

	
	//supprimer un vol fret
	public void delete(VolsFret obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("delete from volsfret "
					                                + "where NumVolF = '"+obj.getNumVolF()+"'");
			System.out.println("Vol "+obj.getNumVolF()+" supprimer.");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}
    
	
	// liste des avions disponibles pour un vol fret spécifique
	public ResultSet ListAvionsDisponible(){
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from avionsfret");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat; 
	}
	
	
	// infos sur l'avion
	public ResultSet InfosAvion(int NumAvionF){
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select a.*, m.NbPilotes "
					                                + "from avionsfret a, modele m "
					                                + "where NumAvionF ="+NumAvionF+" "
					                                + "and m.modele = a.modele");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}

	
	//liste des pilotes disponibles pour un vol fret, et un modele d'avion spécifique
	public ResultSet ListPilotesDisponible() {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from pilotes");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}

	
	
    // liste des pilotes affecté a un vol, utiliser lors d'affichage de liste des vols
	public ResultSet ListPilotesAffecter(String NumVolF, Date dateVolF) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select p.* "
					                                + "from pilotes p, affectationp ap "
					                                + "where p.NumPersoP = ap.NumPersoP "
					                                + "and ap.NumVol='"+NumVolF+"' ");
					                               // + "and ap.DateVol='"+DateVolF+"' ");
			                                       
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}

    
	//infos d'un vol fret utiliser lors du modification d'un vol
	public ResultSet InfosVolF(String numVolF) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from VolsFret "
					                                + "where NumVolF ='"+numVolF+"' "
					                                + "");
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}


	//infos des pilotes affecter à un vol fret utiliser lors du modification d'un vol
	public ResultSet InfoPilotesAffecter(String numVolF, Date dateVolF) {
		
		return null;
	}


	//validation d'un vol fret
	public void ValiderVolF(String NumVolF) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("update volsfret "
					                                + "set Termine='O' "
					                                + "where NumVolF = '"+NumVolF+"' ");
            System.out.println("Vol "+NumVolF+" valider.");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}


	
}
