package model;

import java.util.Random;

public class DeplacementAleatoire implements DeplacementStrategy {
    @Override
    public Position calculerDeplacement(Animal animal, Carte carte) {
        return animal.trouverCaseVideProche(carte);
    }
}
