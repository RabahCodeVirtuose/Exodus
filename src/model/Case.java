package model;

public class Case {
    private TileState etat;
    private boolean isJeteParjouer;

    public Case(TileState etatInitial) {
        this.etat = etatInitial;
        this.isJeteParjouer = false;
    }

    public boolean estVide() {
        return etat.isMovable();
    }

    public void setEtat(TileState nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public void setJeteparJouer(){
       this.isJeteParjouer = true;
    }

    public boolean isJeteParjouer() {
        return this.isJeteParjouer;
    }

    public TileState getEtat() {
        return etat;
    }
}
