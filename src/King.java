public class King extends Figure {
	public King(String s_color, String startingPosition) {
		super(1, s_color, startingPosition);
	}

	public King(int color) {
		super(1, color);
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
		King copy = new King(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
}
