package nl.hu.ipass.domain;

public class Verzoek {
	
	private String teamverzoek;
	private Speler speler;
	
	public Verzoek() {}
	
	public Verzoek(Speler speler, String team) {
		this.speler = speler;
		this.teamverzoek = team;
	}

	public String getTeamverzoek() {
		return teamverzoek;
	}

	public void setTeamverzoek(String teamverzoek) {
		this.teamverzoek = teamverzoek;
	}

	public Speler getSpeler() {
		return speler;
	}

	public void setSpeler(Speler speler) {
		this.speler = speler;
	}
	
	public String toString() {
		return "Team verzoek: " + teamverzoek + "\nVan: " + speler.getVoornaam() + " met persoonsID " + speler.getPersoonsID();
	}
		
	

}
