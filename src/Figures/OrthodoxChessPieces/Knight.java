package Figures.OrthodoxChessPieces;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Knight extends Figure {
	public Knight(int color, int startIndex) {
        super(2, color, startIndex);
    }
	
	public Knight(int color, int currentID, int currentIndex) {
		super(2, color, currentID, currentIndex);
	}

	/**
	 * Determines whether a move is legal on a chessboard.
	 *
	 * @param  currentBoard    the current state of the chessboard
	 * @param  selectedFigure  the figure that is selected to move
	 * @param  targetIndex     the index of the target position on the chessboard
	 * @return                 true if the move is legal, false otherwise
	 */
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.lShapedMove()) {
			return true;
		}
		
		return false;
	}

	/**
	 * Creates a deep copy of the Knight object.
	 *
	 * @return  a new instance of the Knight object with the same properties as the original
	 */
	public Figure deepCopy() {
		Knight copy = new Knight(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}

	/**
	 * Updates the eligibility of en passant for the current game.
	 *
	 * @param  gameLog  the game log containing the moves made so far
	 */
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
