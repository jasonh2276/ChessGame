package game;

import board.Board;
import java.util.Scanner;

public class Game {

    private Board board;

    public Game(){
        board = new Board();
    }

    public void start(){

        Scanner scanner = new Scanner(System.in);

        while(true){

            board.display();

            System.out.println("Enter move (Example: E2 E4)");

            String move = scanner.nextLine();

            System.out.println("Move entered: "+move);
        }
    }
}