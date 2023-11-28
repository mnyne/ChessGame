import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ChessBoard {
	CoordinateHelper ch = new CoordinateHelper();
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
		int index = ch.convertXYtoIndex(x, y);
		return figures[index];
	}

	public Figure getClickedFigure(int x, int y) {
		int index = ch.convertOpticalXYtoIndex(x, y);
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

	public void makeMove(Figure figure, int targetX, int targetY) {
		int index = ch.convertXYtoIndex(targetX, targetY);
		figures[index] = figure;
		figure.setNewPosition(targetX, targetY);
	}

	public void logMove(Figure figure, int targetIndex) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("chess_board_log.txt", true))) {
            int oldX = figure.getXPosition();
            int oldY = figure.getYPosition();
            String id = figure.getFigureID();
            int type = figure.getFigureType();
            int color = figure.getFigureColor();
            int newX = ch.convertIndextoX(targetIndex);
            int newY = ch.convertIndextoY(targetIndex);

            // Schreibe die Informationen in die Textdatei
            writer.println(String.format("Move: ID=%s, Type=%d, Color=%d, OldX=%d, OldY=%d, NewX=%d, NewY=%d",
                    id, type, color, oldX, oldY, newX, newY));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void removeOldFigure(int xCache, int yCache) {
		int oldindex = ch.convertXYtoIndex(xCache, yCache);
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
