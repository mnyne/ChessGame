public class Knight extends Figure {
	public Knight(String s_color, String startingPosition) {
        super(2, s_color, startingPosition);
    }
	
	public Knight(int color) {
		super(2, color);
	}
	
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.lShapedMove()) {
			return true;
		}
		
		return false;
	}
	
	public Figure deepCopy() {
		Knight copy = new Knight(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
