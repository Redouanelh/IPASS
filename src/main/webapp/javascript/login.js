// Session storage legen zodra loginpagina wordt geladen, in het geval dat iemand nadat er ingelogt is via de URL balk terug gaat naar de inlogpagina. 
// Als dat wordt gedaan blijft de sessiontoken namelijk bestaan, wat ik tegen wil gaan.
sessionStorage.clear();

document.querySelector("#btn_login").addEventListener("click", function(event) {
    login();
    event.stopPropagation();
    event.preventDefault();
  })
  
 function login(event) {
    // De ingevoerde parameters ophalen uit de form
    var formData = new FormData(document.querySelector("#loginformuliercontent"));
    var encData = new URLSearchParams(formData.entries());
    
    // Het fetchen naar de backend
    fetch('restservices/authentication/', { method : 'POST', body: encData})
      .then(response => Promise.all([response.status, response.json()]))
  
        .then(function([status, myJson]) {
          if (status == 200) {
            if (myJson.role == "N") {
              window.location = "beheerderdashboard.html";
              console.log("Key received");
              window.sessionStorage.setItem("JWT", myJson.JWT);
            } else {
              window.location = "spelerdashboard.html";
              console.log("Key received");
              window.sessionStorage.setItem("JWT", myJson.JWT);
            }
          }
          else {
            console.log(myJson.error);
            document.getElementById('login_foutmelding').style.display = "block";
            document.getElementById('responsive_whitespace').style.display = "block";
            document.getElementById('responsive_whitespace2').style.display = "block";
          }
        })
        .catch(error => console.log(error.message))
      }