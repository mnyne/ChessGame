import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import acm.graphics.GImage;
import acm.graphics.GCompound;
import acm.program.GraphicsProgram;

import java.awt.Color;

public class King extends GCompound {
	Game shit = new Game();
	Figure king;
	GImage sprite;
	int x;
	int y;
	int xBeforeMove;
	int yBeforeMove;
	int xAfterMove;
	int yAfterMove;
	int xDiff;
	int yDiff;
	String col;

	public King(int in_x, int in_y, String col2) {
		x = in_x;
		y = in_y;
		col = col2;
		king = new Figure(in_x, in_y, col);
		if (king.isBlack()) {
			sprite = new GImage("graphics/king1.png");
		} else {
			sprite = new GImage("graphics/king.png");
		}
		add(sprite);
	}

	public int getXBoard() {
		xBeforeMove = x * 42;
		return x * 42;
	}

	public int getYBoard() {
		yBeforeMove = y * 42;
		return y * 42;
	}

	public String toString() {
		String s = col + "king, " + this.getX() + ", " + this.getY() + " "
				+ legalIndicator();
		return s;
	}

	public boolean isLegal() {
		transformDifference();
		boolean legal;
		if (xDiff == 1 || yDiff == 1) {
			legal = true;
		} else {
			legal = false;
		}
		if (xDiff == 0 && yDiff == 0) {
			legal = false;
		}
		if (xDiff > 1 || yDiff > 1) {
			legal = false;
		}
		return legal;
	}

	private void transformDifference() {
		int xBeforeAdjusted = xBeforeMove / 42;
		int xAfterAdjusted = xAfterMove / 42;
		xDiff = xBeforeAdjusted - xAfterAdjusted;
		if (xDiff < 0) {
			xDiff = -xDiff;
		}
		int yBeforeAdjusted = yBeforeMove / 42;
		int yAfterAdjusted = yAfterMove / 42;
		yDiff = yBeforeAdjusted - yAfterAdjusted;
		if (yDiff < 0) {
			yDiff = -yDiff;
		}
	}

	public String legalIndicator() {
		if (isLegal()) {
			return "Legal";
		} else {
			return null;
		}
	}

	public Color getColor() {
		xBeforeMove = (int) this.getX();
		yBeforeMove = (int) this.getY();
		return Color.CYAN;
	}

	public double getHeight() {
		xAfterMove = (int) this.getX();
		yAfterMove = (int) this.getY();
		return 1.0;
	}

}