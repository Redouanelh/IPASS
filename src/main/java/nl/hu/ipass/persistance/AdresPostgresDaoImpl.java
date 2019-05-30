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
				
				result += "Postcode: " + adres.getPostcode() + "\nStraat: " + adres.getStraat() + "\nHuisnummer: " + adres.getHuisnummer();
			}
			myRs.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return adres;
	}

	@Override
	public boolean updateAdres(Adres adres) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
