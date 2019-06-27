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
      else {
        window.location = "unauthorized.html"; // Als je unauthorized bent, wordt je terug gestuurd naar je eigen dashboard.
      }
    })
    .then(function(myJson) {
      // Hier de functie die de json values op de webpagina plaatst.
      setProfileVariables(myJson);
      setMapStreet(myJson.straat);
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

// De functie die de profielgegevens op de webpagina plaatst.
function setProfileVariables(myJson) {
  setValue("#voornaam", myJson.voornaam);
  if (myJson.tussenvoegsel != undefined) {
    setValue("#tussenvoegsel", myJson.tussenvoegsel);
  } else {
    setValue("#tussenvoegsel", "Geen tussenvoegsel beschikbaar");
  }
  setValue("#achternaam", myJson.achternaam);
  setValue("#geboortedatum", myJson.geboortedatum);
  setValue("#mobiel", myJson.mobiel);
  setValue("#straathuisnummer", myJson.straat + " ");
  setValue("#straathuisnummer", myJson.huisnummer);
  setValue("#postcode", myJson.postcode);
  setValue("#woonplaats", myJson.woonplaats);
}

// Voor de maps zodat je de juiste straat mee krijgt.
function setMapStreet(street) {
  document.querySelector("#gmap_canvas").setAttribute("src", "https://maps.google.com/maps?q=" + street + "&t=&z=13&ie=UTF8&iwloc=&output=embed");
}

// Een functie die bepaalde values in de webpagina plaatst met behulp van een id.
function setValue(id, value) {
  document.querySelector(id).innerHTML += value;
}

loadProfile();
