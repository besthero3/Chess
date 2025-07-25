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
    public static final String GREEN = "\u001B[32m";

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

        while(!gameOver) {
            printBoard();

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

            String notationCheck = checkNotation(move);

            if (!notationCheck.equals("Invalid")) {
                boolean validMove = false;
                boolean captures = false;
                int[] squareCoordinates = new int[2];
                int[] pieceCoordinates = new int[2];

                if (notationCheck.contains("takes")) {
                    captures = true;
                }

                //TODO: find square that the piece is on depending on which piece.. we have
                //iterate through it and look for a piece with that color... see if we have iterations
                //TODO could start at square that we are looking at and iterate in necessary directions...
                //TODO: get square that we are going to for string parsing

                //contains because takes can be present...
                if (notationCheck.contains("Pawn")) {
                    //f4
                    if (move.length() == 2) {
                        squareCoordinates = convertMoveToSquare(move.charAt(0), (int) move.charAt(1));
                    }

                    //exf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), (int) move.charAt(3));
                    }

                    //TODO: WE MAY WANT A CAPTURES SEPARATE FUNCTION - need to ensure that the correct pawn is taking...
                    //also move and captures differently...

                    if (whoseMove) {
                        //separating the functions because they have such different behavior
                        if (captures) {
                            Pawn pawn = new Pawn(PieceColor.WHITE);
                            pieceCoordinates = pawn.isValidCapture(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE,move.charAt(0)); //check if the format is right
                        }
                        else {
                            Pawn pawn = new Pawn(PieceColor.WHITE);
                            pieceCoordinates = pawn.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, captures); //check if the format is right
                        }

                    }
                    else {
                        if (captures) {
                            Pawn pawn = new Pawn(PieceColor.BLACK);
                            pieceCoordinates = pawn.isValidCapture(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, move.charAt(0)); //check if the format is right
                        }
                        else {
                            Pawn pawn = new Pawn(PieceColor.BLACK);
                            pieceCoordinates = pawn.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, captures); //check if the format is right
                        }
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }
                }
                else if (notationCheck.contains("Rook")) {

                    //Rf4
                    if (move.length() == 3) {
                        squareCoordinates = convertMoveToSquare(move.charAt(1), (int) move.charAt(2));
                    }

                    //Rxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), (int) move.charAt(3));
                    }

                    //call valid move and valid capture...
                    //pass in the piece color because thats helpful...

                    //TODO: Rfe4
                    //TODO: Rfxe4

                    //TODO: 4 length without captures...then Rfe8 make sure to process the second number...
                    //captures - Rxf4, check that there is a piece on f4 of the opposite color, then look for rook
                    //not captures Rf4, check surrounding f4 for a rook of the correct color

                    if (whoseMove) {
                        Rook rook = new Rook(PieceColor.WHITE);
                        pieceCoordinates = rook.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, captures); //check if the format is right
                    }
                    else {
                        Rook rook = new Rook(PieceColor.BLACK);
                        pieceCoordinates = rook.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, captures); //check if the format is right
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }
                }
                else if (notationCheck.contains("Knight")) {

                    //Bf4
                    if (move.length() == 3) {
                        squareCoordinates = convertMoveToSquare(move.charAt(1), (int) move.charAt(2));
                    }

                    //Bxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), (int) move.charAt(3));
                    }

                    if (whoseMove) {
                        Knight knight = new Knight(PieceColor.WHITE);
                        pieceCoordinates = knight.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, captures); //check if the format is right
                    }
                    else {
                        Knight knight = new Knight(PieceColor.BLACK);
                        pieceCoordinates = knight.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, captures); //check if the format is right
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }
                }
                else if (notationCheck.contains("Bishop")) {

                    //Bf4
                    if (move.length() == 3) {
                        squareCoordinates = convertMoveToSquare(move.charAt(1), (int) move.charAt(2));
                    }

                    //Bxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), (int) move.charAt(3));
                    }

                    if (whoseMove) {
                        Bishop bishop = new Bishop(PieceColor.WHITE);
                        pieceCoordinates = bishop.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, captures); //check if the format is right
                    }
                    else {
                        Bishop bishop = new Bishop(PieceColor.BLACK);
                        pieceCoordinates = bishop.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, captures); //check if the format is right
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }
                }
                else if (notationCheck.contains("Queen")) {
                    //Qf4
                    if (move.length() == 3) {
                        squareCoordinates = convertMoveToSquare(move.charAt(1), (int) move.charAt(2));
                    }

                    //Bxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), (int) move.charAt(3));
                    }

                    if (whoseMove) {
                        Queen queen = new Queen(PieceColor.WHITE);
                        pieceCoordinates = queen.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, captures); //check if the format is right
                    }
                    else {
                        Queen queen = new Queen(PieceColor.BLACK);
                        pieceCoordinates = queen.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, captures); //check if the format is right
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }
                }
                else if (notationCheck.contains("King")) {

                    //Kf4
                    if (move.length() == 3) {
                        squareCoordinates = convertMoveToSquare(move.charAt(1), (int) move.charAt(2));
                    }

                    //Kxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), (int) move.charAt(3));
                    }

                    if (whoseMove) {
                        King king = new King(PieceColor.WHITE);
                        boolean inCheck = king.check(squareCoordinates[0], squareCoordinates[1], PieceColor.WHITE); //check if the format is right

                        if (!inCheck) {
                            pieceCoordinates = king.isValidMove(squareCoordinates[0], squareCoordinates[1], PieceColor.WHITE, captures);
                        }
                    }
                    else {
                        King king = new King(PieceColor.BLACK);
                        boolean inCheck = king.check(squareCoordinates[0], squareCoordinates[1], PieceColor.BLACK); //check if the format is right

                        if (!inCheck) {
                            pieceCoordinates = king.isValidMove(squareCoordinates[0], squareCoordinates[1], PieceColor.BLACK, captures);
                        }
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }

                }

                //call different class to check move validation

                //TODO: the moves are good - now need to check every move for check
                //TODO: then write a move function if not in check...
                //TODO: can just call check


                if (validMove) {
                    //TODO: if valid pawn move then need to set the first move to false!!!!!!!!!
                    //the move before this one says if the king is in check

                    //TODO: could make a copy of the board and check if the copy is in check ...  IF VALID MOVE
                    //IF IT IS - THEN CHANGE THE ORIGINAL BOARD TO HAVE THE MOVE ON IT....
                    //SQUARE COORDINATES
                    //PIECE COODRIANETS - SET THE PIECE EQUAL TO SQUARE COORDINATE, AND SET PIECE EQUAL TO NULL
                    //TODO: MAYBE DO THIS EITHER WAY - DO I NEED TO DO IT TWICE? UNDER IN CHECK AND UNDER VALID MOVE

                    if (inCheck) {
                        //TODO: have to make sure that the king gets out of check... CPR...
                        //see above notes

                    }
                    //move the piece

                    //then after moving - check if the king is in check after that move
                    //Check - could put it in the king class? or make a check class
                    //TODO: STORE THE KINGS VALUE SO WE DONT HAVE TO CHECK EVERY SINGLE MOVE, UPDATE WHEN SUCCESSFUL KINGMOVE
                    //then write a check method where we check all attack vectors, cant just check the piece being moved bc of discoveries and other things
                    //change the incheck method appropriately

                }

                //if valid move
                //check if last move put king in check
                if (validMove) {
                    whoseMove = !whoseMove;
                }
                else {
                    System.out.println("Move was not a legal move! Try again!");
                }
            }
        }
    }

    static int[] convertMoveToSquare(char col, int row) {
        int[] return_array = new int[2];

        return_array[0] = (row - 8) * -1;

        if(col == 'a') {
            return_array[1] = 0;
        }
        else if(col == 'b') {
            return_array[1] = 1;
        }
        else if(col == 'c') {
            return_array[1] = 2;
        }
        else if(col == 'd') {
            return_array[1] = 3;
        }
        else if(col == 'e') {
            return_array[1] = 4;
        }
        else if(col == 'f') {
            return_array[1] = 5;
        }
        else if(col == 'g') {
            return_array[1] = 6;
        }
        else if(col == 'h') {
            return_array[1] = 7;
        }

        return return_array;
    }

    static void printBoard() {
        //prints the board
        //TODO: can play with background
        System.out.println(GREEN + "    a b c d e f g h " + WHITE);
        System.out.println(GREEN + "    ________________" + WHITE);

        int borderCounter = 8;
        for(int row = 0; row < board[0].length; row++) {

            System.out.print(GREEN + (borderCounter - row) + " | " + WHITE);

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
            //System.out.print(GREEN +  " | " + (borderCounter - row) + WHITE);
            System.out.println();

        }
        //end of it
        System.out.println(GREEN + "    ________________" + WHITE);
        System.out.println(GREEN + "    a b c d e f g h " + WHITE);

    }

    static String checkNotation(String move) {
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
