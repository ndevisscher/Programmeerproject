#Eindverslag Programmeerproject 
###Niek de Visscher (10667474)

####Projectnaam:
Receptenhulp

####Projectbeschrijving:
De app is een hulpmiddel om recepten van gerechten te vinden. Deze recepten kun je zelf invoeren in de app, doormiddel van het ingeven van de benodigde ingrediënten en de bereidingswijze. Hierna kun je de gerechten opzoeken aan de hand van de ingrediënten die je wilt gebruiken. Verder is het mogelijk om mensen aan de app toe te voegen en allergieën op te geven voor deze mensen. Je kunt deze mensen toevoegen aan een groep, zodat je slechts een groep hoeft te selecteren in plaats van elke persoon individueel. Op het moment dat je een recept gaat zoeken kan men aangeven dat een groep/persoon of personen mee-eten en zullen alle allergieën worden meegenomen in het zoeken naar recepten. Zo zul je dus geen recepten krijgen die noten bevatten als iemand mee eet die een notenallergie heeft.

####Technisch design:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/einproject%20mprog%20database.png "1")
<br>
De app maakt gebruik van een SQlite database die alle recepten, toegevoegde mensen en groepen bijhoudt. De database maakt gebruik van 3 tabellen, 1 voor de recepten, 1 voor de mensen en 1 voor de groepen. De tabellen van mensen en groepen zijn aan elkaar gelinkt doormiddel van het persoonsID. Zo staat er voor een groep een lijst met alle ID's van mensen die in de groep zitten, zodat er duidelijk kan worden gezocht naar alle bijbehorende allergieën van de mensen. Voor de interacties met de database wordt gebruik gemaakt van verschillende queries. Deze queries worden doormiddel van een databasehelper geimplementeerd. Bijna elke activity maakt gebruik van een querie, daarom wordt ook in bijna elke activity een databasehelper aangemaakt. Hiermee worden de lijsten in de app gevuld, zodat de gebruiker selecties kan maken en hiermee wordt ook de input van de gerbuiker naar de database toe gestuurd.

####Aplication design:

De activity's zelf zijn niet heel uitzonderlijk. Er wordt gebruik gemaakt van simpele elementen in de vorm van inputfields en knoppen. Deze hebben allemaal duidelijke hints en tekst, zodat het makkelijk moet zijn voor de gebruiker om te begrijpen wat ze doen. Verder wordt er gebruik gemaakt van enkele listviews, die de gebruikers input weergeven en opslaan totdat deze moet worden gebruikt. Zo wordt bijgehouden welke ingrediënten de gebruiker ingeeft bij de zoekfunctie en maakt de listview het ook mogelijk om ingrediënten weer uit de zoekquerie te halen door ze uit de lijst te verwijderen. Er zit bij de zoekfunctie ook een switch, deze maakt het mogelijk om te switchen tussen het toevoegen van mensen en groepen als er gezocht wordt naar recepten. Als men eerst mensen toevoegd en hierna groepen worden beide meegenomen. Dit betekent dus dat alle allergieën van de mensen en de mensen in de groepen worden meegenomen in het zoeken naar goede recepten die aan de zoekcriteria voldoen.

####Activity's:

#####Activiteiten verloop:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/activityVerloop.png "1")

#####Home scherm:
Hier wordt geen data binnengehaald, hiervandaan kan genavigeerd worden naar de verschillende activiteiten
#####Recept zoeken:
Hier wordt uit de database de lijsten met mensen en groepen binnen gehaald. Verder wordt er de userinput gebruikt voor de ingrediënten waar men op wil zoeken. De zoekfunctie wordt binnen deze activiteit uitgevoerd en de resultaten hiervan worden doorgegeven naar de "gevonden recepten" activiteit.
#####Receptinvoer:
Hier wordt de input van de user opgevangen en doorgegeven naar de database. Het gaat hier om het ingeven van nieuwe recepten met een naam, ingrediëntenlijst en beschrijving. Deze data wordt via een databasehelper in de database verwerkt in de Recipes_table.
#####Persooninvoer:
Hier wordt ook de input van de user opgevangen om mensen aan de database toe te voegen. Hiervoor wordt de volledige naam van de persoon die toegevoegd moet worden gevraagd en de mogelijke allergieën die de persoon heeft. Deze persoon wordt dan aan de Person_table toegevoegd.
#####Groepenmanagement:
Hier wordt de input van de gebruiker gevraagd als er een nieuwe groep aangemaakt moet worden. Er wordt ook de lijst met alle groepen en mensen uit de database gehaald en getoond aan de gebruiker. De gebruiker kan dan alle groepen selecteren waar mensen aan moeten worden toegevoegd en alle mensen selecteren die moeten worden toegevoegd.
#####Gevonden recepten:
Hier wordt een lijst getoon met alle recepten die zijn gevonden die voldeden aan de eisen van de zoekfunctie. Als er op een receptnaam wordt geklikt wordt de informatie van het recept getoond, de naam, ingrediënten en bereidingswijze.

