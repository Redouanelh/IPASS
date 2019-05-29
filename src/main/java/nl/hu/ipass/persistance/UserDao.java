package nl.hu.ipass.persistance;

public interface UserDao {
	public String findRoleForUser(String name, String pass);

}
