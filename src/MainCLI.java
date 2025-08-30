import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainCLI {

    //Todo: NEED TO FIX TYPE - None is a better type than making everything NULL

    boolean whiteCanCastle = true;
    boolean blackCanCastle = true;
    static int whiteMoveCount = 0;
    static int blackMoveCount = 0;
    static boolean gameOver = false;
    static boolean inCheck = false;
    static boolean whoseMove = true;

    //currentInCheck means if white or black is in check
    static boolean currentInCheck = false;
    static boolean opponentCurrentInCheck = false;

    public static final String BLACK = "\u001B[30m";
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";

    //board has square
    //TODO: could set 0,0 to be a1
    static Square[][] board = new Square[8][8];

    static int[] whiteKingPosition = new int[2];
    static int[] blackKingPosition = new int[2];

    public static void main(String[] args) {

        //right now this does column length then row length
        //board[0] is number of columns
        for(int row = 0; row < board[0].length; row++) {

            for (int col = 0; col < board.length; col++) {
                //TODO: add this no piece to everywhere
                board[row][col] = new Square(new NoPiece(), true);

            }
        }
        createBoard();
        //row, col
        whiteKingPosition[0] = 7;
        whiteKingPosition[1] = 4;

        blackKingPosition[0] = 0;
        blackKingPosition[1] = 4;

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

                //reset them every time
                pieceCoordinates = null;

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
                        squareCoordinates = convertMoveToSquare(move.charAt(0), Character.getNumericValue(move.charAt(1)) );
                    }

                    //exf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)) );
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
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)) );
                    }

                    //Rxf4
                    if (move.length() == 4 && captures) {
                        squareCoordinates = convertMoveToSquare(move.charAt(2), Character.getNumericValue(move.charAt(3)) );
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
                        //TODO: NF3 - CHAR AT IS GETTING THE CHARACTER AT THAT POINT
                        squareCoordinates = convertMoveToSquare(move.charAt(1), Character.getNumericValue(move.charAt(2)));
                    }

                    //Bxf4
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

                    //Bxf4
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

                //call different class to check move validation

                //TODO: the moves are good - now need to check every move for check
                //TODO: then write a move function if not in check...
                //TODO: can just call check

                //valid move is completed...
                if (validMove) {
                    //TODO: if valid pawn move then need to set the first move to false!!!!!!!!!
                    //the move before this one says if the king is in check

                    //TODO: could make a copy of the board and check if the copy is in check ...  IF VALID MOVE
                    //IF IT IS - THEN CHANGE THE ORIGINAL BOARD TO HAVE THE MOVE ON IT....
                    //SQUARE COORDINATES
                    //PIECE COODRIANETS - SET THE PIECE EQUAL TO SQUARE COORDINATE, AND SET PIECE EQUAL TO NULL
                    //TODO: MAYBE DO THIS EITHER WAY - DO I NEED TO DO IT TWICE? UNDER IN CHECK AND UNDER VALID MOVE

                    //this means before our turn the king is in check... but we also need to see if king is in check after move
                    King finalKingWhite = new King(PieceColor.WHITE);
                    King finalKingBlack = new King(PieceColor.BLACK);

                    /*
                    if (inCheck) {
                        //TODO: have to make sure that the king gets out of check... CPR...
                        //see above notes

                    }
                    */

                    //Square[][] boardCopy = board;
                    //TODO: BOARD IS STILL CORRECT HERE
                    //printBoard();

                    //TODO: these two lines are what breaks it
                    //square we are moving to equals the square of the piece we moved from...
                    //boardCopy[squareCoordinates[0]][squareCoordinates[1]].p = boardCopy[pieceCoordinates[0]][pieceCoordinates[1]].p;
                    //boardCopy[squareCoordinates[0]][squareCoordinates[1]].empty = boardCopy[pieceCoordinates[0]][pieceCoordinates[1]].empty;


                    //TODO: ONE THING TO NOTE IS THAT WE NEED TO BE CHANGING THIS? - THIS IS ONLY CASE WHERE THE KING IS PLACED ON WRONG SQUARE BUT VALUES ARE CHANGED
                    //TODO: MAYBE TRY ITERATING THROUGH ALL THE VALUES
                    //board[2][2].p = new King(PieceColor.WHITE);
                    //board[2][2].empty = false;
                    //board[squareCoordinates[0]][squareCoordinates[1]].p.type = PieceType.KING;
                    //board[squareCoordinates[0]][squareCoordinates[1]].p.color = PieceColor.WHITE;

                    //TODO: CHANGE BACK TO BOARD COPY, FIX KING CHECK METHOD TO USE BOARD COPY
                    //todo: FIGURE OUT WHY WE ARE MOVING THE H PAWN INSTEAD OF THE E PAWN

                    //board[squareCoordinates[0]][squareCoordinates[1]] = board[pieceCoordinates[0]][pieceCoordinates[1]];

                    //square we are moving from is now empty
                    //board[pieceCoordinates[0]][pieceCoordinates[1]] = new Square(new NoPiece(), true);


                    //square we are moving from is now empty
                    //boardCopy[pieceCoordinates[0]][pieceCoordinates[1]] = new Square(new NoPiece(), true);
                    //boardCopy[pieceCoordinates[0]][pieceCoordinates[1]].p = new NoPiece();
                    //boardCopy[pieceCoordinates[0]][pieceCoordinates[1]].empty = true;

                    //printBoard();

                    //TODO: check
                    //white
                    if (whoseMove) {

                        //need to track the white and black king's placements at the start of the game and update them here


                        //White's turn...
                        //check if the white king is in check
                        //if it is then revert the move and say this move is illegal - king in check
                        //if it is not, then say, move is legal and make the move

                        //TODO - update king position
                        //TODO: THIS IS NOT USING THE BOARD WITH THE MOVE ON IT...!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        currentInCheck = finalKingWhite.check(whiteKingPosition[0], whiteKingPosition[1], PieceColor.WHITE);

                        if (currentInCheck) { //in check - make sure move gets out of check

                            //get the checking piece first
                            int[] checkingPieceCoordinates = finalKingWhite.checkPiece(whiteKingPosition[0], whiteKingPosition[1], PieceColor.WHITE);
                            PieceType checkingPieceType = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type;
                            PieceColor checkingPieceColor = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color;
                            //checkingPieceType = PieceType.KNIGHT;

                            PieceType movingPieceType = board[pieceCoordinates[0]][pieceCoordinates[1]].p.type;

                            //Pass in checking piece coordinates... then check if any of the moves match our move?
                            //pass in our move with the type of piece as well...
                            //pass in black
                            //pass in square coordinates that the piece is moving to...
                            boolean captureCheckingPiece = finalKingWhite.captureOutOfCheck(squareCoordinates[0], squareCoordinates[1], movingPieceType, checkingPieceCoordinates[0], checkingPieceCoordinates[1], PieceColor.WHITE);

                            boolean moveKing = false;
                            //our move does not capture the checking piece
                            if (!captureCheckingPiece) {
                                if (movingPieceType == PieceType.KING) {
                                    //then can move the king
                                    //TODO: just have to check if the square coordinates are in check...
                                    //TODO: if they are - not a valid move - if not then its valid...
                                    //pass the piece in
                                    moveKing = finalKingWhite.moveOutOfCheck(squareCoordinates[0],squareCoordinates[1],PieceColor.WHITE);
                                }
                                else {
                                    moveKing = false;
                                }

                                //can't move
                                //can't capture
                                //HAVE TO BLOCK
                                if (!moveKing) {


                                    //int checkingPieceRow, int checkingPieceCol, PieceColor checkingPieceColor,
                                    //PieceType checkingPieceType, int blockingSquareRow, int blockingSquareCol, int movingPieceRow,
                                    //int movingPieceCol, PieceType movingPieceType, PieceColor movingPieceColor) {
                                    boolean block = finalKingWhite.blockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1], checkingPieceColor,
                                            checkingPieceType, squareCoordinates[0], squareCoordinates[1], pieceCoordinates[0], pieceCoordinates[1],
                                            movingPieceType, PieceColor.WHITE, whiteKingPosition[0],whiteKingPosition[1]);

                                    if (!block) {
                                        //We know from the end of the execution that it is not checkmate
                                        //we could also change the execution? think about it
                                        //pass in check to the start and if in check then check if it is mate
                                        validMove = false;
                                    }

                                }

                                //TODO: figure out if our move blocks checks - that is the one that will need checking
                            }
                        }

                        //CHeck the board TODO!!!
                        if (!currentInCheck || (currentInCheck && validMove)) {
                            //TODO make the move

                            //TODO: SMTH IS WRONG WITH THE COORDINATES I THINK FOR COLUMNS - NONE ARE WORKING RIGHT
                            board[squareCoordinates[0]][squareCoordinates[1]] = board[pieceCoordinates[0]][pieceCoordinates[1]];

                            //square we are moving from is now empty
                            board[pieceCoordinates[0]][pieceCoordinates[1]] = new Square(new NoPiece(), true);

                            //king - update piece coordinates
                            if (board[squareCoordinates[0]][squareCoordinates[1]].p.type == PieceType.KING) {
                                whiteKingPosition[0] = squareCoordinates[0];
                                whiteKingPosition[1] = squareCoordinates[1];
                            }
                        }
                        //we are in check
                        //else {

                            //check if our move is one of the valid ones?

                        //    validMove = false;
                        //}

                        //then need to check if black king is in check so it can be recorded and the static vairbale can be changed
                        opponentCurrentInCheck = finalKingBlack.check(blackKingPosition[0], blackKingPosition[1], PieceColor.BLACK);

                        //TODO: set valid move equal to false if the move is not valid!!!
                    }
                    //black
                    else  {
                        //see above

                        currentInCheck = finalKingBlack.check(blackKingPosition[0], blackKingPosition[1], PieceColor.BLACK);

                        //TODO: BIG FIX!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //TODO: THIS IS WHERE WE CURRENTLY ARE!!!!!
                        //check if our move gets out of check -
                        //then check if there is a move that is possible
                        //black is in check
                        if (currentInCheck) { //in check - make sure move gets out of check

                            //get the checking piece first
                            int[] checkingPieceCoordinates = finalKingBlack.checkPiece(blackKingPosition[0],blackKingPosition[1],PieceColor.BLACK);
                            PieceType checkingPieceType = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type;
                            PieceColor checkingPieceColor = board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color;
                            //checkingPieceType = PieceType.KNIGHT;

                            PieceType movingPieceType = board[pieceCoordinates[0]][pieceCoordinates[1]].p.type;

                            //Pass in checking piece coordinates... then check if any of the moves match our move?
                            //pass in our move with the type of piece as well...
                            //pass in black
                            //pass in square coordinates that the piece is moving to...
                            boolean captureCheckingPiece = finalKingBlack.captureOutOfCheck(squareCoordinates[0], squareCoordinates[1], movingPieceType, checkingPieceCoordinates[0], checkingPieceCoordinates[1], PieceColor.BLACK);

                            boolean moveKing = false;
                            //our move does not capture the checking piece
                            if (!captureCheckingPiece) {
                                if (movingPieceType == PieceType.KING) {
                                    //then can move the king
                                    //TODO: just have to check if the square coordinates are in check...
                                    //TODO: if they are - not a valid move - if not then its valid...
                                    //pass the piece in
                                    moveKing = finalKingBlack.moveOutOfCheck(squareCoordinates[0],squareCoordinates[1],PieceColor.BLACK);
                                }
                                else {
                                    moveKing = false;
                                }

                                //can't move
                                //can't capture
                                //HAVE TO BLOCK
                                if (!moveKing) {


                                    //int checkingPieceRow, int checkingPieceCol, PieceColor checkingPieceColor,
                                    //PieceType checkingPieceType, int blockingSquareRow, int blockingSquareCol, int movingPieceRow,
                                    //int movingPieceCol, PieceType movingPieceType, PieceColor movingPieceColor) {
                                    boolean block = finalKingBlack.blockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1], checkingPieceColor,
                                            checkingPieceType, squareCoordinates[0], squareCoordinates[1], pieceCoordinates[0], pieceCoordinates[1],
                                            movingPieceType, PieceColor.BLACK, blackKingPosition[0],blackKingPosition[1]);

                                    if (!block) {
                                        //We know from the end of the execution that it is not checkmate
                                        //we could also change the execution? think about it
                                        //pass in check to the start and if in check then check if it is mate
                                        validMove = false;
                                    }

                                }

                                //TODO: figure out if our move blocks checks - that is the one that will need checking
                            }
                        }

                        //if current in check is true
                        //check if our move gets out of check...
                        //if it does then make the move

                        //if it does not then either declare the move invalid or declare checkmate
                        //(how to do this):
                        //arraylist returning coordinates of pieces, as well as type of piece???
                        //Capturing - square moved to, piece that can capture
                        //moving - square moved to and then we verify that the king is moving there
                        //blocking - square moved to, piece that can move there

                        //functions to check if checkmate exists
                        //these are already written partially below
                        //Could pass in the function the type of piece to narrow down legal moves
                        //so bishop passed in - only check legal bishop moves with the bishop... TODO

                        //HAVE SAME THING AS CURRENTLY DOING BUT ADD TO IT
                        //if opponent in check - check if they are getting mated
                        //if they are then end the game - if they are not then let the game play on
                        //then on the current turn do the above checks to make sure that the current person can
                        //make a move to get out of check
                        //this way we fix the current problem of getting out of check with checking for mate

                        //TODO: MAKE SURE THIS TRIGGERS!!! EXECUTION ORDER
                        //either we are not in check so its fine
                        //or we are in check and need to make sure the move is valid
                        if (!currentInCheck || (currentInCheck && validMove)) {
                            //TODO make the move!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                            ////TODO: here!!!!!!!!!!!!
                            board[squareCoordinates[0]][squareCoordinates[1]] = board[pieceCoordinates[0]][pieceCoordinates[1]];

                            //square we are moving from is now empty
                            board[pieceCoordinates[0]][pieceCoordinates[1]] = new Square(new NoPiece(), true);

                            //king - update piece coordinates
                            if (board[squareCoordinates[0]][squareCoordinates[1]].p.type == PieceType.KING) {
                                blackKingPosition[0] = squareCoordinates[0];
                                blackKingPosition[1] = squareCoordinates[1];
                            }
                        }
                        //we are in check
                        //else {

                            //TODO: check if our move is one of the valid ones
                            //if it is - enable valid moves...
                            //TODO: if we are still in check then enable a still in check flag?
                            //
                            //validMove = false;
                        //}

                        //then need to check if black king is in check so it can be recorded and the static vairbale can be changed
                        opponentCurrentInCheck = finalKingWhite.check(whiteKingPosition[0], whiteKingPosition[1], PieceColor.WHITE);
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
                //TODO: make sure to adjust this with variables above...

                //TODO: only case where move isnt valid from recent stuff is gonna be the move not getting out of check

                //TODO - SEE EXECUTION PATH OF CODE
                //execution flow!!! - e4 f6 Qh5+
                //other king in check above gets evaluated
                //down here we check if the move checkmates the other king
                //if it does - game over
                //if it doesnt - game continues
                //above makes sure that you get out of check - otherwise invalid move
                //keeps evaluating moves to see if other king in checkmate
                //TODO: mainly need to write a blcoking function and make sure everything resets in iterations...
                if (validMove) {
                    //TODO:
                    if (opponentCurrentInCheck) {
                        //FIND OUT WHAT PIECE IT IS
                        //row, col, piececolor color
                        //King finalKingWhite = new King(PieceColor.WHITE);
                        //King finalKingBlack = new King(PieceColor.BLACK);
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

                        //TODO: means there is a checking piece
                        //change this to checking piece coordinates
                        if (pieceCoordinates != null) {
                            //white piece is doing the checking...
                            if (whoseMove) {
                                //Checks if another piece can't capture it
                                boolean captureCheckingPiece = checkmateKingBlack.check(checkingPieceCoordinates[0],checkingPieceCoordinates[1],PieceColor.WHITE);

                                //checks if the king can't capture it
                                //TODO: ALSO CAN CHECK THESE COORDINATES TO SEE IF MOVE BEING EXECUTED WORKS...
                                //TODO: DO THE SAME FOR THE OTHER things - blocking and check if that is the move
                                //TODO: capture and move check against and disable check...
                                //TODO: - this code does not run if the king is in check so i need a way around it...
                                //looking from the white checking piece to the black checking piece
                                //int[] kingCaptureCheckingPiece = checkmateKingBlack.isValidMove(checkingPieceCoordinates[0],checkingPieceCoordinates[1],PieceColor.WHITE,true);

                                //TODO: make sure we are updating king position
                                boolean kingCaptures = checkmateKingBlack.canCaptureOutOfCheck(blackKingPosition[0], blackKingPosition[1], checkingPieceCoordinates[0],
                                        checkingPieceCoordinates[1], PieceColor.BLACK);

                                //should call a modified version of can we capture the piece on that square...
                                //have checking piece coordinates
                                //check if (kingRow + | - 1 = checkRow) && (kingCol + | - 1 = checkCol)
                                //that checks if the king is next to it -
                                //then check if the checking piece square in check...
                                //if it is then put false, if not then put true

                                //CPR - can't capture
                                if (!captureCheckingPiece && !kingCaptures) {
                                    //TODO: need to do another board check - to make sure that the piece is pinned
                                    //TODO: complete when the other board check is added

                                    //have to check if the king can move
                                    //write a can move function, is there a square that is empty, not in check, around the king
                                    //use current check function - write it in the king class.
                                    //black king in check
                                    if (!checkmateKingBlack.canMove(blackKingPosition[0],blackKingPosition[1],PieceColor.BLACK)) {

                                        if (!checkmateKingBlack.canblockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1],
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color,
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type,
                                                blackKingPosition[0], blackKingPosition[1])) {
                                            System.out.println("Black has been checkmated");

                                            //TODO: FIX CHECK FUNCTION - make a new one with pawn moves instead of captures
                                            //basically just call pawn can move inside the check function

                                        }


                                    }
                                }
                            }
                            //black piece is doing the checking
                            else {
                                boolean captureCheckingPiece = checkmateKingWhite.check(checkingPieceCoordinates[0],checkingPieceCoordinates[1],PieceColor.BLACK);

                                //int[] kingCaptureCheckingPiece = checkmateKingBlack.isValidMove(checkingPieceCoordinates[0],checkingPieceCoordinates[1],PieceColor.BLACK,true);

                                //TODO: make sure we are updating king position
                                boolean kingCaptures = checkmateKingWhite.canCaptureOutOfCheck(whiteKingPosition[0], whiteKingPosition[1], checkingPieceCoordinates[0],
                                        checkingPieceCoordinates[1], PieceColor.WHITE);
                                //CPR - can't capture
                                //TODO: CAPTURE ALGO IS BROKEN - just need to check whether the piece can be captured - incuding by king
                                if (!captureCheckingPiece && !kingCaptures) {
                                    //TODO: need to do another board check - to make sure that the piece is pinned
                                    //TODO: complete when the other board check is added

                                    if (!checkmateKingBlack.canMove(whiteKingPosition[0],whiteKingPosition[1], PieceColor.WHITE)) {

                                        if (!checkmateKingWhite.canblockCheck(checkingPieceCoordinates[0], checkingPieceCoordinates[1],
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.color,
                                                board[checkingPieceCoordinates[0]][checkingPieceCoordinates[1]].p.type,
                                                whiteKingPosition[0], whiteKingPosition[1])) {
                                            System.out.println("White has been checkmated, except blocking");

                                        }



                                    }


                                }
                            }
                        }



                    }

                    whoseMove = !whoseMove;
                }
                else {
                    //TODO: IF THE LAST MOVE PUT US IN CHECK WE NEED TO MAKE SURE THAT OUR MOVE FIXES IT
                    //TODO: if it does then its a valid move
                    if (currentInCheck) {
                        System.out.println("You are in check! Move was not legal! Try again!");
                    }
                    else {
                        System.out.println("Move was not a legal move! Try again!");
                    }

                }
            }
        }

        //todo add checkmate
    }

    static int[] convertMoveToSquare(char col, int row) {
        int[] return_array = new int[2];

        //Nf3 = 3 means
        //could do number by number conversion...
        return_array[0] = (row - 8) * -1;

        /* Notation = row
        * 8 = 0
        * 7 = 1
        * 6 = 2
        * 5 = 3
        * 4 = 4
        * 3 = 5
        * 2 = 6
        * 1 = 7
        *
        *
        *
        *
        *
        * */

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
                if (board[row][col].p.type == PieceType.NONE) { //todo
                    System.out.print(WHITE + "  " + WHITE);
                }
                //print the string representation of the board
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

    //

    //TODO: need to write checkmate code!!!


    //TODO: ENSURE THAT AFTER A MOVE THE KING IS NOT IN CHECK
    //todo: new temp board version - check if the new move is fine...


}


