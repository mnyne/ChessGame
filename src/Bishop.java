public class Bishop extends Figure {
	public Bishop(int color, int startIndex) {
		super(0, color, startIndex);
	}

	public Bishop(int color, int currentID, int currentIndex) {
		super(0, color, currentID, currentIndex);
	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker(currentBoard, selectedFigure, targetIndex);
		
		if (move.diagonalMove() && !move.diagonalCollision()) {
			return true;
		}
		return false;
		
	}

	public void updateEnPassantEligibility(GameLog gameLog) {
	}

	public Figure deepCopy() {
		Bishop copy = new Bishop(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}

}
