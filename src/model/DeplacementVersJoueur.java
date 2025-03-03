package model;

public class DeplacementVersJoueur implements DeplacementStrategy {
    @Override
    public Position calculerDeplacement(Animal animal, Carte carte) {
        return animal.getMonMaitre().meilleurPositionpouranimal(animal.getPosition());
    }
}
