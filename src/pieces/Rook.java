package pieces;

import board.Position;

public class Rook extends Piece {

    public Rook(String color, Position position){
        super(color, position);
    }

    @Override
    public void possibleMoves(){
        System.out.println("Rook moves forward");
    }
}