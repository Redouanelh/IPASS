package nl.hu.ipass.inlog;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import nl.hu.ipass.domain.Adres;
import nl.hu.ipass.domain.Beheerder;
import nl.hu.ipass.domain.Speler;
import nl.hu.ipass.domain.Team;
import nl.hu.ipass.persistance.BeheerderPostgresDaoImpl;
import nl.hu.ipass.persistance.SpelerPostgresDaoImpl;

@Path("/wachtlijstsysteem")
public class WachtlijstResources {
	
	@GET
	@Path("/spelerprofile")
	@RolesAllowed("J")
	@Produces("application/json")
	public String getProfileFromSpeler(@Context SecurityContext sc
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
	
	// Extra maken waarbij je de teamgenoten kan inzien van je team, en één waarin je alle beheerders kan opvragen
	
}
