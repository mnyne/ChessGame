public class ChessBoard {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	private Figure[] figures;

	public ChessBoard(int numberOfFigures) {
		figures = new Figure[numberOfFigures];
	}

	public void addFigure(int index, Figure figure) {
		figures[index] = figure;
	}

	public Figure getFigureAtIndex(int index) {
		return figures[index];
	}

	public int getLength() {
		return figures.length;
	}

	public Figure getFigureAt(int x, int y) {
		int index = coordinateHelper.convertXYtoIndex(x, y);
		return figures[index];
	}

	public Figure getClickedFigure(int x, int y) {
		int index = coordinateHelper.convertOpticalXYtoIndex(x, y);
		return figures[index];
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

	public void moveFigure(Figure figure, int targetX, int targetY) {
		int index = coordinateHelper.convertXYtoIndex(targetX, targetY);
		figures[index] = figure;
		figure.setNewPosition(targetX, targetY);
	}

	public void removeFigure(int xCache, int yCache) {
		int oldindex = coordinateHelper.convertXYtoIndex(xCache, yCache);
		figures[oldindex] = null;
	}

	public ChessBoard deepCopy() {
		ChessBoard copy = new ChessBoard(this.figures.length);

		for (int i = 0; i < this.figures.length; i++) {
			if (this.figures[i] != null) {
				copy.figures[i] = this.figures[i].deepCopy();
			}
		}

		return copy;
	}
}
