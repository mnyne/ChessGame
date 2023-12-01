public class Bishop extends Figure {
	public Bishop(String s_color, String startingPosition) {
		super(0, s_color, startingPosition);
	}

	public Bishop(int color) {
		super(0, color);
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
		Bishop copy = new Bishop(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}

}
