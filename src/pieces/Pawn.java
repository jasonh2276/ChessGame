package pieces;

import board.Position;

public class Pawn extends Piece {

    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        Piece targetPiece = board[to.getRow()][to.getCol()];

        if (color.equals("White")) {
            // Move forward one
            if (colDiff == 0 && rowDiff == -1 && targetPiece == null) {
                return true;
            }

            // Move forward two from starting row
            if (from.getRow() == 6 && colDiff == 0 && rowDiff == -2 && targetPiece == null
                    && board[5][from.getCol()] == null) {
                return true;
            }

            // Capture diagonally
            return Math.abs(colDiff) == 1 && rowDiff == -1 && targetPiece != null
                    && !targetPiece.getColor().equals(color);
        } else {
            // Move forward one
            if (colDiff == 0 && rowDiff == 1 && targetPiece == null) {
                return true;
            }

            // Move forward two from starting row
            if (from.getRow() == 1 && colDiff == 0 && rowDiff == 2 && targetPiece == null
                    && board[2][from.getCol()] == null) {
                return true;
            }

            // Capture diagonally
            return Math.abs(colDiff) == 1 && rowDiff == 1 && targetPiece != null
                    && !targetPiece.getColor().equals(color);
        }
    }

    @Override
    public String getSymbol() {
        return color.equals("White") ? "WP" : "BP";
    }
}