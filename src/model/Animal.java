package model;
import java.util.*;

public abstract class Animal {
    //protected TerminalColors colors;
    protected EtatAnimal etatCourant;
    protected EtatAnimal affame;
    protected EtatAnimal rassasie;
    protected EtatAnimal amiDuPersonnage;
    protected EtatAnimal junkie;
    protected EtatAnimal cache;
    protected EtatAnimal perched;
    private Personnage monMaitre;
    protected Position position;
    private int id;
    private static String symbole;
    protected int nbTours;
    protected int nbToursPourEtatBizzare;
    private DeplacementStrategy deplacementStrategy;
    private List<AnimalPredateur> mes_ennemis;
    protected boolean estDansPoche;
    protected int nbToursPourPoserAnimal;
    protected boolean estTue;
    protected boolean je_ne_peux_plus_changer_de_position=false;
    protected int compteur_perte_de_danger=3;
    public Animal(Position position,Personnage monMaitre,int nbToursInitial,int id) {
        this.position = position;
        this.nbTours = nbToursInitial;

        // États communs
        this.affame = new EtatAnimalAffame(this);
        this.rassasie = new EtatAnimalRassasie(this);
        this.amiDuPersonnage = new EtatAnimalAmiDuPersonnage(this);
        this.junkie = new EtatAnimalJunkie(this);
        this.cache = new EtatAnimalCache(this);
        this.perched = new EtatAnimalPerche(this);
        this.estDansPoche = false;
        this.etatCourant = affame;
        this.monMaitre = monMaitre;
        this.estTue = false;
        this.id = id;


    }

    public boolean estTue(){
        return estTue;
    }
    public void toggleEsttue(){
        this.estTue = !this.estTue;
    }
    abstract void setNbToursPourPoserAnimal();
    abstract int getNbToursPourPoserAnimal();
    abstract void decrementeNbToursPourPoserAnimal();

    public abstract int  getCompteur_perte_de_danger();
    public abstract void decerement_compteur_perte_de_danger();
    public abstract void reset_compteur_perte_de_danger();



    public abstract void setEstDansPoche();
    public abstract void resetEstDansPoche();
    public abstract boolean estDansPoche();
    public void setMes_ennemis(List<AnimalPredateur> mes_ennemis) {
        this.mes_ennemis = mes_ennemis;
    }

    public boolean ilyaanimalDansCettePosition(Position tester) {
        System.out.println("Passage dans la nouvelle fonction ajouter pour fixer les erruers ");
        for (Animal animaliterator : monMaitre.getAnimaux()) {
            if(animaliterator.getPosition().getX()==tester.getX() && animaliterator.getPosition().getY()==tester.getY()) {
                System.out.println("il y a un autre animal "+this.getClass().getSimpleName()+" N° : "+this.getId()+" ne pas pas se deplacer dans cette case");
               return true;
            }
        }
        for (AnimalPredateur animaliterator : monMaitre.getAnimauxDanger()) {
            if(animaliterator.getPosition().getX()==tester.getX() && animaliterator.getPosition().getY()==tester.getY()) {
                System.out.println("il y a un autre animal dangereux "+this.getClass().getSimpleName()+" N° : "+this.getId()+" ne pas pas se deplacer dans cette case");
                return true;
            }
        }
        return false;
    }

    public abstract void ressetNbToursPourEtatBizzare();
    public abstract int getNbToursPourEtatBizzare();
    public abstract void decrementeNbToursPourEtatBizzare();

    public abstract String getSymbole();
    public abstract String getCouleur();
    public abstract String seCacheOu1();
    public abstract String seCacheOu2();
    public abstract TileState getTileCachettePrincipale();
    public abstract TileState getTileCachetteSecondaire();

    public EtatAnimal getJunkie() {
        return junkie;
    }

    public EtatAnimal getPerched() {
        return perched;
    }

