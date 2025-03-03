package model;

public class EtatAnimalDansPoche implements EtatAnimal {
    private Personnage joueur;
    private Animal animal;
    private int toursRestants;

    public EtatAnimalDansPoche(Personnage joueur, Animal animal) {
        this.joueur = joueur;
        this.animal = animal;
        this.toursRestants = 3; // L'animal reste dans la poche pendant 3 tours
    }

    @Override
    public void seDeplacer(Carte carte) {
        System.out.println(animal.getClass().getSimpleName() + " est dans chez le joueur. Tours restants : " + toursRestants);

        // Réduire le compteur
        if (toursRestants > 0) {
            animal.setPosition(new Position(-1, -1));
            toursRestants--;
        } else {
            // Une fois les tours écoulés, sortir l'animal de la poche
            joueur.retirerAnimalPoche(animal); // Méthode à ajouter dans `Personnage`

            // Positionner l'animal à côté du joueur
            Position nouvellePosition = joueur.meilleurPositionpouranimal(joueur.getPosition());
            if (nouvellePosition != null) {
                animal.setPosition(nouvellePosition);
                System.out.println(animal.getClass().getSimpleName() + " est sorti de chez le joueur et a été posé à côté du joueur.");
            } else {
                System.out.println("Pas de place pour poser " + animal.getClass().getSimpleName() + " à côté du joueur.");
            }

            // Revenir à l'état ami
            animal.resetEstDansPoche();
            animal.setEtatCourant(new EtatAnimalAmiDuPersonnage(animal));
        }
    }
}
