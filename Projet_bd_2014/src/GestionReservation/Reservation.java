package GestionReservation;

import java.sql.Date;

public class Reservation {

	private int NumResa;
	private int NumClient;
	private String DateResa;
	
	
	public Reservation(){}
	
	
	public int getNumResa() {
		return NumResa;
	}
	
	
	public void setNumResa(int numResa) {
		NumResa = numResa;
	}
	
	
	public int getNumClient() {
		return NumClient;
	}
	
	
	public void setNumClient(int numClient) {
		NumClient = numClient;
	}
	
	
	public String getDateResa() {
		return DateResa;
	}
	
	
	public void setDateResa(String dateResa) {
		DateResa = dateResa;
	}
	
	
	public String toString() {
		return "Reservation [NumResa=" + NumResa + ", NumClient=" + NumClient
				+ ", DateResa=" + DateResa + "]";
	}
	
	
}
