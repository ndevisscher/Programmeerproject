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

#day 9:
Vanwege ziekte en falend internet vrij weinig voor het project gedaan, ik heb een klein beetje nagedacht over het implementeren van groepen in de app en hoe ik het uiterlijk er betere uit kan laten zien.

#day 10:
- Groepfunctie geimplementeerd, je kunt nu groepen aanmaken en mensen aan groepen toevoegen. er zit nog een kleine fout in, waarbij de database nog niet doorheeft dat iemand aan een groep is toegevoegd, waardoor je hem meerdere keren kunt toevoegen. Als je naar een andere activity gaat heeft hij wel door dat hij al in de groep zit en kun je hem niet meer toevoegen.
- Begonnen met het implementeren van de groep zoekfunctie, je moet een groep kunnen selecteren en dan moeten de mensen die in de groep zitten worden toegevoegd aan de zoekopdracht.
- Aanpassingen gemaakt aan een aantal invoerplekken, zodat je geen lege inputs meer hebt in de database en er beter wordt omgegaan met invoeren. Zo krijg je altijd een bericht als je een ongeldige invoer geeft.

#day 11:
- Notificaties toegevoegd in de app, dit is om gebruikers te laten weten wat ze hebben toegevoegd aan de database en voor feedback.

#day 12:
- Checks toegevoegd aan verschillende functies die data in de database moeten zetten, hierdoor krijg ik geen duplicaten meer van mensen of groepen.
- Zoekfunctie compleet gemaakt voor groepen, je kunt nu groepen selecteren die meegenomen moeten worden in de functie, de mensen worden uit de groep gehaald en de allergien van deze mensen worden meegenomen in het zoeken.

#day 13:
- Gezocht naar een goede dataset die ik kan gebruiken om de database al te vullen met recepten, dit zou ervoor moeten zorgen dat de app al bruikbaar is zonder zelf iets toe te voegen.

#day 14:
- Kleine aanpassingen gemaakt aan de notificaties, het is nu duidelijker te lezen wat er in een recept zit.
- Als je een ingredient aan de lijst hebt toegevoegd voor een recept kun je deze nu weer verwijderen, handig als je een fout had gemaakt.
- Ingredienten worden nu met een "," ertussen opgeslagen om het makkelijker te maken de verschillende ingredienten te splitsen.
- Model classes geimplementeerd voor Person,Group en Recipe. De variables die nodig zijn worden nu opgeroepen in databasehelper.

#Last days:
- Optimalizatie door testcode weg te halen.
- Verbeteren van de grammatica op sommige plekken.
- Verduidelijken van variabelen door nieuwe naamgeving.
