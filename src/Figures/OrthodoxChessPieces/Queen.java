package Figures.OrthodoxChessPieces;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Queen extends Figure {
	public Queen(int color, int startIndex) {
		super(4, color, startIndex);
	}

	public Queen(int color, int currentID, int currentIndex) {
		super(4, color, currentID, currentIndex);
	}

	/**
	 * Determines if the move is legal on the chessboard.
	 *
	 * @param  currentBoard  the current state of the chessboard
	 * @param  selectedFigure  the figure that is selected to make the move
	 * @param  targetIndex  the index of the target position on the chessboard
	 * @return  true if the move is legal, false otherwise
	 */
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.orthogonalMove() && !move.orthogonalCollision()) {
			return true;
		}
		if (move.diagonalMove() && !move.diagonalCollision()) {
			return true;
		}
		return false;
	}

	/**
	 * Creates a deep copy of the Figure object.
	 *
	 * @return  a new Figure object that is an exact copy of the original Figure object
	 */
	public Figure deepCopy() {
		Queen copy = new Queen(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}

	/**
	 * Updates the eligibility for en passant in the game.
	 *
	 * @param  gameLog  the game log containing the move history
	 */
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
