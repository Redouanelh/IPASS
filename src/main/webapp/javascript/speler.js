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

    console.log(myJson);

    document.querySelector("#tableTrigger").onclick = function() {
      clearTable();
      insertVerzoek(myJson); 
    }

  });
}

// Als je een teamverzoek verzend, geef dan ook een spelernummer mee, want wachtlijstmensen hebben spelersnummer 0., of bij beheerder als hij accept
// dan moet hij een random nummer genereren en meegeven en setten voor de speler. 
// Misschien random nummer genereren, of gewoon zeggen dat ze zelf mogen inputten en dan als formparam setten.

// Responsive sidebar
document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.sidenav');
  var instances = M.Sidenav.init(elems, {});
});

function insertVerzoek(myJson) {
  var table = document.getElementById("verzoekTable");
  var i = 1;

  for (const value of myJson) {
    const row = table.insertRow(i);
    row.setAttribute("team", value.melding);
    cell0 = row.insertCell(0);
    cell1 = row.insertCell(1);
    cell0.innerHTML = value.melding;

    // Alleen de verzoekbutton toevoegen als er daadwerkelijk voor jou een team beschikbaar is.
    if (value.melding == "JO19" || value.melding == "JO18" || value.melding == "JO17") {
      cell1.innerHTML = "<button class='waves-effect waves-light btn-small #bf360c deep-orange darken-4' id='verzoek_btn'>Verzoek indienen</button>";
    }

    i++; 
  }
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
    document.getElementById("teammateTable").deleteRow(1);
    i++;
  }
}

  loadTeamSpots();