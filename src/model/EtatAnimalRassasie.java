package model;
import java.util.List;

public class EtatAnimalRassasie implements EtatAnimal {
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatAnimalRassasie(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementAleatoire(); // Stratégie spécifique
    }

    @Override
    public void seDeplacer(Carte carte) {
        System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " est rassasié.");

        // Vérifie les dangers autour avec scoopDanger
        List<Position> dangers = animal.scoopDanger(carte, 3); // Rayon de 3 cases

        if (!dangers.isEmpty()) {
            System.out.println("Danger détecté dans les positions suivantes : " + dangers);
            System.out.println(animal.getClass().getSimpleName() + " reste sur place pour éviter le danger.");
        } else {
            // Pas de danger, trouver une case vide la plus proche
            Position nouvellePosition = deplacementStrategy.calculerDeplacement(animal,carte);
            if (nouvellePosition != null) {
                animal.setPosition(nouvellePosition);
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " se déplace vers la case vide " + nouvellePosition);
            } else {
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " ne trouve aucune case vide.");
            }
        }

        // Décrémente les tours restants
        animal.decrementeTours();
        if (animal.getNbTours() <= 0) {
            animal.setEtatCourant(new EtatAnimalAffame(animal));
            animal.chargerTours();
        }
    }
}
