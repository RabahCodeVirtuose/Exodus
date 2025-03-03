package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Personnage {
    private Scanner sc = new Scanner(System.in);
    private Carte cartePourAiderMesAnimaux;
    private Position position;
    private List<Objet> inventaire;
    private List<Animal> animaux;
    private AbstractFactory themeFactoryDansPersonnage;
    private Animal[] epaule = new Animal[1];
    private List<Animal> pocheAnimal;
    private List<AnimalPredateur> les_ennemis_de_mon_ami;


    public Personnage(Position position,List<Animal> animaux, Carte cartePourAiderMesAnimaux) {
        this.position = position;
        this.inventaire = new ArrayList<>();
        this.les_ennemis_de_mon_ami = new ArrayList<>();
        this.pocheAnimal = new ArrayList<>();

        this.animaux = animaux;
        this.cartePourAiderMesAnimaux = cartePourAiderMesAnimaux;

    }

    public void setLes_ennemis_de_mon_ami(List<AnimalPredateur> animauxPred){
        this.les_ennemis_de_mon_ami=animauxPred;
    }


    public  List<Animal> getAnimaux(){
        return this.animaux;
    }
    public  List<AnimalPredateur> getAnimauxDanger(){
        return this.les_ennemis_de_mon_ami;
    }

    public boolean positionOccupeeParEnnemi(Position position) {
        for (AnimalPredateur ennemi : les_ennemis_de_mon_ami) {
            if (ennemi.getPosition() != null && ennemi.getPosition().equals(position)) {
                return true; // La position est occupée par un prédateur
            }
        }
        return false; // Aucun prédateur à cette position
    }


    public void setEpaule(Animal epaule) {
        this.epaule[0] = epaule;
    }

    public void ajouterAnimalPoche(Animal animal) {
        this.pocheAnimal.add(animal);
        System.out.println(animal.getClass().getSimpleName() + " a été ajouté à la poche.");
    }

    // Méthode pour retirer un animal de la poche
    public void retirerAnimalPoche(Animal animal) {
        this.pocheAnimal.remove(animal);
        System.out.println(animal.getClass().getSimpleName() + " a été retiré de la poche.");
    }


    public void setAbstractFactory(AbstractFactory abstractFactory) {
        this.themeFactoryDansPersonnage = abstractFactory;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getCouleur() {
        return TerminalColors.ANSI_DARK_PURPLE + TerminalColors.ANSI_WHITEY_BACKGROUND+ TerminalColors.ANSI_BOLD;
    }

    public Position getPosition() {
        return position;
    }

    public List<Objet> getInventaire() {
        return inventaire;
    }

    public void ajouterObjet(Objet objet) {
        inventaire.add(objet);
        System.out.println(TerminalColors.LIGHT_GREEN + "(+) Vous avez ramassé : " + objet.getType() + TerminalColors.ANSI_RESET);
    }

    public void rendreObjet(Objet objet) {
        inventaire.add(objet);
    }

    public void afficherInventaire() {
        if (inventaire.isEmpty()) {
            System.out.println(TerminalColors.ANSI_YELLOW + "/!\\ Votre inventaire est vide."+ TerminalColors.ANSI_RESET);
        } else {
            System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "Inventaire :" + TerminalColors.ANSI_RESET);
            for (Objet objet : inventaire) {
                System.out.println(TerminalColors.ANSI_CYAN + "- " + objet.getType() + TerminalColors.ANSI_RESET);
            }
        }
    }

    public void seDeplacer(Direction direction, Carte carte) {
        Position nouvellePosition = position.positionTowards(direction, 1);
        Case nouvelleCase = carte.getCase(nouvellePosition.getX(), nouvellePosition.getY());
        boolean isAnimal=false;
        for (Animal animaliterator : animaux) {
            if(animaliterator.getPosition().getX()==nouvellePosition.getX() && animaliterator.getPosition().getY()==nouvellePosition.getY()) {
                System.out.println("il y a un animal tu ne peux pas te déplacer ");
                isAnimal=true;
                break;
            }
        }

        if(positionOccupeeParEnnemi(nouvellePosition)){
            System.out.println("il y a un animal Predateur tu ne peux pas te déplacer ");

            isAnimal=true;

        }

        if (nouvelleCase != null && nouvelleCase.getEtat().isMovable() && !isAnimal) {
            // Vérifie si le joueur interagit avec un objet (exemple : ramasser)
            if (nouvelleCase.getEtat() instanceof NourriturePrincipale) {
                System.out.println( TerminalColors.ANSI_YELLOW + "(!) Vous avez trouvé un(e) " + TerminalColors.ANSI_BOLD + nouvelleCase.getEtat().getDescription()+ TerminalColors.ANSI_RESET + TerminalColors.ANSI_YELLOW + ". Souhaitez-vous le/la prendre ? (oui/non)" + TerminalColors.ANSI_RESET);
                String str = sc.nextLine();
                if (str.equals("oui")){
                    ajouterObjet(new Objet(nouvelleCase.getEtat()));
                    //System.out.println("Vous avez ramassé un "+ nouvelleCase.getEtat().getClass().getSimpleName()+" !");
                    nouvelleCase.setEtat(new EmptyTile()); // La nourriture est ramassée
                }
            }
            else if (nouvelleCase.getEtat() instanceof NourritureSecondaire) {
                System.out.println( TerminalColors.ANSI_YELLOW + "(!) Vous avez trouvé un(e) " + TerminalColors.ANSI_BOLD + nouvelleCase.getEtat().getDescription()+ TerminalColors.ANSI_RESET + TerminalColors.ANSI_YELLOW + ". Souhaitez-vous le/la prendre ? (oui/non)" + TerminalColors.ANSI_RESET);
                String str = sc.nextLine();
                if (str.equals("oui")){
                    ajouterObjet(new Objet(nouvelleCase.getEtat()));
                    //System.out.println("Vous avez ramassé un "+nouvelleCase.getEtat().getClass().getSimpleName()+"  !");
                    nouvelleCase.setEtat(new EmptyTile()); // La nourriture est ramassée
                }

            }

            // Déplace le joueur
            if (!( (carte.getCase(position.getX(), position.getY()).getEtat() instanceof NourritureSecondaire)||( carte.getCase(position.getX(), position.getY()).getEtat() instanceof NourriturePrincipale))) {
                carte.getCase(position.getX(), position.getY()).setEtat(new EmptyTile()); // L'ancienne position devient vide
                position = nouvellePosition;
                System.out.println("Vous vous êtes déplacé vers " + direction);
            } else{
                if(carte.getCase(position.getX(), position.getY()).getEtat() instanceof NourritureSecondaire){
                    carte.getCase(position.getX(), position.getY()).setEtat(themeFactoryDansPersonnage.createNourritureSecondaire());
                    // System.out.println("création nourriture secondaire ");
                } else if (carte.getCase(position.getX(), position.getY()).getEtat() instanceof NourriturePrincipale) {

                    carte.getCase(position.getX(), position.getY()).setEtat(themeFactoryDansPersonnage.createNourriturePrincipale());
                    //   System.out.println("création nourriture principale ");

                }

                position = nouvellePosition;
                System.out.println("Vous vous êtes déplacé vers " + direction);
            }

        } else {
            System.out.println("Déplacement impossible !");
        }




    }
    public Position meilleurPositionpouranimal(Position positiondemonanimal) {
        if((cartePourAiderMesAnimaux.getCase(position.getX()+1, position.getY()).getEtat() instanceof EmptyTile)){
            return new Position(position.getX()+1, position.getY());
        }else if((cartePourAiderMesAnimaux.getCase(position.getX()-1, position.getY()).getEtat() instanceof EmptyTile)){
            return new Position(position.getX()-1, position.getY());

        }else if((cartePourAiderMesAnimaux.getCase(position.getX(), position.getY()+1).getEtat() instanceof EmptyTile)){
            return new Position(position.getX(), position.getY()+1);

        }else if((cartePourAiderMesAnimaux.getCase(position.getX(), position.getY()-1).getEtat() instanceof EmptyTile)){
            return new Position(position.getX(), position.getY()-1);

        }


        Position retour = null;

        return retour;
    }


    public Objet retirerObjet() {
        if (inventaire.isEmpty()) {
            return null;
        }
        return inventaire.remove(0); // Retire le premier objet
    }
}