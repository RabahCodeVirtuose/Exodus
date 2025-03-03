package model;

import vue.Ihm;

import java.util.ArrayList;
import java.util.List;

public class FightCommand implements Command {
    private Personnage joueur;
    private Carte carte;
    private Ihm vue;
    private List<Animal> animaux;
    private List<AnimalPredateur> predateurs;

    public FightCommand(Personnage joueur, Carte carte, Ihm vue, List<Animal> animaux, List<AnimalPredateur> predateurs) {
        this.joueur = joueur;
        this.carte = carte;
        this.vue = vue;
        this.animaux = animaux;
        this.predateurs = predateurs;
    }

    @Override
    public void execute() {
        List<Animal> animauxAdjacents = new ArrayList<>();
        List<AnimalPredateur> predateursAdjacents = new ArrayList<>();

        // Récupérer les animaux et prédateurs dans les cases adjacentes
        for (Direction direction : Direction.values()) {
            Position cible = joueur.getPosition().positionTowards(direction, 1);
            Case caseCible = carte.getCase(cible.getX(), cible.getY());

            if (caseCible != null) {
                for (Animal animal : animaux) {
                    if (animal.getPosition().equals(cible)) {
                        animauxAdjacents.add(animal);
                    }
                }
                for (AnimalPredateur predateur : predateurs) {
                    if (predateur.getPosition().equals(cible)) {
                        predateursAdjacents.add(predateur);
                    }
                }
            }
        }

        // Si aucun animal ou prédateur n'est adjacent
        if (animauxAdjacents.isEmpty() && predateursAdjacents.isEmpty()) {
            vue.afficherMessage("Aucun animal ou prédateur à proximité à frapper.");
            return;
        }

        // Afficher les options disponibles
        vue.afficherMessage("Animaux et prédateurs adjacents :");
        int index = 1;

        for (Animal animal : animauxAdjacents) {
            vue.afficherMessage(index + ". Animal : " + animal.getClass().getSimpleName() + " à la position " + animal.getPosition());
            index++;
        }
        for (AnimalPredateur predateur : predateursAdjacents) {
            vue.afficherMessage(index + ". Prédateur : " + predateur.getClass().getSimpleName() + " à la position " + predateur.getPosition());
            index++;
        }

        // Demander à l'utilisateur de choisir un animal ou un prédateur
        int choix = vue.demanderEntier("Choisissez qui frapper (1-" + (index - 1) + ")", 1, index - 1);

        // Identifier la cible choisie
        if (choix <= animauxAdjacents.size()) {
            Animal cible = animauxAdjacents.get(choix - 1);

            // Traiter un animal non prédateur
            if (cible.getEtatCourant() instanceof EtatAnimalAmiDuPersonnage) {
                cible.setEtatCourant(new EtatAnimalAffame(cible)); // L'animal redevient affamé
                vue.afficherMessage("Vous avez donné un coup à l'animal ! Il n'est plus votre ami.");
                return;
            } else {
                vue.afficherMessage("Vous avez donné un coup à un " + cible.getClass().getSimpleName() + ".");
                return;
            }
        }
            AnimalPredateur cible = predateursAdjacents.get(choix - 1 - animauxAdjacents.size());
            System.out.println("animal predateur choisi de type "+cible.getClass().getSimpleName());
        System.out.println("Vérification si on peut le tuer : "+cible.peutEtreTue());

        if(cible.peutEtreTue()){

                // Traiter un prédateur
                vue.afficherMessage("Vous avez frappé un prédateur : " + cible.getClass().getSimpleName() + " !");

                 cible.letuer();
                for (Animal proie : cible.mes_proies)
                    proie.clearEnnemis();


            return;
        }
    }
}
