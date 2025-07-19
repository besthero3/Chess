public class King extends Piece {

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {

        int[] pieceCoordinates = new int[2];
        //check one in every direction from row and col

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

        //off of the top edge of the board
        if(row > 0) {

            //top left
            if (col > 0) {
                if (MainCLI.board[row - 1][col - 1].p.type == PieceType.KING && MainCLI.board[row - 1][col - 1].p.color == color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }

            //top middle
            if (MainCLI.board[row - 1][col].p.type == PieceType.KING && MainCLI.board[row - 1][col].p.color == color) {
                pieceCoordinates[0] = row - 1;
                pieceCoordinates[1] = col;
                return pieceCoordinates;
            }

            if (col < 7) {
                //top right
                if (MainCLI.board[row - 1][col + 1].p.type == PieceType.KING && MainCLI.board[row - 1][col + 1].p.color == color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }


        }

        //off the left edge of the board
        if(col > 0) {
            //middle left
            if (MainCLI.board[row][col - 1].p.type == PieceType.KING && MainCLI.board[row][col - 1].p.color == color) {
                pieceCoordinates[0] = row;
                pieceCoordinates[1] = col - 1;
                return pieceCoordinates;
            }
        }

        //off the right edge of the board
        if(col < 7) {
            //middle right
            if (MainCLI.board[row][col + 1].p.type == PieceType.KING && MainCLI.board[row][col + 1].p.color == color) {
                pieceCoordinates[0] = row;
                pieceCoordinates[1] = col + 1;
                return pieceCoordinates;
            }
        }

        //off the bottom edge of the board
        if(row < 7) {

            if (col > 0) {
                //bottom left
                if (MainCLI.board[row + 1][col - 1].p.type == PieceType.KING && MainCLI.board[row + 1][col - 1].p.color == color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }

            //bottom middle
            if (MainCLI.board[row + 1][col].p.type == PieceType.KING && MainCLI.board[row + 1][col].p.color == color) {
                pieceCoordinates[0] = row + 1;
                pieceCoordinates[1] = col;
                return pieceCoordinates;
            }

            if (col < 7) {
                //bottom right
                if (MainCLI.board[row + 1][col + 1].p.type == PieceType.KING && MainCLI.board[row + 1][col + 1].p.color == color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }


    //check function
    //need square to iterate from, int row, int col, (store where king is),
    //want color to be not equal to if we are checking for a check and then return true...
    boolean check(int row, int col, PieceColor color) {

        //<---------------------------------------HORIZONTAL/VERTICAL---------------------------------------->
        //back up the board...
        for(int i = row - 1; i >= 0; i--) {

            if (row - 1 < 0) {
                break;
            }
            //empty square
            if (MainCLI.board[i][col].p != null) {
                if ((MainCLI.board[i][col].p.type == PieceType.ROOK || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                    return true;
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
                if ((MainCLI.board[i][col].p.type == PieceType.ROOK || MainCLI.board[i][col].p.type == PieceType.QUEEN)&& MainCLI.board[i][col].p.color != color) {
                    return true;
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
                if ((MainCLI.board[row][i].p.type == PieceType.ROOK || MainCLI.board[row][i].p.type == PieceType.QUEEN) && MainCLI.board[row][i].p.color != color) {
                    return true;
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
                if ((MainCLI.board[row][i].p.type == PieceType.ROOK || MainCLI.board[row][i].p.type == PieceType.QUEEN) && MainCLI.board[row][i].p.color != color) {
                    return true;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }
        //<---------------------------------------HORIZONTAL/VERTICAL---------------------------------------->

        //<---------------------------------KNIGHT-------------------------------------------->
        //up
        if (row >= 2) {
            if (col > 0) {
                if (MainCLI.board[row - 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
            if(col < 7) {
                if (MainCLI.board[row - 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
        }

        //down
        if (row <= 5) {
            if (col > 0) {

                if (MainCLI.board[row + 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }

            }
            if(col < 7) {
                if (MainCLI.board[row + 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
        }

        //left
        if (col >= 2) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
        }

        //right
        if (col <= 5) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color != color) {
                    return true;
                }
            }
        }

        //<----------------------KNIGHT------------------------------------------------->>>>>>


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
                    if ((MainCLI.board[i][col].p.type == PieceType.BISHOP || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                        return true;
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
                    if ((MainCLI.board[i][col].p.type == PieceType.BISHOP || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                        return true;
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
                    if ((MainCLI.board[i][col].p.type == PieceType.BISHOP || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                        return true;
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
                    if ((MainCLI.board[i][col].p.type == PieceType.BISHOP || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                        return true;
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

        //PAWN - opposites because check
        if (color == PieceColor.BLACK) {
            //value needs to be one more or one less to be able to capture as a pawn
            //plus because down the board
            if (row < 7 && col > 0) {
                if (MainCLI.board[row + 1][col - 1].p.type == PieceType.PAWN && MainCLI.board[row + 1][col - 1].p.color == PieceColor.WHITE) {
                    return true;
                }
            }

            if (row < 7 && col < 7) {
                if (MainCLI.board[row + 1][col + 1].p.type == PieceType.PAWN && MainCLI.board[row + 1][col + 1].p.color == PieceColor.WHITE) {
                    return true;
                }
            }
        }
        else if (color == PieceColor.WHITE) {
            if (row > 0 && col > 0) {
                if (MainCLI.board[row - 1][col - 1].p.type == PieceType.PAWN && MainCLI.board[row - 1][col - 1].p.color == PieceColor.BLACK) {
                    return true;
                }
            }

            if (row > 0 && col < 7) {
                if (MainCLI.board[row - 1][col + 1].p.type == PieceType.PAWN && MainCLI.board[row - 1][col + 1].p.color == PieceColor.BLACK) {
                    return true;
                }
            }
        }

        return false;
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

