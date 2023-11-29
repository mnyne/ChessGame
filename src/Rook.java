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
		if (xDiff == 0 || yDiff == 0) {
			bool = true;
		} else {
			bool = false;
		}
		if (xDiff == 0 && yDiff == 0) {
			bool = false;
		}

		// hozCollisionPos
		if (bool && potentialX > currentX) {
			xDiff = potentialX - currentX;
			for (int i = 1; i < xDiff; i++) {
				Figure fig = currentBoard.getFigureAt(currentX + i, currentY);
				if (fig != null) {
					bool = false;
				}
			}
		}
		// hozCollisionNeg
		if (bool && potentialX < currentX) {
			xDiff = currentX - potentialX;
			for (int i = xDiff - 1; i > 0; i--) {
				Figure fig = currentBoard.getFigureAt(currentX - i, currentY);
				if (fig != null) {
					bool = false;
				}
			}
		}
		// vertCollisionPos
		if (bool && potentialY > currentY) {
			yDiff = potentialY - currentY;
			for (int i = 1; i < yDiff; i++) {
				Figure fig = currentBoard.getFigureAt(currentX, currentY + i);
				if (fig != null) {
					bool = false;
				}
			}
		}
		// vertCollisionNeg
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

	public Figure deepCopy() {
		Rook copy = new Rook(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		copy.updateMovedStatus(this.hasMovedStatus());
		return copy;
	}
	
	public void updateEnPassantEligibility(GameLog gameLog) {
	}
}
