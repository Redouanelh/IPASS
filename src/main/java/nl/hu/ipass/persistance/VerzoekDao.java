package nl.hu.ipass.persistance;

import java.util.ArrayList;

import nl.hu.ipass.domain.Verzoek;

public interface VerzoekDao {
	public ArrayList<Verzoek> getAllVerzoeken();
	public Verzoek getVerzoekByPersoonsid(int id);
	public boolean addVerzoek(Verzoek v);
	public boolean deleteVerzoek(Verzoek v);
	public boolean deleteVerzoekByTeamverzoek(Verzoek v);
	public boolean deleteVerzoekByPersoonsid(Verzoek v);

	
}
