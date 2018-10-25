# L3 Projet informatique

## Run parameter usage

```
Usage:
./game.jar <run-count> <player-count>
```

## How to work

> Note that you firstly need to build the code,
> by running `mvn install`.

- Running the tests: `mvn test`
- Running a single game: `mvn exec:java@partie`
- Running a stat game (500 games): `mvn exec:java@stat`

> You can both install and execute the program by joining both tasks,
> e.g. `mvn install exec:java@partie`.

## Groupe

- William
- Maxime
- Tao
- Ivan
- Sow

## Milestones

1.  Rendu 1 : 25/10 :
    - Partie à 2 bots
    - Inventaire
    - Lancer un dé non modifiable
    - Condition de victoire
    - Execution Maven du programme

2.  Rendu 2 : 08/11
    - Dé modifiable
    - Toutes les faces de dé
    - Achat face de dé
    - Choix achat de dé

3. Rendu 3 : 15/11 : 
    - Déplacement sur le plateau
    - Toutes les cartes normales sans action attachées au plateau
    - Achat de carte normales
    - Choix achat de carte normale

4. Rendu 4 : 29/11 :
    - Toutes les actions des cartes normales
    - Choix achat de carte amélioré
    
5. Rendu 5 : 06/12 :
    - Partie à 4 bots
    - Mode experts

6. Rendu 6 : 13/12 :
    - 2ème statégie
