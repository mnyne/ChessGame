public class Knight extends Figure {
	public Knight(int color, int startIndex) {
        super(2, color, startIndex);
    }
	
	public Knight(int color, int currentID, int currentIndex) {
		super(2, color, currentID, currentIndex);
	}
	
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		
		if (move.lShapedMove()) {
			return true;
		}
		
		return false;
	}
	
	public Figure deepCopy() {
		Knight copy = new Knight(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
