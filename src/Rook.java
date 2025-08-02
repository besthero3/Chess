import com.sun.tools.javac.Main;

public class Rook extends Piece {

    public Rook(PieceColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        //TODO: need to check that pieces are not on edge of board
        //then need to iterate back in the direction if we can
        //need to start at row -1 and that kind of thing so our first check isnt on the square we
        //are moving to

        int[] pieceCoordinates = new int[2];

        //only need to check this captures
        if (captures) {
            //no piece there
            if (MainCLI.board[row][col].p == null) {
                return null;
                //need to wrap this because i want to avoid a null pointer error
            }
            else {
                //can't take a piece that is the same color as your piece
                if (MainCLI.board[row][col].p.color == color) {
                    return null;
                }
            }
        }
        else {
            if (MainCLI.board[row][col].p != null && MainCLI.board[row][col].p.type != PieceType.NONE) {
                return null;
                //need to wrap this because i want to avoid a null pointer error
            }
        }

        //for loop in each direction looking for a piece

        //back up the board...
        for(int i = row - 1; i >= 0; i--) {

            if (row - 1 < 0) {
                break;
            }
            //empty square
            if (MainCLI.board[i][col].p != null) {
                if (MainCLI.board[i][col].p.type == PieceType.ROOK && MainCLI.board[i][col].p.color == color) {
                    pieceCoordinates[0] = i;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {

                    if (MainCLI.board[i][col].p != null && MainCLI.board[i][col].p.type != PieceType.NONE) {
                        break;
                    }

                }
            }
        }

        //down the board
        for (int i = row + 1; i < 8; i++) {

            //if (row + 1 > 7) {
            //    break;
            //}

            //picece could be none and then the else clause below will trigger... could also add the none part up here
            if (MainCLI.board[i][col].p != null) {
                if (MainCLI.board[i][col].p.type == PieceType.ROOK && MainCLI.board[i][col].p.color == color) {
                    pieceCoordinates[0] = i;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    //We do not want it to automatically break just because there is not a piece there
                    //this would be when we run into a piece
                    //must change to i and col
                    if (MainCLI.board[i][col].p != null && MainCLI.board[i][col].p.type != PieceType.NONE) {
                        break;
                        //need to wrap this because i want to avoid a null pointer error
                    }
                    //break;
                }
            }
        }

        //to the left
        for (int i = col - 1; i >= 0; i--) {

            if (col - 1 < 0) {
                break;
            }

            if (MainCLI.board[row][i].p != null) {
                if (MainCLI.board[row][i].p.type == PieceType.ROOK && MainCLI.board[row][i].p.color == color) {
                    pieceCoordinates[0] = row;
                    pieceCoordinates[1] = i;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {

                    if (MainCLI.board[row][i].p != null && MainCLI.board[row][i].p.type != PieceType.NONE) {
                        break;
                    }

                }
            }
        }

        // to the right
        for (int i = col + 1; i < 8; i++) {

            if (col + 1 > 7) {
                break;
            }

            if (MainCLI.board[row][i].p != null) {
                if (MainCLI.board[row][i].p.type == PieceType.ROOK && MainCLI.board[row][i].p.color == color) {
                    pieceCoordinates[0] = row;
                    pieceCoordinates[1] = i;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    if (MainCLI.board[row][i].p != null && MainCLI.board[row][i].p.type != PieceType.NONE) {
                        break;
                    }
                }
            }
        }

        //if unsuccessful we return null
        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
