package GestionReservation;

import java.sql.Date;

public class ReservationFret {

	
	private String NumVol;
	private Date DateVol;
	private int NumResa;
	private float Volume;
	private float Poids;
	private float Prix;
	
	
	public ReservationFret(){}
	
	
	public String getNumVol() {
		return NumVol;
	}
	
	
	public void setNumVol(String numVol) {
		NumVol = numVol;
	}
	
	
	public Date getDateVol() {
		return DateVol;
	}
	
	
	public void setDateVol(Date dateVol) {
		DateVol = dateVol;
	}
	
	
	public int getNumResa() {
		return NumResa;
	}
	
	
	public void setNumResa(int numResa) {
		NumResa = numResa;
	}
	
	
	public float getVolume() {
		return Volume;
	}
	
	
	public void setVolume(float volume) {
		Volume = volume;
	}
	
	
	public float getPoids() {
		return Poids;
	}
	
	
	public void setPoids(float poids) {
		Poids = poids;
	}
	
	
	public float getPrix() {
		return Prix;
	}
	
	
	public void setPrix(float prix) {
		Prix = prix;
	}
	
	
	public String toString() {
		return "ReservationFret [NumVol=" + NumVol + ", DateVol=" + DateVol
				+ ", NumResa=" + NumResa + ", Volume=" + Volume + ", Poids="
				+ Poids + ", Prix=" + Prix + "]";
	}
	
	
}
