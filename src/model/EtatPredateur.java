package model;

public interface EtatPredateur {
    void attaquer(Animal cible, Carte carte);
    void seDeplacer(Carte carte);
    String getCouleur();
}
