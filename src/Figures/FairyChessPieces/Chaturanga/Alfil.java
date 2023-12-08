package Figures.FairyChessPieces.Chaturanga;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Alfil extends Figure {
    public Alfil(int color, int startIndex) {
        super(6, color, startIndex);
    }

    public Alfil(int color, int currentID, int currentIndex) {
        super(6, color, currentID, currentIndex);
    }

    /**
     * Determines if a move is legal on a chessboard.
     *
     * @param  currentBoard      the current state of the chessboard
     * @param  selectedFigure    the figure that is selected to move
     * @param  targetIndex       the index of the target position on the chessboard
     * @return                   true if the move is legal, false otherwise
     */
    public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
        MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);

        if (move.hasExactLength(2) && move.diagonalMove()) {
            return true;
        }
        return false;
    }

    /**
     * Updates the eligibility for en passant capture in the current game.
     *
     * @param  gameLog  the game log containing the moves made in the game
     */
    public void updateEnPassantEligibility(GameLog gameLog) {
    }

    /**
     * Creates a deep copy of the Figure object.
     *
     * @return  the deep copy of the Figure object
     */
    public Figure deepCopy() {
        Alfil copy = new Alfil(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
        copy.updateMovedStatus(this.hasMoved());
        return copy;
    }
}