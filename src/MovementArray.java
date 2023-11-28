public class MovementArray {
	private final int BOARD_LENGTH = 8;
	private boolean[] moveAtIndexAllowed;
	int figureIndex;
	int turn;
	Figure currentFigure;
	ChessBoard currentBoard;
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	LogHelper logHelper = new LogHelper();

	public MovementArray(ChessBoard in_currentBoard, int in_turn) {
		currentBoard = in_currentBoard;
		moveAtIndexAllowed = new boolean[BOARD_LENGTH * BOARD_LENGTH];
		turn = in_turn;
	}
	
	public MovementArray() {
		moveAtIndexAllowed = new boolean[BOARD_LENGTH * BOARD_LENGTH];
	}
	//move to MovementHandler
	public void isLegal(int index, Figure figure) {
		currentFigure = figure;
		moveAtIndexAllowed[index] = isAllowed(index);
	}
	//move to MovementHandler
	//rewrite as separate bool arrays
	public boolean isAllowed(int index) {
		Figure indexfigure = currentBoard.getFigureAtIndex(index);
		boolean allowed = true;
		if (wrongTurn(index)) {
			allowed = false;
		}
		if (isSameFigure(index, indexfigure)) {
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
	//move to MovementHandler
	//rewrite as playerTurnArray
	private boolean wrongTurn(int index) {
		boolean bool = false;
		if(turn % 2 != currentFigure.getFigureColor()) {
			bool = true;
		}
		return bool;
	}
	//move to MovementHandler
	//rewrite as figureMovementArray
	private boolean isInMovement(int index) {
		boolean bool = true;
		int potentialX = coordinateHelper.convertIndextoX(index);
		int potentialY = coordinateHelper.convertIndextoY(index);
		int currentX = currentFigure.getXPosition();
		int currentY = currentFigure.getYPosition();
		int color = currentFigure.getFigureColor();
		// don't need to pass currentX and currentY, can do this.currentX and this.currentY for each figure
		bool = currentFigure.moveIsLegal(potentialX, potentialY, currentX, currentY, color, currentBoard);
		return bool;
	}
	//move to MovementHandler
	//rewrite as figureColorArray
	private boolean isSameColor(int index, Figure indexfigure) {
		boolean bool = false;
		if (indexfigure != null
				&& indexfigure.getFigureColor() == currentFigure
						.getFigureColor()) {
			bool = true;
		}
		return bool;
	}
	//unnecessary, checks for color anyway
	public boolean isSameFigure(int index, Figure indexfigure) {
		boolean bool = false;
		if (currentFigure.equals(indexfigure)) {
			bool = true;
		}
		return bool;
	}

	public boolean moveToIndexAllowed(int index) {
		return moveAtIndexAllowed[index];
	}
	
	public void setIndexToTrue(int index) {
		moveAtIndexAllowed[index] = false;
	}

	public void setIndexToFalse(int index) {
		moveAtIndexAllowed[index] = false;
	}
	public int getLength() {
		return moveAtIndexAllowed.length;
	}
}