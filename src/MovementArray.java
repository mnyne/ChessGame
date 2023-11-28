public class MovementArray {
	private final int BOARD_LENGTH = 8;
	private boolean[] moveAtIndexAllowed;
	int figureIndex;
	int turn;
	Figure currentFigure;
	ChessBoard currentBoard;
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	LogHelper logHelper = new LogHelper();

	public MovementArray() {
		moveAtIndexAllowed = new boolean[BOARD_LENGTH * BOARD_LENGTH];
	}

	public boolean moveAtIndexAllowed(int index) {
		return moveAtIndexAllowed[index];
	}
	
	public void setIndexToTrue(int index) {
		moveAtIndexAllowed[index] = true;
	}

	public void setIndexToFalse(int index) {
		moveAtIndexAllowed[index] = false;
	}
	public int getLength() {
		return moveAtIndexAllowed.length;
	}
}