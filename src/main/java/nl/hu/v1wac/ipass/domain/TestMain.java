package nl.hu.v1wac.ipass.domain;

import java.util.ArrayList;
import java.util.Date;

public class TestMain {
	  public static void main(String[] args) {
		  
		  Date date = new Date();
		  
		  Adres a1 = new Adres(8, "3544NL", 17, "Hooivlinder", "Utrecht");
		  Adres a2 = new Adres(9, "3544OL", 19, "Allenstraat", "Amsterdam");

		  Team t1 = new Team("JO19-5", "Wij zijn de beste!", 5, 3, 1, "Liga B", "Jan@outlook.com");
		  Team t2 = new Team("JO19-9", "Wij zijn de beste!", 5, 3, 1, "Liga B", "Jan@outlook.com");

		  Speler s1 = new Speler(1, "Redouan", "el", "Hidraoui", "Wachtwoordd", "J", "N", 1, date, 34234234, t1, a1);
		  Speler s2 = new Speler(2, "Redouan2", "el", "Hidraoui", "Wachtwoordd", "J", "N", 1, date, 34234234, t2, a2);
		  
		  Verzoek v1 = new Verzoek(s1, "JO19-1");
		  
		  Beheerder b1 = new Beheerder(2, "Jannepan", "de", "Haas", "Wachtwoordd", "N", "J", date, 35654654, a1);	
		  
		  
		  
		  
	  }
}
