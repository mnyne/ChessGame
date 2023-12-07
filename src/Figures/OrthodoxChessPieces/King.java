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
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}

	public Figure deepCopy() {
		King copy = new King(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}
}
