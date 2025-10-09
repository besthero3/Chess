/**
* Driver class for the chess program. This is where input is gathered and other classes are called to check piece
* behavior.
 * Code flow: Initialize board and pieces, check if input is valid, check if piece can move there, see if king is in check,
 * check if move is valid for king being in check, then check if king can get out of check (is it checkmate)
* */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainCLI {

    //king positions are tracked to make other checks easier
    public static Square[][] board = new Square[8][8];
    public static int[] whiteKingPosition = new int[2];
    public static int[] blackKingPosition = new int[2];

    //game and turn state
    private static boolean gameOver = false;
    private static boolean inCheck = false;
    private static boolean whoseMove = true; //true = white's move, false = black's move

    //currentInCheck means if white or black is in check (depending on whose turn it is)
    private static boolean currentInCheck = false;
    private static boolean opponentCurrentInCheck = false;

    //Used for string printing
    private static final String BLACK = "\u001B[30m";
    private static final String WHITE = "\u001B[37m";
    private static final String GREEN = "\u001B[32m";

    /* TODO: Castling and move count implementations
    To be used for castling and move count implementations

    private static boolean whiteCanCastle = true;
    private static boolean blackCanCastle = true;
    private static int whiteMoveCount = 0;
    private static int blackMoveCount = 0;

    Note: may need more states, these track if a king has moved but castling has
    temporary situations that must also be covered
    */

    public static void main(String[] args) {
    try {
        //initializes every square to be empty
        for(int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[0].length; col++) {

                //empty square with a Piece of type NoPiece on it
                //avoids unnecessary null comparisons
                board[row][col] = new Square(new NoPiece(), true);

            }
        }

        createBoard();

        //the game is played until it is marked as over - from checkmate
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

            //notationCheck returns a result string that determines if the move is valid notation or not
            String notationCheck = checkNotation(move);

            //if the check is invalid
            if (!notationCheck.equals("Invalid")) {

                //assumes captures and valid move are false
                boolean validMove = false;
                boolean captures = false;

                //resets the square coordinates every time
                int[] squareCoordinates = new int[2];

                //resets piece coordinates every move
                int[] pieceCoordinates = null;

                //checks if a move includes captures and records that
                if (notationCheck.contains("takes")) {
                    captures = true;
                }

                //pawn
                if (notationCheck.contains("Pawn")) {

                    //example: e4
                    if (move.length() == 2) {
                        squareCoordinates = convertMoveToSquare(move.charAt(0), Character.getNumericValue(move.charAt(1)) );
                    }

                    //exf4 - gets f4 for square to move to
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)));
                    }

                    //white's move
                    if (whoseMove) {

                        //capturing
                        if (captures) {
                            Pawn pawn = new Pawn(PieceColor.WHITE);
                            pieceCoordinates = pawn.isValidCapture(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, move.charAt(0)); //check if the format is right
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
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)) );
                    }

                    //Rxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)) );
                    }

                    if (whoseMove) {
                        Rook rook = new Rook(PieceColor.WHITE);
                        pieceCoordinates = rook.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.WHITE, captures); //check if the format is right
                    }
                    else {
                        Rook rook = new Rook(PieceColor.BLACK);
                        pieceCoordinates = rook.isValidMove(squareCoordinates[0],squareCoordinates[1], PieceColor.BLACK, captures); //check if the format is right
                    }

                    //if piece coordinates returns null then it is not a valid move
                    if (pieceCoordinates != null) {
                        validMove = true;
                    }
                }
                else if (notationCheck.contains("Knight")) {

                    //Nf3
                    if (move.length() == 3) {
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)));
                    }

                    //Nxf3
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)));
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
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)) );
                    }

                    //Bxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)) );
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
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)) );
                    }

                    //Qxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)) );
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
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)) );
                    }

                    //Kxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)) );
                    }

                    if (whoseMove) {
                        King king = new King(PieceColor.WHITE);
                        boolean inCheckKing = king.check(squareCoordinates[0], squareCoordinates[1], PieceColor.WHITE); //check if the format is right

                        if (!inCheckKing) {
                            pieceCoordinates = king.isValidMove(squareCoordinates[0], squareCoordinates[1], PieceColor.WHITE, captures);
                        }
                    }
                    else {
                        King king = new King(PieceColor.BLACK);
                        boolean inCheckKing = king.check(squareCoordinates[0], squareCoordinates[1], PieceColor.BLACK); //check if the format is right

                        if (!inCheckKing) {
                            pieceCoordinates = king.isValidMove(squareCoordinates[0], squareCoordinates[1], PieceColor.BLACK, captures);
                        }
                    }

                    if (pieceCoordinates != null) {
                        validMove = true;
                    }

                }


                //a move is valid - the chosen piece can move to a square
                if (validMove) {

                    King finalKingWhite = new King(PieceColor.WHITE);
                    King finalKingBlack = new King(PieceColor.BLACK);

                    if (whoseMove) {

                        //checks if the white king is currently in check - before the move
                        currentInCheck = finalKingWhite.check(whiteKingPosition[0], whiteKingPosition[1], PieceColor.WHITE);

                        //if the king is currently in check - make sure the move gets the king out of check
                        if (currentInCheck) {

                            //Find the checking pieces type and color
                            int[] checkingPieceCoordinates = finalKingWhite.checkPiece(whiteKingPosition[0], whiteKingPosition[1], PieceColor.WHITE);
                            PieceType checkingPieceType = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type;
                            PieceColor checkingPieceColor = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color;

                            PieceType movingPieceType = board[pieceCoordinates[0]][pieceCoordinates[1]].p.type;

                            //checks if the checking piece can be captured
                            boolean captureCheckingPiece = finalKingWhite.captureOutOfCheck(squareCoordinates[0], squareCoordinates[1], movingPieceType, checkingPieceCoordinates[0], checkingPieceCoordinates[1], PieceColor.WHITE);

                            boolean moveKing = false;

                            //move does not capture the checking piece
                            if (!captureCheckingPiece) {

                                //check if we are moving the king
                                if (movingPieceType == PieceType.KING) {

                                    //if we are then see if the king is moved out of check
                                    moveKing = finalKingWhite.moveOutOfCheck(squareCoordinates[0],squareCoordinates[1],PieceColor.WHITE);
                                }

                                //can't capture, can't move, have to block
                                if (!moveKing) {

                                    boolean block = finalKingWhite.blockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1], checkingPieceColor,
                                            checkingPieceType, squareCoordinates[0], squareCoordinates[1], pieceCoordinates[0], pieceCoordinates[1],
                                            movingPieceType, PieceColor.WHITE, whiteKingPosition[0],whiteKingPosition[1]);

                                    //our move does not get out of check - so it is not valid
                                    if (!block) {
                                        validMove = false;
                                    }

                                }

                            }
                        }


                        //TODO: ENSURE THAT AFTER A MOVE THE KING IS NOT IN CHECK - create a temp board and check if the new move means the king is in check
                        //if the king is not in check - or the king is in check, and we are making a valid move
                        //then change the board state and update the King's position
                        if (!currentInCheck || (currentInCheck && validMove)) {

                            //square we are moving to has new piece
                            board[squareCoordinates[0]][squareCoordinates[1]] = board[pieceCoordinates[0]][pieceCoordinates[1]];

                            //square we are moving from is now empty
                            board[pieceCoordinates[0]][pieceCoordinates[1]] = new Square(new NoPiece(), true);

                            //king - update piece coordinates
                            if (board[squareCoordinates[0]][squareCoordinates[1]].p.type == PieceType.KING) {
                                whiteKingPosition[0] = squareCoordinates[0];
                                whiteKingPosition[1] = squareCoordinates[1];
                            }
                        }

                        //then need to check if black king is in check so it can be recorded and the static variable can be changed
                        opponentCurrentInCheck = finalKingBlack.check(blackKingPosition[0], blackKingPosition[1], PieceColor.BLACK);

                    }

                    //black - check if our move gets out of check
                    else  {

                        currentInCheck = finalKingBlack.check(blackKingPosition[0], blackKingPosition[1], PieceColor.BLACK);

                        //in check - make sure move gets out of check
                        if (currentInCheck) {

                            //get the checking piece first
                            int[] checkingPieceCoordinates = finalKingBlack.checkPiece(blackKingPosition[0],blackKingPosition[1],PieceColor.BLACK);
                            PieceType checkingPieceType = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type;
                            PieceColor checkingPieceColor = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color;

                            PieceType movingPieceType = board[pieceCoordinates[0]][pieceCoordinates[1]].p.type;

                            //see if our move captures the checking piece
                            boolean captureCheckingPiece = finalKingBlack.captureOutOfCheck(squareCoordinates[0], squareCoordinates[1],
                                    movingPieceType, checkingPieceCoordinates[0], checkingPieceCoordinates[1], PieceColor.BLACK);


                            boolean moveKing = false;

                            //our move does not capture the checking piece
                            if (!captureCheckingPiece) {
                                if (movingPieceType == PieceType.KING) {

                                    moveKing = finalKingBlack.moveOutOfCheck(squareCoordinates[0],squareCoordinates[1],PieceColor.BLACK);
                                }

                                //can't capture, can't move, have to block
                                if (!moveKing) {

                                    boolean block = finalKingBlack.blockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1], checkingPieceColor,
                                            checkingPieceType, squareCoordinates[0], squareCoordinates[1], pieceCoordinates[0], pieceCoordinates[1],
                                            movingPieceType, PieceColor.BLACK, blackKingPosition[0],blackKingPosition[1]);

                                    if (!block) {
                                        validMove = false;
                                    }

                                }
                            }
                        }

                        //check that our move is valid and updates the board if it is
                        if (!currentInCheck || (currentInCheck && validMove)) {

                            board[squareCoordinates[0]][squareCoordinates[1]] = board[pieceCoordinates[0]][pieceCoordinates[1]];

                            //square we are moving from is now empty
                            board[pieceCoordinates[0]][pieceCoordinates[1]] = new Square(new NoPiece(), true);

                            //king - update piece coordinates
                            if (board[squareCoordinates[0]][squareCoordinates[1]].p.type == PieceType.KING) {
                                blackKingPosition[0] = squareCoordinates[0];
                                blackKingPosition[1] = squareCoordinates[1];
                            }
                        }

                        //then need to check if black king is in check so it can be recorded and the static variable can be changed
                        opponentCurrentInCheck = finalKingWhite.check(whiteKingPosition[0], whiteKingPosition[1], PieceColor.WHITE);
                    }
                }

                if (validMove) {

                    //stores whether our opponent is in check
                    if (opponentCurrentInCheck) {

                        int[] checkingPieceCoordinates = new int[2];
                        King checkmateKingBlack = new King(PieceColor.BLACK);
                        King checkmateKingWhite = new King(PieceColor.WHITE);

                        //white's move - then black king may be in checkmate...
                        if (whoseMove) {
                            checkingPieceCoordinates = checkmateKingBlack.checkPiece(blackKingPosition[0],blackKingPosition[1],PieceColor.BLACK);
                        }
                        //black's move - then white's king may be in checkmate
                        else {
                            checkingPieceCoordinates = checkmateKingWhite.checkPiece(whiteKingPosition[0],whiteKingPosition[1], PieceColor.WHITE);
                        }

                        //piece coordinates before, changed to checking piece coordinates
                        if (checkingPieceCoordinates != null) {

                            //white piece is doing the checking
                            if (whoseMove) {

                                //Checks if another piece can capture white's checking piece
                                //essentially checks if that square is in check
                                boolean captureCheckingPiece = checkmateKingBlack.check(checkingPieceCoordinates[0],checkingPieceCoordinates[1],PieceColor.WHITE);

                                boolean kingCaptures = checkmateKingBlack.canCaptureOutOfCheck(blackKingPosition[0], blackKingPosition[1], checkingPieceCoordinates[0],
                                        checkingPieceCoordinates[1], PieceColor.BLACK);

                                //CPR - can't capture
                                if (!captureCheckingPiece && !kingCaptures) {

                                    //checks if black's king can move
                                    if (!checkmateKingBlack.canMove(blackKingPosition[0],blackKingPosition[1],PieceColor.BLACK)) {

                                        //Checks if black can block the checking piece
                                        if (!checkmateKingBlack.canblockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1],
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color,
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type,
                                                blackKingPosition[0], blackKingPosition[1])) {

                                            System.out.println("Black has been checkmated");
                                            gameOver = true;
                                        }
                                    }
                                }
                            }
                            //black piece is doing the checking
                            else {
                                //check if we can capture the checking piece
                                boolean captureCheckingPiece = checkmateKingWhite.check(checkingPieceCoordinates[0],checkingPieceCoordinates[1],PieceColor.BLACK);

                                boolean kingCaptures = checkmateKingWhite.canCaptureOutOfCheck(whiteKingPosition[0], whiteKingPosition[1], checkingPieceCoordinates[0],
                                        checkingPieceCoordinates[1], PieceColor.WHITE);

                                //CPR - can't capture
                                if (!captureCheckingPiece && !kingCaptures) {

                                    //can't move
                                    if (!checkmateKingBlack.canMove(whiteKingPosition[0],whiteKingPosition[1], PieceColor.WHITE)) {

                                        //can't block
                                        if (!checkmateKingWhite.canblockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1],
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color,
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type,
                                                whiteKingPosition[0], whiteKingPosition[1])) {

                                            System.out.println("White has been checkmated, except blocking");
                                            gameOver = true;

                                        }
                                    }
                                }
                            }
                        }
                    }

                    whoseMove = !whoseMove;
                }
                else {

                    if (currentInCheck) {
                        System.out.println("You are in check! Move was not legal! Try again!");
                    }
                    else {
                        System.out.println("Move was not a legal move! Try again!");
                    }

                }
            }
        }


        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The array went out of bounds");
        }
    }

    /**
     * convertMoveToSquare converts algebraic notation to array coordinates
     * @param row represents a rank in Chess, 1-8 are expected values
     * @param col represents a file in Chess, a through h are expected values
     * @return a 2D array with a valid coordinate for the corresponding square (row, column)
     * */
    static int[] convertMoveToSquare(char col, int row) {
        int[] return_array = new int[2];


        /* Notation to row coordinate conversion
         * 8 = 0
         * 7 = 1
         * 6 = 2
         * 5 = 3
         * 4 = 4
         * 3 = 5
         * 2 = 6
         * 1 = 7
         * */

        //converts a row to a coordinate row
        return_array[0] = (row - 8) * -1;

        //checks if each column is a certain value
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

    /**
    * printBoard prints out an updated board every turn
    * */
    static void printBoard() {

        System.out.println(GREEN + "    a b c d e f g h " + WHITE);
        System.out.println(GREEN + "    ________________" + WHITE);

        //used to track the current rank(row)
        int borderCounter = 8;

        for(int row = 0; row < board.length; row++) {

            //prints the rank(row) labels
            System.out.print(GREEN + (borderCounter - row) + " | " + WHITE);

            //goes through each square and prints a value based on the piece
            for(int col = 0; col < board[0].length; col++) {

                if (board[row][col].p.type == PieceType.NONE) {

                    System.out.print(WHITE + "  " + WHITE);

                }

                else if (board[row][col].p.type == PieceType.ROOK) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "R " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "R " + WHITE);
                    }

                }

                else if (board[row][col].p.type == PieceType.KNIGHT) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "N " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "N " + WHITE);
                    }

                }

                else if (board[row][col].p.type == PieceType.BISHOP) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "B " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "B " + WHITE);
                    }

                }

                else if (board[row][col].p.type == PieceType.QUEEN) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "Q " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "Q " + WHITE);
                    }

                }

                else if (board[row][col].p.type == PieceType.KING) {

                    //white
                    if (board[row][col].p.color == PieceColor.WHITE) {
                        System.out.print(WHITE + "K " + WHITE);
                    }
                    //black
                    else {
                        System.out.print(BLACK + "K " + WHITE);
                    }

                }

                else if (board[row][col].p.type == PieceType.PAWN) {

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

        //bottom of board
        System.out.println(GREEN + "    ________________" + WHITE);
        System.out.println(GREEN + "    a b c d e f g h " + WHITE);

    }

    /**
     * checkNotation checks if notation is valid, what piece is being moved, and if the move is a capture
     * @param move - move in algebraic notation which is input from the user
     * @return a string which either says the move is invalid or includes the piece being moved and/or that the
     *      spiece is capturing
     * */
    static String checkNotation(String move) {
        String returnString = "";

        //sets patterns for acceptable values in a move
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

        //checks which piece it is and sets the return string to the piece
        if(move.charAt(0) == 'R') {
            returnString = "Rook";
        }
        else if(move.charAt(0) == 'N') {
            returnString = "Knight";
        }
        else if(move.charAt(0) == 'B') {
            returnString = "Bishop";
        }
        else if(move.charAt(0) == 'Q') {
            returnString = "Queen";
        }
        else if(move.charAt(0) == 'K') {
            returnString = "King";
        }

        //checks if move is castling
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

        //a pawn is not a piece, so checks if the piece does not exist
        if(!pieceExists) {

            //captures are always multiple moves... (4)
            String first = String.valueOf(move.charAt(0));

            //checks if the first square is a column because then it is a pawn move
            Matcher pawn = columnPattern.matcher(first);
            boolean isPawnMove = pawn.find();

            //TODO: add stricter checks for order of items with pawns
            if (isPawnMove) {
                //set equal to pawn
                returnString = "Pawn";
            }
            else {
                System.out.println("The notation was invalid");
                return "Invalid";
            }
        }

        if (move.contains("x")) {
            if (move.charAt(1) == 'x')
            {
                returnString += " takes";
            }
            else {
                System.out.println("Invalid capture: x for captures must be the second letter");
                return "Invalid";
            }

        }

        return returnString;
    }

    /**
     * createBoard initializes the starting pieces to their typical starting squares
     * */
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

        //initializes starting position for the kings
        whiteKingPosition[0] = 7;
        whiteKingPosition[1] = 4;

        blackKingPosition[0] = 0;
        blackKingPosition[1] = 4;

    }
}


