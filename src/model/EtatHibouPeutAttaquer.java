package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EtatHibouPeutAttaquer implements EtatPredateur{
    private Hibou hibou;

    public EtatHibouPeutAttaquer(Hibou hibou) {
        this.hibou = hibou;
    }

    @Override
    public void attaquer(Animal cible, Carte carte) {
        if (!(cible instanceof Ecureuil)) {
            System.out.println("Hibou N° " + hibou.getId() + " ne peut attaquer que des écureuils.");
            return;
        }

        Ecureuil ecureuil = (Ecureuil) cible;

        // Vérifie si la case de l'écureuil est dégagée
        Case caseEcureuil = carte.getCase(ecureuil.getPosition().getX(), ecureuil.getPosition().getY());
        if (!(caseEcureuil.getEtat() instanceof EmptyTile)) {
            System.out.println("Hibou N° " + hibou.getId() + " ne peut attaquer que des écureuils sur des cases dégagées.");
            return;
        }

        if (!hibou.proieEstProtegeParCachette(cible.getPosition(), carte) && !hibou.verifierBuissonAutourDeLecureuil(ecureuil.getPosition(), carte)) {

            System.out.println("Hibou N° " + hibou.getId() + " a tué l'écureuil !");
            hibou.setPosition(ecureuil.getPosition());
            ecureuil.setPosition(new Position(-1,-1));
            ecureuil.toggleEsttue();
            hibou.setEtat(new EtatHibouParTerre(hibou)); // Transition vers l'état ParTerre
        } else {
            System.out.println("Hibou N° " + hibou.getId() + " ne peut pas attaquer car l'écureuil est protégé.");
        }
    }

    @Override
    public void seDeplacer(Carte carte) {

        // Chercher une proie dans un rayon de 3
        List<Animal> proies = hibou.proieScoop(carte, 3, Ecureuil.class);
        if (!proies.isEmpty()) {
            boolean ret = false; // Initialiser ret à false
            int index = 0;

            // Parcourir les proies dans la liste tant que l'attaque ne réussit pas
            while (!ret && index < proies.size()) {
                Animal cible = proies.get(index);

                // Vérifier si la cible est déjà revendiquée
                if (!hibou.peutAttaquer(cible)) {
                    System.out.println(getClass().getSimpleName() + " N° " + hibou.getId() + " ignore " + cible.getClass().getSimpleName() + " car elle est déjà revendiquée.");
                    index++;
                    continue; // Passer à la prochaine proie
                }

                // Notifier les autres prédateurs de la cible choisie
                hibou.notifierObservateurs(cible);

                // Tenter d'attaquer la proie
                ret = hibou.attaquer(cible, carte);

                index++; // Passer à la prochaine proie si l'attaque échoue
            }

            if (!ret) {
                System.out.println("Aucune proie n'a pu être attaquée avec succès.");
            }
            return;
        }


        // Déplacement normal : vole de 2 cases
        Random random = new Random();
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(directions, random);

        for (Direction direction : directions) {
            Position nouvellePosition = hibou.getPosition().positionTowards(direction, 2);
            Case caseCible = carte.getCase(nouvellePosition.getX(), nouvellePosition.getY());

            if (caseCible != null && caseCible.getEtat().isMovable()&&!hibou.ilYaUnAnimalDansCettePosition(nouvellePosition)) {
                System.out.println("Hibou N° " + hibou.getId() + " vole de " + hibou.getPosition() + " à " + nouvellePosition);
                hibou.setPosition(nouvellePosition);
                return;
            }
        }

        System.out.println("Hibou N° " + hibou.getId() + " ne trouve aucune case pour se déplacer.");
    }

    @Override
    public String getCouleur() {
        return TerminalColors.ANSI_LIGHT_RED + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
    }
}
