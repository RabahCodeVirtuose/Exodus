package model;

public class EtatAnimalCache implements EtatAnimal {
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatAnimalCache(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementVersNourriture(); // Réutilise la stratégie d'un animal affamé
    }

    @Override
    public void seDeplacer(Carte carte) {
        System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " est caché et cherche un "+animal.seCacheOu2()+", un "+animal.seCacheOu1()+" ou de la nourriture.");

        // Vérifier s'il y a de la nourriture dans une case adjacente
        Position nourriture = animal.trouverCaseAdaptee(carte, NourriturePrincipale.class);

        if (nourriture == null) {
            nourriture = animal.trouverCaseAdaptee(carte, NourritureSecondaire.class);
        }

        if (nourriture != null) {
            // Récupérer la case cible et vérifier son état
            Case caseCible = carte.getCase(nourriture.getX(), nourriture.getY());
            if (caseCible.isJeteParjouer() && animal.estVoisinDuPersonnage()) {
                System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " a trouvé de la nourriture jetée par le personnage et devient son ami.");
                animal.setEtatCourant(new EtatAnimalAmiDuPersonnage(animal));
            } else {
                System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " a trouvé de la nourriture et devient rassasié.");
                animal.setEtatCourant(new EtatAnimalRassasie(animal));
            }
            // L'animal mange et se déplace vers la nourriture
            caseCible.setEtat(new EmptyTile());
            animal.setPosition(nourriture);
            animal.decrementeTours();
            return; // Terminer le déplacement après avoir mangé
        }

        // Si aucune nourriture n'est trouvée, chercher un buisson ou un arbre adjacent
        Position buissonOuArbreOuCocotierOuPetitRocher = animal.trouverCaseAdaptee(carte, CachetteSecondaire.class);
        if (buissonOuArbreOuCocotierOuPetitRocher == null) {
            buissonOuArbreOuCocotierOuPetitRocher = animal.trouverCaseAdaptee(carte, CachettePrincipale.class);
        }


        if (buissonOuArbreOuCocotierOuPetitRocher != null) {
            // Déplacer l'animal vers le buisson ou l'arbre
            animal.setPosition(buissonOuArbreOuCocotierOuPetitRocher);

            // Déterminer le nouvel état en fonction du type de la case
            Case caseCible = carte.getCase(buissonOuArbreOuCocotierOuPetitRocher.getX(), buissonOuArbreOuCocotierOuPetitRocher.getY());
            if (caseCible.getEtat() instanceof CachetteSecondaire) {
                System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " s'est caché dans un autre "+animal.seCacheOu2()+".");
                animal.setEtatCourant(new EtatAnimalCache(animal));
            }  else if (caseCible.getEtat() instanceof CachettePrincipale) {
                System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " s'est perché dans un "+animal.seCacheOu1()+".");
                animal.setEtatCourant(new EtatAnimalPerche(animal));
            }
            animal.decrementeTours();
            return; // Terminer après avoir trouvé un buisson ou un arbre
        }

        // Si aucune nourriture, buisson ou arbre n'est trouvé, rester sur place et attendre
        System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " reste caché mais ne trouve ni nourriture, ni autre"+animal.seCacheOu2()+" ou "+animal.seCacheOu1()+".");
        animal.decrementeTours();

        // Si les tours sont épuisés, revenir à l'état affamé
        if (animal.getNbTours() <= 0) {
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " sort du "+animal.seCacheOu2()+" et devient affamé.");
            animal.setEtatCourant(new EtatAnimalAffame(animal));
            animal.setPosition(animal.trouverCaseAdaptee(carte, EmptyTile.class));
            animal.chargerTours();
        }
    }
}
