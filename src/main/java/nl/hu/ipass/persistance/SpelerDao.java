package nl.hu.ipass.persistance;

import java.util.ArrayList;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.Speler;

public interface SpelerDao {
	
	public ArrayList<Speler> findAllSpelers();
	public Speler getSpelerByUsername(String username);
	public Speler getSpelerById(int id);
	public boolean addSpeler(Speler speler, Adres adres);
	public boolean updateSpeler(Speler speler);
	public boolean deleteSpeler(Speler speler);

}
