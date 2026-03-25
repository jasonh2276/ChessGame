package utils;

public class InputParser {

    public static boolean validMove(String input){

        if(input.length()!=5)
            return false;

        if(input.charAt(2)!=' ')
            return false;

        return true;
    }
}