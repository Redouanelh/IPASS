package nl.hu.v1wac.ipass.domain;

public class Adres {
	private int adresID;
	private String postcode;
	private int huisnummer;
	private String straat;
	private String woonplaats;
	
	public Adres() {}
	
	public Adres(int id, String code, int huisnr, String straat, String woonplaats) {
		this.adresID = id;
		this.postcode = code;
		this.huisnummer = huisnr;
		this.straat = straat;
		this.woonplaats = woonplaats;
	}

	public int getAdresID() {
		return adresID;
	}

	public void setAdresID(int adresID) {
		this.adresID = adresID;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(int huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}
	
	public String toString() {
		return "AdresID: " + adresID + "\nStraat: " + straat + "\nHuisnummer: " + huisnummer + "\nWoonplaats: " + woonplaats;
	}
	
}
