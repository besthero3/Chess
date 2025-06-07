import java.awt.*;
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

    public static final String BLACK = "\u001B[30m";
    public static final String WHITE = "\u001B[37m";



    //board has square
    //TODO: could set 0,0 to be a1
    static Square[][] board = new Square[8][8];


    public static void main(String[] args) {

        for(int row = 0; row < board[0].length; row++) {

            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Square(null, true);

            }
        }
        createBoard();

        //prints the board
        //TODO: can play with background
        for(int row = 0; row < board[0].length; row++) {

            for(int col = 0; col < board.length; col++) {

                //TODO: NULL CHECK, COULD FIND A WAY TO MAKE IT SO THINGS ARE NOT NULL
                if (board[row][col].p == null) {
                    break;
                }
                //print the string representation of the board
                if (board[row][col].p.type == PieceType.ROOK) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "R " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "R " + WHITE);
                    }

                }

                if (board[row][col].p.type == PieceType.KNIGHT) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "N " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "N " + WHITE);
                    }

                }

                if (board[row][col].p.type == PieceType.BISHOP) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "B " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "B " + WHITE);
                    }

                }

                if (board[row][col].p.type == PieceType.QUEEN) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "Q " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "Q " + WHITE);
                    }

                }

                if (board[row][col].p.type == PieceType.KING) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "K " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "K " + WHITE);
                    }

                }

                if (board[row][col].p.type == PieceType.PAWN) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "P " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "P " + WHITE);
                    }

                }
            }
            System.out.println();

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

    //TODO: going to check the piece type
    //TODO: CONSIDER THAT 0,0 IS IN THE TOP LEFT
    static void createBoard(){
        //black pieces
        board[0][0].p = new Rook(PieceColor.BLACK);
        board[0][0].empty = false;

        board[0][1].p = new Knight(PieceColor.BLACK);
        board[0][1].empty = false;

        board[0][2].p = new Bishop(PieceColor.BLACK);
        board[0][2].empty = false;

        board[0][3].p = new Queen(PieceColor.BLACK);
        board[0][3].empty = false;

        board[0][4].p = new King(PieceColor.BLACK);
        board[0][4].empty = false;

        board[0][5].p = new Bishop(PieceColor.BLACK);
        board[0][5].empty = false;

        board[0][6].p = new Knight(PieceColor.BLACK);
        board[0][6].empty = false;

        board[0][7].p = new Rook(PieceColor.BLACK);
        board[0][7].empty = false;

        //Pawn line for black
        board[1][0].p = new Pawn(PieceColor.BLACK);
        board[1][0].empty = false;

        board[1][1].p = new Pawn(PieceColor.BLACK);
        board[1][1].empty = false;

        board[1][2].p = new Pawn(PieceColor.BLACK);
        board[1][2].empty = false;

        board[1][3].p = new Pawn(PieceColor.BLACK);
        board[1][3].empty = false;

        board[1][4].p = new Pawn(PieceColor.BLACK);
        board[1][4].empty = false;

        board[1][5].p = new Pawn(PieceColor.BLACK);
        board[1][5].empty = false;

        board[1][6].p = new Pawn(PieceColor.BLACK);
        board[1][6].empty = false;

        board[1][7].p = new Pawn(PieceColor.BLACK);
        board[1][7].empty = false;

        //White pawn line
        board[6][0].p = new Pawn(PieceColor.WHITE);
        board[6][0].empty = false;

        board[6][1].p = new Pawn(PieceColor.WHITE);
        board[6][1].empty = false;

        board[6][2].p = new Pawn(PieceColor.WHITE);
        board[6][2].empty = false;

        board[6][3].p = new Pawn(PieceColor.WHITE);
        board[6][3].empty = false;

        board[6][4].p = new Pawn(PieceColor.WHITE);
        board[6][4].empty = false;

        board[6][5].p = new Pawn(PieceColor.WHITE);
        board[6][5].empty = false;

        board[6][6].p = new Pawn(PieceColor.WHITE);
        board[6][6].empty = false;

        board[6][7].p = new Pawn(PieceColor.WHITE);
        board[6][7].empty = false;

        //white pieces
        board[7][0].p = new Rook(PieceColor.WHITE);
        board[7][0].empty = false;

        board[7][1].p = new Knight(PieceColor.WHITE);
        board[7][1].empty = false;

        board[7][2].p = new Bishop(PieceColor.WHITE);
        board[7][2].empty = false;

        board[7][3].p = new Queen(PieceColor.WHITE);
        board[7][3].empty = false;

        board[7][4].p = new King(PieceColor.WHITE);
        board[7][4].empty = false;

        board[7][5].p = new Bishop(PieceColor.WHITE);
        board[7][5].empty = false;

        board[7][6].p = new Knight(PieceColor.WHITE);
        board[7][6].empty = false;

        board[7][7].p = new Rook(PieceColor.WHITE);
        board[7][7].empty = false;

    }

}
