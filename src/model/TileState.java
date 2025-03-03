package model;

public interface TileState {
    boolean isMovable();
    String getDescription(); // Décrit la case.
    boolean isDangerous();   // Indique si la case est dangereuse.
}
