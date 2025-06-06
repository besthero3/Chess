import javafx.css.Match;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainCLI {

    boolean whiteCanCastle = true;
    boolean blackCanCastle = true;
    static int whiteMoveCount = 0;
    static int blackMoveCount = 0;
    static boolean gameOver = false;
    static boolean inCheck = false;
    static boolean whoseMove = true;

    //board has square
    static Square[][] board = new Square[8][8];

    public static void main(String[] args) {
        for(int row = 0; row < board[0].length; row++) {

            for(int col = 0; col < board.length; col++) {
                //print the string representation of the board
            }
        }

        while(!gameOver) {
            Scanner input = new Scanner(System.in);

            //white's move
            if(whoseMove) {
                System.out.println("White, what is your move?");
            }
            else {
                System.out.println("Black, what is your move?");
            }

            //gets the move
            String move = input.nextLine();

            //call different class to check move validation
            if (inCheck) {
                //ensure that the check is working
            }

            //if valid move
            //check if last move put king in check

            whoseMove = !whoseMove;
        }
    }

    String checkNotation(String move) {
        String returnString = "";

        Pattern piecePattern = Pattern.compile("[RNBQK]", Pattern.CASE_INSENSITIVE);
        Pattern columnPattern = Pattern.compile("[abcdefgh]");
        Pattern rowPattern = Pattern.compile("[1-8]");

        //Match whether the piece is in the move and then evaluates it
        Matcher pieceMatcher = piecePattern.matcher(move);
        boolean pieceExists = pieceMatcher.find();

        //checks whether the col in the move exists
        Matcher colMatcher = columnPattern.matcher(move);
        boolean colExists = colMatcher.find();

        //checks whether the row in the move exists
        Matcher rowMatcher = rowPattern.matcher(move);
        boolean rowExists = rowMatcher.find();

        //means the square did not exist
        if (!colExists || !rowExists) {
            System.out.println("Invalid square");
            return "Invalid";
        }

        if(move.charAt(0) == 'R') {
            //piece type is a rook, could return that
            //or could not and check valid move from here
            returnString = "Rook";
        }
        else if(move.charAt(0) == 'N') {
            //piece type is a rook, could return that
            //or could not and check valid move from here
            returnString = "Knight";
        }
        else if(move.charAt(0) == 'B') {
            //piece type is a rook, could return that
            //or could not and check valid move from here
            returnString = "Bishop";
        }
        else if(move.charAt(0) == 'Q') {
            //piece type is a rook, could return that
            //or could not and check valid move from here
            returnString = "Queen";
        }
        else if(move.charAt(0) == 'K') {
            //piece type is a rook, could return that
            //or could not and check valid move from here
            returnString = "King";
        }

        if(move.contains("O-O")) {
            if(move.equals("O-O")) {
                //castles kingside
                returnString = "Kingside";
            }
            else if (move.equals("O-O-O")) {
                //castles queenside
                returnString = "Kingside";
            }
        }

        //either invalid input or a pawn... how to determine that
        if(!pieceExists) {

            //captures are always multiple moves... (4)
            String first = String.valueOf(move.charAt(0));
            Matcher pawn = columnPattern.matcher(first);
            boolean isPawnMove = pawn.find();

            if (isPawnMove) {
                //set equal to pawn
                returnString = "Pawn";
            }
            else {
                System.out.println("The notation was invalid");
                return "Invalid";
            }
        }

        //TODO: return a string and then add takes to it if we are capturing
        if (move.contains("x")) {
            returnString += " takes";
        }

        return returnString;
    }

}
