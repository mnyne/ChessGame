package GUI;

import javax.swing.*;
import java.awt.*;

public class Sprite {
	ImageIcon sprite;
	int spriteid;
	int spriteWidth;
	int spriteHeight;
	private final int TILE_SIZE = 96;

	// 0 black bishop
	// 1 black king
	// 2 black knight
	// 3 black pawn
	// 4 black queen
	// 5 black rook

	// 6 white bishop
	// 7 white king
	// 8 white knight
	// 9 white pawn
	// 10 white queen
	// 11 white rook
	
	//use ID instead of spriteID to determine which graphic to use

	public Sprite(int figureType, int color) {
		spriteid = figureType;
		if (color == 0) {
			spriteid = spriteid + 1000;
		}
	}

	/**
	 * Retrieves the sprite image for the given scale factor.
	 *
	 * @param  scaleFactor   the scale factor to apply to the sprite image
	 * @return         		the ImageIcon representing the sprite image
	 */
	public ImageIcon getSprite(double scaleFactor) {
		//todo: array
		Image temp;
		switch (spriteid) {
		case 0:
			sprite = new ImageIcon("graphics_deja_view/bishop1.png");
			break;
		case 1:
			sprite = new ImageIcon("graphics_deja_view/king1.png");
			break;
		case 2:
			sprite = new ImageIcon("graphics_deja_view/knight1.png");
			break;
		case 3:
			sprite = new ImageIcon("graphics_deja_view/pawn1.png");
			break;
		case 4:
			sprite = new ImageIcon("graphics_deja_view/queen1.png");
			break;
		case 5:
			sprite = new ImageIcon("graphics_deja_view/rook1.png");
			break;
		case 6:
			sprite = new ImageIcon("graphics_deja_view/alfil1.png");
			break;
		case 7:
			sprite = new ImageIcon("graphics_deja_view/ferz1.png");
			break;
		case 8:
			sprite = new ImageIcon("graphics/pawn1.png");
			break;
		case 1000:
			sprite = new ImageIcon("graphics_deja_view/bishop.png");
			break;
		case 1001:
			sprite = new ImageIcon("graphics_deja_view/king.png");
			break;
		case 1002:
			sprite = new ImageIcon("graphics_deja_view/knight.png");
			break;
		case 1003:
			sprite = new ImageIcon("graphics_deja_view/pawn.png");
			break;
		case 1004:
			sprite = new ImageIcon("graphics_deja_view/queen.png");
			break;
		case 1005:
			sprite = new ImageIcon("graphics_deja_view/rook.png");
			break;
		case 1006:
			sprite = new ImageIcon("graphics_deja_view/alfil.png");
			break;
		case 1007:
			sprite = new ImageIcon("graphics_deja_view/ferz.png");
			break;
		case 1008:
			sprite = new ImageIcon("graphics/pawn.png");
			break;
		default:
			sprite = new ImageIcon("graphics_deja_view/bishop.png");
			break;
		}
		double scaledWidth = (int) sprite.getIconWidth() * scaleFactor;
		double scaledHeight = (int) sprite.getIconHeight() * scaleFactor;
		spriteWidth = (int) scaledWidth;
		spriteHeight = (int) scaledHeight;
		temp = sprite.getImage().getScaledInstance(spriteWidth, spriteHeight, Image.SCALE_DEFAULT);
		sprite = new ImageIcon(temp);
		return sprite;
	}

	/**
	 * Retrieves the sprite ID.
	 *
	 * @return the sprite ID
	 */
		public int getSpriteID() {
		return spriteid;
	}

	/**
	 * Retrieves the width of the raw sprite.
	 *
	 * @return the width of the raw sprite
	 */
	public int getRawSpriteWidth() {
		ImageIcon temp = getSprite(1);
		return temp.getIconWidth();
	}

	/**
	 * Gets the width of the sprite.
	 *
	 * @return the width of the sprite
	 */
	public int getSpriteWidth() {
		return spriteWidth;
	}

	/**
	 * Retrieves the height of the sprite.
	 *
	 * @return the height of the sprite
	 */
	public int getSpriteHeight() {
		return spriteHeight;
	}

}
