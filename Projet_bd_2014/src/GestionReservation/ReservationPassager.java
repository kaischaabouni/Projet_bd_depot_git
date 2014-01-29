package GestionReservation;

import java.sql.Date;

public class ReservationPassager {
	
	private String NumVolP;
	private Date DateVolP;
	private int NumPlace; 
	private int NumResa;
	private float Prix;
	
	
	public ReservationPassager(){}
	
	
	public String getNumVolP() {
		return NumVolP;
	}
	
	
	public void setNumVolP(String numVolP) {
		NumVolP = numVolP;
	}
	
	
	public Date getDateVolP() {
		return DateVolP;
	}
	
	
	public void setDateVolP(Date dateVolP) {
		DateVolP = dateVolP;
	}
	
	
	public int getNumPlace() {
		return NumPlace;
	}
	
	
	public void setNumPlace(int numPlace) {
		NumPlace = numPlace;
	}
	
	
	public int getNumResa() {
		return NumResa;
	}
	
	
	public void setNumResa(int numResa) {
		NumResa = numResa;
	}
	
	
	public float getPrix() {
		return Prix;
	}
	
	
	public void setPrix(float prix) {
		Prix = prix;
	}
	
	
	public String toString() {
		return "ReservationPassager [NumVolP=" + NumVolP + ", DateVolP="
				+ DateVolP + ", NumPlace=" + NumPlace + ", NumResa=" + NumResa
				+ ", Prix=" + Prix + "]";
	}
	
	
}
