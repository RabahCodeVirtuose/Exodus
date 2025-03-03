package model;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimalPredateur implements Sujet, Observateur {

    protected Position position;
    protected EtatPredateur etatPredateurEnCours;
    private int id;
    protected List<Animal> mes_proies;
    protected Position afterKillPosition=null;
    protected Personnage monEnnemi;
    private List<Observateur> observateurs = new ArrayList<>(); // Liste des observateurs
    protected List<Animal> proiesIgnorees = new ArrayList<>(); // Proies à ignorer
    protected boolean estMort=false;
    protected boolean peutEtreattaque=false;
   // protected Position positionDeNotreEnnemiSave=null;
    public AnimalPredateur(Position position, int id,Personnage monEnnemi) {
        this.position = position;
        this.id = id;
        this.monEnnemi = monEnnemi;

    }

    public Position getPosition() {
        return position;
    }
    public void setEtatPredateurEnCours(EtatPredateur etatPredateurEnCours) {
        this.etatPredateurEnCours = etatPredateurEnCours;
    }
    public void setMonEnnemi(Personnage monEnnemi) {
        this.monEnnemi = monEnnemi;
    }
    protected abstract void togglePossibilteDeLeTuer();
    protected abstract boolean peutEtreTue();
    @Override
    public void ajouterObservateur(Observateur observateur) {
        System.out.println(this.getClass().getSimpleName() + " N°" + id + " a ajouté " + ((AnimalPredateur) observateur).getClass().getSimpleName() + " N°" + ((AnimalPredateur) observateur).getId() + " comme observateur.");

        observateurs.add(observateur);
    }

    @Override
    public void retirerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateurs(Animal proie) {
        for (Observateur observateur : observateurs) {
            observateur.mettreAJour(proie);
        }
    }

    @Override
    public void mettreAJour(Animal proie) {
        proiesIgnorees.add(proie);
        System.out.println(getClass().getSimpleName() + " N° " + id + " a été notifié de ne pas attaquer " + proie.getClass().getSimpleName());
    }


    public boolean peutAttaquer(Animal proie) {
        return !proiesIgnorees.contains(proie); // Vérifie si la proie est dans la liste ignorée
    }

    public void letuer(){
        System.out.println("fonction letuer dans AnimalPredateur est appelle !");
        estMort=true;
    }

    public boolean estMort() {
        return estMort;
    }






    public void setAfterKillPosition(Position afterKillPosition) {
        this.afterKillPosition = afterKillPosition;
    }
    public void toogleAfterKillPosition() {
        afterKillPosition = null;


    }
    public abstract boolean proieEstProtegeParCachette(Position atester, Carte carte);

    public abstract  String getSymbole();
    public abstract String getCouleur();
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setMes_proies(List<Animal> mes_proies) {
        this.mes_proies = mes_proies;
    }

    // Méthode abstraite pour le déplacement
    public abstract void seDeplacer(Carte carte);

    protected boolean positionEgaleAuMaitre(Position position) {


        return position.equals(monEnnemi.getPosition()); // Aucun maître ou liste vide
    }

    protected boolean ilYaUnAnimalDansCettePosition(Position position) {
        //System.out.println("Passage dans la nouvelle fonction ajouter pour fixer les erruers N°2 ");
        for (Animal animaliterator : monEnnemi.getAnimaux()) {
            if(animaliterator.getPosition().getX()==position.getX() && animaliterator.getPosition().getY()==position.getY()) {
                System.out.println("il y a un autre animal "+this.getClass().getSimpleName()+" N° : "+this.getId()+" ne pas pas se deplacer dans cette case");
                return true;
            }
        }
        for (AnimalPredateur animaliterator : monEnnemi.getAnimauxDanger()) {
            if(animaliterator.getPosition().getX()==position.getX() && animaliterator.getPosition().getY()==position.getY()) {
                System.out.println("il y a un autre animal dangeureux "+this.getClass().getSimpleName()+" N° : "+this.getId()+" ne pas pas se deplacer dans cette case");
                return true;
            }
        }
        return false;
    }


    // Méthode pour détecter les proies dans un rayon donné
    public List<Animal> proieScoop(Carte carte, int rayon, Class<? extends Animal> typeProie) {
        List<Animal> proies = new ArrayList<>();

        for (int dx = -rayon; dx <= rayon; dx++) {
            for (int dy = -rayon; dy <= rayon; dy++) {
                // Calculer la position cible
                int x = position.getX() + dx;
                int y = position.getY() + dy;

                // Vérifier les limites de la carte
                Case caseCible = carte.getCase(x, y);
                if (caseCible != null) {
                    // Parcourir tous les animaux pour voir s'ils correspondent à la classe demandée

                    for (Animal animal : mes_proies) {
                        if (animal.getPosition() != null &&
                                animal.getPosition().equals(new Position(x, y)) &&
                                typeProie.isInstance(animal)) {
                            proies.add(animal); // Ajouter l'animal à la liste des proies
                        }
                    }
                }
            }
        }

        return proies;
    }









    // Méthode abstraite pour l'attaque
    public abstract boolean attaquer(Animal cible, Carte carte);

    public boolean estImmobiliser() { return false; }

}
