package model;
import controleur.Controleur;

import java.util.List;

public class EtatAnimalAmiDuPersonnage implements EtatAnimal {
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatAnimalAmiDuPersonnage(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementVersJoueur(); // Stratégie spécifique
    }

    @Override
    public void seDeplacer(Carte carte) {
        System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " suit le joueur car il est ami.");

        // 1. Détection des dangers via scoopDanger
        List<Position> dangers = animal.scoopDanger(carte, 4);
        System.out.println("Danger??: is empty ??: "+dangers.isEmpty());
        // Si un danger est détecté
        if (!dangers.isEmpty()) {
            System.out.println("Danger détecté! " + animal.getClass().getSimpleName() + " tente de se protéger.");

            // Vérifier si le joueur est adjacent
            if (animal.estVoisinDuPersonnage()) {
                // Ajouter l'animal à la poche du joueur
                Personnage joueur = animal.getMonMaitre();
                joueur.ajouterAnimalPoche(animal);
                if(animal instanceof Singe){
                    System.out.println("Singe N°"+animal.getId()+" lance un cri car il y a un danger ! Au secours !!!!!!!!!!!");
                }
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " court vers le joueur pour éviter le danger.");

                // Désactiver la position de l'animal temporairement
               // animal.setPosition(null);

                // Passer à l'état `EtatAnimalDansPoche`
                animal.setEstDansPoche();
                animal.setEtatCourant(new EtatAnimalDansPoche(joueur, animal));
                return;
            } else {
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " ne peut pas atteindre le joueur et continue à se déplacer.");
            }
        }

        // 2. Sinon, continuer à suivre le joueur
        if (animal.getNbTours() <= 0) {
            System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " n'est plus ami avec le joueur.");
            animal.setEtatCourant(new EtatAnimalAffame(animal));
        } else {
            Position nouvellePosition = deplacementStrategy.calculerDeplacement(animal, carte);
            if (nouvellePosition != null) {
                animal.setPosition(nouvellePosition);
            }
            animal.decrementeTours();
        }
    }
}
