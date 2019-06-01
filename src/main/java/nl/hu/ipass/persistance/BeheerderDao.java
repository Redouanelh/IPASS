package nl.hu.ipass.persistance;

import java.util.ArrayList;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.Beheerder;

public interface BeheerderDao {
	
	public ArrayList<Beheerder> findAllBeheerders();
	public Beheerder getBeheerderByUsername(String username);
	public Beheerder getBeheerderById(int id);
	public boolean addBeheerder(Beheerder beheerder, Adres adres);
	public boolean updateBeheerder(Beheerder beheerder);
	public boolean deleteBeheerder(Beheerder beheerder);

}
