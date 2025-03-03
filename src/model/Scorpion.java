package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Scorpion extends AnimalPredateur {
    private int immobilisationTours = 0; // Nombre de tours d'immobilisation sous un rocher
    private int cooldownAttaque = 0;// Nombre de tours où le scorpion ne peut pas attaquer après une attaque

    public Scorpion(Position position, int id, Personnage ennemi) {
        super(position, id, ennemi);
        this.etatPredateurEnCours=new EtatScorpionPeutPiquer(this);
    }
    @Override
    public boolean estImmobiliser() {
        if(immobilisationTours == 0)
            return false;

        return true;
    }

    @Override
    protected void togglePossibilteDeLeTuer() {
        // Peut être tué selon la logique du jeu
    }

    @Override
    protected boolean peutEtreTue() {
        return true; // Peut être tué à tout moment
    }

    @Override
    public boolean proieEstProtegeParCachette(Position atester, Carte carte) {
        return false; // Les cachettes ne protègent pas contre les scorpions
    }

    @Override
    public String getSymbole() {
        return " N ";
    }

    @Override
    public String getCouleur() {
        if (cooldownAttaque > 0) {
            return TerminalColors.ANSI_BLUE + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
        }
        if (immobilisationTours > 0) {
            return TerminalColors.ANSI_PURPLE + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
        }
        return TerminalColors.ANSI_RED + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
    }

    @Override
    public void seDeplacer(Carte carte) {
        // Gestion de l'immobilisation sous un rocher
        if (immobilisationTours > 0) {
            System.out.println("Scorpion N° " + getId() + " est caché sous un rocher pour " + immobilisationTours + " tours.");
            immobilisationTours--;
            List<Animal> proies = proieScoop(carte, 0, Singe.class);
            if (!proies.isEmpty()) {
                // Attaquer la première proie détectée
                attaquer(proies.get(0), carte);
                return;
            }
            return;
        }

        if (cooldownAttaque > 0) {
            System.out.println("Scorpion N° " + getId() + " est en cooldown d'attaque pour " + cooldownAttaque + " tours.");
            cooldownAttaque--;

        }

        // Chercher une proie dans un rayon de 1
        List<Animal> proies = proieScoop(carte, 1, Singe.class);
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

        // Déplacement aléatoire d'une case
        Random random = new Random();
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directions, random);

        for (Direction direction : directions) {
            Position nouvellePosition = position.positionTowards(direction, 1);
            Case caseCible = carte.getCase(nouvellePosition.getX(), nouvellePosition.getY());

            if (caseCible != null && !(caseCible.getEtat() instanceof CachettePrincipale)) {
                // Si la case est un petit rocher, immobilisation pour 5 tours
                if (caseCible.getEtat() instanceof PetitRocherTile) {
                    System.out.println("Scorpion N° " + getId() + " s'est caché sous un rocher.");
                    immobilisationTours = 5;
                    setPosition(nouvellePosition);
                    return;
                }

                // Déplacement normal
                if (caseCible.getEtat() instanceof EmptyTile &&
                        !positionEgaleAuMaitre(nouvellePosition)&&!ilYaUnAnimalDansCettePosition(nouvellePosition)) {
                    System.out.println("Scorpion N° " + getId() + " se déplace de " + position + " à " + nouvellePosition);
                    setPosition(nouvellePosition);
                    return;
                }
            }
        }

        System.out.println("Scorpion N° " + getId() + " ne trouve aucune case valide pour se déplacer.");
    }

    @Override
    public boolean attaquer(Animal cible, Carte carte) {
        if (!(cible instanceof Singe)) {
            System.out.println("Scorpion N° " + getId() + " ne peut attaquer que des singes.");
            return false;
        }

        Singe singe = (Singe) cible;

        // Récupérer la case actuelle du scorpion et celle du singe
        Case caseScorpion = carte.getCase(this.position.getX(), this.position.getY());
        Case caseSinge = carte.getCase(singe.getPosition().getX(), singe.getPosition().getY());
        //System.out.println("caseScor : "+ caseScorpion.getEtat());
        //System.out.println("caseSinge : "+ caseSinge.getEtat());
        // Si le scorpion est sous un rocher, il n'attaque pas un singe adjacent
        if (caseScorpion.getEtat() instanceof PetitRocherTile) {
            if (singe.getPosition().equals(this.position)) {
                // Si le singe entre dans le rocher, il est piqué
                System.out.println("Scorpion N° " + getId() + " a piqué le singe dans le rocher ! Le singe est mort.");
                this.setAfterKillPosition(singe.getPosition());
                singe.setPosition(new Position(-1, -1)); // Retirer le singe
                singe.toggleEsttue(); // Marque le singe comme tué
                cooldownAttaque = 2; // Cooldown après attaque
                immobilisationTours = 0;
                return true;
            } else {
                // Sinon, laisser le singe tranquille (il est adjacent mais le scorpion attend)
                System.out.println("Scorpion N° " + getId() + " est caché sous un rocher et n'attaque pas un singe adjacent.");
                return false;
            }
        }

        // Si le scorpion n'est pas sous un rocher, il peut attaquer un singe adjacent ou sur un autre rocher
        if (caseSinge != null && (singe.getPosition().estAdjacente(this.position) || caseSinge.getEtat() instanceof PetitRocherTile)) {
            System.out.println("Scorpion N° " + getId() + " a piqué le singe ! Le singe est mort.");
            this.setPosition(singe.getPosition());
            singe.setPosition(new Position(-1, -1)); // Retirer le singe
            singe.toggleEsttue(); // Marque le singe comme tué
            cooldownAttaque = 2; // Cooldown après attaque
            return true;
        }

        System.out.println("Scorpion N° " + getId() + " n'a pas pu atteindre le singe.");
        return false;
    }

}
