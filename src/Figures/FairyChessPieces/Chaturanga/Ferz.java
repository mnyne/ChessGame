package Figures.FairyChessPieces.Chaturanga;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Ferz extends Figure {
    public Ferz(int color, int startIndex) {
        super(7, color, startIndex);
    }

    public Ferz(int color, int currentID, int currentIndex) {
        super(7, color, currentID, currentIndex);
    }

    /**
     * Determines if a move is legal on the chess board.
     *
     * @param  currentBoard    the current state of the chess board
     * @param  selectedFigure  the figure selected to make the move
     * @param  targetIndex     the index of the target position on the board
     * @return                 true if the move is legal, false otherwise
     */
    public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
        MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);

        if (move.hasLength(1) && move.diagonalMove()) {
            return true;
        }
        return false;
    }

    /**
     * Updates the eligibility of en passant in the game.
     *
     * @param  gameLog  the game log containing the moves history
     */
    public void updateEnPassantEligibility(GameLog gameLog) {
    }

    /**
     * Creates a deep copy of the Figure object.
     *
     * @return  the deep copy of the Figure object
     */
    public Figure deepCopy() {
        Ferz copy = new Ferz(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
        copy.updateMovedStatus(this.hasMoved());
        return copy;
    }
}