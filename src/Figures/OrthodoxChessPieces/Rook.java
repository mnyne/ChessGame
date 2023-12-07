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

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.orthogonalMove() && !move.orthogonalCollision()) {
			return true;
		}
		return false;
	}

	public Figure deepCopy() {
		Rook copy = new Rook(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
