package GestionVol;

import Main.DAO;

public class VolsPassagerDAO extends DAO<VolsPassagerDAO>{

	
	public VolsPassagerDAO ShowList(long id) {
		String query ="select * from VolsPassagers";
		ResulSet rs = conn.exec
	}

	public VolsPassagerDAO create(VolsPassagerDAO obj) {
		return null;
	}

	public VolsPassagerDAO update(VolsPassagerDAO obj) {
		return null;
	}

	public void delete(VolsPassagerDAO obj) {
		
	}

}
