package game;

import board.Board;
import board.Position;
import java.util.Scanner;
import utils.InputParser;

public class Game {

    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private String currentTurn;
    private SimpleAI ai;

    public Game() {
        board = new Board();
        whitePlayer = new Player("White");
        blackPlayer = new Player("Black");
        currentTurn = "White";
        ai = new SimpleAI();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Console Chess!");
        System.out.println("Extra Credit B: AI opponent is enabled.");
        System.out.println("You are White. The AI plays Black.");
        System.out.println("Enter moves in format: E2 E4");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            board.display();
            System.out.println(currentTurn + "'s turn.");
            System.out.print("Enter move: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Game ended.");
                break;
            }

            if (!InputParser.validMove(input)) {
                System.out.println("Invalid input. Use format like E2 E4.\n");
                continue;
            }

            Position from = InputParser.parsePosition(input.substring(0, 2));
            Position to = InputParser.parsePosition(input.substring(3, 5));

            String result = board.movePiece(from, to, currentTurn);

            if (!result.equals("SUCCESS")) {
                System.out.println(result + "\n");
                continue;
            }

            if (board.isKingCaptured()) {
                board.display();
                System.out.println(currentTurn + " wins! The opponent's king was captured.");
                break;
            }

            switchTurn();

            if (currentTurn.equals("Black")) {
                ai.makeMove(board);

                if (board.isKingCaptured()) {
                    board.display();
                    System.out.println("Black wins! The opponent's king was captured.");
                    break;
                }

                switchTurn();
            }

            System.out.println();
        }

        scanner.close();
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("White") ? "Black" : "White";
    }
}