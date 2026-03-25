package pieces; //Knight rep

import board.Position;

public class Knight extends Piece {

    public Knight(String color, Position position){
        super(color, position);
    }

    @Override
    public void possibleMoves(){
        System.out.println("Knight moves forward");
    }
}