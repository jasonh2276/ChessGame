package pieces;

import board.Position;

public class Rook extends Piece {

    public Rook(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board) {
        return from.getRow() == to.getRow() || from.getCol() == to.getCol();
    }

    @Override
    public boolean canMoveTo(Position from, Position to, Piece[][] board) {
        return isPathClearStraight(from, to, board);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "WR" : "BR";
    }
}