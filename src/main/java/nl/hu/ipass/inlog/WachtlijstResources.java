package nl.hu.ipass.inlog;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.Beheerder;
import nl.hu.ipass.domain.Speler;
import nl.hu.ipass.domain.Team;
import nl.hu.ipass.domain.Verzoek;
import nl.hu.ipass.persistance.BeheerderPostgresDaoImpl;
import nl.hu.ipass.persistance.SpelerPostgresDaoImpl;
import nl.hu.ipass.persistance.TeamPostgresDaoImpl;
import nl.hu.ipass.persistance.VerzoekPostgresDaoImpl;

@Path("/wachtlijstsysteem")
public class WachtlijstResources {
	
	@POST
	@Path("/verzoekindienen")
	@RolesAllowed("J")
	@Produces("application/json")
	public Response verzoekIndienen(@Context SecurityContext sc,
									@FormParam("teamverzoek")String teamverzoek) {
		
		SpelerPostgresDaoImpl spelerdao = new SpelerPostgresDaoImpl();
		VerzoekPostgresDaoImpl verzoekdao = new VerzoekPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		Speler speler = spelerdao.getSpelerByUsername(username);
		System.out.println(teamverzoek);
		
		Verzoek verzoek = new Verzoek(speler, teamverzoek);
		System.out.println(verzoek.getSpeler().getPersoonsID() + " " + verzoek.getTeamverzoek());
		
		if (!verzoekdao.addVerzoek(verzoek)) {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put("error", "Verzoek niet kunnen toevoegen!");
			return Response.status(409).entity(messages).build();
		}
	
		return Response.ok(verzoek).build();
	}

	
	@GET
	@Path("/spelerdashboard")
	@RolesAllowed("J")
	@Produces("application/json")
	public String checkTeamSpots(@Context SecurityContext sc
								) { // Geeft de team terug waar er een plek is ontstaan
		
		SpelerPostgresDaoImpl spelerdao = new SpelerPostgresDaoImpl();
		TeamPostgresDaoImpl teamdao = new TeamPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		Speler speler = spelerdao.getSpelerByUsername(username);
		Team team = speler.getTeam();
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		if (team.getTeam().equals("Wachtlijst")) { // Check of je wel op de wachtlijst zit, en niet al in een officiëel team.
			
			// Check voor ieder team of er een plek/meerdere plekken zijn ontstaan. Zoja, dan wordt dat terug gestuurd.		
			if (teamdao.getSpelersFromTeam("JO19").size() < 12 ) {
				job.add("melding", "JO19");
				jab.add(job);
			}
			if (teamdao.getSpelersFromTeam("JO18").size() < 12 ) {
				job.add("melding", "JO18");
				jab.add(job);
			}
			if (teamdao.getSpelersFromTeam("JO17").size() < 12 ) {
				job.add("melding", "JO17");
				jab.add(job);
			}
			if (teamdao.getSpelersFromTeam("JO19").size() < 12 && teamdao.getSpelersFromTeam("JO18").size() < 12 && teamdao.getSpelersFromTeam("JO17").size() < 12 ) {
				job.add("melding", "Er zijn helaas geen teams beschikbaar, probeer het later opnieuw.");
				jab.add(job);
			}
			
		} else {
			// Je hebt 'Wachtlijst' niet als team.
			job.add("melding", "U bevind zich al in een team!");
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		
		return array.toString();
	}
	
	@GET
	@Path("/beheerderdashboard")
	@RolesAllowed("N")
	@Produces("application/json")
	public String getVerzoeken(@Context SecurityContext sc) { // Haalt de opgegeven verzoeken op.
		
		VerzoekPostgresDaoImpl verzoekdao = new VerzoekPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		// Check of er überhaupt wel verzoeken zijn
		if (verzoekdao.getAllVerzoeken().size() == 0) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("melding", "Momenteel geen openstaande verzoeken beschikbaar.");
			
			jab.add(job);
			JsonArray array = jab.build();
			return array.toString();
		}
			
		for (Verzoek v : verzoekdao.getAllVerzoeken()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("teamverzoek", v.getTeamverzoek());
			job.add("persoonsid", v.getSpeler().getPersoonsID());
			
			jab.add(job);
		}
		JsonArray array = jab.build();
		
		return array.toString();
	}	
	
