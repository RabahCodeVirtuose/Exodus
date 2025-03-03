package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Renard extends AnimalPredateur {
    public Renard(Position position, int id,Personnage ennemi) {
        super(position, id,ennemi );
    }

    @Override
    protected void togglePossibilteDeLeTuer() {

    }

    @Override
    protected boolean peutEtreTue() {
        return true;
    }

    @Override
    public boolean proieEstProtegeParCachette(Position atester, Carte carte) {
        return false;
    }



    @Override
    public String getSymbole() {
        return " R ";
    }

    @Override
    public String getCouleur() {
        return TerminalColors.ANSI_CYAN + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
    }

    @Override
    public void seDeplacer(Carte carte) {


        // Chercher une proie dans un rayon de 1
        List<Animal> proies = proieScoop(carte, 1, Ecureuil.class);
        if (!proies.isEmpty()) {
            // Attaquer la première proie détectée
            //attaquer(proies.get(0), carte);
            boolean ret = false; // Initialiser ret à false
            int index = 0;

            // Parcourir les proies dans la liste tant que l'attaque ne réussit pas
            while (!ret && index < proies.size()) {
                Animal cible = proies.get(index);

                // Vérifier si la cible est déjà revendiquée
                if (!peutAttaquer(cible)) {
                    System.out.println(getClass().getSimpleName() + " N° " + getId() + " ignore " + cible.getClass().getSimpleName() + " car elle est déjà revendiquée.");
                    index++;
                    continue; // Passer à la prochaine proie
                }

                // Notifier les autres prédateurs de la cible choisie
                notifierObservateurs(cible);

                // Tenter d'attaquer la proie
                ret = attaquer(cible, carte);

                index++; // Passer à la prochaine proie si l'attaque échoue
            }

            if (!ret) {
                System.out.println("Aucune proie n'a pu être attaquée avec succès.");
            }
            return;
        }

        // Déplacement aléatoire dans une case vide
        Random random = new Random();

        // Mélanger les directions pour un ordre aléatoire
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directions, random);

        // Parcourir toutes les directions
        for (Direction direction : directions) {
            Position candidatePosition = position.positionTowards(direction, 1);
            Case caseCible = carte.getCase(candidatePosition.getX(), candidatePosition.getY());

            // Vérifier si la position est valide, vide et différente du maître de la première proie
            if (caseCible != null && caseCible.getEtat() instanceof EmptyTile &&
                    !positionEgaleAuMaitre(candidatePosition)&&!ilYaUnAnimalDansCettePosition(candidatePosition)) {
                System.out.println("Position valide trouvé.");
                System.out.println("Renard se déplace de " + position + " à " + candidatePosition);
                setPosition(candidatePosition);
                return; // Terminer le déplacement après avoir trouvé une position valide
            }else{


                // Si aucune position valide n'est trouvée
                System.out.println("Renard ne peut pas se déplacer, aucune case vide disponible.");
            }
        }


    }


    @Override
    public boolean attaquer(Animal cible, Carte carte) {
        if (!peutAttaquer(cible)) {
            System.out.println("Hibou N° " + getId() + " ignore l'écureuil car il a été revendiqué.");
            return false;
        }
        // Vérifier si la cible est dans un état de cache ou perche
        if (cible.getEtatCourant() instanceof EtatAnimalCache || cible.getEtatCourant() instanceof EtatAnimalPerche || cible.estDansPoche) {
            System.out.println("Renard ne peut pas attaquer un écureuil caché ou perché !");
            return false; // L'attaque est annulée
        }

        System.out.println("Renard attaque un écureuil !");


            // l'écureuil est tué
            System.out.println("Renard a tué l'écureuil !");
           // carte.getCase(cible.getPosition().getX(), cible.getPosition().getY()).setEtat(new EmptyTile());

         this.setPosition(cible.getPosition());
         cible.setPosition(new Position(-1,-1));
            cible.toggleEsttue(); // Marque l'écureuil comme tué
            return true;

    }
}
