public class Pawn extends Figure {

	public Pawn(String s_color, String startingPosition) {
		super(3, s_color, startingPosition);
	}
	
	public Pawn(int color) {
		super(3, color);
	}
	
	boolean doubleMove = false;

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		int potentialX = coordinateHelper.convertIndextoX(targetIndex);
		int potentialY = coordinateHelper.convertIndextoY(targetIndex);
		int currentX = selectedFigure.getXPosition();
		int currentY = selectedFigure.getYPosition();
		int color = selectedFigure.getFigureColor();
		boolean bool = false;
		int xDiff = coordinateHelper.getAdjustedDiff(potentialX, currentX);
		int yDiffRaw = coordinateHelper.getRawDiff(potentialY, currentY);
		//blackpawn
		if (color == 1) {
			if (xDiff == 0 && -yDiffRaw > 0 && -yDiffRaw < 2) {
				bool = true;
			} else {
				bool = false;
			}
			int index = coordinateHelper.convertXYtoIndex(potentialX, potentialY);
			if (currentY == 1 && xDiff == 0 && -yDiffRaw > 0 && -yDiffRaw == 2 && currentBoard.getFigureAtIndex(index) == null) {
				bool = true;
			}
			if (potentialY == currentY + 1) {
				index = coordinateHelper.convertXYtoIndex(potentialX, potentialY);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY + 2 && currentY == 1) {
				index = coordinateHelper.convertXYtoIndex(potentialX, potentialY-1);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY + 1) {
				if (potentialX == currentX - 1 || potentialX == currentX + 1) {
					index = coordinateHelper.convertXYtoIndex(potentialX, potentialY);
					if (currentBoard.getFigureAtIndex(index) != null) {
						bool = true;
					}
					if(currentBoard.getFigureAt(potentialX, potentialY-1) != null && currentBoard.getFigureAt(potentialX, potentialY-1).getEnPassantStatus()) {
						bool = true;
					}
				}
			}
		}
		//whitepawn
		if (color == 0) {
			if (xDiff == 0 && -yDiffRaw < 0 && -yDiffRaw > -2) {
				bool = true;
			} else {
				bool = false;
			}
			int index = coordinateHelper.convertXYtoIndex(potentialX, potentialY);
			if (currentY == 6 && xDiff == 0 && -yDiffRaw < 0 && -yDiffRaw == -2 && currentBoard.getFigureAtIndex(index) == null) {
				bool = true;
			}
			if (potentialY == currentY - 1) {
				index = coordinateHelper.convertXYtoIndex(potentialX, potentialY);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY - 2 && currentY == 6) {
				index = coordinateHelper.convertXYtoIndex(potentialX, potentialY+1);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY - 1) {
				if (potentialX == currentX - 1 || potentialX == currentX + 1) {
					index = coordinateHelper.convertXYtoIndex(potentialX, potentialY);
					if (currentBoard.getFigureAtIndex(index) != null) {
						bool = true;
					}
					if(currentBoard.getFigureAt(potentialX, potentialY+1) != null && currentBoard.getFigureAt(potentialX, potentialY+1).getEnPassantStatus()) {
						bool = true;
					}
				}
			}
		}
		return bool;
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
