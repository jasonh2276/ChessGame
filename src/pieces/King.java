package pieces;

import board.Position;

public class King extends Piece {

    public King(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());

        return rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0);
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "WK" : "BK";
    }
}