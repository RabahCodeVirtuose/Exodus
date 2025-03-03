package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Hibou extends AnimalPredateur {
    private EtatPredateur etatCourant;

    public Hibou(Position position, int id, Personnage ennemi) {
        super(position, id, ennemi);
        this.etatCourant = new EtatHibouPeutAttaquer(this); // État initial : Peut attaquer
    }

    @Override
    protected void togglePossibilteDeLeTuer() {
        this.peutEtreattaque = !this.peutEtreattaque;
    }

    @Override
    protected boolean peutEtreTue() {
        return peutEtreattaque;
    }

    @Override
    public boolean proieEstProtegeParCachette(Position atester, Carte carte) {
        for (Direction direction : Direction.values()) {
            Position adjacente = atester.positionTowards(direction, 1);
            Case caseAdjacente = carte.getCase(adjacente.getX(), adjacente.getY());

            if (caseAdjacente != null && caseAdjacente.getEtat() instanceof CachetteSecondaire) {
                return true; // La proie est protégée par un buisson
            }
        }
        return false;
    }

    public boolean verifierBuissonAutourDeLecureuil(Position position, Carte carte) {
        for (Direction direction : Direction.values()) {
            Position adjacente = position.positionTowards(direction, 1);
            Case caseAdjacente = carte.getCase(adjacente.getX(), adjacente.getY());

            if (caseAdjacente != null && caseAdjacente.getEtat() instanceof BuissonTile) {
                return true; // Il y a un buisson autour de l'écureuil
            }
        }
        return false; // Aucune case adjacente avec un buisson
    }

    @Override
    public String getSymbole() {
        return " H ";
    }

    @Override
    public String getCouleur() {
        return etatCourant.getCouleur();
    }

    @Override
    public void seDeplacer(Carte carte) {
        etatCourant.seDeplacer(carte);
    }

    @Override
    public boolean attaquer(Animal cible, Carte carte) {
        etatCourant.attaquer(cible, carte);
        return true;
    }

    public void setEtat(EtatPredateur nouvelEtat) {
        this.etatCourant = nouvelEtat;
    }



}
