package model;

public class DeplacementVersNourriture implements DeplacementStrategy {
    @Override
    public Position calculerDeplacement(Animal animal, Carte carte) {
        Position nourriture = animal.trouverCaseAdaptee(carte, NourriturePrincipale.class);
        if (nourriture == null) {
            nourriture = animal.trouverCaseAdaptee(carte, NourritureSecondaire.class);
        }
        if (nourriture == null) {
            nourriture = animal.trouverCaseAdaptee(carte, NourritureSecondaire.class);
        }
        // traiter le cas ou c'est une nourriture dangeurese
        if (nourriture == null) {
            nourriture = animal.trouverCaseAdaptee(carte, NourritureDangeureuse.class);
        }

        return nourriture;
    }
}
