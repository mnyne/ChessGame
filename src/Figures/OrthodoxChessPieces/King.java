package Figures.OrthodoxChessPieces;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class King extends Figure {
	public King(int color, int startIndex) {
		super(1, color, startIndex);
	}

	public King(int color, int currentID, int currentIndex) {
		super(1, color, currentID, currentIndex);
	}

	/**
	 * Checks if a move is legal on the chessboard.
	 *
	 * @param  currentBoard   the current state of the chessboard
	 * @param  selectedFigure the figure that is selected for the move
	 * @param  targetIndex    the index of the target position on the chessboard
	 * @return                true if the move is legal, false otherwise
	 */
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.hasLength(1)) {
			return true;
		}
		
		if (move.canCastle()) {
			return true;
		}
		return false;
	}

	/**
	 * Updates the eligibility for en passant in the game.
	 *
	 * @param  gameLog  the game log containing the moves and actions of the game
	 */
	public void updateEnPassantEligibility(GameLog gameLog) {
	}

	/**
	 * Creates a deep copy of the King object.
	 *
	 * @return         	A new King object that is a copy of the current King object.
	 */
	public Figure deepCopy() {
		King copy = new King(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}
}
