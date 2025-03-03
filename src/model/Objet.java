package model;

public class Objet {
    private TileState type;

    public Objet(TileState type) {
        this.type = type;
    }

    public String getType() {
        String tileClass = type.getDescription();
        return tileClass;
    }

    public TileState getType1(){
        return type;
    }


    @Override
    public String toString() {
        return type.getClass().getSimpleName();
    }
}
