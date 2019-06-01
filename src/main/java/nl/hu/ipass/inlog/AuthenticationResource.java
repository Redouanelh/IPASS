package nl.hu.ipass.inlog;

import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Path("/authentication")
public class AuthenticationResource {
final static public Key key = MacProvider.generateKey();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("username") String username,
									 @FormParam("password") String password) {
		
		try {
			// Role van de gegeven username/password combinatie in de database zoeken
			UserDao dao = new UserPostgresDaoImpl();
			String role = dao.findRoleForUser(username, password);
			
			if (role == null) {
				throw new IllegalArgumentException("No user found!");
			}
			
			// Creëren van de JWT token voor in de Sessionstorage
			String token = createToken(username, role);
			System.out.println("Token created.");
						
			JsonObjectBuilder job = Json.createObjectBuilder();
				
			job.add("JWT", token);
			job.add("role", role);		
			String obj = job.build().toString();
			
			// Return als response het JSON-object met de token en de role
			return Response.ok(obj).build();
			
			
		} catch(Exception e) {
			Map<String, String> message = new HashMap<String, String>();
			message.put("error", "Invalid username/password");
			
			// Return als response de melding dat het een invalid username/password combinatie is
			Response resp = Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
			return resp;

		}	
	}
	
	private String createToken(String username, String role) throws JwtException {
		Calendar expiration = Calendar.getInstance();
		expiration.add(Calendar.MINUTE, 30);
		System.out.println("Creating token...");
		
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(expiration.getTime())
				.claim("role", role)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();	
	}

}
