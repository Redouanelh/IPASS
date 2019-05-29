package nl.hu.ipass.domain;

import java.util.Date;

public class Beheerder {
	private int persoonsID;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String wachtwoord;
	private String isSpeler;
	private String isBeheerder;
	private Date geboortedatum;
	private int mobiel;
	private Adres huidigAdres;
	
	public Beheerder() {}
	
	public Beheerder(int id, String vnaam, String tvoegsel, String anaam, String ww, String isspeler, String isbeheerder, Date gbdatum, int mobiel, Adres adres) {
		this.persoonsID = id;
		this.voornaam = vnaam;
		this.tussenvoegsel = tvoegsel;
		this.achternaam = anaam;
		this.wachtwoord = ww;
		this.isSpeler = isspeler;
		this.isBeheerder = isbeheerder;
		this.geboortedatum = gbdatum;
		this.mobiel = mobiel;
		this.huidigAdres = adres;
	}

	public int getPersoonsID() {
		return persoonsID;
	}

	public void setPersoonsID(int persoonsID) {
		this.persoonsID = persoonsID;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}

	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public String getIsSpeler() {
		return isSpeler;
	}

	public void setIsSpeler(String isSpeler) {
		this.isSpeler = isSpeler;
	}

	public String getIsBeheerder() {
		return isBeheerder;
	}

	public void setIsBeheerder(String isBeheerder) {
		this.isBeheerder = isBeheerder;
	}

	public Date getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Date geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public int getMobiel() {
		return mobiel;
	}

	public void setMobiel(int mobiel) {
		this.mobiel = mobiel;
	}
	
	public Adres getHuidigAdres() {
		return huidigAdres;
	}

	public void setHuidigAdres(Adres huidigAdres) {
		this.huidigAdres = huidigAdres;
	}
	
	public String toString() {
		boolean beheerder = false;
		boolean speler = false;
		if (isSpeler == "J") {
			speler = true;
		} else {
			beheerder = true;
		}
		
		return "PersoonsID: " + persoonsID + "\nVoornaam: " + voornaam + "\nTussenvoegsel: " + tussenvoegsel + "\nAchternaam: " + achternaam + "\nWoonplaats: " + huidigAdres.getWoonplaats() + "\nisSpeler: " + speler + "\nisBeheerder: " + beheerder;
	}
	
}
