package GestionVol;

import Main.DAO;

public class VolsPassagerMain {

	
	public void CreateVol(){
		
		//debut
		System.out.println("===> Consulter la liste des vols \n");
		
		//creation Data Access Object
		DAO<VolsPassagerDAO> VPDAO = new VolsPassagerDAO();
		
		//creation objet vol passager
		VolsPassager VolsP = new VolsPassager();
		
		//....
		System.out.println("suite de fonction");
		
		
		//fin
		System.out.println("****************************************************\n");
		
	}
	
	

	public void ShowListeVols() {
		
		//debut
		System.out.println("===> Ajouter un nouveau vol \n");
		
		//....
		System.out.println("suite de fonction");
		
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
