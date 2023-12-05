import javax.swing.*;
import java.awt.*;

public abstract class Figure {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	private int id;
	private int figureType;
	private int currentX;
	private int currentY;
	private final int color;
	private ImageIcon sprite;
	private Sprite sp;
	private boolean hasMoved = false;
	private boolean enPassantable = false;
	private int SPRITE_SIZE;
	private int TILE_SIZE = 96;
	private double SCALE_FACTOR;


	public Figure(int in_figureType, int color, int startIndex) {
		this.currentX = coordinateHelper.convertIndextoX(startIndex);
		this.currentY = coordinateHelper.convertIndextoY(startIndex);
		this.color = color;
		this.figureType = in_figureType;
		this.id = generateID(startIndex);
		this.sp = new Sprite(this.figureType, this.color);
		this.SPRITE_SIZE = sp.getRawSpriteWidth();
		this.SCALE_FACTOR = TILE_SIZE / SPRITE_SIZE;
		this.sprite = sp.getSprite(SCALE_FACTOR);
	}
	
		// constructor for copying
	public Figure(int in_figureType, int in_color, int in_id, int currentIndex) {
		figureType = in_figureType;
		color = in_color;
		id = in_id;
		currentX = coordinateHelper.convertIndextoX(currentIndex);
		currentY = coordinateHelper.convertIndextoY(currentIndex);
	}
	
	private int generateID(int startIndex) {
		return (color+1) * 1000 + (figureType * 100) + startIndex;
	}
	
	public int getCurrentIndex() {
		return coordinateHelper.convertXYtoIndex(currentX, currentY);
	}

	public Image getSprite() {
		return sprite.getImage();
	}

	public int getOpticalX() {
		return coordinateHelper.convertCoordToOptical(currentX);
	}

	public int getOpticalY() {
		return coordinateHelper.convertCoordToOptical(currentY);
	}

	public int getSpriteX() {
		int spriteOffset = (TILE_SIZE - sp.getSpriteWidth()) / 2 ;
		return getOpticalX()+spriteOffset;
	}
	public int getSpriteY() {
		int spriteOffset = (TILE_SIZE - sp.getSpriteHeight());
		return getOpticalY()+spriteOffset;
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