package GestionClient;

public class Client {
	
	private int NumClient;
	private String NomC;
	private String PrenomC;
	private int Numero;
	private String Rue;
	private String Ville;
	private String CP;
	private String Pays;
	private String Passeport;
	private int Points;
	
	
	public Client(){}


	public int getNumClient() {
		return NumClient;
	}


	public void setNumClient(int numClient) {
		NumClient = numClient;
	}


	public String getNomC() {
		return NomC;
	}


	public void setNomC(String nomC) {
		NomC = nomC;
	}


	public String getPrenomC() {
		return PrenomC;
	}


	public void setPrenomC(String prenomC) {
		PrenomC = prenomC;
	}


	public int getNumero() {
		return Numero;
	}


	public void setNumero(int numero) {
		Numero = numero;
	}


	public String getRue() {
		return Rue;
	}


	public void setRue(String rue) {
		Rue = rue;
	}


	public String getVille() {
		return Ville;
	}


	public void setVille(String ville) {
		Ville = ville;
	}


	public String getCP() {
		return CP;
	}


	public void setCP(String cP) {
		CP = cP;
	}


	public String getPays() {
		return Pays;
	}


	public void setPays(String pays) {
		Pays = pays;
	}


	public String getPasseport() {
		return Passeport;
	}


	public void setPasseport(String passeport) {
		Passeport = passeport;
	}


	public int getPoints() {
		return Points;
	}


	public void setPoints(int points) {
		Points = points;
	}


	public String toString() {
		return "Client [NumClient=" + NumClient + ", NomC=" + NomC
				+ ", PrenomC=" + PrenomC + ", Numero=" + Numero + ", Rue="
				+ Rue + ", Ville=" + Ville + ", CP=" + CP + ", Pays=" + Pays
				+ ", Passeport=" + Passeport + ", Points=" + Points + "]";
	}
	

}
