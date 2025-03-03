package model;

import java.util.Objects;

public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position positionTowards(Direction direction, int offset) {
        int newX = x, newY = y;
        switch (direction) {
            case NORD -> newY -= offset;
            case SUD -> newY += offset;
            case EST -> newX += offset;
            case OUEST -> newX -= offset;
        }
        return new Position(newX, newY);
    }
    public boolean estAdjacente(Position autrePosition) {
        if (autrePosition == null) {
            return false; // Si la position est nulle, elle ne peut pas être adjacente
        }

        int deltaX = Math.abs(this.getX() - autrePosition.getX());
        int deltaY = Math.abs(this.getY() - autrePosition.getY());

        // Une position est adjacente si la distance en X et Y est au maximum de 1
        return (deltaX <= 1 && deltaY <= 1) && !(deltaX == 0 && deltaY == 0);
    }


    @Override
    public String toString() {
        return "["+x+", "+y+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Vérifie si c'est le même objet
        if (o == null || getClass() != o.getClass()) return false; // Vérifie le type de classe
        Position position = (Position) o;
        return x == position.x && y == position.y; // Compare les valeurs des champs
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y); // Calcule un hash basé sur `x` et `y`
    }
}
