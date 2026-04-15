package pieces;

import board.Position;

public abstract class Piece {

    protected String color;
    protected Position position;

    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void move(Position newPosition) {
        this.position = newPosition;
    }

    public abstract boolean isValidMove(Position from, Position to, Piece[][] board);

    public abstract String getSymbol();

    public boolean canMoveTo(Position from, Position to, Piece[][] board) {
        return true;
    }

    protected boolean isPathClearStraight(Position from, Position to, Piece[][] board) {
        if (from.getRow() == to.getRow()) {
            int row = from.getRow();
            int start = Math.min(from.getCol(), to.getCol()) + 1;
            int end = Math.max(from.getCol(), to.getCol());

            for (int col = start; col < end; col++) {
                if (board[row][col] != null) {
                    return false;
                }
            }
            return true;
        }

        if (from.getCol() == to.getCol()) {
            int col = from.getCol();
            int start = Math.min(from.getRow(), to.getRow()) + 1;
            int end = Math.max(from.getRow(), to.getRow());

            for (int row = start; row < end; row++) {
                if (board[row][col] != null) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    protected boolean isPathClearDiagonal(Position from, Position to, Piece[][] board) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (Math.abs(rowDiff) != Math.abs(colDiff)) {
            return false;
        }

        int rowStep = rowDiff > 0 ? 1 : -1;
        int colStep = colDiff > 0 ? 1 : -1;

        int currentRow = from.getRow() + rowStep;
        int currentCol = from.getCol() + colStep;

        while (currentRow != to.getRow() && currentCol != to.getCol()) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }
}