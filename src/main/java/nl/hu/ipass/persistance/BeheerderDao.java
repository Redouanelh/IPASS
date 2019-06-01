package nl.hu.ipass.persistance;

import java.util.ArrayList;

import nl.hu.ipass.domain.Beheerder;
import nl.hu.ipass.domain.Speler;

public interface BeheerderDao {
	
	public ArrayList<Beheerder> findAllBeheerders();
	public Beheerder getBeheerderByUsername(String username);
	public Beheerder getBeheerderById(int id);
	public boolean updateBeheerder(Beheerder beheerder);
	public boolean deleteBeheerder(Beheerder beheerder);

}
