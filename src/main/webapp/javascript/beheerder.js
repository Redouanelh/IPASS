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
    //Hier de functie aanroepen 
    console.log(myJson);
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

// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

loadVerzoeken();