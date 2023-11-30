public class Bishop extends Figure {
	public Bishop(String s_color, String startingPosition) {
		super(0, s_color, startingPosition);
	}

	public Bishop(int color) {
		super(0, color);
	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		int potentialX = coordinateHelper.convertIndextoX(targetIndex);
		int potentialY = coordinateHelper.convertIndextoY(targetIndex);
		int currentX = selectedFigure.getXPosition();
		int currentY = selectedFigure.getYPosition();
		boolean bool = false;
		int xDiff = coordinateHelper.getAdjustedDiff(potentialX, currentX);
		int yDiff = coordinateHelper.getAdjustedDiff(potentialY, currentY);
		bool = checkForDiagonalMovement(bool, xDiff, yDiff);
		bool = checkDiagonalPosPosCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkDiagonalNegNegCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkDiagonalPosNegCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkDiagonalNegPosCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		return bool;

	}

	private boolean checkDiagonalNegPosCollision(ChessBoard currentBoard,
			int potentialX, int potentialY, int currentX, int currentY,
			boolean bool) {
		int xDiff;
		if (bool && potentialX < currentX && potentialY > currentY) {
			xDiff = currentX - potentialX;
			for (int i = xDiff-1; i > 0; i--) {
				Figure fig = currentBoard.getFigureAt(currentX - i, currentY
						+ i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkDiagonalPosNegCollision(ChessBoard currentBoard,
			int potentialX, int potentialY, int currentX, int currentY,
			boolean bool) {
		int xDiff;
		if (bool && potentialX > currentX && potentialY < currentY) {
			xDiff = potentialX - currentX;
			for (int i = 1; i < xDiff; i++) {
				Figure fig = currentBoard.getFigureAt(currentX + i, currentY
						- i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkDiagonalNegNegCollision(ChessBoard currentBoard,
			int potentialX, int potentialY, int currentX, int currentY,
			boolean bool) {
		int xDiff;
		if (bool && potentialX < currentX && potentialY < currentY) {
			xDiff = currentX - potentialX;
			for (int i = xDiff-1; i > 0; i--) {
				Figure fig = currentBoard.getFigureAt(currentX - i, currentY
						- i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkDiagonalPosPosCollision(ChessBoard currentBoard,
			int potentialX, int potentialY, int currentX, int currentY,
			boolean bool) {
		int xDiff;
		if (bool && potentialX > currentX && potentialY > currentY) {
			xDiff = potentialX - currentX;
			for (int i = 1; i < xDiff; i++) {
				Figure fig = currentBoard.getFigureAt(currentX + i, currentY
						+ i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		return bool;
	}

	private boolean checkForDiagonalMovement(boolean bool, int xDiff, int yDiff) {
		if (xDiff == 0 || yDiff == 0 || xDiff == yDiff) {
			if (xDiff == yDiff) {
				bool = true;
			} else {
				bool = false;
			}
		}
		return bool;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}

	public Figure deepCopy() {
		Bishop copy = new Bishop(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}

}
