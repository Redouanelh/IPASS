package nl.hu.v1wac.ipass.domain;

public class Team {
	private String team;
	private String motto;
	private int gewonnen;
	private int gelijk;
	private int verloren;
	private String competitie;
	private String trainermail;
	
	public Team() {}
	
	public Team(String team, String motto, int winst, int gelijk, int verlies, String comp, String mail) {
		this.team = team;
		this.motto = motto;
		this.gewonnen = winst;
		this.gelijk = gelijk;
		this.verloren = verlies;
		this.competitie = comp;
		this.trainermail = mail;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public int getGewonnen() {
		return gewonnen;
	}

	public void setGewonnen(int gewonnen) {
		this.gewonnen = gewonnen;
	}

	public int getGelijk() {
		return gelijk;
	}

	public void setGelijk(int gelijk) {
		this.gelijk = gelijk;
	}

	public int getVerloren() {
		return verloren;
	}

	public void setVerloren(int verloren) {
		this.verloren = verloren;
	}

	public String getCompetitie() {
		return competitie;
	}

	public void setCompetitie(String competitie) {
		this.competitie = competitie;
	}

	public String getTrainermail() {
		return trainermail;
	}

	public void setTrainermail(String trainermail) {
		this.trainermail = trainermail;
	}
	
	public String toString() {
		return "Team: " + team + "\nMotto: " + motto + "\nWinst: " + gewonnen + "\nGelijk: " + gelijk + "\nVerloren: " + verloren + "\nCompetitie: " + competitie + "\nMail: " + trainermail; 
	}

}
