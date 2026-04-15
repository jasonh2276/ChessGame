package pieces;

import board.Position;

public class Bishop extends Piece {

    public Bishop(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board) {
        return Math.abs(to.getRow() - from.getRow()) == Math.abs(to.getCol() - from.getCol());
    }

    @Override
    public boolean canMoveTo(Position from, Position to, Piece[][] board) {
        return isPathClearDiagonal(from, to, board);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "WB" : "BB";
    }
}