package controleur;

import model.*;
import vue.Ihm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controleur {
    private Carte carte;
    private Personnage joueur;
    private Ihm vue;
    private CommandManager commandManager;
    private List<Animal> animaux;
    private List<AnimalPredateur> animauxpredateurs1;
    private List<AnimalPredateur> animauxpredateurs2;
    private List<AnimalPredateur> mergedList;

    private String theme;
    private AbstractFactory themefactory;

    public Controleur(Ihm vue) {
        this.carte = new Carte();
        this.vue = vue;
        this.commandManager = new CommandManager();
        this.animaux = new ArrayList<>();
        this.animauxpredateurs1 = new ArrayList<>();
        this.animauxpredateurs2 = new ArrayList<>();
        this.mergedList = new ArrayList<>();

    }


    public void demarrerJeu() {
        vue.afficherMessage("Démarrage du jeu...");
        vue.afficherMessage("Choisissez un thème ! (forest/jungle)");
        theme = vue.lireCommande();
        themefactory = switch (theme.toLowerCase()) {
            case "forest" -> new ForestFactory();
            case "jungle" -> new JungleFactory();
            default -> throw new IllegalArgumentException("Thème inconnu");
        };
        vue.setThemeFactoryDansIhm(themefactory);
        carte.setAbstractFactory(themefactory);
        chargerCarteDepuisFichier();
        // Initialisation des animaux
        initialiserAnimaux();
        joueur.setAbstractFactory(themefactory);

        // Afficher la carte initiale
        vue.setTheme(theme);
        vue.afficherCarte(carte, joueur, animaux,animauxpredateurs1,animauxpredateurs2);
        vue.afficherCommandes();

        // Boucle principale du jeu
        boolean enCours = true;
        while (enCours) {
            String commande = vue.lireCommande();
            enCours = traiterCommande(commande);
        }

        vue.afficherMessage("Fin du jeu. À bientôt !");
    }

    private void chargerCarteDepuisFichier() {

       String chemin= themefactory.getCarteChemin() ;

        try {
            carte.chargerCarteDepuisFichier(chemin);
            vue.afficherMessage("Carte chargée avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors du chargement de la carte : " + e.getMessage());
            creerNouvelleCarte(); // Repli sur une nouvelle carte
        }
    }

    private void creerNouvelleCarte() {
        vue.afficherMessage("Création d'une nouvelle carte...");
        // Ajouter une logique pour générer une nouvelle carte si nécessaire
       // carte.genererNouvelleCarte();
    }

    private void initialiserAnimaux() {
        for (Position pos : carte.getAnimals()) {
            Animal animal = themefactory.createAnimal( pos, null, animaux.size() + 1);
            animaux.add(animal);
        }
        for (Position pos : carte.getAnimalsPredateur1()) {
            AnimalPredateur animalpred1 = themefactory.createAnimalPredateur1(pos,animauxpredateurs1.size()+1,null);
            animauxpredateurs1.add(animalpred1);
        }
        for (Position pos : carte.getAnimalsPredateur2()) {
            AnimalPredateur animalpred2 = themefactory.createAnimalPredateur2(pos,animauxpredateurs2.size()+1,null);
            animauxpredateurs2.add(animalpred2);
        }

        joueur = new Personnage(carte.getPersonnagePosition(), animaux, carte);
        for (Animal animal : animaux) {
            animal.setMonMaitre(joueur);
        }

        for (AnimalPredateur animal : animauxpredateurs1) {
            animal.setMonEnnemi(joueur);
        }
        for (AnimalPredateur animal : animauxpredateurs2) {
            animal.setMonEnnemi(joueur);
        }




        // Ajouter les éléments des deux listes
        mergedList.addAll(animauxpredateurs1);
        mergedList.addAll(animauxpredateurs2);
        for (Animal animal : animaux) {
            animal.setMes_ennemis(mergedList);
        }

        joueur.setLes_ennemis_de_mon_ami(mergedList);

        for (AnimalPredateur animal : animauxpredateurs1) {
            animal.setMes_proies(animaux);
        }

        for (AnimalPredateur animal : animauxpredateurs2) {
            animal.setMes_proies(animaux);
        }

        for (AnimalPredateur predateur : mergedList) {
            for (AnimalPredateur autrePredateur : mergedList) {
                // Ne pas s'ajouter soi-même
                if (predateur != autrePredateur) {
                    predateur.ajouterObservateur(autrePredateur);
                }
            }
        }



    }

    private boolean traiterCommande(String commande) {

        Iterator<Animal> iterator = animaux.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.estTue()) {
                iterator.remove(); // Supprime l'animal de manière sécurisée
            }
        }



        Iterator<AnimalPredateur> iterator1 = animauxpredateurs1.iterator();
        while (iterator1.hasNext()) {
            AnimalPredateur animal = iterator1.next();
            if (animal.estMort()) {
                iterator1.remove();// Supprime l'animal de manière sécurisée
                System.out.println("un animal a été supprimé ! ");
            }
        }



        Iterator<AnimalPredateur> iterator2 = animauxpredateurs2.iterator();
        while (iterator2.hasNext()) {
            AnimalPredateur animal = iterator2.next();
            if (animal.estMort()) {
                iterator2.remove(); // Supprime l'animal de manière sécurisée
            }
        }
       // vue.afficherCarte(carte, joueur, animaux,animauxpredateurs1,animauxpredateurs2);
        vue.afficherCommandes(); // Rappel des commandes
        switch (commande.toLowerCase()) {
            case "nord", "sud", "est", "ouest" -> {
                Direction direction = Direction.valueOf(commande.toUpperCase());
                commandManager.executeCommand(new MoveCommand(joueur, direction, carte));


                for (Animal animal : animaux) {
                    animal.seDeplacer(carte);
                }
                for (AnimalPredateur animal : animauxpredateurs1) {
                    animal.seDeplacer(carte);
                }
                for (AnimalPredateur animal : animauxpredateurs2) {
                    animal.seDeplacer(carte);
                }

                vue.afficherCarte(carte, joueur, animaux,animauxpredateurs1,animauxpredateurs2);
            }
            case "explorer" -> {
                commandManager.executeCommand(new InteractCommand(carte, joueur, vue));
                vue.afficherCarte(carte, joueur, animaux,animauxpredateurs1,animauxpredateurs2);
            }
            case "inventaire" -> commandManager.executeCommand(new InventoryCommand(joueur));
            case "lancer" -> {
                vue.afficherMessage(TerminalColors.ANSI_YELLOW + "/!\\ Dans quelle direction voulez-vous lancer l'objet ? (nord/sud/est/ouest)" + TerminalColors.ANSI_RESET);
                try {
                    Direction direction = Direction.valueOf(vue.lireCommande().toUpperCase());
                    commandManager.executeCommand(new ThrowCommand(joueur, direction, carte));
                } catch (IllegalArgumentException e) {
                    vue.afficherMessage("Direction invalide.");
                }
                vue.afficherCarte(carte, joueur, animaux,animauxpredateurs1,animauxpredateurs2);
            }
            case "battre" -> {
                commandManager.executeCommand(new FightCommand(joueur, carte, vue, animaux,mergedList));
                vue.afficherCarte(carte, joueur, animaux,animauxpredateurs1,animauxpredateurs2);
            }

            case "quitter" -> {
                return false;
            }
            default -> vue.afficherMessage("Commande inconnue.");
        }
        return true;
    }
}
