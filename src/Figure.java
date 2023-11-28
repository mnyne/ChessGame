import java.awt.Color;
import java.awt.Graphics;

import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.graphics.GImage;

public class Figure extends GObject {
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

	public Figure(int in_figureType, String s_color, String startingPosition) {
		startX = convertStringPositionToX(startingPosition);
		startY = convertStringPositionToY(startingPosition);
		currentX = startX;
		currentY = startY;
		figureType = in_figureType;
		color = convertColorStringToInteger(s_color);
		id = generateID(color);
		sp = new Sprite(this.figureType, this.color);
		sprite = sp.getSprite();
	}

	private String generateID(int in_color) {
		String newid = in_color + "" + figureType + "" + startX;
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

	private Color convertIntegerToColor(int in_color) {
		if (in_color == 1) {
			return Color.BLACK;
		} else {
			return Color.WHITE;
		}
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
	
	public double getGraphicX() {
		double graphicX = currentX * 42;
		return graphicX;
	}
	
	public double getGraphicY() {
		double graphicY = currentY * 42;
		return graphicY;
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
		String ts = "id: " + id + ", figureType: " + figureType + ", currentX: " + currentX + ", currentY: " + currentY + ", SpriteID: " + sp.getSpriteID();
		return ts;
	}
	
	public String toShortString() {
		String ts = figureType + "";
		return ts;
	}
	
}