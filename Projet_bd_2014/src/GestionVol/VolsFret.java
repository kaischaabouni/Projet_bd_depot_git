package GestionVol;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class VolsFret {
	
	


	private String NumVolF;
	private Date DateVolF;
	private String Origine;
	private String Destination;
	private Timestamp HeureDepGMT;
	private int Duree;
	private int Distance;
	private float VolumeMin;
	private float PoidsMin;
	private int NumAvionF;
	private boolean Terminer; //(Termine in ('O','N')
	List AffectationP = new ArrayList();
	
	
	public VolsFret(){}
	
	
	public String getNumVolF() {
		return NumVolF;
	}
	
	
	public void setNumVolF(String numVolF) {
		NumVolF = numVolF;
	}
	
	
	public Date getDateVolF() {
		return DateVolF;
	}
	
	
	public void setDateVolF(Date dateVolF) {
		DateVolF = dateVolF;
	}
	
	
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
	
	
	public Timestamp getHeureDepGMT() {
		return HeureDepGMT;
	}
	
	
	public void setHeureDepGMT(Timestamp heureDepGMT) {
		HeureDepGMT = heureDepGMT;
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
	
	
	public float getVolumeMin() {
		return VolumeMin;
	}
	
	
	public void setVolumeMin(float volumeMin) {
		VolumeMin = volumeMin;
	}
	
	
	public float getPoidsMin() {
		return PoidsMin;
	}
	
	
	public void setPoidsMin(float poidsMin) {
		PoidsMin = poidsMin;
	}
	
	
	public int getNumAvionF() {
		return NumAvionF;
	}
	
	
	public void setNumAvionF(int numAvionF) {
		NumAvionF = numAvionF;
	}
	
	
	public boolean isTerminer() {
		return Terminer;
	}
	
	
	public void setTerminer(boolean terminer) {
		Terminer = terminer;
	}
	
    
	public List getAffectationP() {
		return AffectationP;
	}


	public void setAffectationP(List affectationP) {
		AffectationP = affectationP;
	}


	@Override
	public String toString() {
		return "VolsFret [NumVolF=" + NumVolF + ", DateVolF=" + DateVolF
				+ ", Origine=" + Origine + ", Destination=" + Destination
				+ ", HeureDepGMT=" + HeureDepGMT + ", Duree=" + Duree
				+ ", Distance=" + Distance + ", VolumeMin=" + VolumeMin
				+ ", PoidsMin=" + PoidsMin + ", NumAvionF=" + NumAvionF
				+ ", Terminer=" + Terminer + ", AffectationP=" + AffectationP
				+ "]";
	}
	
	
	
	
	
}
