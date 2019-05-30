package nl.hu.ipass.persistance;

import java.util.ArrayList;

import nl.hu.ipass.domain.Speler;

public interface SpelerDao {
	
	public ArrayList<Speler> findAllSpelers();
	public Speler getSpelerByUsername(String username);
	public boolean updateSpeler(Speler speler);
	public boolean deleteSpeler(Speler speler);

}
