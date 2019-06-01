package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.Speler;

public class SpelerPostgresDaoImpl extends PostgresBaseDao implements SpelerDao {
	static AdresDao adresDao = new AdresPostgresDaoImpl();
	static TeamDao teamDao = new TeamPostgresDaoImpl();

	@Override
	public ArrayList<Speler> findAllSpelers() {
		String query = "select * from gebruikers where isspeler = 'J'";
		String result = null;
		
		ArrayList<Speler> spelers = new ArrayList<Speler>(); 
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				Speler speler = new Speler();
				
				speler.setVoornaam(myRs.getString("voornaam"));
				speler.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				speler.setAchternaam(myRs.getString("achternaam"));
				speler.setSpelersnummer(myRs.getInt("spelersnummer"));
				speler.setGeboortedatum(myRs.getDate("geboortedatum"));
				speler.setMobiel(myRs.getInt("mobiel"));
				
				speler.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				speler.setTeam(teamDao.findTeamByName(myRs.getString("team")));
				
				spelers.add(speler);
				result += "Spelernaam: " + speler.getVoornaam() + "\n";
			}
			myRs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return spelers;
	}

	@Override
	public Speler getSpelerByUsername(String username) {
		String query = "select g.voornaam, g.tussenvoegsel, g.achternaam, g.spelersnummer, g.geboortedatum, g.mobiel, g.team, a.adresid, a.postcode, a.straat, a.huisnummer, a.woonplaats\r\n" + 
						"from gebruikers g, adres a " + 
						"where g.adresid = a.adresid " + 
						"and g.voornaam = ?";
		String result = null;
		
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
	public Speler getSpelerById(int id) {
		String query = "select g.voornaam, g.tussenvoegsel, g.achternaam, g.spelersnummer, g.geboortedatum, g.mobiel, g.team, a.adresid, a.postcode, a.straat, a.huisnummer, a.woonplaats\r\n" + 
				"from gebruikers g, adres a " + 
				"where g.adresid = a.adresid " + 
				"and g.persoonsid = ?";
		String result = null;

		Speler speler = new Speler();

		try (Connection conn = super.getConnection()) {
	
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
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
	public boolean addSpeler(Speler speler, Adres adres) {
		String query = "insert into gebruikers(persoonsid, voornaam, tussenvoegsel, achternaam, wachtwoord, isspeler, isbeheerder, spelersnummer, geboortedatum, mobiel, adresid, team) "
					 + "values ((select max(persoonsid) + 1 from gebruikers), ?, ?, ?, ?, ?, ?, ?, ?, ?, (select max(adresid) + 1 from gebruikers), ?)";
		boolean spelerAdded = false;
		
		// Eerst een adres toevoegen voordat je een gebruiker kan toevoegen wegens de constraints
		adresDao.addAdres(adres);
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, speler.getVoornaam());
			pstmt.setString(2, speler.getTussenvoegsel());
			pstmt.setString(3, speler.getAchternaam());
			pstmt.setString(4, speler.getWachtwoord());
			pstmt.setString(5, speler.getIsSpeler());
			pstmt.setString(6, speler.getIsBeheerder());
			pstmt.setInt(7, speler.getSpelersnummer());
			pstmt.setDate(8, speler.getGeboortedatum());
			pstmt.setInt(9, speler.getMobiel());
			pstmt.setString(10, speler.getTeam().getTeam());
			
			spelerAdded = pstmt.executeUpdate() > 0;
			pstmt.close();
			System.out.println("Speler toegevoegd: " + spelerAdded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spelerAdded;
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
		boolean spelerExist = getSpelerByUsername(speler.getVoornaam()) != null;
		boolean spelerDeleted = false;
		
		if(spelerExist) {
			String query = "delete from gebruikers where persoonsid = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, speler.getPersoonsID());
				spelerDeleted = pstmt.executeUpdate() > 0;
				pstmt.close();
				System.out.println("Speler deleted: " + spelerDeleted);
								
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Speler bestaat niet");
		}
		// Eerst een speler verwijderen, daarna de bijbehorende adres wegens constraints
		adresDao.deleteAdres(speler.getHuidigAdres());
		return spelerDeleted;
	}

	
}
