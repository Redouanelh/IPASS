function loadSpelers() {
    fetch('restservices/countries')
    .then(function(response) {
      return response.json();
    })
    .then(function(myJson) {
      loadInfo(myJson)
    });
  }