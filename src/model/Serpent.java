package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Serpent extends AnimalPredateur {
    private int digestionTours = 0; // Nombre de tours pendant lesquels le serpent digère

    public Serpent(Position position, int id, Personnage ennemi) {
        super(position, id, ennemi);
        this.etatPredateurEnCours=new EtatSerpentPeutAttaquer(this);
    }

    @Override
    protected void togglePossibilteDeLeTuer() {
        // Peut être tué selon la logique du jeu
    }

    @Override
    protected boolean peutEtreTue() {
        if(digestionTours == 0)
            return false;

        return true;
    }

    @Override
    public boolean proieEstProtegeParCachette(Position atester, Carte carte) {
        for (Direction direction : Direction.values()) {
            Position adjacente = atester.positionTowards(direction, 1);
            Case caseAdjacente = carte.getCase(adjacente.getX(), adjacente.getY());

            if (caseAdjacente != null && caseAdjacente.getEtat() instanceof CachettePrincipale) {
                return true; // La proie est protégée par un cocotier
            }
        }
        return false; // Pas de protection trouvée
    }

    @Override
    public String getSymbole() {
        return " T ";
    }

    @Override
    public String getCouleur() {
        if (digestionTours > 0) {
            return TerminalColors.ANSI_GREEN + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
        }
        return TerminalColors.ANSI_RED + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
    }

    @Override
    public void seDeplacer(Carte carte) {

        // Gestion de l'état digestion
        if (digestionTours > 0) {
            System.out.println("Serpent N° " + getId() + " digère et reste immobile pour " + digestionTours + " tours.");
            digestionTours--;
            return;
        }
        // Chercher une proie dans un rayon de 1
        List<Animal> proies = proieScoop(carte, 2, Singe.class);
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



        // Déplacement aléatoire de 2 cases
        Random random = new Random();
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directions, random);

        for (Direction direction : directions) {
            Position nouvellePosition = position.positionTowards(direction, 2);
            Case caseCible = carte.getCase(nouvellePosition.getX(), nouvellePosition.getY());

            if (caseCible != null && caseCible.getEtat() instanceof EmptyTile &&
                    !positionEgaleAuMaitre(nouvellePosition)&&!ilYaUnAnimalDansCettePosition(nouvellePosition)) {
                System.out.println("Serpent N° " + getId() + " se déplace de " + position + " à " + nouvellePosition);
                setPosition(nouvellePosition);
                return;
            }
        }

        System.out.println("Serpent N° " + getId() + " ne trouve aucune case pour se déplacer.");
    }

    @Override
    public boolean attaquer(Animal cible, Carte carte) {
        if (!(cible instanceof Singe)) {
            System.out.println("Serpent N° " + getId() + " ne peut attaquer que des singes.");
            return false;
        }

        Singe singe = (Singe) cible;

        // Vérifier si la case de la cible est protégée par un cocotier
        if (proieEstProtegeParCachette(singe.getPosition(), carte)) {
            System.out.println("Serpent N° " + getId() + " ne peut pas tuer le singe car il est protégé par un cocotier.");
            // Le singe fuit dans le cocotier
           /* Position cocotier = singe.trouverCaseAdaptee(carte, CachettePrincipale.class);
            if (cocotier != null) {
                singe.setPosition(cocotier);
                singe.setEtatCourant(new EtatAnimalCache(singe));
                System.out.println("Singe s'est réfugié dans un cocotier à " + cocotier);
            }*/
            return false;
        }

        // Si la proie n’est pas protégée, le serpent la mange
        System.out.println("Serpent N° " + getId() + " a mangé le singe !");
        digestionTours = 3; // Immobilisation pendant la digestion
        this.setPosition(cible.getPosition());

    
        System.out.println("position actuelle du serpent ! "+this.getPosition());

        cible.setPosition(new Position(-1,-1));
        cible.toggleEsttue(); // Marque l'écureuil comme tué
        return true;
    }
}
