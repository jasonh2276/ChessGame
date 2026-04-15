package model;

import java.io.Serializable;

/**
 * Stores one move for history and undo support.
 */
public class Move implements Serializable {
    private static final long serialVersionUID = 1L;

    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private Piece movedPiece;
    private Piece capturedPiece;
    private PieceColor player;

    public Move(int fromRow, int fromCol, int toRow, int toCol,
                Piece movedPiece, Piece capturedPiece, PieceColor player) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.player = player;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public PieceColor getPlayer() {
        return player;
    }

    public String toSquare(int row, int col) {
        char file = (char) ('A' + col);
        int rank = 8 - row;
        return "" + file + rank;
    }

    @Override
    public String toString() {
        String base = player + ": " + movedPiece.getType() + " " +
                toSquare(fromRow, fromCol) + " -> " + toSquare(toRow, toCol);
        if (capturedPiece != null) {
            base += " (captured " + capturedPiece.getColor() + " " + capturedPiece.getType() + ")";
        }
        return base;
    }
}