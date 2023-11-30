public class Rook extends Figure {
	public Rook(String s_color, String startingPosition) {
		super(5, s_color, startingPosition);
	}

	public Rook(int color) {
		super(5, color);
	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		int potentialX = coordinateHelper.convertIndextoX(targetIndex);
		int potentialY = coordinateHelper.convertIndextoY(targetIndex);
		int currentX = selectedFigure.getXPosition();
		int currentY = selectedFigure.getYPosition();
		boolean bool;
		int xDiff = coordinateHelper.getAdjustedDiff(potentialX, currentX);
		int yDiff = coordinateHelper.getAdjustedDiff(potentialY, currentY);
		bool = checkForOrthogonalMove(xDiff, yDiff);

		bool = checkHorizontalCollisionPos(currentBoard, potentialX, currentX,
				currentY, bool);
		bool = checkHorizontalCollisionNeg(currentBoard, potentialX, currentX,
				currentY, bool);
		bool = checkVerticalCollisionPos(currentBoard, potentialY, currentX,
				currentY, bool);
		bool = checkVerticalCollisionNeg(currentBoard, potentialY, currentX,
				currentY, bool);
		return bool;
	}

	private boolean checkForOrthogonalMove(int xDiff, int yDiff) {
		boolean bool;
		if (xDiff == 0 || yDiff == 0) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	private boolean checkVerticalCollisionNeg(ChessBoard currentBoard,
			int potentialY, int currentX, int currentY, boolean bool) {
		int yDiff;
		if (bool && potentialY < currentY) {
			yDiff = currentY - potentialY;
			for (int i = yDiff - 1; i > 0; i--) {
				Figure fig = currentBoard.getFigureAt(currentX, currentY - i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkVerticalCollisionPos(ChessBoard currentBoard,
			int potentialY, int currentX, int currentY, boolean bool) {
		int yDiff;
		if (bool && potentialY > currentY) {
			yDiff = potentialY - currentY;
			for (int i = 1; i < yDiff; i++) {
				Figure fig = currentBoard.getFigureAt(currentX, currentY + i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkHorizontalCollisionNeg(ChessBoard currentBoard,
			int potentialX, int currentX, int currentY, boolean bool) {
		int xDiff;
		if (bool && potentialX < currentX) {
			xDiff = currentX - potentialX;
			for (int i = xDiff - 1; i > 0; i--) {
				Figure fig = currentBoard.getFigureAt(currentX - i, currentY);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkHorizontalCollisionPos(ChessBoard currentBoard,
			int potentialX, int currentX, int currentY, boolean bool) {
		int xDiff;
		if (bool && potentialX > currentX) {
			xDiff = potentialX - currentX;
			for (int i = 1; i < xDiff; i++) {
				Figure fig = currentBoard.getFigureAt(currentX + i, currentY);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	public Figure deepCopy() {
		Rook copy = new Rook(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
