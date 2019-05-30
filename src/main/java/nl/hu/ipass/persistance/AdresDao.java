package nl.hu.ipass.persistance;

import nl.hu.ipass.domain.Adres;

public interface AdresDao {

	public Adres findAdresById(int id);
	public boolean updateAdres(Adres adres);
	
}
