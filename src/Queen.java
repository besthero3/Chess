public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        int[] pieceCoordinates = new int[2];

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

        //<-------------------------HORIZONTAL AND VERTICAL MOVEMENT --------------------------------------------->

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
                    break;
                }
            }
        }

        //down the board
        for (int i = row + 1; i < 8; i++) {

            if (row + 1 > 7) {
                break;
            }
            if (MainCLI.board[i][col].p != null) {
                if (MainCLI.board[i][col].p.type == PieceType.ROOK && MainCLI.board[i][col].p.color == color) {
                    pieceCoordinates[0] = i;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
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
                    break;
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
                    break;
                }
            }
        }

        //<-------------------------HORIZONTAL AND VERTICAL MOVEMENT --------------------------------------------->

        //<-------------------------DIAGONAL--------------------------------------------->
        //row - 1, col -1. up left
        if (row - 1 >= 0 && col - 1 >= 0) {
            int j = col - 1;

            for(int i = row - 1; i >= 0; i--) {
                //error checking

                if (j < 0) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][col].p != null) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][col].p.type == PieceType.BISHOP && MainCLI.board[i][col].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j--;
            }

        }

        //row - 1 col + 1. up right
        if (row - 1 >= 0 && col + 1 < 8) {
            int j = col + 1;

            for(int i = row - 1; i >= 0; i--) {

                if (j > 7) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][col].p != null) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][col].p.type == PieceType.BISHOP && MainCLI.board[i][col].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j++;
            }
        }

        //row + 1, col - 1. down left
        if (row + 1 < 8 && col - 1 >= 0) {
            int j = col - 1;

            for(int i = row + 1; i < 8; i++) {

                if (j < 0) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][col].p != null) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][col].p.type == PieceType.BISHOP && MainCLI.board[i][col].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j--;
            }
        }

        //row + 1, col + 1. down right
        if (row + 1 < 8 && col + 1 < 8) {
            int j = col + 1;

            for(int i = row + 1; i < 8; i++) {

                if (j > 7) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][col].p != null) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][col].p.type == PieceType.BISHOP && MainCLI.board[i][col].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j++;
            }
        }
        //<-------------------------DIAGONAL--------------------------------------------->

        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
