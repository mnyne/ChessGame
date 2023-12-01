public class Pawn extends Figure {

	public Pawn(String s_color, String startingPosition) {
		super(3, s_color, startingPosition);
	}
	
	public Pawn(int color) {
		super(3, color);
	}
	
	boolean doubleMove = false;

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker moveCheck = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		return moveCheck.pawnMove();
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
        String lastEntry = gameLog.getLastEntry();
        String logID = gameLog.getFigureIDfromEntry(lastEntry);
        int yMovement = gameLog.getYMovementfromEntry(lastEntry);
        if(logID.equals(this.getFigureID())) {
        	if(yMovement == 2 || yMovement == -2) {
        		setEnPassantStatus(true);
        	}
        }
	}
	
}
