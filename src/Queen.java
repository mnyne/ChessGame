public class Queen extends Figure {
	public Queen(int color, int startIndex) {
		super(4, color, startIndex);
	}

	public Queen(int color, int currentID, int currentIndex) {
		super(4, color, currentID, currentIndex);
	}

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

	public Figure deepCopy() {
		Queen copy = new Queen(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
