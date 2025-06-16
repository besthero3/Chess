public class King extends Piece {

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}

//if king wants to move to a square or capture
//check if that square is in check
//iterate in each direction to find a piece that could check...
//opposite color and can move means the king is in check
//if that squarer is in check then cant move or capture to it
//have to do all nine directions and knight moves
//make sure to check board boundaries
//check method should take a square
//move method should call chekc method and check if king is aorund the 9 squares
//todo track king position in MainCLI class - so then can just know where it is...

//For checking for check on each turn
//check if move is valid first, then make a copy of the board with the move on it...
//pass an array of type squares to function
//modify that array (arrays are passed by reference so should modify the original as well)
//make the "valid" move in that array...
//check the king whose turn it was first... - white turn check white king - ensure that the king is not in check
//if the king is in check then revert the move
//if king is not in check - then edit the original board using the move function
//then check Black's king and see if they are in check... - if they are output a message letting them know

//TODO: am I going to enforce proper notation with + and # for check and mate?
//TODO: need to decide how I would like to handle checkmate chekcing
//if check - check for possible moves - if find one//
//could start with iterate from checking piece and look for captures
//iterate from squares between the piece and king and look for blocks
//then check moves?

