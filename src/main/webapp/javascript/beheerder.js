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

    loadProfile() // Voor de welkomstbericht.

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
  fetch('restservices/wachtlijstsysteem/beheerderprofile/', fetchget)
  .then(function(response) {
    if (response.ok) {
      return response.json();
    }
  })
  .then(function(myJson) {
    
    document.querySelector("#welkomtitle").innerHTML = "Welkom " + myJson.voornaam + "!";

  });
}

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
      cell1 = row.insertCell(1); // De teamnaam
      cell2 = row.insertCell(2); // Verzoek accepteren
      cell3 = row.insertCell(3); // Verzoek weigeren

      cell0.innerHTML = value.persoonsid;
      cell1.innerHTML = value.teamverzoek;
      cell2.innerHTML = "<button class='waves-effect waves-light btn-small #bf360c deep-orange darken-4' id='verzoek_accepteren' style='width: 100%;'>✔</button>";
      cell3.innerHTML = "<button class='waves-effect waves-light btn-small #bf360c deep-orange darken-4' id='verzoek_weigeren' style='width: 100%;'>X</button>";

      // Verzoek accepteren button
      cell2.addEventListener("click", function() {
        var team = row.getAttribute("id");
        hiddenInput.setAttribute("value", team);
        hiddenInput2.setAttribute("value", value.persoonsid);

        verzoekAccepteren(team);
        event.stopPropagation();
        event.preventDefault();
      })
     
      // Verzoek weigeren button
      cell3.addEventListener("click", function() {
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

function verzoekAccepteren(team) {
  var formData = new FormData(document.querySelector("#verzoekForm"))
  var encData = new URLSearchParams(formData.entries());
  var fetchupdate = {
    method: 'PUT',
    headers: {
      'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
    },
    body: encData
  }

  fetch('restservices/wachtlijstsysteem/verzoekaccepteren', fetchupdate)
      .then(function(response) {
        if (response.ok) {
          document.getElementById('foutmelding').style.display = "none"; // Haal de vorige meldingen eerst weg.
          document.getElementById('foutmelding2').style.display = "none";
          document.getElementById('goedmelding').style.display = "none";
          document.getElementById('goedmelding2').style.display = "block"; // Toon melding dat het gelukt is.

          loadVerzoeken();
          clearTable();
          return response.json();
        } else {
          document.getElementById('goedmelding').style.display = "none";  // Haal de vorige meldingen eerst weg.
          document.getElementById('goedmelding2').style.display = "none"; 
          document.getElementById('foutmelding').style.display = "none"; 
          document.getElementById('foutmelding2').style.display = "block"; // Toon foutmelding.

          return;
        }
      })
      .then(function(myJson) {
      console.log(myJson);
        
      });
}

function verzoekWeigeren(team) {
  var formData = new FormData(document.querySelector("#verzoekForm"))
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
        document.getElementById('foutmelding2').style.display = "none"; // Haal de vorige meldingen eerst weg.
        document.getElementById('goedmelding2').style.display = "none"; 

        document.getElementById('foutmelding').style.display = "none";
        document.getElementById('goedmelding').style.display = "block"; // Toon melding dat het gelukt is.

        loadVerzoeken();
        clearTable();
      } else {
        document.getElementById('foutmelding2').style.display = "none"; // Haal de vorige meldingen eerst weg.
        document.getElementById('goedmelding2').style.display = "none"; 

        document.getElementById('goedmelding').style.display = "none"; 
        document.getElementById('foutmelding').style.display = "block"; // Toon foutmelding.
        
      }
    });
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