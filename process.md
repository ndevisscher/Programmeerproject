##processbook

#day 2:
Vandaag vooral bezig geweest met framework te maken en te kijken naar de verschillende manieren om de nodige data in de app op
te slaan en weer te geven.

#day 3:
Begonnen met het maken van de database met hulp van deze sites:
http://www.techotopia.com/index.php/An_Android_Studio_SQLite_Database_Tutorial <br>
http://instinctcoder.com/android-studio-sqlite-database-example/ <br>
Vond een probleem met de emulator cache, waardoor ik dacht dat de database niet werkte, is nu opgelost.

#day 4:
- Een verandering gemaakt in de layout en indeling van het hoofdscherm, receptenvinder en receptentoevoeger. 
- Keuze gemaakt om recepten te kunnen toevoegen aan de app, hiervoor een apparte activity gemaakt. 
- Verder een begin gemaakt aan de zoekfunctie, je kunt nu ingredienten aan een lijst toevoegen die gezocht moeten worden in de recepten.
- Recepten kunnen nu op naam gezocht worden uit de lijst.
- Een extra database aangemaakt voor mensen met hun allergien.
- Mensen kunnen worden toegevoegd aan een database met de allergien die ze hebben.

#day 5:
- Gewerkt aan het optimaliseren van de databse en de bijbehorende tabellen, alles staat nu in 1 database.
- Design document gemaakt
- kleine aanpassingen gemaakt de layout van de app activities, enkele schermen hebben nu nog testknoppen die later verwijdert moeten worden.

#day 6:
- Zoekfunctie volledig geimplementeerd.
- verandering gemaakt in de opslag van ingredienten, ze worden nu als lange string opgeslagen en alfabetisch gesorteerd, hierdoor kan ik makkelijker de database doorzoeken.
- Zoekresultaten van de zoekfunctie worden nu op de goede activity getoond en kunnen worden aangeklikt om meer informatie te laten zien.
- Comments toegevoegd aan de meeste java documente om de code overzichtelijker te maken.
- Toevoegen van bereidingswijze is mogelijk als een recept wordt toegevoegd.
- Navigeren tussen activities aangepast door de back knop nu in de toolbar te zetten.

#day 7:
- Zoekfunctie werkt nu volledig, er kan gezocht worden op ingredienten en de allergien van mensen worden meegenomen
- extra methodes voor de database geschreven, deze zijn nodig voor het ophalen van de allergien van een persoon en deze toe te voegen aan een lijst zodat deze kunnen worden gebruikt voor de zoekfunctie

#day 8:
- code een beetje opgeschoond, testvariabelen en methodes verwijdert om de app in zijn geheel te kunnen testen, alles werkt goed zoals het er nu uit ziet.
- begonnen met het toevoegen van alle strings aan de strings.xml
