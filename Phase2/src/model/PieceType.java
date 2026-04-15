package model;

import java.io.Serializable;

/**
 * Represents the type of a chess piece.
 */
public enum PieceType implements Serializable {
    PAWN,
    ROOK,
    KNIGHT,
    BISHOP,
    QUEEN,
    KING
}