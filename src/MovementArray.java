public class MovementArray {
	private boolean[] moveAllowed;
	int figureIndex;
	int turn;
	Figure currentFigure;
	ChessBoard currentBoard;
	CoordinateHelper ch = new CoordinateHelper();
	LogHelper lh = new LogHelper();

	public MovementArray(ChessBoard in_currentBoard, int in_turn) {
		currentBoard = in_currentBoard;
		moveAllowed = new boolean[64];
		turn = in_turn;
	}

	public void isLegal(int index, Figure figure) {
		currentFigure = figure;
		moveAllowed[index] = isAllowed(index);
	}

	public boolean isAllowed(int index) {
		Figure indexfigure = currentBoard.getFigureAtIndex(index);
		boolean allowed = true;
		if (wrongTurn(index)) {
			allowed = false;
		}
		if (isSame(index, indexfigure)) {
			allowed = false;
		}
		if (isSameColor(index, indexfigure)) {
			allowed = false;
		}
		if (!isInMovement(index)) {
			allowed = false;
		}
		return allowed;
	}

	private boolean wrongTurn(int index) {
		boolean bool = false;
		if(turn % 2 != currentFigure.getFigureColor()) {
			bool = true;
		}
		return bool;
	}

	private boolean isInMovement(int index) {
		boolean bool = true;
		int potentialX = ch.convertIndextoX(index);
		int potentialY = ch.convertIndextoY(index);
		int currentX = currentFigure.getXPosition();
		int currentY = currentFigure.getYPosition();
		int color = currentFigure.getFigureColor();
		// don't need to pass currentX and currentY, can do this.currentX and this.currentY for each figure
		bool = currentFigure.moveIsLegal(potentialX, potentialY, currentX, currentY, color, currentBoard);
		return bool;
	}

	private boolean isSameColor(int index, Figure indexfigure) {
		boolean bool = false;
		if (indexfigure != null
				&& indexfigure.getFigureColor() == currentFigure
						.getFigureColor()) {
			bool = true;
		}
		return bool;
	}

	public boolean isSame(int index, Figure indexfigure) {
		boolean bool = false;
		if (currentFigure.equals(indexfigure)) {
			bool = true;
		}
		return bool;
	}

	public boolean isIndexAllowed(int index) {
		return moveAllowed[index];
	}

	public void setIndexToFalse(int index) {
		moveAllowed[index] = false;
	}
}