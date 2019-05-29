package nl.hu.ipass.inlog;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		
		boolean isSecure = requestCtx.getSecurityContext().isSecure();
		
		// Users worden als gast behandeld, tenzij een valid JWT is gegeven.
		MySecurityContext msc = new MySecurityContext("Unknown", "guest", isSecure);
		
		String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			
			String token = authHeader.substring("Bearer".length()).trim();
			
			try {
				// Token controleren
				System.out.println("Checking token");
				JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
				Claims claims = parser.parseClaimsJws(token).getBody();
				
				String user = claims.getSubject();
				String role = claims.get("role").toString();
				msc = new MySecurityContext(user, role, isSecure);
				
			} catch (JwtException | IllegalArgumentException e) {
				System.out.println("Invalid JWT, doorgaan als gast!");
			}
		}
		requestCtx.setSecurityContext(msc);
	}
}
