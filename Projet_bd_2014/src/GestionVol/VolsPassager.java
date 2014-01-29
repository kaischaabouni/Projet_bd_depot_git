package GestionVol;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VolsPassager {

	private int NumVolP;
	private String DateVolP;
	private String Origine;
	private String Destination;
	private String HeurDepGMT;
	private int Duree;
	private int Distance;
	private int Nb1ClMin;
	private int Nb2ClMin;
	private int NumAvionP;
	private String Termine; //(Termine in ('O','N')
	List AffectationP = new ArrayList();
	List AffectationH = new ArrayList();
	
	
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


	public String getHeurDepGMT() {
		return HeurDepGMT;
	}


	public void setHeurDepGMT(String heurDepGMT) {
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


	public String isTermine() {
		return Termine;
	}


	public void setTermine(String termine) {
		Termine = termine;
	}


	public int getNumVolP() {
		return NumVolP;
	}


	public void setNumVolP(int numVolP) {
		NumVolP = numVolP;
	}


	public String getDateVolP() {
		return DateVolP;
	}


	public void setDateVolP(String dateVolP) {
		DateVolP = dateVolP;
	}

    
	public List getAffectationP() {
		return AffectationP;
	}


	public void setAffectationP(List affectationP) {
		AffectationP = affectationP;
	}
	
	
	public List getAffectationH() {
		return AffectationH;
	}


	public void setAffectationH(List affectationH) {
		AffectationH = affectationH;
	}


	@Override
	public String toString() {
		return "VolsPassager [NumVolP=" + NumVolP + ", DateVolP=" + DateVolP
				+ ", Origine=" + Origine + ", Destination=" + Destination
				+ ", HeurDepGMT=" + HeurDepGMT + ", Duree=" + Duree
				+ ", Distance=" + Distance + ", Nb1ClMin=" + Nb1ClMin
				+ ", Nb2ClMin=" + Nb2ClMin + ", NumAvionP=" + NumAvionP
				+ ", Termine=" + Termine + ", AffectationP=" + AffectationP
				+ ", AffectationH=" + AffectationH + "]";
	}
	
	

}
