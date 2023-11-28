public class King extends Figure {
	public King(String s_color, String startingPosition) {
		super(1, s_color, startingPosition);
	}

	public King(int color) {
		super(1, color);
	}

	public boolean moveIsLegal(int potentialX, int potentialY, int currentX,
			int currentY, int color, ChessBoard currentBoard) {
		boolean bool;
		int xDiff = coordinateHelper.getAdjustedDiff(potentialX, currentX);
		int yDiff = coordinateHelper.getAdjustedDiff(potentialY, currentY);
		if (xDiff == 1 || yDiff == 1) {
			bool = true;
		} else {
			bool = false;
		}
		if (xDiff > 1 || yDiff > 1) {
			bool = false;
		}
		// checkForCastling
		if (!this.hasMoved()) {
			if (potentialY == currentY && potentialX == currentX - 2) {
				Figure fig1 = currentBoard.getFigureAt(currentX - 4, currentY);
				if (fig1 != null && !fig1.hasMoved()) {
					bool = true;
				}
				for (int i = currentX - 1; i > 0; i--) {
					Figure fig2 = currentBoard.getFigureAt(i, currentY);
					if (fig2 != null) {
						bool = false;
					}
				}
			}
		}
		if (!this.hasMoved()) {
			if (potentialY == currentY && potentialX == currentX + 2) {
				Figure fig3 = currentBoard.getFigureAt(currentX + 3, currentY);
				if (fig3 != null && !fig3.hasMoved()) {
					bool = true;
				}

				for (int i = currentX + 1; i < 7; i++) {
					Figure fig4 = currentBoard.getFigureAt(i, currentY);
					if (fig4 != null) {
						bool = false;
					}
				}
			}
		}
		return bool;
	}
	
	public boolean isEligibleForEnPassant() {
		return false;
	}

	public Figure deepCopy() {
		King copy = new King(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		return copy;
	}
}
