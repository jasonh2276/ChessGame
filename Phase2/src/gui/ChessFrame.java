package gui;

import model.GameState;
import model.Move;
import model.Piece;
import model.PieceColor;
import model.PieceType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Main GUI window for the Phase 2 chess project.
 */
public class ChessFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private Piece[][] board;
    private JButton[][] buttons;
    private PieceColor currentTurn;

    private int selectedRow = -1;
    private int selectedCol = -1;

    private JPanel boardPanel;
    private JTextArea historyArea;
    private JTextArea whiteCapturedArea;
    private JTextArea blackCapturedArea;
    private JLabel turnLabel;

    private Stack<Move> moveStack;
    private List<String> historyEntries;
    private List<String> whiteCaptured;
    private List<String> blackCaptured;

    private Color lightSquare = new Color(240, 217, 181);
    private Color darkSquare = new Color(181, 136, 99);
    private int buttonFontSize = 32;
    private static final int AI_SEARCH_DEPTH = 3;

    public ChessFrame() {
        setTitle("Chess Game - Phase 2 GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new Piece[8][8];
        buttons = new JButton[8][8];
        moveStack = new Stack<>();
        historyEntries = new ArrayList<>();
        whiteCaptured = new ArrayList<>();
        blackCaptured = new ArrayList<>();
        currentTurn = PieceColor.WHITE;

        initializeBoard();
        createMenuBar();
        createTopPanel();
        createBoardPanel();
        createSidePanel();
        refreshBoard();

        setSize(1000, 750);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        turnLabel = new JLabel("Current Turn: WHITE");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(turnLabel);
        add(topPanel, BorderLayout.NORTH);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem settingsItem = new JMenuItem("Settings");

        newGameItem.addActionListener(e -> newGame());
        saveGameItem.addActionListener(e -> saveGame());
        loadGameItem.addActionListener(e -> loadGame());
        settingsItem.addActionListener(e -> openSettings());

        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(loadGameItem);
        optionsMenu.add(settingsItem);

        menuBar.add(gameMenu);
        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);
    }

    private void createBoardPanel() {
        boardPanel = new JPanel(new GridLayout(8, 8));
        buildBoardButtons();
        add(boardPanel, BorderLayout.CENTER);
    }

    private void buildBoardButtons() {
        boardPanel.removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setFont(new Font("SansSerif", Font.PLAIN, buttonFontSize));
                button.setFocusPainted(false);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final int r = row;
                final int c = col;

                button.addActionListener(e -> handleSquareClick(r, c));

                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());

        historyArea = new JTextArea(18, 24);
        historyArea.setEditable(false);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(new TitledBorder("Move History"));

        whiteCapturedArea = new JTextArea(5, 24);
        whiteCapturedArea.setEditable(false);
        whiteCapturedArea.setBorder(new TitledBorder("White Captured Pieces"));

        blackCapturedArea = new JTextArea(5, 24);
        blackCapturedArea.setEditable(false);
        blackCapturedArea.setBorder(new TitledBorder("Black Captured Pieces"));

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> undoMove());

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        bottomPanel.add(new JScrollPane(whiteCapturedArea));
        bottomPanel.add(new JScrollPane(blackCapturedArea));
        bottomPanel.add(undoButton);

        sidePanel.add(historyScroll, BorderLayout.CENTER);
        sidePanel.add(bottomPanel, BorderLayout.SOUTH);

        add(sidePanel, BorderLayout.EAST);
    }

    private void initializeBoard() {
        board = new Piece[8][8];

        board[0][0] = new Piece(PieceType.ROOK, PieceColor.BLACK);
        board[0][1] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
        board[0][2] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
        board[0][3] = new Piece(PieceType.QUEEN, PieceColor.BLACK);
        board[0][4] = new Piece(PieceType.KING, PieceColor.BLACK);
        board[0][5] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
        board[0][6] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
        board[0][7] = new Piece(PieceType.ROOK, PieceColor.BLACK);

        for (int c = 0; c < 8; c++) {
            board[1][c] = new Piece(PieceType.PAWN, PieceColor.BLACK);
            board[6][c] = new Piece(PieceType.PAWN, PieceColor.WHITE);
        }

        board[7][0] = new Piece(PieceType.ROOK, PieceColor.WHITE);
        board[7][1] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        board[7][2] = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        board[7][3] = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        board[7][4] = new Piece(PieceType.KING, PieceColor.WHITE);
        board[7][5] = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        board[7][6] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        board[7][7] = new Piece(PieceType.ROOK, PieceColor.WHITE);
    }

    private void handleSquareClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            if (board[row][col] == null) {
                return;
            }

            if (board[row][col].getColor() != currentTurn) {
                JOptionPane.showMessageDialog(this, "It is " + currentTurn + "'s turn.");
                return;
            }

            selectedRow = row;
            selectedCol = col;
            highlightSelectedSquare();
        } else {
            movePiece(selectedRow, selectedCol, row, col);
            clearSelection();
        }
    }

    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow == toRow && fromCol == toCol) {
            return;
        }

        Piece movingPiece = board[fromRow][fromCol];
        Piece targetPiece = board[toRow][toCol];

        if (movingPiece == null) {
            return;
        }

        if (targetPiece != null && targetPiece.getColor() == movingPiece.getColor()) {
            JOptionPane.showMessageDialog(this, "You cannot move onto your own piece.");
            return;
        }

        if (!isLegalMove(board, fromRow, fromCol, toRow, toCol, currentTurn)) {
            JOptionPane.showMessageDialog(this, "That is not a legal move for this piece.");
            return;
        }

        Move move = new Move(fromRow, fromCol, toRow, toCol, movingPiece, targetPiece, currentTurn);
        moveStack.push(move);

        if (targetPiece != null) {
            if (targetPiece.getColor() == PieceColor.WHITE) {
                whiteCaptured.add(targetPiece.getType().toString());
            } else {
                blackCaptured.add(targetPiece.getType().toString());
            }
        }

        board[toRow][toCol] = movingPiece;
        board[fromRow][fromCol] = null;

        historyEntries.add(move.toString());

        refreshBoard();
        updateHistoryPanels();

        if (targetPiece != null && targetPiece.getType() == PieceType.KING) {
            JOptionPane.showMessageDialog(this, currentTurn + " wins! The opponent's King was captured.");
            System.exit(0);
        }

        currentTurn = (currentTurn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        turnLabel.setText("Current Turn: " + currentTurn);

        if (currentTurn == PieceColor.BLACK) {
            makeAIMove();
        }
    }

    private void undoMove() {
        if (moveStack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No moves to undo.");
            return;
        }

        Move move = moveStack.pop();

        board[move.getFromRow()][move.getFromCol()] = move.getMovedPiece();
        board[move.getToRow()][move.getToCol()] = move.getCapturedPiece();

        if (!historyEntries.isEmpty()) {
            historyEntries.remove(historyEntries.size() - 1);
        }

        if (move.getCapturedPiece() != null) {
            if (move.getCapturedPiece().getColor() == PieceColor.WHITE && !whiteCaptured.isEmpty()) {
                whiteCaptured.remove(whiteCaptured.size() - 1);
            } else if (move.getCapturedPiece().getColor() == PieceColor.BLACK && !blackCaptured.isEmpty()) {
                blackCaptured.remove(blackCaptured.size() - 1);
            }
        }

        currentTurn = move.getPlayer();
        turnLabel.setText("Current Turn: " + currentTurn);

        refreshBoard();
        updateHistoryPanels();
    }

    private void newGame() {
        selectedRow = -1;
        selectedCol = -1;
        currentTurn = PieceColor.WHITE;
        moveStack.clear();
        historyEntries.clear();
        whiteCaptured.clear();
        blackCaptured.clear();
        initializeBoard();
        refreshBoard();
        updateHistoryPanels();
        turnLabel.setText("Current Turn: WHITE");
    }

    private void saveGame() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                GameState state = new GameState(board, currentTurn, moveStack,
                        historyEntries, whiteCaptured, blackCaptured);
                out.writeObject(state);
                JOptionPane.showMessageDialog(this, "Game saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving game: " + e.getMessage());
            }
        }
    }

    private void loadGame() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                GameState state = (GameState) in.readObject();

                board = state.getBoard();
                currentTurn = state.getCurrentTurn();
                moveStack = state.getMoveStack();
                historyEntries = state.getHistoryEntries();
                whiteCaptured = state.getWhiteCaptured();
                blackCaptured = state.getBlackCaptured();

                clearSelection();
                refreshBoard();
                updateHistoryPanels();
                turnLabel.setText("Current Turn: " + currentTurn);

                JOptionPane.showMessageDialog(this, "Game loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error loading game: " + e.getMessage());
            }
        }
    }

    private void openSettings() {
        SettingsDialog dialog = new SettingsDialog(this);
        dialog.setVisible(true);

        if (!dialog.isApplied()) {
            return;
        }

        String theme = dialog.getSelectedTheme();
        String size = dialog.getSelectedSize();

        if ("Classic".equals(theme)) {
            lightSquare = new Color(240, 217, 181);
            darkSquare = new Color(181, 136, 99);
        } else if ("Gray".equals(theme)) {
            lightSquare = new Color(220, 220, 220);
            darkSquare = new Color(120, 120, 120);
        } else if ("Blue".equals(theme)) {
            lightSquare = new Color(210, 225, 255);
            darkSquare = new Color(90, 130, 190);
        }

        if ("Small".equals(size)) {
            buttonFontSize = 24;
            setSize(850, 650);
        } else if ("Medium".equals(size)) {
            buttonFontSize = 32;
            setSize(1000, 750);
        } else if ("Large".equals(size)) {
            buttonFontSize = 40;
            setSize(1150, 850);
        }

        buildBoardButtons();
        refreshBoard();
    }

    private void refreshBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = buttons[row][col];

                if ((row + col) % 2 == 0) {
                    button.setBackground(lightSquare);
                } else {
                    button.setBackground(darkSquare);
                }

                if (board[row][col] == null) {
                    button.setText("");
                } else {
                    button.setText(board[row][col].getSymbol());
                }
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void updateHistoryPanels() {
        StringBuilder historyBuilder = new StringBuilder();
        for (String entry : historyEntries) {
            historyBuilder.append(entry).append("\n");
        }
        historyArea.setText(historyBuilder.toString());

        StringBuilder whiteBuilder = new StringBuilder();
        for (String entry : whiteCaptured) {
            whiteBuilder.append(entry).append("\n");
        }
        whiteCapturedArea.setText(whiteBuilder.toString());

        StringBuilder blackBuilder = new StringBuilder();
        for (String entry : blackCaptured) {
            blackBuilder.append(entry).append("\n");
        }
        blackCapturedArea.setText(blackBuilder.toString());
    }

    private void highlightSelectedSquare() {
        refreshBoard();
        if (selectedRow != -1 && selectedCol != -1) {
            buttons[selectedRow][selectedCol].setBackground(Color.YELLOW);
        }
    }

    private void clearSelection() {
        selectedRow = -1;
        selectedCol = -1;
        refreshBoard();
    }

    private void makeAIMove() {
        AIMove bestMove = findBestAIMove(PieceColor.BLACK, AI_SEARCH_DEPTH);

        if (bestMove == null) {
            JOptionPane.showMessageDialog(this, "AI has no legal moves.");
            currentTurn = PieceColor.WHITE;
            turnLabel.setText("Current Turn: " + currentTurn);
            return;
        }

        Piece movingPiece = board[bestMove.fromRow][bestMove.fromCol];
        Piece targetPiece = board[bestMove.toRow][bestMove.toCol];

        Move move = new Move(bestMove.fromRow, bestMove.fromCol, bestMove.toRow, bestMove.toCol,
                movingPiece, targetPiece, PieceColor.BLACK);
        moveStack.push(move);

        if (targetPiece != null) {
            whiteCaptured.add(targetPiece.getType().toString());
        }

        board[bestMove.toRow][bestMove.toCol] = movingPiece;
        board[bestMove.fromRow][bestMove.fromCol] = null;

        historyEntries.add("AI: " + move.toString());

        refreshBoard();
        updateHistoryPanels();

        if (targetPiece != null && targetPiece.getType() == PieceType.KING) {
            JOptionPane.showMessageDialog(this, "BLACK wins! The AI captured the opponent's King.");
            System.exit(0);
        }

        currentTurn = PieceColor.WHITE;
        turnLabel.setText("Current Turn: " + currentTurn + " | AI used minimax with alpha-beta pruning");
    }

    private AIMove findBestAIMove(PieceColor color, int depth) {
        List<AIMove> moves = generateLegalMoves(board, color);
        AIMove bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (AIMove move : moves) {
            Piece captured = makeTemporaryMove(board, move);
            int score = minimax(board, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            undoTemporaryMove(board, move, captured);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(Piece[][] currentBoard, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || kingMissing(currentBoard, PieceColor.WHITE) || kingMissing(currentBoard, PieceColor.BLACK)) {
            return evaluateBoard(currentBoard);
        }

        PieceColor color = maximizingPlayer ? PieceColor.BLACK : PieceColor.WHITE;
        List<AIMove> moves = generateLegalMoves(currentBoard, color);

        if (moves.isEmpty()) {
            return evaluateBoard(currentBoard);
        }

        if (maximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (AIMove move : moves) {
                Piece captured = makeTemporaryMove(currentBoard, move);
                int score = minimax(currentBoard, depth - 1, alpha, beta, false);
                undoTemporaryMove(currentBoard, move, captured);

                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (AIMove move : moves) {
                Piece captured = makeTemporaryMove(currentBoard, move);
                int score = minimax(currentBoard, depth - 1, alpha, beta, true);
                undoTemporaryMove(currentBoard, move, captured);

                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }

    private List<AIMove> generateLegalMoves(Piece[][] currentBoard, PieceColor color) {
        List<AIMove> moves = new ArrayList<>();

        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Piece piece = currentBoard[fromRow][fromCol];
                if (piece == null || piece.getColor() != color) {
                    continue;
                }

                for (int toRow = 0; toRow < 8; toRow++) {
                    for (int toCol = 0; toCol < 8; toCol++) {
                        if (isLegalMove(currentBoard, fromRow, fromCol, toRow, toCol, color)) {
                            moves.add(new AIMove(fromRow, fromCol, toRow, toCol));
                        }
                    }
                }
            }
        }

        return moves;
    }

    private boolean isLegalMove(Piece[][] currentBoard, int fromRow, int fromCol, int toRow, int toCol, PieceColor color) {
        if (!inBounds(fromRow, fromCol) || !inBounds(toRow, toCol)) {
            return false;
        }
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }

        Piece movingPiece = currentBoard[fromRow][fromCol];
        Piece targetPiece = currentBoard[toRow][toCol];

        if (movingPiece == null || movingPiece.getColor() != color) {
            return false;
        }
        if (targetPiece != null && targetPiece.getColor() == color) {
            return false;
        }

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;
        int absRow = Math.abs(rowDiff);
        int absCol = Math.abs(colDiff);

        switch (movingPiece.getType()) {
            case PAWN:
                return isLegalPawnMove(currentBoard, fromRow, fromCol, toRow, toCol, color);
            case ROOK:
                return (rowDiff == 0 || colDiff == 0) && isPathClear(currentBoard, fromRow, fromCol, toRow, toCol);
            case BISHOP:
                return absRow == absCol && isPathClear(currentBoard, fromRow, fromCol, toRow, toCol);
            case QUEEN:
                return (rowDiff == 0 || colDiff == 0 || absRow == absCol)
                        && isPathClear(currentBoard, fromRow, fromCol, toRow, toCol);
            case KNIGHT:
                return (absRow == 2 && absCol == 1) || (absRow == 1 && absCol == 2);
            case KING:
                return absRow <= 1 && absCol <= 1;
            default:
                return false;
        }
    }

    private boolean isLegalPawnMove(Piece[][] currentBoard, int fromRow, int fromCol, int toRow, int toCol, PieceColor color) {
        int direction = color == PieceColor.WHITE ? -1 : 1;
        int startRow = color == PieceColor.WHITE ? 6 : 1;
        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;
        Piece targetPiece = currentBoard[toRow][toCol];

        if (colDiff == 0 && rowDiff == direction && targetPiece == null) {
            return true;
        }

        if (colDiff == 0 && fromRow == startRow && rowDiff == 2 * direction
                && targetPiece == null && currentBoard[fromRow + direction][fromCol] == null) {
            return true;
        }

        return Math.abs(colDiff) == 1 && rowDiff == direction
                && targetPiece != null && targetPiece.getColor() != color;
    }

    private boolean isPathClear(Piece[][] currentBoard, int fromRow, int fromCol, int toRow, int toCol) {
        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int row = fromRow + rowStep;
        int col = fromCol + colStep;

        while (row != toRow || col != toCol) {
            if (currentBoard[row][col] != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return true;
    }

    private Piece makeTemporaryMove(Piece[][] currentBoard, AIMove move) {
        Piece captured = currentBoard[move.toRow][move.toCol];
        currentBoard[move.toRow][move.toCol] = currentBoard[move.fromRow][move.fromCol];
        currentBoard[move.fromRow][move.fromCol] = null;
        return captured;
    }

    private void undoTemporaryMove(Piece[][] currentBoard, AIMove move, Piece captured) {
        currentBoard[move.fromRow][move.fromCol] = currentBoard[move.toRow][move.toCol];
        currentBoard[move.toRow][move.toCol] = captured;
    }

    private int evaluateBoard(Piece[][] currentBoard) {
        int score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = currentBoard[row][col];
                if (piece == null) {
                    continue;
                }

                int value = getPieceValue(piece.getType());
                score += piece.getColor() == PieceColor.BLACK ? value : -value;
            }
        }

        return score;
    }

    private int getPieceValue(PieceType type) {
        switch (type) {
            case PAWN: return 100;
            case KNIGHT: return 320;
            case BISHOP: return 330;
            case ROOK: return 500;
            case QUEEN: return 900;
            case KING: return 20000;
            default: return 0;
        }
    }

    private boolean kingMissing(Piece[][] currentBoard, PieceColor color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = currentBoard[row][col];
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() == color) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private static class AIMove {
        int fromRow;
        int fromCol;
        int toRow;
        int toCol;

        AIMove(int fromRow, int fromCol, int toRow, int toCol) {
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            this.toRow = toRow;
            this.toCol = toCol;
        }
    }

}