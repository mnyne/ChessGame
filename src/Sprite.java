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
			spriteid = spriteid + 6;
		}
	}

	public ImageIcon getSprite(double scaleFactor) {
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
			sprite = new ImageIcon("graphics_deja_view/bishop.png");
			break;
		case 7:
			sprite = new ImageIcon("graphics_deja_view/king.png");
			break;
		case 8:
			sprite = new ImageIcon("graphics_deja_view/knight.png");
			break;
		case 9:
			sprite = new ImageIcon("graphics_deja_view/pawn.png");
			break;
		case 10:
			sprite = new ImageIcon("graphics_deja_view/queen.png");
			break;
		case 11:
			sprite = new ImageIcon("graphics_deja_view/rook.png");
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

		public int getSpriteID() {
		return spriteid;
	}

	public int getRawSpriteWidth() {
		ImageIcon temp = getSprite(1);
		return temp.getIconWidth();
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}

}
