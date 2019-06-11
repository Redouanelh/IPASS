// Haalt de gegevens van desbetreffende gebruiker op.
function loadProfile() {
  var fetchget = {
    method: 'GET',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    }
  }
  fetch('restservices/wachtlijstsysteem/spelerprofile/', fetchget)
  .then(function(response) {
    if (response.ok) {
      return response.json();
    }
    else {
      window.location = "unauthorized.html"; // Als je unauthorized bent, wordt je terug gestuurd naar je eigen dashboard.
    }
  })
  .then(function(myJson) {
    // Hier de functie die de teamgegevens op de webpagina plaatst.
    setTeamVariables(myJson);
    console.log(myJson);
    // Haalt de teamgenoten van de gebruiker op.
    loadTeammates();
  });
}

// Responsive sidebar
document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.sidenav');
  var instances = M.Sidenav.init(elems, {});
});

function loadTeammates() {
  var fetchget = {
    method: 'GET',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    }
  }
  fetch('restservices/wachtlijstsysteem/spelerteam', fetchget)
  .then(function(response) {
    return response.json();
  })
  .then(function(myJson) {
    //Hier de functie aanroepen voor tabel vullen, maar niet de tabel vullen functie hier schrijven anders wordt t onoverzichtelijk.
    console.log(myJson);
  });
}

// Bij team pagina een lijst tonen met al je teamgenoten, team naam, gewonnen/gelijk/verloren en de optie team verlaten, dit wordt dan een update en je wordt in de wachtlijstteam geplaatst.

// ALS JE ALS TEAM AL "WACHTLIJST" HEBT MOET JE NIET KUNNEN VERLATEN! HET SYSTEEM MOET DAN EEN BERICHT GEVEN!

// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

  // De functie die de teamgegevens op de webpagina plaatst.
function setTeamVariables(myJson) {
  setValue("#teamnaam", myJson.teamnaam);
  setValue("#competitie", myJson.competitie);
  setValue("#trainermail", myJson.trainermail);
  setValue("#motto", myJson.motto);
  setValue("#gewonnen", myJson.gewonnen);
  setValue("#gelijk", myJson.gelijk);
  setValue("#verloren", myJson.verloren);
}

// Een functie die bepaalde values in de webpagina plaatst met behulp van een id.
function setValue(id, value) {
  document.querySelector(id).innerHTML += value;
}

  loadProfile();
