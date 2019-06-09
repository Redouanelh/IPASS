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
    // Hier de functie die de json values in de innerhtml plaatst.
    console.log(myJson);
    loadTeammates();
  });
}

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


// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

  loadProfile();
