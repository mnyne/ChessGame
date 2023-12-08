package Figures;

import GUI.Sprite;
import Game.ChessBoard;
import Game.GameLog;
import Tools.CoordinateHelper;

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
	/**
	 * Generate an ID based on the given start index.
	 *
	 * @param  startIndex  the starting index for generating the ID
	 * @return             the generated ID
	 */
		private int generateID(int startIndex) {
		return (color+1) * 1000 + (figureType * 100) + startIndex;
	}

	/**
	 * Returns the current index.
	 *
	 * @return the current index
	 */
	public int getCurrentIndex() {
		return coordinateHelper.convertXYtoIndex(currentX, currentY);
	}

	/**
	 * Returns the sprite image.
	 *
	 * @return the sprite image
	 */
	public Image getSprite() {
		return sprite.getImage();
	}

	/**
	 * Get the optical X coordinate.
	 *
	 * @return the converted optical X coordinate
	 */
	public int getOpticalX() {
		return coordinateHelper.convertCoordToOptical(currentX);
	}

	/**
	 * Converts the current Y-coordinate to its optical representation.
	 *
	 * @return the optical representation of the current Y-coordinate
	 */
	public int getOpticalY() {
		return coordinateHelper.convertCoordToOptical(currentY);
	}

	/**
	 * Gets the x-coordinate of the sprite.
	 *
	 * @return the x-coordinate of the sprite
	 */
	public int getSpriteX() {
		int spriteOffset = (TILE_SIZE - sp.getSpriteWidth()) / 2 ;
		return getOpticalX()+spriteOffset;
	}

	/**
	 * Returns the y-coordinate of the sprite on the screen.
	 *
	 * @return the y-coordinate of the sprite
	 */
	public int getSpriteY() {
		int spriteOffset = (TILE_SIZE - sp.getSpriteHeight());
		return getOpticalY()+spriteOffset;
	}

	/**
	 * Retrieves the X position.
	 *
	 * @return the current X position
	 */
	public int getXPosition() {
		return currentX;
	}

	/**
	 * Retrieves the current Y position.
	 *
	 * @return the current Y position
	 */
	public int getYPosition() {
		return currentY;
	}

	/**
	 * Sets a new position for the object.
	 *
	 * @param  newx  the new x-coordinate
	 * @param  newy  the new y-coordinate
	 */
	public void setNewPosition(int newx, int newy) {
		currentX = newx;
		currentY = newy;
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return         	a string representation of the object
	 */
	public String toString() {
		String ts = "id: " + id + ", figureType: " + figureType + ", color:" + color
				+ ", currentX: " + currentX + ", currentY: " + currentY
				+ ", SpriteID: " + sp.getSpriteID();
		return ts;
	}

	/**
	 * Returns the color of the figure.
	 *
	 * @return the color of the figure
	 */
	public int getFigureColor() {
		return color;
	}

	/**
	 * A description of the entire Java function.
	 *
	 * @return int     The ID of the figure.
	 */
	public int getFigureID() {
		return id;
	}

	/**
	 * A function to check if the current figure is equal to the comparison figure.
	 *
	 * @param  comparisonFig  the figure to compare with the current figure
	 * @return                true if the current figure is equal to the comparison figure, false otherwise
	 */
	public boolean equals(Figure comparisonFig) {
		return comparisonFig != null && this.getCurrentIndex() == comparisonFig.getCurrentIndex();
	}
	/**
	 * Returns the figure type.
	 *
	 * @return the figure type
	 */
	public int getFigureType() {
		return figureType;
	}

	/**
	 * Sets the moved status of the object to true.
	 *
	 * @return None
	 */
	public void setMovedStatus() {
		this.hasMoved = true;
	}

	/**
	 * Updates the moved status of the object.
	 *
	 * @param  hasMoved  a boolean indicating whether the object has moved
	 */
	public void updateMovedStatus(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * Checks if the object has been moved.
	 *
	 * @return true if the object has been moved, false otherwise.
	 */
	public boolean hasMoved() {
		return this.hasMoved;
	}

	/**
	 * Sets the en passant status of the object.
	 *
	 * @param  enPassantable  the en passant status to set
	 */
	public void setEnPassantStatus(boolean enPassantable) {
		this.enPassantable = enPassantable;
	}

	/**
	 * Determines if the piece is eligible for en passant.
	 *
	 * @return  true if the piece is eligible for en passant, false otherwise
	 */
	public boolean eligibleForEnPassant() {
		return enPassantable;
	}

	/**
	 * Updates the eligibility for en passant in the game log.
	 *
	 * @param  gameLog  the game log object that needs to be updated
	 */
	public abstract void updateEnPassantEligibility(GameLog gameLog);

	/**
	 * Checks if the move is legal on the chessboard.
	 *
	 * @param  currentBoard     the current state of the chessboard
	 * @param  selectedFigure   the selected figure to move
	 * @param  targetIndex      the target index to move the figure to
	 * @return                  true if the move is legal, false otherwise
	 */
	public abstract boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex);

	/**
	 * Creates a deep copy of the Figure object.
	 *
	 * @return  a deep copy of the Figure object
	 */
	public abstract Figure deepCopy();

}