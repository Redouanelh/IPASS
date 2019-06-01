package nl.hu.ipass.persistance;

import nl.hu.ipass.inlog.UserDao;
import nl.hu.ipass.inlog.UserPostgresDaoImpl;

public class Main {
	public static void main(String[] args) {
        try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  
		  UserDao userDao = new UserPostgresDaoImpl();
		  SpelerDao spelerDao = new SpelerPostgresDaoImpl();
		  TeamDao teamDao = new TeamPostgresDaoImpl();
		  AdresDao adresDao = new AdresPostgresDaoImpl();
		  
//		  System.out.println(spelerDao.getSpelerByUsername("Redouan"));
//		  System.out.println(teamDao.findTeamByName("JO19"));
		  
	  }
}


