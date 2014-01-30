
package GestionVol;

import Main.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class VolsPassagerDAO extends DAO<VolsPassager>{
    
	ResultSet resultat;
	
	//afficher la liste des vols passager
	public ResultSet ShowList() {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select NumVolP, Origine, Destination, Duree, Distance, Nb1ClMin, Nb2ClMin, NumAvionP, Termine, "
					                                + "TO_CHAR(DateVolP, 'DD/MM/YYYY') as datevol, "
					                                + "to_char(HeureDepGMT,'HH24:MI:SS') as heuredep  from volspassager ");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;	
	}

	
	//ajouter un nouveau vol passager
	public void create(VolsPassager obj){
		try{
	        Statement requete = cn.createStatement();
			requete.executeQuery("insert into volsPassager "
					           + "values('"+obj.getNumVolP()+"',"
					           + "to_date('"+obj.getDateVolP()+"', 'yyyy-mm-dd'),"
					           + "'"+obj.getOrigine()+"',"
					           + "'"+obj.getDestination()+"',"
					           + "to_date('"+obj.getHeureDepGMT()+"', 'yyyy-mm-dd HH24:MI:SS'),"
					           + "'"+obj.getDuree()+"',"
					           + "'"+obj.getDistance()+"',"
					           + "'"+obj.getNb1ClMin()+"',"
					           + "'"+obj.getNb2ClMin()+"',"
					           + "'"+obj.getNumAvionP()+"',"
					           + "'N')");
			
			//parcourir la liste pour ajouter les pilotes
			Iterator it = obj.getAffectationP().iterator();
			while(it.hasNext()){
				requete.executeQuery("insert into affectationp "
			                       + "values('"+it.next()+"',"
				                   + "'"+obj.getNumVolP()+"',"
		                           + "to_date('"+obj.getDateVolP()+"', 'yyyy-mm-dd'))");
			
			}
			
			
			//parcourir la liste pour ajouter les hotesses
			Iterator itH = obj.getAffectationH().iterator();
			while(itH.hasNext()){
				requete.executeQuery("insert into affectationh "
			                       + "values('"+itH.next()+"',"
				                   + "'"+obj.getNumVolP()+"',"
		                           + "to_date('"+obj.getDateVolP()+"', 'yyyy-mm-dd'))");
			
			}
			
			System.out.println("Vol ajouter. \n");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}	
	}

	
	//modifier un vol passager
	public void update(VolsPassager obj) {
		try{
			Statement requete = cn.createStatement();
			requete.executeQuery("update volsPassager "
					           + "set  Origine = '"+obj.getOrigine()+"', "
					           + "Destination = '"+obj.getDestination()+"',  "
					          // + "HeureDepGMT ='"+obj.getHeureDepGMT()+"', "
					           + "Duree = '"+obj.getDuree()+"', "
					           + "Distance = '"+obj.getDistance()+"', "
					           + "Nb1ClMin = '"+obj.getNb1ClMin()+"', "
					           + "Nb2ClMin = '"+obj.getNb2ClMin()+"', "
					           + "NumAvionP = '"+obj.getNumAvionP()+"' "
					           + "where NumVolP = '"+obj.getNumVolP()+"' ");//and DateVolF= '"+obj.getDateVolF()+"' ");
			
			//parcourir la liste pour ajouter les pilotes
			Iterator it = obj.getAffectationP().iterator();
			while(it.hasNext()){
				requete.executeQuery("insert into affectationp "
			                       + "values('"+it.next()+"',"
				                   + "'"+obj.getNumVolP()+"',"
		                           + "to_date('"+obj.getDateVolP()+"', 'yyyy-mm-dd'))");
			}
			
			//parcourir la liste pour ajouter les pilotes
			Iterator itH = obj.getAffectationH().iterator();
			while(itH.hasNext()){
				requete.executeQuery("insert into affectationh "
			                       + "values('"+itH.next()+"',"
					               + "'"+obj.getNumVolP()+"',"
			                       + "to_date('"+obj.getDateVolP()+"', 'yyyy-mm-dd'))");
			}
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}

	
	//supprimer un vol passager
	public void delete(VolsPassager obj) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("delete from volspassager "
					                                + "where NumVolP = '"+obj.getNumVolP()+"'");
			System.out.println("Vol "+obj.getNumVolP()+" supprimer.");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}
    
	
	// liste des avions disponibles pour un vol passager spécifique
	public ResultSet ListAvionsDisponible(){
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from AvionsPassagers");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat; 
	}
	
	
	// infos sur l'avion
	public ResultSet InfosAvion(int NumAvionP){
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select a.*, m.NbPilotes "
					                                + "from avionspassagers a, modele m "
					                                + "where NumAvionP ="+NumAvionP+" "
					                                + "and m.modele = a.modele");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}

	
	//liste des pilotes disponibles pour un vol passager, et un modele d'avion spécifique
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
	public ResultSet ListPilotesAffecter(String NumVolP, Date dateVolP) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select p.* "
					                                + "from pilotes p, affectationp ap "
					                                + "where p.NumPersoP = ap.NumPersoP "
					                                + "and ap.NumVol='"+NumVolP+"' ");
					                               // + "and ap.DateVol='"+DateVolF+"' ");
			                                       
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}

    
	//infos d'un vol passager utiliser lors du modification d'un vol
	public ResultSet InfosVolP(String numVolP) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from VolsPassager "
					                                + "where NumVolP ='"+numVolP+"' "
					                                + "");
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}


	//infos des pilotes affecter à un vol passager utiliser lors du modification d'un vol
	public ResultSet InfoPilotesAffecter(String numVolP, Date dateVolP) {
		
		return null;
	}


	//validation d'un vol passager
	public void ValiderVolP(String NumVolP) {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("update volspassager "
					                                + "set Termine='O' "
					                                + "where NumVolP = '"+NumVolP+"' ");
            System.out.println("Vol "+NumVolP+" valider.");
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
	}


	public ResultSet ListHotessesDisponible() {
		try{
	        Statement requete = cn.createStatement();
			ResultSet resultat = requete.executeQuery("select * from hotesses");
			
			return resultat;
			
		}catch(SQLException e){	
			System.out.println("ERROR ! \n Code d'erreur"+e.getErrorCode());
			System.out.println("Message d'erreur : "+e.getMessage());
		}
		return resultat;
	}


	
}
