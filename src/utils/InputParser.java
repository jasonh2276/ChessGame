package utils;

import board.Position;

public class InputParser {

    public static boolean validMove(String input) {
        if (input == null) {
            return false;
        }

        input = input.trim().toUpperCase();

        if (input.length() != 5) {
            return false;
        }

        if (input.charAt(2) != ' ') {
            return false;
        }

        String from = input.substring(0, 2);
        String to = input.substring(3, 5);

        return isValidSquare(from) && isValidSquare(to);
    }

    private static boolean isValidSquare(String square) {
        if (square.length() != 2) {
            return false;
        }

        char file = square.charAt(0);
        char rank = square.charAt(1);

        return file >= 'A' && file <= 'H' && rank >= '1' && rank <= '8';
    }

    public static Position parsePosition(String square) {
        square = square.toUpperCase();
        int col = square.charAt(0) - 'A';
        int row = 8 - Character.getNumericValue(square.charAt(1));
        return new Position(row, col);
    }
}