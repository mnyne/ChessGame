public class ChessBoard {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	private Figure[] chessBoard;

	public ChessBoard(int chessBoardLength) {
		chessBoard = new Figure[chessBoardLength];
	}

	public void addFigure(int index, Figure figure) {
		chessBoard[index] = figure;
	}

	public Figure getFigureAtIndex(int index) {
		return chessBoard[index];
	}

	public int getLength() {
		return chessBoard.length;
	}

	public Figure getFigureAt(int x, int y) {
		int index = coordinateHelper.convertXYtoIndex(x, y);
		return chessBoard[index];
	}

	public Figure getClickedFigure(int x, int y) {
		int index = coordinateHelper.convertOpticalXYtoIndex(x, y);
		return chessBoard[index];
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < this.getLength(); i++) {
			Figure fig = getFigureAtIndex(i);
			if (fig != null) {
				s = s + "(" + fig.getFigureID() + ")";
			}
			if (fig == null) {
				s = s + "(XXX)";
			}
		}
		return s;
	}

	public void moveFigure(Figure figure, int targetX, int targetY, GameLog gameLog) {
		int index = coordinateHelper.convertXYtoIndex(targetX, targetY);
		if(getFigureAtIndex(index) != null) {
			gameLog.setMovementTypeForLastEntry(1);
		}
		chessBoard[index] = figure;
		figure.setNewPosition(targetX, targetY);
		figure.setMovedStatus();
	}

	public void removeFigure(int xCache, int yCache) {
		int oldindex = coordinateHelper.convertXYtoIndex(xCache, yCache);
		chessBoard[oldindex] = null;
	}

	public ChessBoard deepCopy() {
		ChessBoard copy = new ChessBoard(this.chessBoard.length);

		for (int i = 0; i < this.chessBoard.length; i++) {
			if (this.chessBoard[i] != null) {
				copy.chessBoard[i] = this.chessBoard[i].deepCopy();
			}
		}

		return copy;
	}

	public void clearBoard() {
		for (int i=0; i< chessBoard.length; i++) {
			this.chessBoard[i] = null;
		}
	}
}