    public EtatAnimal getCache() {
        return cache;
    }
    public void setMonMaitre(Personnage monMaitre) {
        this.monMaitre = monMaitre;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public void setDeplacementStrategy(DeplacementStrategy strategy) {
        this.deplacementStrategy = strategy;
    }
    public EtatAnimal getAffame() {
        return affame;
    }

    public EtatAnimal getRassasie() {
        return rassasie;
    }
    public int getId() {
        return id;
    }
    public DeplacementStrategy getDeplacementStrategy() {
        return deplacementStrategy;
    }

    public EtatAnimal getAmiDuPersonnage() {
        return amiDuPersonnage;
    }

    // public Etatanimal getPerched() { return perched; }

    // public Etatanimal getCache() { return cache; }

    public EtatAnimal getEtatCourant() {
        return etatCourant;
    }

    public void setEtatCourant(EtatAnimal etatCourant) {
        this.etatCourant = etatCourant;
    }

    public void decrementeTours() {
        nbTours--;
    }

    public void chargerTours() {
        nbTours = getInitialTours();
    }

    public int getNbTours() { return nbTours; }

    public void setNbTours(int nbTours) { this.nbTours = nbTours; }




    public void seDeplacer(Carte carte) {
            etatCourant.seDeplacer(carte);


    }

    public Position trouverCaseAdaptee(Carte carte, Class<?> typeTile) {
        for (Direction direction : Direction.values()) {
            Position cible = position.positionTowards(direction, 1);
            Case caseCible = carte.getCase(cible.getX(), cible.getY());
            if (caseCible != null && typeTile.isInstance(caseCible.getEtat())) {
                //System.out.println("HERE?: "+cible);
                return cible;
            }
        }
        return null;
    }

    public Position trouverCaseVideProche(Carte carte) {
        Queue<Position> positionsAExplorer = new LinkedList<>();
        Set<Position> positionsVisitees = new HashSet<>();
        Position positionMaitre = monMaitre.getPosition(); // Position du joueur

        positionsAExplorer.add(position);
        positionsVisitees.add(position);

        while (!positionsAExplorer.isEmpty()) {
            Position current = positionsAExplorer.poll();

            List<Direction> directions = Arrays.asList(Direction.values());
            Collections.shuffle(directions);

            for (Direction direction : directions) {
                Position cible = current.positionTowards(direction, 1);

                if (positionsVisitees.contains(cible)) {
                    continue;
                }

                positionsVisitees.add(cible);

                Case caseCible = carte.getCase(cible.getX(), cible.getY());

                if (caseCible != null && caseCible.getEtat().isMovable() &&
                        !cible.equals(positionMaitre) && !ilyaanimalDansCettePosition(cible)) {
                    return cible;
                }
                
                if (caseCible != null && caseCible.getEtat().isMovable()) {
                    positionsAExplorer.add(cible);
                }
            }
        }

        return null;
    }

    // Méthode abstraite pour définir le nombre de tours initial
    protected abstract int getInitialTours();



    public Personnage getMonMaitre() {
        return monMaitre;
    }

    public boolean estVoisinDuPersonnage() {
        if(Math.abs(monMaitre.getPosition().getX()-position.getX())<=2 || Math.abs(monMaitre.getPosition().getY()-position.getY())<=2){
            return true;
        }
        return false;
}
    public List<Position> scoopDanger(Carte carte, int rayon) {
        List<Position> dangers = new ArrayList<>();
        System.out.println("scoop exec !");

        // Parcourir toutes les positions dans le rayon autour de l'animal
        for (int dx = -rayon; dx <= rayon; dx++) {
            for (int dy = -rayon; dy <= rayon; dy++) {
                // Calculer la position cible
                int x = position.getX() + dx;
                int y = position.getY() + dy;

                // Vérifier si la position est dans les limites de la carte
                if (carte.getCase(x, y) != null) {
                    Position positionCible = new Position(x, y);

                    // Vérifier si un prédateur se trouve dans mes_ennemis à cette position
                    for (AnimalPredateur ennemi : mes_ennemis) {
                        if (ennemi.getPosition().equals(positionCible) && !ennemi.estImmobiliser()) {
                            dangers.add(positionCible); // Ajouter la position du danger
                            break; // Sortir de la boucle une fois l'ennemi trouvé
                        }
                    }
                }
            }
        }
        System.out.println("scoop isEmpty ?? :  !"+dangers.isEmpty());
        return dangers; // Retourne les positions des dangers détectés
    }






    public Position fuirDanger(Carte carte, Position positionDanger) {
        // Calculer la direction opposée au danger (pas de diagonale)
        int deltaX = position.getX() - positionDanger.getX();
        int deltaY = position.getY() - positionDanger.getY();

        // Déterminer la direction dans laquelle se déplacer (éviter les diagonales)
        int newX = position.getX();
        int newY = position.getY();

        // Se déplacer horizontalement ou verticalement pour fuir le danger
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Se déplacer horizontalement (EST ou OUEST)
            newX = position.getX() + (deltaX != 0 ? (deltaX / Math.abs(deltaX)) : 0);
        } else if (Math.abs(deltaY) > Math.abs(deltaX)) {
            // Se déplacer verticalement (NORD ou SUD)
            newY = position.getY() + (deltaY != 0 ? (deltaY / Math.abs(deltaY)) : 0);
        }

        // Vérifier si la case est dans les limites de la carte et est accessible
        Position nouvellePosition = new Position(newX, newY);
        Case caseCible = carte.getCase(newX, newY);

        if (caseCible != null && caseCible.getEtat().isMovable() && !ilyaanimalDansCettePosition(nouvellePosition)) {
            return nouvellePosition; // Retourner une case valide
        }

        // Si la case opposée est invalide, essayer de trouver une case vide proche (haut, bas, gauche, droite)
        for (Direction direction : Direction.values()) {
            Position alternative = position.positionTowards(direction, 1);
            Case caseAlternative = carte.getCase(alternative.getX(), alternative.getY());
            if (caseAlternative != null && caseAlternative.getEtat().isMovable()) {
                return alternative;
            }
        }

        // Si aucune position valide n'est trouvée, retourner null
        return null;
    }

    public List<AnimalPredateur> getMes_ennemis() {
        return mes_ennemis;
    }

    public void clearEnnemis() {
        mes_ennemis.clear();
    }
}

