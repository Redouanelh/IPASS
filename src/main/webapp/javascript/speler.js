// Haalt de beschikbare teams op voor spelers op de wachtlijst.
function loadTeamSpots() {
  var fetchget = {
    method: 'GET',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    }
  }
  fetch('restservices/wachtlijstsysteem/spelerdashboard', fetchget)
  .then(function(response) {
    if (response.ok) {
      return response.json();
    }
    else {
      window.location = "unauthorized.html"; // Als je unauthorized bent, wordt je terug gestuurd naar je eigen dashboard.
    }
  })
  .then(function(myJson) {

    loadProfile(); // Voor de welkomstbericht.
    
    document.querySelector("#tableTrigger").onclick = function() {
      clearTable();
      insertVerzoek(myJson); 
    }

  });
}

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
  })
  .then(function(myJson) {
    
    // Voor de welkomstbericht
    document.querySelector("#welkomtitle").innerHTML = "Welkom " + myJson.voornaam + "!";
  });
}

// Responsive sidebar
document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.sidenav');
  var instances = M.Sidenav.init(elems, {});
});

// In de backend wordt gecheckt of je al in een officiëel team zit of niet.
// Vult de tabel met de beschikbare teams.
function insertVerzoek(myJson) {
  var table = document.getElementById("verzoekTable");
  var hiddenInput = document.getElementById("hiddenInput");
  var i = 1;

  // Als er geen teams beschikbaar zijn (een extra check, dit wordt ook in de backend gedaan.)
  if (myJson.length == 0) {
    var row = table.insertRow(i);
    cell0 = row.insertCell(0);
    cell0.innerHTML = "Momenteel geen teams beschikbaar.";
  }

  for (const value of myJson) {
    const row = table.insertRow(i);
    row.setAttribute("id", value.melding);
    row.setAttribute("name", "teamverzoek");
    cell0 = row.insertCell(0);
    cell1 = row.insertCell(1);
    cell0.innerHTML = value.melding;

    // Alleen de verzoekbutton toevoegen als er daadwerkelijk voor jou een team beschikbaar is.
    if (value.melding == "JO19" || value.melding == "JO18" || value.melding == "JO17") {
      cell1.innerHTML = "<button class='waves-effect waves-light btn-small #bf360c deep-orange darken-4' id='verzoek_btn'>Verzoek indienen</button>";

      // Haalt de teamnaam van voor het verzoek op en roept de functie voor het indienen van een verzoek aan.
      cell1.addEventListener("click", function() {
        var team = row.getAttribute("id");
        hiddenInput.setAttribute("value", team);

        verzoekIndienen(team);
        event.stopPropagation(); // Zodat de pagina niet refresht door de <form>.
        event.preventDefault();
      });

    }

    i++; 
  }

}

function verzoekIndienen(team) {
    var formData = new FormData(document.querySelector("#verzoekForm"))
    var encData = new URLSearchParams(formData.entries());
    var fetchadd = {
      method: 'POST',
      headers: {

        'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
      },
      body: encData
    }

    fetch('restservices/wachtlijstsysteem/verzoekindienen', fetchadd)
        .then(function(response) {
          if (response.ok) {
            document.getElementById('foutmelding').style.display = "none";
            document.getElementById('goedmelding').style.display = "block"; // Toon melding dat het gelukt is.
            deleteSelectedRow(team);

            return response.json();
          } else {
            document.getElementById('goedmelding').style.display = "none"; 
            document.getElementById('foutmelding').style.display = "block"; // Toon foutmelding.

            return;
          }
        })
        .then(function(myJson) {
          // console.log(myJson);
        });
  }


// Een rij uit tabel verwijderen.
function deleteSelectedRow(team) {
  var row = document.getElementById(team)
  row.parentNode.removeChild(row);
}


// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

  // Tabel leeg gooien.
function clearTable() {
  var list = document.querySelectorAll("tr");
  var i = 1;
  while (i < list.length) {
    document.getElementById("verzoekTable").deleteRow(1);
    i++;
  }
}

  loadTeamSpots();