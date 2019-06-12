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
    // Functie die de teamgegevens op de webpagina plaatst.
    setTeamVariables(myJson);
    console.log(myJson);
    // Haalt de teamgenoten van de gebruiker op.
    loadTeammates();
    // Voert alvast de persoonsid in van de speler voor het verlaten van een team.
    document.querySelector("#persoonsidmodal").setAttribute("value", myJson.id);

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
    //Hier de functie aanroepen voor tabel vullen.
    insertTeam(myJson);
    console.log(myJson);
  });
}

// Team verlaten functie
function leaveTeam() {
  var formData = new FormData(document.querySelector("#teamVerlatenForm"))
  var encData = new URLSearchParams(formData.entries());
  var fetchupdate = {
    method: 'PUT',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    },
    body: encData
  }

  fetch('restservices/wachtlijstsysteem/teamverlaten', fetchupdate)
      .then(function(response) {
        if (response.ok) {
          document.getElementById('foutmelding').style.display = "none";
          modalTeamVerlaten.style.display = "none";
          return response.json();
        } else {
          document.getElementById('foutmelding').style.display = "block";
          return;
        }
      })
      .then(function(myJson) {
      clearTable();
      loadProfile();
        
      });
}

// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

  // De functie die de teamgegevens op de webpagina plaatst.
function setTeamVariables(myJson) {
  document.querySelector("#teamnaam").innerHTML = "Team: ";
  document.querySelector("#competitie").innerHTML = "Competitie: ";
  document.querySelector("#trainermail").innerHTML = "Trainer: ";
  document.querySelector("#motto").innerHTML = "Motto: ";
  document.querySelector("#gewonnen").innerHTML = "Aantal gewonnen: ";
  document.querySelector("#gelijk").innerHTML = "Aantal gelijk: ";
  document.querySelector("#verloren").innerHTML = "Aantal verloren: ";
  setValue("#teamnaam", myJson.teamnaam);
  setValue("#competitie", myJson.competitie);
  setValue("#trainermail", myJson.trainermail);
  setValue("#motto", myJson.motto);
  setValue("#gewonnen", myJson.gewonnen);
  setValue("#gelijk", myJson.gelijk);
  setValue("#verloren", myJson.verloren);
}

function setTeammateInfo(value) {
  document.querySelector("#teammateVoornaam").innerHTML = "Voornaam: ";
  document.querySelector("#teammateAchternaam").innerHTML = "Achternaam: ";
  document.querySelector("#teammateMobiel").innerHTML = "Mobiel: ";
  document.querySelector("#teammateSpelernummer").innerHTML = "Spelernummer: ";
  setValue("#teammateVoornaam", value.voornaam);
  setValue("#teammateAchternaam", value.achternaam);
  setValue("#teammateMobiel", value.mobiel);
  setValue("#teammateSpelernummer", value.spelersnummer);

}

function insertTeam(myJson) {
  var table = document.getElementById("teammateTable");
  var i = 1;

  for (const value of myJson) {
    const row = table.insertRow(i);
    row.setAttribute("id", value.spelersnummer);
    cell0 = row.insertCell(0);
    cell1 = row.insertCell(1);
    cell0.innerHTML = value.voornaam
    cell1.innerHTML = value.spelersnummer

    // Toont extra info van een teamgenoot als er op geklikt wordt.
    row.addEventListener("click", function() {
      modal.style.display = "block";
      // Stopt de extra info van de teamgenoot in de pop-up.
      setTeammateInfo(value);
    });

    i++; 
  }
}

// Tabel leeg gooien.
function clearTable() {
  var list = document.querySelectorAll("tr");
  var i = 1;
  while (i < list.length) {
    document.getElementById("teammateTable").deleteRow(1);
    i++;
  }
}

// Een functie die bepaalde values in de webpagina plaatst met behulp van een id.
function setValue(id, value) {
  document.querySelector(id).innerHTML += value;
}

// de pop-up voor extra informatie teamgenoot
var modal = document.getElementById("myModal");
var span = document.getElementsByClassName("close")[0];

span.onclick = function() {
  modal.style.display = "none";
}

window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

// de pop-up voor team verlaten.
var modalTeamVerlaten = document.getElementById("myModalTeamverlaten");
var span2 = document.getElementsByClassName("close2")[0];

span2.onclick = function() {
  document.getElementById('foutmelding').style.display = "none";
  modalTeamVerlaten.style.display = "none";
}

window.onclick = function(event) {
  if (event.target == modalTeamVerlaten) {
    document.getElementById('foutmelding').style.display = "none";
    modalTeamVerlaten.style.display = "none";
  }
}

document.querySelector("#teamVerlaten").onclick = function(event) {
  document.getElementById('foutmelding').style.display = "none";
  modalTeamVerlaten.style.display = "block";

  document.querySelector("#teamVerlatenBevestiging").onclick = function(event) {
    leaveTeam();
    event.stopPropagation();
    event.preventDefault();
  }

}

  loadProfile();