	@GET
	@Path("/spelerprofile")
	@RolesAllowed("J") // De role 'J' betekend dat je een spelersaccount hebt, de role 'N' betekend dat je een beheerdersaccount hebt.
	@Produces("application/json")
	public String getInfoFromSpeler(@Context SecurityContext sc
								 ) {
		
		SpelerPostgresDaoImpl spelerdao = new SpelerPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		Speler speler = spelerdao.getSpelerByUsername(username);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		// Persoonsgegevens
		job.add("id", speler.getPersoonsID());
		job.add("voornaam", speler.getVoornaam());
		if (speler.getTussenvoegsel() != null) {
			job.add("tussenvoegsel", speler.getTussenvoegsel());
		}
		job.add("achternaam", speler.getAchternaam());
		if (speler.getSpelersnummer() == 0) {
			job.add("spelersnummer", "Momenteel geen spelersnummer");
		} else {
			job.add("spelersnummer", speler.getSpelersnummer());
		}
		job.add("geboortedatum", speler.getGeboortedatum().toString());
		if (speler.getMobiel() == 0) {
			job.add("mobiel", "Geen mobiel beschikbaar");
		} else {
			job.add("mobiel", speler.getMobiel());
		}

		// Team gegevens
		Team team = speler.getTeam();
		if (team.getMotto() == null) {
			job.add("motto", "Geen motto beschikbaar");
		} else {
			job.add("motto", team.getMotto());
		}
		if (team.getCompetitie() == null) {
			job.add("competitie", "Geen competitie beschikbaar");
		} else {
			job.add("competitie", team.getCompetitie());
		}
		if (team.getTrainermail() == null) {
			job.add("trainermail", team.getTrainermail());
		} else {
			job.add("trainermail", team.getTrainermail());
		}
		job.add("teamnaam", team.getTeam());
		job.add("gewonnen", team.getGewonnen());
		job.add("gelijk", team.getGelijk());
		job.add("verloren", team.getVerloren());
		
		// Adres gegevens
		Adres adres = speler.getHuidigAdres();
		job.add("postcode", adres.getPostcode());
		job.add("straat", adres.getStraat());
		if (adres.getHuisnummer() == 0) {
			job.add("huisnummer", adres.getHuisnummer());
		} else {
			job.add("huisnummer", adres.getHuisnummer());
		}
		job.add("woonplaats", adres.getWoonplaats());

		String obj = job.build().toString();
		return obj;
	}
	
	@GET
	@Path("/beheerderprofile")
	@RolesAllowed("N")
	@Produces("application/json")
	public String getProfileFromBeheerder(@Context SecurityContext sc
								 ) {
		
		BeheerderPostgresDaoImpl beheerderdao = new BeheerderPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		Beheerder beheerder = beheerderdao.getBeheerderByUsername(username);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		// Persoonsgegevens
		job.add("id", beheerder.getPersoonsID());
		job.add("voornaam", beheerder.getVoornaam());
		if (beheerder.getTussenvoegsel() != null) {
			job.add("tussenvoegsel", beheerder.getTussenvoegsel());
		}
		job.add("achternaam", beheerder.getAchternaam());
		job.add("geboortedatum", beheerder.getGeboortedatum().toString());
		if (beheerder.getMobiel() == 0) {
			job.add("mobiel", "Geen mobiel beschikbaar");
		} else {
			job.add("mobiel", beheerder.getMobiel());
		}
		
		// Adres gegevens
		Adres adres = beheerder.getHuidigAdres();
		job.add("postcode", adres.getPostcode());
		job.add("straat", adres.getStraat());
		if (adres.getHuisnummer() == 0) {
			job.add("huisnummer", adres.getHuisnummer());
		} else {
			job.add("huisnummer", adres.getHuisnummer());
		}
		job.add("woonplaats", adres.getWoonplaats());

		String obj = job.build().toString();
		return obj;
	}
	
	@GET
	@Path("/spelerteam")
	@RolesAllowed("J")
	@Produces("application/json")
	public String showTeammates(@Context SecurityContext sc
								) { // Toont de teamgenoten van de desbetreffende speler
		
		SpelerPostgresDaoImpl spelerdao = new SpelerPostgresDaoImpl();
		TeamPostgresDaoImpl teamdao = new TeamPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		Speler speler = spelerdao.getSpelerByUsername(username);
		Team team = speler.getTeam();
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Speler s : teamdao.getSpelersFromTeam(team.getTeam())) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			
			job.add("voornaam", s.getVoornaam());
			job.add("achternaam", s.getAchternaam());
			job.add("spelersnummer", s.getSpelersnummer());
			job.add("mobiel", s.getMobiel());
			
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@PUT
	@Path("/teamverlaten")
	@RolesAllowed("J")
	@Produces("application/json")
	public Response teamVerlaten(@Context SecurityContext sc
								 ) { // Team verlaten, zet de team op 'Wachtlijst' en de spelersnummer op 0.
		
		SpelerPostgresDaoImpl spelerdao = new SpelerPostgresDaoImpl();
		TeamPostgresDaoImpl teamdao = new TeamPostgresDaoImpl();
		
		String username = sc.getUserPrincipal().getName();
		Speler speler = spelerdao.getSpelerByUsername(username);
		
		// Check of speler al op de wachtlijst zit.
		if(speler.getTeam().getTeam().equals("Wachtlijst")) {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put("error", "Speler bevind zich al op de Wachtlijst!!");
			return Response.status(409).entity(messages).build(); 
		}
		
		speler.setTeam(teamdao.findTeamByName("Wachtlijst"));
		speler.setSpelersnummer(0);
		
		// Check of de speler succesful heeft kunnen updaten.
		if(!spelerdao.updateSpeler(speler)) {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put("error", "Speler heeft niet kunnen updaten!");
			return Response.status(409).entity(messages).build(); 
		}
		
		return Response.ok(speler).build();
	}
	
}
