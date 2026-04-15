package model;

import java.io.Serializable;

/**
 * Represents a chess piece with a type and color.
 */
public class Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    private PieceType type;
    private PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public String getSymbol() {
        if (color == PieceColor.WHITE) {
            switch (type) {
                case PAWN: return "\u2659";
                case ROOK: return "\u2656";
                case KNIGHT: return "\u2658";
                case BISHOP: return "\u2657";
                case QUEEN: return "\u2655";
                case KING: return "\u2654";
                default: return "";
            }
        } else {
            switch (type) {
                case PAWN: return "\u265F";
                case ROOK: return "\u265C";
                case KNIGHT: return "\u265E";
                case BISHOP: return "\u265D";
                case QUEEN: return "\u265B";
                case KING: return "\u265A";
                default: return "";
            }
        }
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}