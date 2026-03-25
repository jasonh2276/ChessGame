package pieces; //Bishop rep

import board.Position;

public class Bishop extends Piece {

    public Bishop(String color, Position position){
        super(color, position);
    }

    @Override
    public void possibleMoves(){
        System.out.println("Bishop moves forward");
    }
}