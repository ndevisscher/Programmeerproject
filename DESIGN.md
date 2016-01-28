#Design document voor Receptenhulp

##MVP:
In de app moet minimaal het volgende mogelijk zijn:
- Het toevoegen van recepten met de bijbehorende ingrediënten
- Het toevoegen van mensen met de allergiën die ze hebben
- Groepen aanmaken en mensen aan de groepen toevoegen
- Het kunnen zoeken naar recepten aan de hand van ingrediënten die je wilt gebruiken
- Mensen en groepen kunnen toevoegen aan het zoeken naar recepten en de resultaten aanpassen aan de hand van hun allergiën


##Classes and Methods:
Er worden verschillende classes gebruikt in de app die helpen bij het maken en onderhouden van de database en de tabellen die gebruikt worden. Verder gebruik ik de normale classes voor de verschilllende activities.
#####Belangrijke Classes en Methods:
- DatabaseHelper
- Activity classes
<br>
<br>Belangrijke methods staan in de databasehelper, deze zijn vooral gericht op het toevoegen van info aan de database en het ophalen van items uit de database.

##API's and Frameworks:
Ik maak gebruik van de standaard API van android appstudio en de gegeven frameworks van dit programma. Ik heb nog niet veel gekeken naar verder extra functionaliteiten, dit ga ik vooral doen als ik verder ben met de minimale vereisten van de app.

##Datasources and Databases:
Ik maak gebruik van een aantal tabellen in een database die met elkaar in verbinding staan. Zo sla ik de ingredienten van recepten en allergien van mensen op in apparte tabellen, om ervoor te zorgen dat de informatie makkelijker op te halen is met SQlite. Ik ben momenteel nog aan het kijken hoe ik het verder kan optimaliseren zodat ik geen data buiten de database hoef te veranderen. <br>

##Sources:
Voor het maken van de databse en de opbouw hiervan heb ik gebruik gemaakt van de volgende websites:
<br>
http://www.techotopia.com/index.php/An_Android_Studio_SQLite_Database_Tutorial
http://instinctcoder.com/android-studio-sqlite-database-example/

####Databasestructuur:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/einproject%20mprog%20database.png "1")

##Advanced sketches:

#####Home scherm:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/HomeFinal.png "1")
#####Recept zoeken:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/ZoekReceptFinal.png "1")
#####Receptinvoer:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/receptInvoerFinal.png "1")
#####Persooninvoer:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/PersoonInvoerFinal.png "1")
#####Groepenmanagement:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/groepManagerFinal.png "1")
#####Gevonden recepten:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/gevondenReceptenFinal.png "1")

