package game;

import board.Board;
import board.Position;
import java.util.ArrayList;
import java.util.Random;

public class SimpleAI {

    private Random random;

    public SimpleAI() {
        random = new Random();
    }

    public void makeMove(Board board) {
        ArrayList<String> legalMoves = new ArrayList<>();

        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                for (int toRow = 0; toRow < 8; toRow++) {
                    for (int toCol = 0; toCol < 8; toCol++) {
                        Position from = new Position(fromRow, fromCol);
                        Position to = new Position(toRow, toCol);

                        Board testBoard = board.copyBoard();
                        String result = testBoard.movePiece(from, to, "Black");

                        if (result.equals("SUCCESS")) {
                            legalMoves.add(fromRow + "," + fromCol + "," + toRow + "," + toCol);
                        }
                    }
                }
            }
        }

        if (legalMoves.isEmpty()) {
            System.out.println("AI has no legal moves.");
            return;
        }

        String move = legalMoves.get(random.nextInt(legalMoves.size()));
        String[] parts = move.split(",");

        Position from = new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        Position to = new Position(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

        board.movePiece(from, to, "Black");

        System.out.println("AI opponent made a legal move.");
    }
}