####API's and Frameworks:
- Standaard API van android appstudio en de gegeven frameworks van dit programma. 
- SQlite Database

####Uitdagingen:
Aan het begin van het project had ik een ander idee voor de database en de opbouw hiervan. Zo had ik het idee om een individuele tabel te maken van ingrediënten, en deze te koppelen aan recepten en mensen. Zo zouden ingrediënten de ID's krijgen van recepten waar ze in voor kwamen en zouden mensen de ID's van ingrediënten meekrijgen waar ze allergisch voor zijn. Ik heb er uiteindelijk voor gekozen om dit niet op deze manier te doen, omdat het erg omslachtig zou worden om de juiste gegevens door te geven. Zo wordt een ID pas aangemaakt als iets wordt toegevoegd aan de database. Hierdoor zou ik of een lijst moeten hebben met alle mogelijke ingrediënten die in een gerecht zouden kunnen zitten of zou ik telkens als een nieuw ingrediënt wordt toegevoegd een work-around moeten maken om het ID van het zojuist toegevoegde ingrediënt toe te kunnen voegen aan een persoon. Ook kan ik het ID van een recept pas ophalen als het is toegevoegd, dus zou ik pas na een recept te hebben toegevoegd kunnen aangeven welke ingrediënten voorkomen in het recept. Ik heb er nu dus voor gekozen om deze ingrediënten op te slaan onder de recepten en mensen zelf. Dit maakte het ook makkelijker om de zoekfunctie te implementeren, aangezien men nu kan zoeken op bijvoorbeeld "meel" als er als ingrediënt "500ml meel" in het recept staat. Dit zou veel moeilijker te implementeren zijn als ik was uitgegaan van een vaste lijst ingrediënten.
<br>
Er was ook de uitdaging om het makkelijk te maken voor de gebruiker om zowel mensen als groepen aan de zoekfunctie toe te voegen, zonder al te veel extra activiteiten aan de app toe te voegen. Het is namelijk makkelijker als je alles vanuit 1 scherm kunt doen. Hierom heb ik ervoor gekozen om een switch te maken die, als je hem gebruikt de listview vult met ofwel mensen of groepen. Boven de listview komt te staan wat je op dat moment zien, groepen of mensen, en door ze aan te vinken worden ze meegenomen in de zoekfunctie.

####Verbeterpunten en opmerkingen:
Op het moment werkt de app zoals ik het voor ogen had, je kunt namelijk als gebruiker recepten toevoegen met ingrediënten en een leuke bereidingswijze. Je kunt dit recept dan ook weer opzoeken volgens een goedwerkende zoekfunctie. Het toevoegen van mensen en groepen is ook volledig geimplementeerd en deze kunnen worden meegenomen in de zoekfunctie.
Wat ik nog zou willen verbeteren/toevoegen is de mogelijkheid voor aanpassingen. Het was niet meer mogelijk in de korte tijd om dit af te krijgen, maar op dit moment is het niet mogelijk om iets aan te passen als het eenmaal in de database staat. Dit geldt voor de recepten, mensen en groepen. Je kunt een recept niet aanpassen nadat je het hebt ingevoerd. Zo kun je ook niet aanpassen welke allergiën iemand heeft, dit zou onhandig kunnen zijn als iemand een allergie ontwikkelt en hij/zij al in de database staat. Ook kun je mensen niet uit groepen halen of groepen verwijderen. Als een groep niet meer nodig is wil je hem waarschijnijk verwijderen, zodat de rest overzichtelijker is en je wilt ook vergissingen kunnen rechtzetten als je per ongeluk iemand aan de verkeerde groep hebt toegevoegd. 
<br>
Het is uiteindelijk niet gelukt om een goede lijst van recepten te vinden die makkelijk om te zetten waren naar mijn database. Het zou handig zijn geweest om alvast een lijst met recepten mee te kunnen geven, zodat de gebruiker alvast iets te zoeken had. Het zou mogelijk zijn om al deze recepten handmatig in te voeren, maar dit zou heel veel tijd kosten, vandaar dat ik niet voor deze optie heb gekozen.

