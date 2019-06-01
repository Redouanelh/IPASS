package nl.hu.ipass.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nl.hu.ipass.persistance.SpelerDao;
import nl.hu.ipass.persistance.SpelerPostgresDaoImpl;

@Path("/wachtlijstsysteem")
public class WachtlijstResources {
	
	@GET
	@Produces("application/json")
	public String showCountries() {
		SpelerDao sdao = new SpelerPostgresDaoImpl();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Country c : ws.getAllCountries()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("country", c.getName());
			if (c.getCapital() == null) {
				job.add("capital", "No capital");
			} else {
				job.add("capital", c.getCapital());
			}
			
			if (c.getCode() == null) {
				job.add("code", "No code");
			} else {
				job.add("code", c.getCode());
			}
			job.add("region", c.getRegion());
			job.add("surface", c.getSurface());
			job.add("population", c.getPopulation());
			job.add("latitude", c.getLatitude());
			job.add("longitude", c.getLongitude());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

}
