package model;

public class EtatAnimalJunkie implements EtatAnimal {
    private Animal animal;
    private DeplacementStrategy deplacementStrategy;

    public EtatAnimalJunkie(Animal animal) {
        this.animal = animal;
        this.deplacementStrategy = new DeplacementJunkie(); // Stratégie spécifique
    }

    @Override
    public void seDeplacer(Carte carte) {
        if(animal.getNbToursPourEtatBizzare()<=0){
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " revient à l'état affamé après avoir été Junkie.");

            animal.ressetNbToursPourEtatBizzare();
            animal.setEtatCourant(new EtatAnimalAffame(animal));
        }

        System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " est dans un état Junkie et se déplace de manière imprévisible et il reste "+animal.getNbToursPourEtatBizzare()+" Tours Pour devenir affamé");

        Position nouvellePosition = deplacementStrategy.calculerDeplacement(animal, carte);
        if (nouvellePosition != null) {
            animal.setPosition(nouvellePosition);
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " s'est déplacé aléatoirement vers : " + nouvellePosition);
        } else {
            System.out.println(animal.getClass().getSimpleName()+" N° " + animal.getId() + " a tenté de se déplacer, mais ne peut pas.");
        }

        animal.decrementeNbToursPourEtatBizzare();


    }
}
