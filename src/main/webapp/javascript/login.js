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
              window.sessionStorage.setItem("myJWT", myJson.JWT);
            } else {
              window.location = "spelerdashboard.html";
              console.log("Key received");
              window.sessionStorage.setItem("myJWT", myJson.JWT);
            }
          }
          else {
            console.log(myJson.error);
            document.getElementById('login_foutmelding').style.display = "block";
            document.getElementById('responsive_whitespace').style.display = "block";
          }
        })
        .catch(error => console.log(error.message))
      }