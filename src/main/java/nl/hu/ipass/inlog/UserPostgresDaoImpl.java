package nl.hu.ipass.inlog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import nl.hu.ipass.persistance.PostgresBaseDao;

public class UserPostgresDaoImpl extends PostgresBaseDao implements UserDao {

	@Override
	public String findRoleForUser(String name, String pass) {
		String query = "select isspeler from gebruikers where voornaam = ? and wachtwoord = ?";
		String result = null;
		
		try (Connection conn = super.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			ResultSet myRs = pstmt.executeQuery();
			while (myRs.next()) {
				result = myRs.getString("isSpeler");
			}
			
			myRs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (result == "") {
			return null;
		}
		return result;
	}
	
}
