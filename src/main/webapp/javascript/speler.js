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
    //Hier de functie aanroepen 
    console.log(myJson);
  });
}

// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

  // Als je unauthorized bent, wordt je terug gestuurd naar je unauthorized pagina. Niet vergeten dat in de functie te zetten net als spelerprofiel.js!

  loadTeamSpots();