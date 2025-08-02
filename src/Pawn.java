import com.sun.tools.javac.Main;

public class Pawn extends Piece {

    boolean firstMove;

    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
        this.firstMove = true;
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {

        //make sure we are trying not to capture a piece that we should not be...
        if (MainCLI.board[row][col].p != null && MainCLI.board[row][col].p.type != PieceType.NONE) {
            return null;
            //need to wrap this because i want to avoid a null pointer error
        }

        int[] pieceCoordinates = new int[2];

        //COULD JUST CHECK IF THE PAWN IS ON 2ND RANK??? - THERE SHOULD BE A WAY TO HAVE AN ATTRIBUTE THO...
        //piece have attribute...
        //TODO:
        if (color == PieceColor.BLACK) {
            //check if two squares back
            //TODO; may want error checking here... - e3?
            //have to check one square back first
            if (MainCLI.board[row - 1][col].p.type == PieceType.PAWN && MainCLI.board[row - 1][col].p.color == PieceColor.BLACK) {
                pieceCoordinates[0] = row - 1;
                pieceCoordinates[1] = col;
                return pieceCoordinates;
            }

            if (MainCLI.board[row - 2][col].p.type == PieceType.PAWN && MainCLI.board[row - 2][col].p.color == PieceColor.BLACK) {
                if (MainCLI.board[row - 2][col].p.firstMove == true) {
                    pieceCoordinates[0] = row - 2;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
            }
        }
        else if (color == PieceColor.WHITE) {

            if (MainCLI.board[row + 1][col].p.type == PieceType.PAWN && MainCLI.board[row + 1][col].p.color == PieceColor.WHITE) {
                pieceCoordinates[0] = row + 1;
                pieceCoordinates[1] = col;
                return pieceCoordinates;
            }

            if (MainCLI.board[row + 2][col].p.type == PieceType.PAWN && MainCLI.board[row + 2][col].p.color == PieceColor.WHITE) {
                if (MainCLI.board[row + 2][col].p.firstMove == true) {
                    pieceCoordinates[0] = row + 2;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
            }
        }

        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color, char pieceCol) {
        int[] pieceCoordinates = new int[2];
        int pieceColValue = 0;

        if (MainCLI.board[row][col].p == null || MainCLI.board[row][col].p.type == PieceType.NONE) {
            return null;
            //need to wrap this because i want to avoid a null pointer error
        }
        else {
            //can't take a piece that is the same color as your piece
            if (MainCLI.board[row][col].p.color == color) {
                return null;
            }
        }

        if(pieceCol == 'a') {
            pieceColValue = 0;
        }
        else if(pieceCol == 'b') {
            pieceColValue = 1;
        }
        else if(pieceCol == 'c') {
            pieceColValue = 2;
        }
        else if(pieceCol == 'd') {
            pieceColValue = 3;
        }
        else if(pieceCol == 'e') {
            pieceColValue = 4;
        }
        else if(pieceCol == 'f') {
            pieceColValue = 5;
        }
        else if(pieceCol == 'g') {
            pieceColValue = 6;
        }
        else if(pieceCol == 'h') {
            pieceColValue = 7;
        }

        if (color == PieceColor.WHITE) {
            //value needs to be one more or one less to be able to capture as a pawn
            if (col - 1 == pieceColValue || col + 1 == pieceColValue) {
                //plus because down the board
                if (MainCLI.board[row + 1][pieceColValue].p.type == PieceType.PAWN && MainCLI.board[row + 1][pieceColValue].p.color == PieceColor.WHITE) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = pieceColValue;
                    return pieceCoordinates;
                }
            }
        }
        else if (color == PieceColor.BLACK) {
            if (col - 1 == pieceColValue || col + 1 == pieceColValue) {
                //plus because down the board
                if (MainCLI.board[row - 1][pieceColValue].p.type == PieceType.PAWN && MainCLI.board[row - 1][pieceColValue].p.color == PieceColor.WHITE) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = pieceColValue;
                    return pieceCoordinates;
                }
            }
        }

        return null;
    }
}
