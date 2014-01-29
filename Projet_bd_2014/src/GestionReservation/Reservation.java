package GestionReservation;

import java.sql.Date;

public class Reservation {

	private int NumResa;
	private int NumClient;
	private Date DateResa;
	
	
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
	
	
	public Date getDateResa() {
		return DateResa;
	}
	
	
	public void setDateResa(Date dateResa) {
		DateResa = dateResa;
	}
	
	
	public String toString() {
		return "Reservation [NumResa=" + NumResa + ", NumClient=" + NumClient
				+ ", DateResa=" + DateResa + "]";
	}
	
	
}
