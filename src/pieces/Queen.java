package pieces;

import board.Position;

public class Queen extends Piece {

    public Queen(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board) {
        boolean straight = from.getRow() == to.getRow() || from.getCol() == to.getCol();
        boolean diagonal = Math.abs(to.getRow() - from.getRow()) == Math.abs(to.getCol() - from.getCol());
        return straight || diagonal;
    }

    @Override
    public boolean canMoveTo(Position from, Position to, Piece[][] board) {
        if (from.getRow() == to.getRow() || from.getCol() == to.getCol()) {
            return isPathClearStraight(from, to, board);
        }

        return isPathClearDiagonal(from, to, board);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "WQ" : "BQ";
    }
}