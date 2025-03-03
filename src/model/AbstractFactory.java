package model;

public interface AbstractFactory {
    Animal createAnimal(Position position, Personnage joueur, int id);
    TileState createCachette1();
    TileState dechiffrerEtAppeller(char type);
    TileState createCachette2();
    TileState createNourriturePrincipale();
    TileState createNourritureSecondaire();
    TileState createNourriturePourrie();
    AnimalPredateur createAnimalPredateur1(Position position, int id,Personnage ennemi);
    AnimalPredateur createAnimalPredateur2(Position position, int id, Personnage ennemi);
    char getAnimalChar();
    char getPredateurChar1();
    char getPredateurChar2();
    String getTileDecoration(TileState tile);
    String getCarteChemin();
}
