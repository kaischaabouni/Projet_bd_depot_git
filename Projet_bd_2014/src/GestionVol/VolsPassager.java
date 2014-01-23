package GestionVol;

import java.sql.Date;
import java.sql.Timestamp;

public class VolsPassager {

	private String NumVolP;
	private Date DateVolP;
	private String Origine;
	private String Destination;
	private Timestamp HeurDepGMT;
	private int Duree;
	private int Distance;
	private int Nb1ClMin;
	private int Nb2ClMin;
	private int NumAvionP;
	private boolean Termine; //(Termine in ('O','N')
	
	
	
	public VolsPassager(){}


	public String getOrigine() {
		return Origine;
	}


	public void setOrigine(String origine) {
		Origine = origine;
	}


	public String getDestination() {
		return Destination;
	}


	public void setDestination(String destination) {
		Destination = destination;
	}


	public Timestamp getHeurDepGMT() {
		return HeurDepGMT;
	}


	public void setHeurDepGMT(Timestamp heurDepGMT) {
		HeurDepGMT = heurDepGMT;
	}


	public int getDuree() {
		return Duree;
	}


	public void setDuree(int duree) {
		Duree = duree;
	}


	public int getDistance() {
		return Distance;
	}


	public void setDistance(int distance) {
		Distance = distance;
	}


	public int getNb1ClMin() {
		return Nb1ClMin;
	}


	public void setNb1ClMin(int nb1ClMin) {
		Nb1ClMin = nb1ClMin;
	}


	public int getNb2ClMin() {
		return Nb2ClMin;
	}


	public void setNb2ClMin(int nb2ClMin) {
		Nb2ClMin = nb2ClMin;
	}


	public int getNumAvionP() {
		return NumAvionP;
	}


	public void setNumAvionP(int numAvionP) {
		NumAvionP = numAvionP;
	}


	public boolean isTermine() {
		return Termine;
	}


	public void setTermine(boolean termine) {
		Termine = termine;
	}


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


	public String toString() {
		return "VolsPassager [NumVolP=" + NumVolP + ", DateVolP=" + DateVolP
				+ ", Origine=" + Origine + ", Destination=" + Destination
				+ ", HeurDepGMT=" + HeurDepGMT + ", Duree=" + Duree
				+ ", Distance=" + Distance + ", Nb1ClMin=" + Nb1ClMin
				+ ", Nb2ClMin=" + Nb2ClMin + ", NumAvionP=" + NumAvionP
				+ ", Termine=" + Termine + "]";
	}
	
	

}
