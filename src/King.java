public class King extends Figure {
	public King(String s_color, String startingPosition) {
		super(1, s_color, startingPosition);
	}

	public King(int color) {
		super(1, color);
	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		int potentialX = coordinateHelper.convertIndextoX(targetIndex);
		int potentialY = coordinateHelper.convertIndextoY(targetIndex);
		int currentX = selectedFigure.getXPosition();
		int currentY = selectedFigure.getYPosition();
		boolean bool;
		int xDiff = coordinateHelper.getAdjustedDiff(potentialX, currentX);
		int yDiff = coordinateHelper.getAdjustedDiff(potentialY, currentY);
		bool = checkForDiagonalOrOrthogonalMove(xDiff, yDiff);
		bool = checkMovementLength(bool, xDiff, yDiff);
		bool = checkForQueensideCastling(currentBoard, potentialX, potentialY,
				currentX, currentY, bool);
		bool = checkForKingsideCastling(currentBoard, potentialX, potentialY,
				currentX, currentY, bool);
		return bool;
	}

	private boolean checkForDiagonalOrOrthogonalMove(int xDiff, int yDiff) {
		boolean bool;
		if (xDiff == 1 || yDiff == 1) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	private boolean checkMovementLength(boolean bool, int xDiff, int yDiff) {
		if (xDiff > 1 || yDiff > 1) {
			bool = false;
		}
		return bool;
	}

	private boolean checkForKingsideCastling(ChessBoard currentBoard,
			int potentialX, int potentialY, int currentX, int currentY,
			boolean bool) {
		int targetIndex;
		if (!this.hasMovedStatus()) {
			if (potentialY == currentY && potentialX == currentX + 2) {
				targetIndex = coordinateHelper.convertXYtoIndex(currentX + 3, currentY);
				Figure fig3 = currentBoard.getFigureAtIndex(targetIndex);
				if (fig3 != null && !fig3.hasMovedStatus()) {
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

	private boolean checkForQueensideCastling(ChessBoard currentBoard,
			int potentialX, int potentialY, int currentX, int currentY,
			boolean bool) {
		if (!this.hasMovedStatus()) {
			if (potentialY == currentY && potentialX == currentX - 2) {
				Figure fig1 = currentBoard.getFigureAt(currentX - 4, currentY);
				if (fig1 != null && !fig1.hasMovedStatus()) {
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
		return bool;
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
