function loadProfile() {
    var fetchget = {
      method: 'GET',
      headers: {
        'Authorization' : 'Bearer ' + window.sessionStorage.getItem("JWT")
      }
    }
    fetch('restservices/wachtlijstsysteem/spelerprofile/', fetchget)
    .then(function(response) {
      return response.json();
    })
    .then(function(myJson) {
      console.log(myJson);
    });
  }
  
loadProfile();
  