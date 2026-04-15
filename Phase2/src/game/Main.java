package game;

import gui.ChessFrame;

import javax.swing.*;

/**
 * Launches the Phase 2 Chess GUI application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessFrame::new);
    }
}