// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }

  // Als je unauthorized bent, wordt je terug gestuurd naar je unauthorized pagina. Niet vergeten dat in de functie te zetten net als spelerprofiel.js!