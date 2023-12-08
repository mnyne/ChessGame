package Figures.FairyChessPieces.Chaturanga;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Padati extends Figure {

    public Padati(int color, int startIndex) {
        super(8, color, startIndex);
    }

    public Padati(int color, int currentID, int currentIndex) {
        super(8, color, currentID, currentIndex);
    }

    /**
     * Determines if a move is legal on the chessboard.
     *
     * @param  currentBoard    the current state of the chessboard
     * @param  selectedFigure  the figure that is selected for the move
     * @param  targetIndex     the index of the target position on the chessboard
     * @return                 true if the move is legal, false otherwise
     */
    public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
        MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
        if(move.isForwardMove(this.getFigureColor())) {
            if (forwardMoveLegal(move) || diagonalMoveLegal(move)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the given movement is a legal diagonal move of length 1.
     *
     * @param  move the movement to be checked
     * @return      true if the diagonal move is legal and can be captured, false otherwise
     */
    private boolean diagonalMoveLegal(MovementValidityChecker move) {
        if (move.diagonalMove() && move.hasLength(1)) {
            if(move.canCapture()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if a forward move is legal.
     *
     * @param  move  the MovementValidityChecker object representing the move to be checked
     * @return       true if the move is legal, false otherwise
     */
    private boolean forwardMoveLegal(MovementValidityChecker move) {
        if (move.orthogonalMove() && !move.orthogonalCollision()  && !move.canCapture()) {
            if (move.hasLength(1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a deep copy of the Figure object.
     *
     * @return  a new Figure object that is an exact copy of the original Figure
     */
    public Figure deepCopy() {
        Padati copy = new Padati(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
        copy.updateMovedStatus(this.hasMoved());
        return copy;
    }

    /**
     * Updates the en passant eligibility of the game.
     *
     * @param  gameLog   the game log
     * @return           nothing
     */
    public void updateEnPassantEligibility(GameLog gameLog) {
        setEnPassantStatus(false);
        int yMovement = gameLog.getYMovementfromEntry(gameLog.getPriorEntry(1));
        if(gameLog.getFigureIDfromEntry(gameLog.getPriorEntry(1)) == this.getFigureID()) {
            if(yMovement == 2 || yMovement == -2) {
                setEnPassantStatus(true);
            }
        }
    }
}