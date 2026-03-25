package pieces;

import board.Position;

public class King extends Piece {

    public King(String color, Position position){
        super(color, position);
    }

    @Override
    public void possibleMoves(){
        System.out.println("King moves forward");
    }
}