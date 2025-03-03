package model;

public class EtatSerpentEntrainDeDigerer implements EtatPredateur{
    private AnimalPredateur animalPredateur;
    public EtatSerpentEntrainDeDigerer(AnimalPredateur animalPredateur) {
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
