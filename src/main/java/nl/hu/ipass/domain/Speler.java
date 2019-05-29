package nl.hu.ipass.domain;

import java.util.Date;

public class Speler {
	private int persoonsID;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String wachtwoord;
	private String isSpeler;
	private String isBeheerder;
	private int spelersnummer;
	private Date geboortedatum;
	private int mobiel;
	private Team team;
	private Adres huidigAdres;
	
	public Speler() {}
	
	public Speler(int id, String vnaam, String tvoegsel, String anaam, String ww, String isspeler, String isbeheerder, int spelernr, Date gbdatum, int mobiel, Team team, Adres adres) {
		this.persoonsID = id;
		this.voornaam = vnaam;
		this.tussenvoegsel = tvoegsel;
		this.achternaam = anaam;
		this.wachtwoord = ww;
		this.isSpeler = isspeler;
		this.isBeheerder = isbeheerder;
		this.spelersnummer = spelernr;
		this.geboortedatum = gbdatum;
		this.mobiel = mobiel;
		this.team = team;
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

	public int getSpelersnummer() {
		return spelersnummer;
	}

	public void setSpelersnummer(int spelersnummer) {
		this.spelersnummer = spelersnummer;
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
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
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
		return "PersoonsID: " + persoonsID + "\nVoornaam: " + voornaam + "\nTussenvoegsel: " + tussenvoegsel + "\nAchternaam: " + achternaam + "\nWoonplaats: " + huidigAdres.getWoonplaats() + "\nisSpeler: " + speler + "\nisBeheerder: " + beheerder + "\nTeam: " + team.getTeam();
	}
	
}
