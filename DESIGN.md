#Design document voor Receptenhulp

##Advanced sketches:

#####Home scherm:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/Home.png "1")
#####Recept zoeken:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/ZoekRecept.png "1")
#####Receptinvoer:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/receptInvoer.png "1")
#####Persooninvoer:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/PersoonInvoer.png "1")

##Classes and Methods:
Er worden verschillende classes gebruikt in de app die helpen bij het maken en onderhouden van de database en de tabellen die gebruikt worden. Verder gebruik ik de normale classes voor de verschilllende activities.
* Belangrijke Classes:
- DatabaseHelper
- Activity classes
Belangrijke methods staan in de databasehelper, deze zijn vooral gericht op het toevoegen van info aan de database en het ophalen van items uit de database.

##API's and Frameworks:

##Datasources and Databases:
Ik maak gebruik van een aantal tabellen in een database die met elkaar in verbinding staan. Zo sla ik de ingredienten van recepten en allergien van mensen op in apparte tabellen, om ervoor te zorgen dat de informatie makkelijker op te halen is met SQlite. Ik ben momenteel nog aan het kijken hoe ik het verder kan optimaliseren zodat ik geen data buiten de database hoef te veranderen. <br>
####Databasestructuur:
![alt text](https://github.com/ndevisscher/Programmeerproject/blob/master/doc/DatabaseStructuur.png "1")

