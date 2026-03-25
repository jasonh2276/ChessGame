package pieces;

import board.Position;

public abstract class Piece {

    protected String color;
    protected Position position;

    public Piece(String color, Position position){
        this.color = color;
        this.position = position;
    }

    public String getColor(){
        return color;
    }

    public Position getPosition(){
        return position;
    }

    public void move(Position newPosition){
        this.position = newPosition;
    }

    public abstract void possibleMoves();
}