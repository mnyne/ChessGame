package Figures.OrthodoxChessPieces;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Rook extends Figure {
	public Rook(int color, int startIndex) {
		super(5, color, startIndex);
	}

	public Rook(int color, int currentID, int currentIndex) {
		super(5, color, currentID, currentIndex);
	}

	/**
	 * Determines if a move is legal on the chessboard.
	 *
	 * @param  currentBoard    the current state of the chessboard
	 * @param  selectedFigure  the figure that is selected to make the move
	 * @param  targetIndex     the target index where the figure wants to move
	 * @return                 true if the move is legal, false otherwise
	 */
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.orthogonalMove() && !move.orthogonalCollision()) {
			return true;
		}
		return false;
	}

	/**
	 * Creates a deep copy of the Figure object.
	 *
	 * @return  a deep copy of the Figure object
	 */
	public Figure deepCopy() {
		Rook copy = new Rook(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}

	/**
	 * Updates the eligibility for en passant in the game.
	 *
	 * @param  gameLog	the game log object
	 */
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
