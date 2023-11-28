import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import acm.graphics.GImage;
import acm.graphics.GCompound;
import acm.program.GraphicsProgram;

import java.awt.Color;

public class BlackPawn extends GCompound {
	Game shit = new Game();
	Figure blackPawn;
	int x;
	int y;
	int xBeforeMove;
	int yBeforeMove;
	int xAfterMove;
	int yAfterMove;
	int xDiff;
	int yDiff;


	public BlackPawn(int in_x, int in_y) {
		x = in_x;
		y = in_y;
		blackPawn = new Figure(in_x, in_y, "black");
		GImage sprite = new GImage("graphics/pawn1.png");
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
		String s = "black pawn, " + this.getX() + ", " + this.getY() + " " + legalIndicator();
		return s;
	}
	
	public boolean isLegal() {
		transformDifference();
		boolean legal;
		if (xDiff == 0 && yDiff > 0 && yDiff < 2) {
			legal = true;
		} else {
			legal = false;
		}
		if (xDiff == 0 && yDiff == 0) {
			legal = false;
		}
		if (yBeforeMove / 42 == 2 && xDiff == 0 && yDiff > 0 && yDiff == 2) {
			legal = true;
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
		yDiff = yAfterAdjusted - yBeforeAdjusted;
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



//import acm.graphics.GObject;
//import acm.graphics.GRect;
//import acm.graphics.GLabel;
//import acm.graphics.GImage;
//import acm.graphics.GCompound;
//import acm.program.GraphicsProgram;
//import java.awt.Color;
//
//public class BlackPawn extends GCompound {
//	private int x;
//	private int y;
//
//	public BlackPawn(int in_x, int in_y) {
//		x = in_x;
//		y = in_y;
//		GImage sprite = new GImage("graphics/pawn1.png");
//		add(sprite, this.getXBoard(), this.getYBoard());
//	}
//
//	public int getXNotation() {
//		int xt = (int) x;
//		xt = xt + 96;
//		char xc = (char) xt;
//		return xc;
//	}
//
//	public int getYNotation() {
//		return y;
//	}
//	
//	public int getXBoard() {
//		return x * 42;
//	}
//	
//	public int getYBoard() {
//		return y * 42;
//	}
//	
//	public int getRawX() {
//		return x;
//	}
//	
//	public int getRawY() {
//		return y;
//	}
//	
//	public String toString() {
//		String s = "x:" + x +", y:" + y;
//		return s;
//	}
//}
