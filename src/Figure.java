import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Graphics;

import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.graphics.GImage;

public abstract class Figure extends GObject {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	LogHelper logHelper = new LogHelper();
	private String id;
	private int figureType;
	private int startX;
	private int startY;
	private int currentX;
	private int currentY;
	private int color;
	private GImage sprite;
	private Sprite sp;

	public GRectangle getBounds() {
		return new GRectangle(this.getX(), this.getY(), 42, 42);
	}

	public void paint(Graphics arg0) {
	}
// update to grab color, x and y as is instead of using strings to allow for easier copying
// remove starting X from ID or find way to keep ID when trading in a pawn
	public Figure(int in_figureType, String s_color, String startingPosition) {
		startX = convertStringPositionToX(startingPosition);
		startY = convertStringPositionToY(startingPosition);
		currentX = startX;
		currentY = startY;
		figureType = in_figureType;
		color = convertColorStringToInteger(s_color);
		id = generateID();
		sp = new Sprite(this.figureType, this.color);
		sprite = sp.getSprite();
	}
// update for each class to include x and y coords to enable logging for copy
	public Figure(int in_figureType, int in_color) {
		figureType = in_figureType;
		color = in_color;
		id = generateID();
	}

	private String generateID() {
		String newid = color + "" + figureType + "" + startX;
		return newid;
	}

	private int convertStringPositionToY(String startingPosition) {
		char c = startingPosition.charAt(1);
		int i = (int) c - 48;
		int convertY = 8 - i;
		return convertY;
	}

	private int convertStringPositionToX(String startingPosition) {
		char c = startingPosition.charAt(0);
		int convertX = (int) c - 97;
		return convertX;
	}

	public int convertColorStringToInteger(String s_color) {
		int int_color;
		if (s_color.equals("black")) {
			int_color = 1;
		} else {
			int_color = 0;
		}

		return int_color;
	}

	public GImage getSprite() {
		return sprite;
	}

	public int getOpticalX() {
		return coordinateHelper.convertCoordToOptical(currentX);
	}

	public int getOpticalY() {
		return coordinateHelper.convertCoordToOptical(currentY);
	}

	public int getXPosition() {
		return currentX;
	}

	public int getYPosition() {
		return currentY;
	}

	public void setNewPosition(int newx, int newy) {
		currentX = newx;
		currentY = newy;
	}

	public String toString() {
		String ts = "id: " + id + ", figureType: " + figureType
				+ ", currentX: " + currentX + ", currentY: " + currentY
				+ ", SpriteID: " + sp.getSpriteID();
		return ts;
	}

	public String toShortString() {
		String ts = figureType + "";
		return ts;
	}

	public boolean hasMoved() {
		//update when LogArray is ready
		String logFilePath = "chess_board_log.txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(
				logFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("ID=" + id)) {
					// Die ID wurde in der Log-Datei gefunden, was darauf
					// hinweist, dass die Figur bewegt wurde
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Die ID wurde nicht gefunden, die Figur wurde also noch nicht bewegt
		return false;
	}

	public int getFigureColor() {
		return color;
	}

	public String getFigureID() {
		return id;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Figure otherFigure = (Figure) obj;
		// Vergleiche die relevanten Eigenschaften der Figuren
		return this.getXPosition() == otherFigure.getXPosition()
				&& this.getYPosition() == otherFigure.getYPosition();
	}

	public int getFigureType() {
		return figureType;
	}
	
	public abstract boolean isEligibleForEnPassant();

	public abstract boolean moveIsLegal(int potentialX, int potentialY,
			int currentX, int currentY, int color, ChessBoard currentBoard);

	public abstract Figure deepCopy();

}