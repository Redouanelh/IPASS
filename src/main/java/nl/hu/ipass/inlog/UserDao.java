package nl.hu.ipass.inlog;

public interface UserDao {
	
	public String findRoleForUser(String name, String pass);
	public int findIdForUser(String name);

}
