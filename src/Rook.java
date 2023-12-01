public class Rook extends Figure {
	public Rook(String s_color, String startingPosition) {
		super(5, s_color, startingPosition);
	}

	public Rook(int color) {
		super(5, color);
	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.orthogonalMove() && !move.orthogonalCollision()) {
			return true;
		}
		return false;
	}

	public Figure deepCopy() {
		Rook copy = new Rook(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
