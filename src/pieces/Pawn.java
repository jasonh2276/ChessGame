package pieces;

import board.Position;

public class Pawn extends Piece {

    public Pawn(String color, Position position){
        super(color, position);
    }

    @Override
    public void possibleMoves(){
        System.out.println("Pawn moves forward");
    }
}