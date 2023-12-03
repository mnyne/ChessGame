import java.awt.Graphics;

import acm.graphics.GObject;
import acm.graphics.GRectangle;
import acm.graphics.GImage;

public abstract class Figure extends GObject {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	private int id;
	private int figureType;
	private int startIndex;
	//todo: check if you actually need startIndex, if not just pass to ID directly
	private int currentX;
	private int currentY;
	private final int color;
	private GImage sprite;
	private Sprite sp;
	private boolean hasMoved = false;
	private boolean enPassantable = false;

	public GRectangle getBounds() {
		// in because GObject stuff...
		return new GRectangle(this.getX(), this.getY(), 42, 42);
	}

	public void paint(Graphics arg0) {
		// in because GObject stuff...
	}
	
	public Figure(int in_figureType, int color, int startIndex) {
		this.startIndex = startIndex;
		this.currentX = coordinateHelper.convertIndextoX(startIndex);
		this.currentY = coordinateHelper.convertIndextoY(startIndex);
		this.color = color;
		this.figureType = in_figureType;
		this.id = generateID();
		this.sp = new Sprite(this.figureType, this.color);
		this.sprite = sp.getSprite();
	}
	
		// constructor for copying
	public Figure(int in_figureType, int in_color, int in_id, int currentIndex) {
		figureType = in_figureType;
		color = in_color;
		id = in_id;
		currentX = coordinateHelper.convertIndextoX(currentIndex);
		currentY = coordinateHelper.convertIndextoY(currentIndex);
	}
	
	private int generateID() { 
		return (color+1) * 1000 + (figureType * 100) + startIndex;
	}
	
	public int getCurrentIndex() {
		return coordinateHelper.convertXYtoIndex(currentX, currentY);
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
		String ts = "id: " + id + ", figureType: " + figureType + ", color:" + color
				+ ", currentX: " + currentX + ", currentY: " + currentY
				+ ", SpriteID: " + sp.getSpriteID();
		return ts;
	}
 
	public int getFigureColor() {
		return color;
	}

	public int getFigureID() {
		return id;
	}

	public boolean equals(Figure comparisonFig) {
		return comparisonFig != null && this.getCurrentIndex() == comparisonFig.getCurrentIndex();
	}

	public int getFigureType() {
		return figureType;
	}

	public void setMovedStatus() {
		this.hasMoved = true;
	}
	
	public void updateMovedStatus(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
	
	public void setEnPassantStatus(boolean enPassantable) {
		this.enPassantable = enPassantable;
	}
	
	public boolean eligibleForEnPassant() {
		return enPassantable;
	}
	
	public abstract void updateEnPassantEligibility(GameLog gameLog);

	public abstract boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex);

	public abstract Figure deepCopy();

}