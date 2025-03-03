package model;

import java.util.List;

public class EtatAnimalAffame implements EtatAnimal {
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatAnimalAffame(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementVersNourriture(); // Stratégie spécifique pour trouver de la nourriture.
    }

    @Override
    public void seDeplacer(Carte carte) {

        System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " est affamé et évalue son environnement.");

        // Récupérer la position du joueur (monMaitre)
        Position positionMaitre = animal.getMonMaitre().getPosition();

        // 1. Utiliser la stratégie pour trouver de la nourriture (priorité absolue)
        Position nourriture = deplacementStrategy.calculerDeplacement(animal, carte);

        if (nourriture != null && !nourriture.equals(positionMaitre)) { // Vérifier que ce n'est pas la case du joueur
            mangerNourriture(carte, nourriture);
            return;
        }

        // 2. Détection des dangers via scoopDanger
        List<Position> dangers = animal.scoopDanger(carte, 4);
        System.out.println(dangers.toString());
        if (!dangers.isEmpty()) {
            Position positionDanger = dangers.get(0); // Prendre le premier danger détecté

            // Réagir au danger en fonction des priorités (b, c, d)
            Position arbre = animal.trouverCaseAdaptee(carte, CachettePrincipale.class);
            if (arbre != null && !arbre.equals(positionMaitre)) { // Vérifier que ce n'est pas la case du joueur
                animal.setPosition(arbre);
                animal.setEtatCourant(new EtatAnimalPerche(animal));
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " s'est réfugié dans un arbre.");
                return;
            }

            Position buisson = animal.trouverCaseAdaptee(carte, CachetteSecondaire.class);
            if (buisson != null && !buisson.equals(positionMaitre)) { // Vérifier que ce n'est pas la case du joueur
                animal.setPosition(buisson);
                animal.setEtatCourant(new EtatAnimalCache(animal));
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " s'est réfugié dans un buisson.");
                return;
            }

              Position fuite = animal.fuirDanger(carte, positionDanger);


            if (fuite != null && !fuite.equals(positionMaitre) && !animal.ilyaanimalDansCettePosition(fuite)) { // Vérifier que ce n'est pas la case du joueur
                animal.setPosition(fuite);
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " fuit le danger.");
                return;
            }
        }

        // 3. Se déplacer vers une case vide proche si aucun danger
        //Position caseVide = animal.trouverCaseAdaptee(carte, EmptyTile.class);
        Position caseVide = animal.trouverCaseVideProche(carte);
        if (caseVide != null && !caseVide.equals(positionMaitre) && !animal.ilyaanimalDansCettePosition(caseVide)) { // Vérifier que ce n'est pas la case du joueur
            animal.setPosition(caseVide);
            System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " se déplace vers une case vide.");
            return;
        }

        // Si aucun déplacement possible
        System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " est immobile.");
    }


    private void mangerNourriture(Carte carte, Position nourriture) {
        Case caseCible = carte.getCase(nourriture.getX(), nourriture.getY());
        if (caseCible.getEtat().isDangerous()) {

            if(caseCible.getEtat() instanceof ChampignonVeneuxTile){
                animal.ressetNbToursPourEtatBizzare();
                animal.setEtatCourant(new EtatAnimalJunkie(animal));
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " a mangé un champignon dangereux et devient junkie.");
            }else if(caseCible.getEtat() instanceof ChampignonHallucinogeneTile){
                animal.reset_compteur_perte_de_danger();
                animal.setEtatCourant(new EtatPerteDeDanger(animal));
                System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " a mangé un champignon hallucinogene et ne croit plus au danger ! hhhh.");
            }


        } else if (caseCible.isJeteParjouer() && animal.estVoisinDuPersonnage()) {
            animal.setEtatCourant(new EtatAnimalAmiDuPersonnage(animal));
            System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " est maintenant votre ami(e).");
        } else {
            animal.setEtatCourant(new EtatAnimalRassasie(animal));
            System.out.println(animal.getClass().getSimpleName() + " N° " + animal.getId() + " a mangé et est maintenant rassasié.");
        }
        caseCible.setEtat(new EmptyTile());
        animal.setPosition(nourriture);
        //animal.decrementeTours();
    }
}
