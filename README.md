Redouan el Hidraoui\
HBO-ICT SIE V1A

Het WachtLijst-Systeem

#### Link naar de website:
https://wachtlijstsysteem.herokuapp.com/login.html

#### Inloggegevens
Username: Redouan\
Password: Utrecht\
Rol: Speler\
Team: JO19

Username: Justin\
Password: machine\
Rol: Speler\
Team: JO18

Username: Kaster\
Password: band\
Rol: Speler\
Team: JO17

Username: Klaas\
Password: banden\
Rol: Speler\
Team: Wachtlijst

Username: Bart\
Password: webapp\
Rol: Beheerder

#### Speler
* Als je je op de wachtlijst bevind & er is/zijn team(s) beschikbaar, dan kun je een verzoek indienen bij de beheerder.
* Als je al in een officiëel team zit, dan zal het systeem dit tonen.
* Als er geen teams beschikbaar zijn zal het systeem dit tonen.
* Bij het verlaten van een team zal de team op 'Wachtlijst' gezet worden, en zal de spelersnummer op 0 gezet worden.


#### Beheerder
* Accepteren
  * Bij het accepteren van een verzoek zullen alle verzoeken van dat persoon worden verwijderd, en de persoon wijzigt van team + krijgt een gegenereerde spelersnummer toegewezen.
  * Als de team na het accepteren 12 spelers bevat, dan zullen alle andere verzoeken voor dat desbetreffende team ook worden verwijderd. Dit heb ik gedaan om te voorkomen dat een team ineens meer dan 12 man gaat bevatten.
  * Als de team na het accepteren minder dan 12 spelers bevat, dan zullen de andere verzoeken blijven bestaan.
* Weigeren
  * Alleen het verzoek van de desbetreffende persoon zal worden verwijderd.
  * Als de persoon meerdere verzoeken voor datzelfde team heeft ingediend, dan worden die ook allemaal verwijderd.
* De verzoeken staan op volgorde van oud naar nieuw.

#### Let op!
* Een team bevat maximaal 12 spelers.
* Er is op dit moment 1 beheerder actief, dit kunnen er later gemakkelijk meer worden.
* Het systeem bevat momenteel 4 teams: JO19, JO18, JO17 & de Wachtlijst. In de toekomst kunnen hier gemakkelijk meerdere teams aan worden toegevoegd.
* Er is geen registratie beschikbaar. Hier heb ik zelf voor gekozen. Het lijkt mij namelijk logischer dat de beheerder zelf de desbetreffende speler in het systeem plaatst nadat deze zich heeft aangesloten bij voetbalclub VV de Meern. Hiermee voorkom je o.a. nepaccounts.
* Als je als speler een pagina wilt bezoeken dat alleen voor de beheerder beschikbaar moet zijn (of andersom) door bijvoorbeeld de URL-balk aan te passen, dan zul je geredirect worden naar een 'Unauthorized' pagina. Op deze pagina heb je als enigste optie 'Uitloggen'. Dit heb ik gedaan om te voorkomen dat een speler een beheerderspagina, of een beheerder een spelerspagina kan bezoeken.
