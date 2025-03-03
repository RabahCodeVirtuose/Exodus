package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeplacementJunkie implements DeplacementStrategy {
    @Override
    public Position calculerDeplacement(Animal animal, Carte carte) {
        Position positionMaitre = animal.getMonMaitre().getPosition(); // Position du joueur

        // Créer une liste de directions mélangées
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directions, new Random()); // Mélanger les directions pour un déplacement aléatoire

        // Parcourir toutes les directions pour trouver une position valide
        for (Direction direction : directions) {
            Position nouvellePosition = animal.getPosition().positionTowards(direction, 2);

            // Vérifier si la position est valide, déplaçable et différente de celle du joueur
            Case caseCible = carte.getCase(nouvellePosition.getX(), nouvellePosition.getY());
            if (caseCible != null && caseCible.getEtat().isMovable() && !nouvellePosition.equals(positionMaitre) && !animal.ilyaanimalDansCettePosition(nouvellePosition)) {
                return nouvellePosition; // Retourner la première position valide trouvée
            }
        }

        // Si aucune position valide n'est trouvée
        return null;
    }
}
