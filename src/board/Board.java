package board;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {

    private Piece[][] board;
    private boolean kingCaptured;

    public Board() {
        board = new Piece[8][8];
        kingCaptured = false;
        initializeBoard();
    }

    private void initializeBoard() {
        // Black major pieces
        board[0][0] = new Rook("Black", new Position(0, 0));
        board[0][1] = new Knight("Black", new Position(0, 1));
        board[0][2] = new Bishop("Black", new Position(0, 2));
        board[0][3] = new Queen("Black", new Position(0, 3));
        board[0][4] = new King("Black", new Position(0, 4));
        board[0][5] = new Bishop("Black", new Position(0, 5));
        board[0][6] = new Knight("Black", new Position(0, 6));
        board[0][7] = new Rook("Black", new Position(0, 7));

        // Black pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn("Black", new Position(1, col));
        }

        // White pawns
        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn("White", new Position(6, col));
        }

        // White major pieces
        board[7][0] = new Rook("White", new Position(7, 0));
        board[7][1] = new Knight("White", new Position(7, 1));
        board[7][2] = new Bishop("White", new Position(7, 2));
        board[7][3] = new Queen("White", new Position(7, 3));
        board[7][4] = new King("White", new Position(7, 4));
        board[7][5] = new Bishop("White", new Position(7, 5));
        board[7][6] = new Knight("White", new Position(7, 6));
        board[7][7] = new Rook("White", new Position(7, 7));
    }

    public void display() {
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println("  +---+---+---+---+---+---+---+---+");

        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " |");

            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print("   |");
                } else {
                    System.out.print(board[row][col].getSymbol() + "|");
                }
            }

            System.out.println(" " + (8 - row));
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }

        System.out.println("    A   B   C   D   E   F   G   H");
    }

    public String movePiece(Position from, Position to, String currentTurn) {
        if (!isInBounds(from) || !isInBounds(to)) {
            return "Position out of bounds.";
        }

        if (from.getRow() == to.getRow() && from.getCol() == to.getCol()) {
            return "Source and destination cannot be the same.";
        }

        Piece movingPiece = board[from.getRow()][from.getCol()];
        Piece targetPiece = board[to.getRow()][to.getCol()];

        if (movingPiece == null) {
            return "No piece found at the source position.";
        }

        if (!movingPiece.getColor().equals(currentTurn)) {
            return "That piece does not belong to " + currentTurn + ".";
        }

        if (targetPiece != null && targetPiece.getColor().equals(currentTurn)) {
            return "You cannot capture your own piece.";
        }

        if (!movingPiece.isValidMove(from, to, board)) {
            return "That piece cannot move like that.";
        }

        if (!movingPiece.canMoveTo(from, to, board)) {
            return "That move is blocked or invalid.";
        }

        if (targetPiece instanceof King) {
            kingCaptured = true;
        }

        board[to.getRow()][to.getCol()] = movingPiece;
        board[from.getRow()][from.getCol()] = null;
        movingPiece.move(to);

        return "SUCCESS";
    }

    public boolean isKingCaptured() {
        return kingCaptured;
    }

    private boolean isInBounds(Position position) {
        return position.getRow() >= 0 && position.getRow() < 8
                && position.getCol() >= 0 && position.getCol() < 8;
    }
}