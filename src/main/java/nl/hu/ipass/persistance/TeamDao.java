package nl.hu.ipass.persistance;

import java.util.ArrayList;

import nl.hu.ipass.domain.Speler;
import nl.hu.ipass.domain.Team;

public interface TeamDao {

	public ArrayList<Team> findAllTeams();
	public ArrayList<Speler> getSpelersFromTeam(String teamname);
	public Team findTeamByName(String name);
	
}
