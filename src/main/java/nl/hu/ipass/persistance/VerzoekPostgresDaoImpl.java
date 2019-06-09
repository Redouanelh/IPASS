package nl.hu.ipass.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import nl.hu.ipass.domain.Verzoek;

public class VerzoekPostgresDaoImpl extends PostgresBaseDao implements VerzoekDao {
	static SpelerDao spelerdao = new SpelerPostgresDaoImpl();


	@Override
	public ArrayList<Verzoek> getAllVerzoeken() {
			String query = "select * from verzoeken";
			String result = "";
			
			ArrayList<Verzoek> verzoeken = new ArrayList<Verzoek>(); 
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet myRs = pstmt.executeQuery();
				
				while (myRs.next()) {
					Verzoek verzoek = new Verzoek();
					
					verzoek.setTeamverzoek(myRs.getString("teamverzoek"));
					verzoek.setSpeler(spelerdao.getSpelerById(myRs.getInt("persoonsid")));
					
					verzoeken.add(verzoek);
					result += "PersoonsID: " + verzoek.getSpeler().getPersoonsID() + "\n" + "Teamverzoek: " + verzoek.getTeamverzoek();
				}
				myRs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
			return verzoeken;
	}

	@Override
	public Verzoek getVerzoekByPersoonsid(int id) {
		String query = "select * from verzoeken where persoonsid = ?";
		String result = "";
		
		Verzoek verzoek = new Verzoek();

		try (Connection conn = super.getConnection()) {
	
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet myRs = pstmt.executeQuery();
	
		while (myRs.next()) {
			
			verzoek.setTeamverzoek(myRs.getString("teamverzoek"));
			verzoek.setSpeler(spelerdao.getSpelerById(myRs.getInt("persoonsid")));
			
			result += "PersoonsID: " + verzoek.getSpeler().getPersoonsID() + "\n" + "Teamverzoek: " + verzoek.getTeamverzoek();
		}
		myRs.close();
		pstmt.close();
	
			} catch (Exception e) {
				e.printStackTrace();
		}
		System.out.println(result);
		return verzoek;	
	}

	@Override
	public boolean addVerzoek(Verzoek v) {
		String query = "insert into verzoeken values (?, ?)";
		boolean verzoekAdded = false;
		
		try (Connection conn = super.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, v.getTeamverzoek());
			pstmt.setInt(2, v.getSpeler().getPersoonsID());
			
			verzoekAdded = pstmt.executeUpdate() > 0;
			pstmt.close();
			System.out.println("Verzoek toegvoegd: " + verzoekAdded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verzoekAdded;
	}

	@Override
	public boolean deleteVerzoek(Verzoek v) {
		boolean verzoekExist = getVerzoekByPersoonsid(v.getSpeler().getPersoonsID()) != null;
		boolean verzoekDeleted = false;
		
		if(verzoekExist) {
			String query = "delete from verzoeken where teamverzoek = ?";
			
			try (Connection conn = super.getConnection()) {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, v.getTeamverzoek());
				verzoekDeleted = pstmt.executeUpdate() > 0;
				pstmt.close();
				System.out.println("Verzoek deleted: " + verzoekDeleted);
								
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Verzoek bestaat niet");
		}
		
		return verzoekDeleted;
	}
	
	

}
