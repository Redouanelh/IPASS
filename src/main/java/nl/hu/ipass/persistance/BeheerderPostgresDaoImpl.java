package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.Beheerder;

public class BeheerderPostgresDaoImpl extends PostgresBaseDao implements BeheerderDao {
	
	static AdresDao adresDao = new AdresPostgresDaoImpl();


	@Override
	public ArrayList<Beheerder> findAllBeheerders() {
		String query = "select * from gebruikers";
		String result = "";
		
		ArrayList<Beheerder> beheerders = new ArrayList<Beheerder>();
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				Beheerder beheerder = new Beheerder();
				
				beheerder.setPersoonsID(myRs.getInt("persoonsid"));
				beheerder.setVoornaam(myRs.getString("voornaam"));
				beheerder.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				beheerder.setAchternaam(myRs.getString("achternaam"));
				beheerder.setGeboortedatum(myRs.getDate("geboortedatum"));
				beheerder.setMobiel(myRs.getInt("mobiel"));
				
				beheerder.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				
				beheerders.add(beheerder);
				result += "Beheerdernaam: " + beheerder.getVoornaam() + "\n";
				
			}
			myRs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return beheerders;
	}

	@Override
	public Beheerder getBeheerderByUsername(String username) {
		String query = "select g.persoonsid, g.voornaam, g.tussenvoegsel, g.achternaam, g.geboortedatum, g.mobiel, a.adresid, a.postcode, a.straat, a.huisnummer, a.woonplaats " + 
						"from gebruikers g, adres a " + 
						"where g.adresid = a.adresid " + 
						"and g.isBeheerder = 'J' " +
						"and g.voornaam = ?";
		String result = null;
		
		Beheerder beheerder = new Beheerder();
		
		try (Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				beheerder.setPersoonsID(myRs.getInt("persoonsid"));
				beheerder.setVoornaam(myRs.getString("voornaam"));
				beheerder.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				beheerder.setAchternaam(myRs.getString("achternaam"));
				beheerder.setGeboortedatum(myRs.getDate("geboortedatum"));
				beheerder.setMobiel(myRs.getInt("mobiel"));
				
				beheerder.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				
				result = "Voornaam : " + beheerder.getVoornaam() + "\nWoonplaats; " + beheerder.getHuidigAdres().getWoonplaats();
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return beheerder;
	}
	
	@Override
	public Beheerder getBeheerderById(int id) {
		String query = "select g.persoonsid, g.voornaam, g.tussenvoegsel, g.achternaam, g.geboortedatum, g.mobiel, a.adresid, a.postcode, a.straat, a.huisnummer, a.woonplaats " + 
						"from gebruikers g, adres a " + 
						"where g.adresid = a.adresid " + 
						"and g.persoonsid = ?";
		String result = null;
		
		Beheerder beheerder = new Beheerder();
		
		try (Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				beheerder.setPersoonsID(myRs.getInt("persoonsid"));
				beheerder.setVoornaam(myRs.getString("voornaam"));
				beheerder.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				beheerder.setAchternaam(myRs.getString("achternaam"));
				beheerder.setGeboortedatum(myRs.getDate("geboortedatum"));
				beheerder.setMobiel(myRs.getInt("mobiel"));
				
				beheerder.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				
				result = "Voornaam : " + beheerder.getVoornaam() + "\nWoonplaats; " + beheerder.getHuidigAdres().getWoonplaats();
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return beheerder;
	}
	
	@Override
	public boolean addBeheerder(Beheerder beheerder, Adres adres) {
		String query = "insert into gebruikers(persoonsid, voornaam, tussenvoegsel, achternaam, wachtwoord, isspeler, isbeheerder, geboortedatum, mobiel, adresid, team) "
					 + "values ((select max(persoonsid) + 1 from gebruikers), ?, ?, ?, ?, ?, ?, ?, ?, (select max(adresid) + 1 from gebruikers), 'Beheerder')";
		boolean beheerderAdded = false;
		
		adresDao.addAdres(adres);
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, beheerder.getVoornaam());
			pstmt.setString(2, beheerder.getTussenvoegsel());
			pstmt.setString(3, beheerder.getAchternaam());
			pstmt.setString(4, beheerder.getWachtwoord());
			pstmt.setString(5, beheerder.getIsSpeler());
			pstmt.setString(6, beheerder.getIsBeheerder());
			pstmt.setDate(7, beheerder.getGeboortedatum());
			pstmt.setInt(8, beheerder.getMobiel());
			
			beheerderAdded = pstmt.executeUpdate() > 0;
			pstmt.close();
			System.out.println("Beheerder updated: " + beheerderAdded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beheerderAdded;
	}
	
	@Override
	public boolean updateBeheerder(Beheerder beheerder) {
		boolean beheerderExist = getBeheerderByUsername(beheerder.getVoornaam()) != null;
		
		if (beheerderExist) {
			String query = "update gebruikers set voornaam = ?, tussenvoegsel = ?, achternaam = ?, wachtwoord = ?, geboortedatum = ?, mobiel = ? where persoonsid = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, beheerder.getVoornaam());
				pstmt.setString(2, beheerder.getTussenvoegsel());
				pstmt.setString(3, beheerder.getAchternaam());
				pstmt.setString(4, beheerder.getWachtwoord());
				pstmt.setDate(5, beheerder.getGeboortedatum());
				pstmt.setInt(6, beheerder.getMobiel());
				pstmt.setInt(7, beheerder.getPersoonsID());
				
				pstmt.execute();
				pstmt.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return beheerderExist;
	}
	
	@Override
	public boolean deleteBeheerder(Beheerder beheerder) {
		boolean beheerderExist = getBeheerderByUsername(beheerder.getVoornaam()) != null;
		boolean beheerderDeleted = false;
		
		if(beheerderExist) {
			String query = "delete from gebruikers where persoonsid = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, beheerder.getPersoonsID());
				beheerderDeleted = pstmt.executeUpdate() > 0;
				pstmt.close();
				System.out.println("Beheerder deleted: " + beheerderDeleted);
				
				adresDao.deleteAdres(beheerder.getHuidigAdres());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Beheerder bestaat niet");
		}
		return beheerderDeleted;
	}


}
