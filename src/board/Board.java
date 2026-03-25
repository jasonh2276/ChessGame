package board;

import pieces.Piece;

public class Board {

    private Piece[][] board;

    public Board(){
        board = new Piece[8][8];
    }

    public void display(){

        System.out.println("  A  B  C  D  E  F  G  H");

        for(int i=0;i<8;i++){
            System.out.print((8-i)+" ");

            for(int j=0;j<8;j++){

                if(board[i][j]==null)
                    System.out.print("## ");
                else
                    System.out.print("P ");

            }

            System.out.println();
        }
    }
}