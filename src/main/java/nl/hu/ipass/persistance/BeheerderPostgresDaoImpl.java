package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import nl.hu.ipass.domain.Beheerder;
import nl.hu.ipass.domain.Speler;

public class BeheerderPostgresDaoImpl extends PostgresBaseDao implements BeheerderDao {

	@Override
	public ArrayList<Beheerder> findAllBeheerders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beheerder getBeheerderByUsername(String username) {
		String query = "select g.voornaam, g.tussenvoegsel, g.achternaam, g.geboortedatum, g.mobiel, a.adresid, a.postcode, a.straat, a.huisnummer, a.woonplaats " + 
						"from gebruikers g, adres a " + 
						"where g.adresid = a.adresid " + 
						"and g.persoonsid = ?";
		String result = null;
		
		AdresDao adresDao = new AdresPostgresDaoImpl();
		Beheerder beheerder = new Beheerder();
		
		try (Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				beheerder.setVoornaam(myRs.getString("voornaam"));
				beheerder.setTussenvoegsel(myRs.getString("tussenvoegsel"));
				beheerder.setAchternaam(myRs.getString("achternaam"));
				beheerder.setGeboortedatum(myRs.getDate("geboortedatum"));
				beheerder.setMobiel(myRs.getInt("mobiel"));
				
				beheerder.setHuidigAdres(adresDao.findAdresById(myRs.getInt("adresid")));
				
				result += "Voornaam : " + beheerder.getVoornaam() + "\nWoonplaats; " + beheerder.getHuidigAdres().getWoonplaats();
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
	public boolean updateBeheerder(Beheerder beheerder) {
		boolean beheerderExist = getBeheerderByUsername(beheerder.getVoornaam()) != null;
		
		if (beheerderExist) {
			String query = "update gebruikers set voornaam = ?, tussenvoegsel = ?, achternaam = ?, wachtwoord = ?, geboortedatum = ?, mobiel = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, beheerder.getVoornaam());
				pstmt.setString(2, beheerder.getTussenvoegsel());
				pstmt.setString(3, beheerder.getAchternaam());
				pstmt.setString(4, beheerder.getWachtwoord());
				pstmt.setDate(5, beheerder.getGeboortedatum());
				pstmt.setInt(6, beheerder.getMobiel());
				
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
		// TODO Auto-generated method stub
		return false;
	}


}
