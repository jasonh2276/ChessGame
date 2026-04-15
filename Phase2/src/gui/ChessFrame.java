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
}