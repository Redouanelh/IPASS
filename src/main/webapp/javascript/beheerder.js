// Logout button stuurt je terug naar de login pagina, en leegt ook de session storage met de JWT token
document.querySelector("#logout_btn").onclick = function(event) {
    sessionStorage.clear();
    window.location = "login.html";
  }