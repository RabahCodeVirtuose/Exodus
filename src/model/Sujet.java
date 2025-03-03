package model;

public interface Sujet {
    void ajouterObservateur(Observateur observateur);
    void retirerObservateur(Observateur observateur);
    void notifierObservateurs(Animal proie);
}
