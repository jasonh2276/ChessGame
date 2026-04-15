package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Stores the full game state for save/load.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Piece[][] board;
    private PieceColor currentTurn;
    private Stack<Move> moveStack;
    private List<String> historyEntries;
    private List<String> whiteCaptured;
    private List<String> blackCaptured;

    public GameState(Piece[][] board, PieceColor currentTurn, Stack<Move> moveStack,
                     List<String> historyEntries, List<String> whiteCaptured, List<String> blackCaptured) {
        this.board = deepCopyBoard(board);
        this.currentTurn = currentTurn;
        this.moveStack = new Stack<>();
        this.moveStack.addAll(moveStack);
        this.historyEntries = new ArrayList<>(historyEntries);
        this.whiteCaptured = new ArrayList<>(whiteCaptured);
        this.blackCaptured = new ArrayList<>(blackCaptured);
    }

    public Piece[][] getBoard() {
        return deepCopyBoard(board);
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public Stack<Move> getMoveStack() {
        Stack<Move> copy = new Stack<>();
        copy.addAll(moveStack);
        return copy;
    }

    public List<String> getHistoryEntries() {
        return new ArrayList<>(historyEntries);
    }

    public List<String> getWhiteCaptured() {
        return new ArrayList<>(whiteCaptured);
    }

    public List<String> getBlackCaptured() {
        return new ArrayList<>(blackCaptured);
    }

    private Piece[][] deepCopyBoard(Piece[][] original) {
        Piece[][] copy = new Piece[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (original[r][c] != null) {
                    Piece p = original[r][c];
                    copy[r][c] = new Piece(p.getType(), p.getColor());
                }
            }
        }
        return copy;
    }
}