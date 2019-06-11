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
      // Hier de functie die de json values op de webpagina plaatst.
      setProfileVariables(myJson);
      console.log(myJson);
    });
  }

// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
  sessionStorage.clear();
  window.location = "login.html";
}

  // Responsive sidebar
document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.sidenav');
  var instances = M.Sidenav.init(elems, {});
});

function setProfileVariables(myJson) {
  setValue("#voornaam", myJson.voornaam);
  setValue("#tussenvoegsel", myJson.tussenvoegsel);
  setValue("#achternaam", myJson.achternaam);
  setValue("#geboortedatum", myJson.geboortedatum);
  setValue("#mobiel", myJson.mobiel);
  setValue("#teamnaam", myJson.teamnaam);
  setValue("#straathuisnummer", myJson.straat + " ");
  setValue("#straathuisnummer", myJson.huisnummer);
  setValue("#postcode", myJson.postcode);
  setValue("#woonplaats", myJson.woonplaats);
}

function setValue(id, value) {
  document.querySelector(id).innerHTML += value;
}

loadProfile();
