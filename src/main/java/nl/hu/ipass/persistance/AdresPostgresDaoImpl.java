package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import nl.hu.ipass.domain.Adres;

public class AdresPostgresDaoImpl extends PostgresBaseDao implements AdresDao {

	@Override
	public Adres findAdresById(int id) {
		String query = "select * from adres where adresid = ?";
		String result = null;
		
		Adres adres = new Adres();

		try(Connection conn = super.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet myRs = pstmt.executeQuery();
			
			while (myRs.next()) {
				adres.setAdresID(myRs.getInt("adresid"));
				adres.setPostcode(myRs.getString("postcode"));
				adres.setHuisnummer(myRs.getInt("huisnummer"));
				adres.setStraat(myRs.getString("straat"));
				adres.setWoonplaats(myRs.getString("woonplaats"));
				
				result = "Postcode: " + adres.getPostcode() + "\nStraat: " + adres.getStraat() + "\nHuisnummer: " + adres.getHuisnummer();
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adres;
	}
	
	@Override
	public boolean addAdres(Adres adres) {
		String query = "insert into adres(adresid, postcode, huisnummer, straat, woonplaats) values ((select max(adresid) + 1 from adres), ?, ?, ?, ?)";
		boolean adresAdded = false;
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, adres.getPostcode());
			pstmt.setInt(2, adres.getHuisnummer());
			pstmt.setString(3, adres.getStraat());
			pstmt.setString(4, adres.getWoonplaats());
			
			adresAdded = pstmt.executeUpdate() > 0;
			pstmt.close();
			System.out.println("Adres toegevoegd: " + adresAdded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adresAdded;
	}

	@Override
	public boolean updateAdres(Adres adres) {
		boolean adresExist = findAdresById(adres.getAdresID()) != null;
		
		if (adresExist) {
			String query = "update adres set postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? where adresid = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, adres.getPostcode());
				pstmt.setInt(2, adres.getHuisnummer());
				pstmt.setString(3, adres.getStraat());
				pstmt.setString(4, adres.getWoonplaats());
				pstmt.setInt(5, adres.getAdresID());
				
				adresExist = pstmt.executeUpdate() > 0;
				pstmt.close();
				System.out.println("Adres updated: " + adresExist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	return adresExist;
	}
	
	public boolean deleteAdres(Adres adres) {
		boolean adresExist = findAdresById(adres.getAdresID()) != null;
		boolean adresDeleted = false;
				
		if (adresExist) {
			String query = "delete from adres where adresid = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, adres.getAdresID());
				adresDeleted = pstmt.executeUpdate() < 0;
				pstmt.close();
				System.out.println("Adres deleted: " + adresDeleted);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Adres niet gevonden");
		}
		return adresDeleted;
	}
	
	
	
}	
