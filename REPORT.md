#Eindverslag Programmeerproject 
###Niek de Visscher (10667474)

####Projectbeschrijving:
De app is een hulpmiddel om recepten van gerechten te vinden. Deze recepten kun je zelf invoeren in de app, doormiddel van het ingeven van de benodigde ingrediënten en de bereidingswijze. Hierna kun je de gerechten opzoeken aan de hand van de ingrediënten die je wilt gebruiken. Verder is het mogelijk om mensen aan de app toe te voegen en allergieën op te geven voor deze mensen. Je kunt deze mensen toevoegen aan een groep, zodat je slechts een groep hoeft te selecteren in plaats van elke persoon individueel. Op het moment dat je een recept gaat zoeken kan men aangeven dat een groep/persoon of personen mee-eten en zullen alle allergieën worden meegenomen in het zoeken naar recepten. Zo zul je dus geen recepten krijgen die noten bevatten als iemand mee eet die een notenallergie heeft.

####Technisch design:
De app maakt gebruik van een SQlite database die alle recepten, toegevoegde mensen en groepen bijhoudt. De database maakt gebruik van 3 tabellen, 1 voor de recepten, 1 voor de mensen en 1 voor de groepen. De tabellen van mensen en groepen zijn aan elkaar gelinkt doormiddel van het persoonsID. Zo staat er voor een groep een lijst met alle ID's van mensen die in de groep zitten, zodat er duidelijk kan worden gezocht naar alle bijbehorende allergieën van de mensen.
