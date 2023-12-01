public class Pawn extends Figure {

	public Pawn(String s_color, String startingPosition) {
		super(3, s_color, startingPosition);
	}
	
	public Pawn(int color) {
		super(3, color);
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
			if (!this.hasMovedStatus() && move.hasLength(2)) {
				return true;
			} else if (move.hasLength(1)) {
				return true;
			}
		}
		return false;
	}
	
	public Figure deepCopy() {
		Pawn copy = new Pawn(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		copy.setEnPassantStatus(this.getEnPassantStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
		setEnPassantStatus(false);
        String logID = gameLog.getFigureIDfromEntry(gameLog.getLastEntry());
        int yMovement = gameLog.getYMovementfromEntry(gameLog.getLastEntry());
        if(logID.equals(this.getFigureID())) {
        	if(yMovement == 2 || yMovement == -2) {
        		setEnPassantStatus(true);
        	}
        }
	}
}
