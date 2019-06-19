function loadVerzoeken() {
  var fetchget = {
    method: 'GET',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    }
  }
  fetch('restservices/wachtlijstsysteem/beheerderdashboard', fetchget)
  .then(function(response) {
    if (response.ok) {
      return response.json();
    }
    else {
      window.location = "unauthorized.html"; // Als je unauthorized bent, wordt je terug gestuurd naar je eigen dashboard.
    }
  })
  .then(function(myJson) {
    document.querySelector("#cardtitle").innerHTML = "Welkom "+ myJson.gebruiker; 
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
  var hiddenInput = document.getElementById("hiddenInput");
  var hiddenInput2 = document.getElementById("hiddenInput2");
  var i = 1;

  for (const value of myJson) {
    const row = table.insertRow(i);
    row.setAttribute("id", value.teamverzoek);
    row.setAttribute("name", "teamverzoek");
    cell0 = row.insertCell(0);

    if (value.melding != "Momenteel geen openstaande verzoeken beschikbaar.") {
      cell1 = row.insertCell(1);
      cell0.innerHTML = value.persoonsid;
      cell1.innerHTML = value.teamverzoek + "<button class='waves-effect waves-light btn-small #bf360c deep-orange darken-4' id='verzoek_weigeren'>X</button>"
                                          + "<button class='waves-effect waves-light btn-small #bf360c deep-orange darken-4' id='verzoek_accepteren'>✔</button>";

      cell1.addEventListener("click", function() {
        var team = row.getAttribute("id");
        hiddenInput.setAttribute("value", team);
        hiddenInput2.setAttribute("value", value.persoonsid);

        verzoekWeigeren(team);
        event.stopPropagation(); // Zodat de pagina niet refresht door de <form>.
        event.preventDefault();
      });
    } else {
      cell0.innerHTML = value.melding;
    }

    i++; 
  }
}

function verzoekWeigeren(team) {
  var formData = new FormData(document.querySelector("#verzoekWeigerenForm"))
  var encData = new URLSearchParams(formData.entries());
  var fetchdelete = {
    method: 'DELETE',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    },
    body: encData
  }

  fetch('restservices/wachtlijstsysteem/verzoekweigeren', fetchdelete)
    .then(function(response) {
      if(response.ok) {
        document.getElementById('foutmelding').style.display = "none";
        document.getElementById('goedmelding').style.display = "block"; // Toon melding dat het gelukt is.

        loadVerzoeken();
        clearTable();
      } else {
        document.getElementById('goedmelding').style.display = "none"; 
        document.getElementById('foutmelding').style.display = "block"; // Toon foutmelding.
        
      }
    });
}

function verzoekAccepteren() {

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

loadVerzoeken();