package nl.hu.v1wac.ipass.domain;

import java.util.Date;

public class TestMain {
	  public static void main(String[] args) {
		  
		  Date date = new Date();
		  Speler s1 = new Speler(1, "Redouan", "el", "Hidraoui", "Wachtwoordd", "J", "N", 1, date, 34234234);
		  Beheerder b1 = new Beheerder(2, "Jannepan", "de", "Haas", "Wachtwoordd", "N", "J", date, 35654654);
		  Team t1 = new Team("JO19-5", "Wij zijn de beste!", 5, 3, 1, "Liga B", "Jan@outlook.com");
		  Adres a1 = new Adres(8, "3544NL", 17, "Hooivlinder", "Utrecht");
		  
		  //Teamobject en adresobject in constructor zetten van speler/team en dan hier toevoegen
		  //Van deze dingen wel getter en setter maken! 
		  //Dit ook doen voor spelers array in team;
		  System.out.println(s1 + "\n\n" + b1 + "\n\n" + t1 + "\n\n" + a1);

		  
		  
	  }
}
