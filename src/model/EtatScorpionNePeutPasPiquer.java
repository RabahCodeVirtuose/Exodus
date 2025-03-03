package model;

public class EtatScorpionNePeutPasPiquer implements EtatPredateur{
    private AnimalPredateur animalPredateur;
    public EtatScorpionNePeutPasPiquer(AnimalPredateur animalPredateur) {
        this.animalPredateur = animalPredateur;
    }

    @Override
    public void attaquer(Animal cible, Carte carte) {

    }

    @Override
    public void seDeplacer(Carte carte) {

    }

    @Override
    public String getCouleur() {
        return "";
    }
}
