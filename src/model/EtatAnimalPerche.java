package model;

public class EtatAnimalPerche implements EtatAnimal {
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatAnimalPerche(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementVersNourriture(); // Réutilise la stratégie d'un animal affamé
    }

    @Override
    public void seDeplacer(Carte carte) {
        System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " est perché et cherche un  "+animal.seCacheOu1()+" ou de la nourriture.");

        // Vérifier si l'animal est toujours sur un arbre
        Case currentCase = carte.getCase(animal.getPosition().getX(), animal.getPosition().getY());
        if ((currentCase != null && currentCase.getEtat() instanceof CachettePrincipale)) {
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " est perché sur un  "+animal.seCacheOu1()+".");
            // Changer la couleur ou appliquer d'autres effets visuels si nécessaire
        }

        // Recherche de nourriture dans une case adjacente
        Position nourriture = animal.trouverCaseAdaptee(carte, NourriturePrincipale.class);
        if (nourriture == null) {
            nourriture = animal.trouverCaseAdaptee(carte, NourritureSecondaire.class);
        }


        if (nourriture != null) {
            Case caseCible = carte.getCase(nourriture.getX(), nourriture.getY());
            if (caseCible.isJeteParjouer() && animal.estVoisinDuPersonnage()) {
                System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " a trouvé de la nourriture jetée par le personnage et devient son ami.");
                animal.setEtatCourant(new EtatAnimalAmiDuPersonnage(animal));
            } else {
                System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " a trouvé de la nourriture et devient rassasié.");
                animal.setEtatCourant(new EtatAnimalRassasie(animal));
            }
            caseCible.setEtat(new EmptyTile());
            animal.setPosition(nourriture);
            animal.decrementeTours();
            return; // Terminer après avoir mangé
        }

        // Recherche d'un autre arbre dans les cases adjacentes
        Position nouvelArbre = animal.trouverCaseAdaptee(carte, CachettePrincipale.class);


        if (nouvelArbre != null) {
            animal.setPosition(nouvelArbre);
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " se perche sur un autre  "+animal.seCacheOu1()+".");
            animal.decrementeTours();
            return; // Terminer après avoir trouvé un nouvel arbre
        }

        // Si rien n'est trouvé, rester sur place et attendre
        System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " reste perché mais ne trouve ni nourriture, ni un autre  "+animal.seCacheOu1()+".");
        animal.decrementeTours();

        // Si les tours sont épuisés, redevenir affamé
        if (animal.getNbTours() <= 0) {
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " descend de  "+animal.seCacheOu1()+"e et devient affamé.");
            animal.setEtatCourant(new EtatAnimalAffame(animal));
            animal.setPosition(animal.trouverCaseAdaptee(carte, EmptyTile.class));
            animal.chargerTours();
        }
    }
}
