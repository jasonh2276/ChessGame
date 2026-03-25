package pieces;

import board.Position;

public class Queen extends Piece {

    public Queen(String color, Position position){
        super(color, position);
    }

    @Override
    public void possibleMoves(){
        System.out.println("Queen moves forward");
    }
}