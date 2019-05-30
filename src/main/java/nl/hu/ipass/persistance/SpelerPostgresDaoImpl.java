package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import nl.hu.ipass.domain.Speler;

public class SpelerPostgresDaoImpl extends PostgresBaseDao implements SpelerDao {

	@Override
	public ArrayList<Speler> findAllSpelers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Speler getSpelerByUsername(String username) {
		String query = "select g.voornaam, g.tussenvoegsel, g.achternaam, g.spelersnummer, g.geboortedatum, g.mobiel, g.team, a.adresid, a.postcode, a.straat, a.huisnummer, a.woonplaats\r\n" + 
						"from gebruikers g, adres a " + 
						"where g.adresid = a.adresid " + 
						"and g.voornaam = ?";
		String result = null;
		
		AdresDao adresDao = new AdresPostgresDaoImpl();
		TeamDao teamDao = new TeamPostgresDaoImpl();
		Speler speler = new Speler();
		
		try (Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				speler.setVoornaam(myRs.getString("voornaam"));
				speler.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				speler.setAchternaam(myRs.getString("achternaam"));
				speler.setSpelersnummer(myRs.getInt("spelersnummer"));
				speler.setGeboortedatum(myRs.getDate("geboortedatum"));
				speler.setMobiel(myRs.getInt("mobiel"));
				
				speler.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				speler.setTeam(teamDao.findTeamByName(myRs.getString("team")));
				
				result += "Voornaam : " + speler.getVoornaam() + "\nTeamnaam: " + speler.getTeam().getTeam() + "\nWoonplaats; " + speler.getHuidigAdres().getWoonplaats();
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return speler;
	}
	
	@Override
	public boolean updateSpeler(Speler speler) {
		boolean spelerExist = getSpelerByUsername(speler.getVoornaam()) != null;
		
		if (spelerExist) {
			String query = "update gebruikers set voornaam = ?, tussenvoegsel = ?, achternaam = ?, wachtwoord = ?, spelersnummer = ?, geboortedatum = ?, mobiel = ?, team = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, speler.getVoornaam());
				pstmt.setString(2, speler.getTussenvoegsel());
				pstmt.setString(3, speler.getAchternaam());
				pstmt.setString(4, speler.getWachtwoord());
				pstmt.setInt(5, speler.getSpelersnummer());
				pstmt.setDate(6, speler.getGeboortedatum());
				pstmt.setInt(7, speler.getMobiel());
				pstmt.setString(8, speler.getTeam().getTeam());
				
				pstmt.execute();
				pstmt.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return spelerExist;
	}
	
	@Override
	public boolean deleteSpeler(Speler speler) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
