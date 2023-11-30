public class Queen extends Figure {
	public Queen(String s_color, String startingPosition) {
		super(4, s_color, startingPosition);
	}

	public Queen(int color) {
		super(4, color);
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
		bool = checkDiagonalPosPosCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkDiagonalNegNegCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkDiagonalPosNegCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkDiagonalNegPosCollision(currentBoard, potentialX,
				potentialY, currentX, currentY, bool);
		bool = checkHorizontalCollisionPos(currentBoard, potentialX, currentX,
				currentY, potentialY, bool);
		bool = checkHorizontalCollisionNeg(currentBoard, potentialX, currentX,
				currentY, potentialY, bool);
		bool = checkVerticalCollisionPos(currentBoard, potentialY, currentX,
				currentY, potentialX, bool);
		bool = checkVerticalCollisionNeg(currentBoard, potentialY, currentX, potentialX, 
				currentY, bool);
		return bool;

	}

	private boolean checkForDiagonalOrOrthogonalMove(int xDiff, int yDiff) {
		boolean bool;
		if (xDiff == 0 || yDiff == 0 || xDiff == yDiff) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}
	
	private boolean checkVerticalCollisionNeg(ChessBoard currentBoard,
			int potentialY, int currentX, int potentialX, int currentY, boolean bool) {
		int yDiff;
		if (bool && potentialY < currentY && potentialX == currentX) {
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
			int potentialY, int currentX, int currentY, int potentialX, boolean bool) {
		int yDiff;
		if (bool && potentialY > currentY && potentialX == currentX) {
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
			int potentialX, int currentX, int currentY, int potentialY, boolean bool) {
		int xDiff;
		if (bool && potentialX < currentX && potentialY == currentY) {
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
			int potentialX, int currentX, int currentY, int potentialY, boolean bool) {
		int xDiff;
		if (bool && potentialX > currentX && potentialY == currentY) {
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

	public Figure deepCopy() {
		Queen copy = new Queen(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
