package model;

import java.util.List;

public class EtatPerteDeDanger implements EtatAnimal{
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatPerteDeDanger(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementAleatoire(); // Stratégie spécifique
    }

    @Override
    public void seDeplacer(Carte carte) {
        System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " a perdu la notion du danger ! pendant: "+animal.getCompteur_perte_de_danger()+" tours");

            // ne prend pas en charge le  danger, trouver une case vide la plus proche
            Position nouvellePosition = deplacementStrategy.calculerDeplacement(animal,carte);
            if (nouvellePosition != null) {
                animal.setPosition(nouvellePosition);
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " se déplace vers la case vide " + nouvellePosition);
            } else {
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " ne trouve aucune case vide.");
            }


        // Décrémente les tours restants
        animal.decerement_compteur_perte_de_danger();
        if (animal.getCompteur_perte_de_danger() <= 0) {
            animal.setEtatCourant(new EtatAnimalAffame(animal));
            animal.reset_compteur_perte_de_danger();
        }

    }
}
