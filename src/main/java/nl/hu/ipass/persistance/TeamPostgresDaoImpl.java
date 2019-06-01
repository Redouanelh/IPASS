package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import nl.hu.ipass.domain.Speler;
import nl.hu.ipass.domain.Team;

public class TeamPostgresDaoImpl extends PostgresBaseDao implements TeamDao {
	
	@Override
	public ArrayList<Team> findAllTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team findTeamByName(String name) {
		String query = "select * from teams where team = ?";
		String result = null;
		
		Team team = new Team();
		
		try(Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				team.setTeam(myRs.getString("team"));
				team.setMotto(myRs.getString("motto"));
				team.setGewonnen(myRs.getInt("gewonnen"));
				team.setGelijk(myRs.getInt("gelijk"));
				team.setVerloren(myRs.getInt("verloren"));
				team.setCompetitie(myRs.getString("competitie"));
				team.setTrainermail(myRs.getString("trainermail"));
				
				result += "Teamnaam: " + team.getTeam() + "\nMotto: " + team.getMotto();
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return team;
	}

	@Override
	public ArrayList<Speler> getSpelersFromTeam(String teamname) {
		String query = "select * from gebruikers g where g.team = ?";
		String result = null;
		
		AdresDao adresDao = new AdresPostgresDaoImpl();
		TeamDao teamDao = new TeamPostgresDaoImpl();
		
		ArrayList<Speler> spelers = new ArrayList<Speler>();
		
		try (Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, teamname);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				Speler speler = new Speler();
				speler.setPersoonsID(myRs.getInt("persoonsid"));
				speler.setVoornaam(myRs.getString("voornaam"));
				speler.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				speler.setAchternaam(myRs.getString("achternaam"));
				speler.setSpelersnummer(myRs.getInt("spelersnummer"));
				speler.setGeboortedatum(myRs.getDate("geboortedatum"));
				speler.setMobiel(myRs.getInt("mobiel"));
				
				speler.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				speler.setTeam(teamDao.findTeamByName(myRs.getString("team")));
				
				spelers.add(speler);	
				
				result += "Persoonsid: " + speler.getPersoonsID() + "\nVoornaam: " + speler.getVoornaam() + "\nTeam: " + speler.getTeam().getTeam() + "\n\n";
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Team team = findTeamByName(teamname);
		team.setSpelers(spelers);
		
		System.out.println(result);
		return spelers;
	}
	
	@Override 
	public boolean addTeam(Team team) {
		String query = "insert into teams(team, motto, gewonnen, gelijk, verloren, competitie, trainermail) values (?, ?, ?, ?, ?, ?, ?)";
		boolean teamAdded = false;
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, team.getTeam());
			pstmt.setString(2, team.getMotto());
			pstmt.setInt(3, team.getGewonnen());
			pstmt.setInt(4, team.getGelijk());
			pstmt.setInt(5, team.getVerloren());
			pstmt.setString(6, team.getCompetitie());
			pstmt.setString(7, team.getTrainermail());
			
			teamAdded = pstmt.executeUpdate() > 0;
			pstmt.close();
			System.out.println("Team toegevoegd: " + teamAdded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamAdded;
	}
	
	@Override
	public boolean updateTeam(Team team) {
		boolean teamExist = findTeamByName(team.getTeam()) != null;
		
		if (teamExist) {
			String query = "update teams set motto = ?, gewonnen = ?, gelijk = ?, verloren = ?, competitie = ?, trainermail = ? "
						 + "where team = ?";
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, team.getMotto());
				pstmt.setInt(2, team.getGewonnen());
				pstmt.setInt(3, team.getGelijk());
				pstmt.setInt(4, team.getVerloren());
				pstmt.setString(5, team.getCompetitie());
				pstmt.setString(6, team.getTrainermail());
				
				teamExist = pstmt.executeUpdate() > 0;
				pstmt.close();
				System.out.println("Team updated: " + teamExist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return teamExist;
	}
	
	@Override
	public boolean deleteTeam(Team team) {
		boolean teamExist = findTeamByName(team.getTeam()) != null;
		boolean teamDeleted = false;
		
		if (teamExist) {
			String query = "delete from teams where team = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, team.getTeam());
				teamDeleted = pstmt.executeUpdate() > 0;
				pstmt.close();
				System.out.println("Team deleted: " + teamDeleted);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return teamDeleted;
	}
	
	
	
	
}
