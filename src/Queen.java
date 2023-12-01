public class Queen extends Figure {
	public Queen(String s_color, String startingPosition) {
		super(4, s_color, startingPosition);
	}

	public Queen(int color) {
		super(4, color);
	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker moveCheck = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (moveCheck.orthogonalMove() && !moveCheck.orthogonalCollision()) {
			return true;
		}
		if (moveCheck.diagonalMove() && !moveCheck.diagonalCollision()) {
			return true;
		}
		return false;
	}

	public Figure deepCopy() {
		Queen copy = new Queen(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
