package Figures.OrthodoxChessPieces;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Bishop extends Figure {
	public Bishop(int color, int startIndex) {
		super(0, color, startIndex);
	}

	public Bishop(int color, int currentID, int currentIndex) {
		super(0, color, currentID, currentIndex);
	}

	/**
	 * Determines if a move is legal on the chess board.
	 *
	 * @param  currentBoard    the current state of the chess board
	 * @param  selectedFigure  the figure selected for the move
	 * @param  targetIndex     the index of the target position on the board
	 * @return                 true if the move is legal, false otherwise
	 */
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker(currentBoard, selectedFigure, targetIndex);
		
		if (move.diagonalMove() && !move.diagonalCollision()) {
			return true;
		}
		return false;
		
	}

	/**
	 * Updates the eligibility for en passant in the game.
	 *
	 * @param  gameLog  the game log object
	 */
	public void updateEnPassantEligibility(GameLog gameLog) {
	}

	/**
	 * Creates a deep copy of the Bishop object.
	 *
	 * @return         	A deep copy of the Bishop object.
	 */
	public Figure deepCopy() {
		Bishop copy = new Bishop(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}

}
