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
    });
  }
  loadProfile();

  // De mijn locatie zit nu hardcodede hooivlinder in, dit moet een variabele worden die telkens met straatnaam van gebruiker wordt ingevoerd!

// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
  sessionStorage.clear();
  window.location = "login.html";
}