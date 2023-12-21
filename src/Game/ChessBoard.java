package Game;

import Figures.Figure;
import Tools.CoordinateHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ChessBoard {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	private Figure[] chessBoard;

	public ChessBoard(int chessBoardLength) {
		chessBoard = new Figure[chessBoardLength];
	}

	/**
	 * Adds a figure to the chess board at the specified index.
	 *
	 * @param index  the index at which to add the figure
	 * @param figure the figure to be added
	 */
	public void addFigure(int index, Figure figure) {
		chessBoard[index] = figure;
	}

	/**
	 * Retrieves the figure at the specified index in the chess board.
	 *
	 * @param index the index of the figure to retrieve
	 * @return the figure at the specified index
	 */
	public Figure getFigureAtIndex(int index) {
		return chessBoard[index];
	}

	/**
	 * Returns the length of the chess board.
	 *
	 * @return the length of the chess board
	 */
	public int getLength() {
		return chessBoard.length;
	}

	/**
	 * Retrieves the figure located at the specified coordinates.
	 *
	 * @param x the x-coordinate of the position
	 * @param y the y-coordinate of the position
	 * @return the figure at the specified coordinates
	 */
	public Figure getFigureAt(int x, int y) {
		int index = coordinateHelper.convertXYtoIndex(x, y);
		return chessBoard[index];
	}

	/**
	 * Retrieves the figure located at the specified coordinates.
	 *
	 * @param x the x-coordinate of the position
	 * @param y the y-coordinate of the position
	 * @return the figure at the specified coordinates
	 */
	public Figure getClickedFigure(int x, int y) {
		int index = coordinateHelper.convertOpticalXYtoIndex(x, y);
		return chessBoard[index];
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return a string representation of the object
	 */
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

	/**
	 * Moves a given figure to the specified target coordinates on the chessboard.
	 *
	 * @param figure  the figure to be moved
	 * @param targetX the target X coordinate
	 * @param targetY the target Y coordinate
	 * @param gameLog the game log to record the movement
	 */
	public void moveFigure(Figure figure, int targetX, int targetY, GameLog gameLog) {
		int index = coordinateHelper.convertXYtoIndex(targetX, targetY);
		if (getFigureAtIndex(index) != null) {
			gameLog.setMovementTypeForLastEntry(1);
		}
		chessBoard[index] = figure;
		figure.setNewPosition(targetX, targetY);
		figure.setMovedStatus();
	}

	/**
	 * Removes a figure from the chess board at the specified coordinates.
	 *
	 * @param xCache the x-coordinate of the figure to be removed
	 * @param yCache the y-coordinate of the figure to be removed
	 */
	public void removeFigure(int xCache, int yCache) {
		int oldindex = coordinateHelper.convertXYtoIndex(xCache, yCache);
		chessBoard[oldindex] = null;
	}

	/**
	 * Creates a deep copy of the ChessBoard object.
	 *
	 * @return Returns a new ChessBoard object that is a deep copy of the original.
	 */
	public ChessBoard deepCopy() {
		ChessBoard copy = new ChessBoard(this.chessBoard.length);

		for (int i = 0; i < this.chessBoard.length; i++) {
			if (this.chessBoard[i] != null) {
				copy.chessBoard[i] = this.chessBoard[i].deepCopy();
			}
		}

		return copy;
	}

	/**
	 * Clears the chess board by setting all positions to null.
	 *
	 * @return None
	 */
	public void clearBoard() {
		for (int i = 0; i < chessBoard.length; i++) {
			this.chessBoard[i] = null;
		}
	}

	public ArrayList<Figure> search(int figureType, int figureColor) {
		ArrayList<Figure> figures = new ArrayList<Figure>();
		for (int i = 0; i< chessBoard.length; i++) {
			Figure fig = getFigureAtIndex(i);
			if (fig != null && fig.getFigureColor() == figureColor && fig.getFigureType() == figureType) {
				figures.add(fig);
			}
		}
		return figures;
	}

	public ArrayList<Figure> search(String all, int figureColor) {
		if (all.equals("all")) {
			ArrayList<Figure> figures = new ArrayList<Figure>();
			for (int i = 0; i < chessBoard.length; i++) {
				Figure fig = getFigureAtIndex(i);
				if (fig != null && fig.getFigureColor() == figureColor) {
					figures.add(fig);
				}
			}
			return figures;
		}
		else {
			return null;
		}
	}
}
