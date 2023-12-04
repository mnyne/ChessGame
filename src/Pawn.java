public class Pawn extends Figure {

	public Pawn(int color, int startIndex) {
		super(3, color, startIndex);
	}
	
	public Pawn(int color, int currentID, int currentIndex) {
		super(3, color, currentID, currentIndex);
	}
	
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		if(move.isForwardMove(this.getFigureColor())) {
			if (forwardMoveLegal(move) || diagonalMoveLegal(move)) {
				return true;
			}
		}
		return false;
	}

	private boolean diagonalMoveLegal(MovementValidityChecker move) {
		if (move.diagonalMove() && move.hasLength(1)) {
			if(move.canCapture() || move.canEnPassant()) {
				return true;
			}
		}
		return false;
	}

	private boolean forwardMoveLegal(MovementValidityChecker move) {
		if (move.orthogonalMove() && !move.orthogonalCollision()  && !move.canCapture()) {
			if (!this.hasMoved() && move.hasLength(2)) {
				return true;
			} else if (move.hasLength(1)) {
				return true;
			}
		}
		return false;
	}
	
	public Figure deepCopy() {
		Pawn copy = new Pawn(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
		setEnPassantStatus(false);
        int yMovement = gameLog.getYMovementfromEntry(gameLog.getPriorEntry(1));
        if(gameLog.getFigureIDfromEntry(gameLog.getPriorEntry(1)) == this.getFigureID()) {
        	if(yMovement == 2 || yMovement == -2) {
        		setEnPassantStatus(true);
        	}
        }
	}
}
