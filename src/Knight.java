public class Knight extends Figure {
	public Knight(String s_color, String startingPosition) {
        super(2, s_color, startingPosition);
    }
	
	public Knight(int color) {
		super(2, color);
	}
	
	public boolean moveIsLegal(int potentialX, int potentialY, int currentX, int currentY, int color, ChessBoard currentBoard) {
		boolean bool;
		int xDiff = coordinateHelper.getAdjustedDiff(potentialX, currentX);
		int yDiff = coordinateHelper.getAdjustedDiff(potentialY, currentY);	
		if (xDiff + yDiff == 3 && xDiff < 3 && yDiff < 3) {
			bool = true;
		} else {
			bool = false;
		}
		if (xDiff == 0 && yDiff == 0) {
			bool = false;
		}
		return bool;
	}
	
	public Figure deepCopy() {
		Knight copy = new Knight(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		return copy;
	}
	
	public boolean isEligibleForEnPassant() {
		return false;
	}
}
