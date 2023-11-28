public class ChessBoard {
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
		int index = convertXYtoIndex(x,y);
		return figures[index];
	}
	
	public Figure getClickedFigure(int x, int y) {
		int index = convertOpticalXYtoIndex(x, y);
		return figures[index];
	}
	
	public int convertXYtoIndex(int x, int y) {
		int index = 0;
		index = (y * 8) + x;
		index = index - 1;
		System.out.println("XYtoIndex " + index);
		return index;
	}
	
	public int convertIndextoX(int index) {
		int x = (index % 8);
		return x;
	}
	
	public int convertIndextoY(int index) {
		int y = (index / 8);
		return y;
	}
	
	public int convertOpticalXYtoIndex(int x, int y) {
		x = x/42 + 1;
		y = y/42;
		int index = (convertXYtoIndex(x, y));
		return index;
	}
	
	public boolean legalMove(Figure figure, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return true;
	}

	public void makeMove(Figure figure, int mouseX, int mouseY) {
		int index = convertOpticalXYtoIndex(mouseX, mouseY);
		figures[index] = figure;
		figure.setNewPosition(convertIndextoX(index), convertIndextoY(index));
	}

	public void removeOldFigure(int xCache, int yCache) {
		int oldindex = convertXYtoIndex(xCache, yCache);
		System.out.println(oldindex);
		figures[oldindex] = null;
	}
}
