import javax.swing.*;
import java.awt.*;

public class Sprite {
	ImageIcon sprite;
	int spriteid;

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

	public ImageIcon getSprite() {
		switch (spriteid) {
		case 0:
			sprite = new ImageIcon("graphics/bishop1.png");
			break;
		case 1:
			sprite = new ImageIcon("graphics/king1.png");
			break;
		case 2:
			sprite = new ImageIcon("graphics/knight1.png");
			break;
		case 3:
			sprite = new ImageIcon("graphics/pawn1.png");
			break;
		case 4:
			sprite = new ImageIcon("graphics/queen1.png");
			break;
		case 5:
			sprite = new ImageIcon("graphics/rook1.png");
			break;
		case 6:
			sprite = new ImageIcon("graphics/bishop.png");
			break;
		case 7:
			sprite = new ImageIcon("graphics/king.png");
			break;
		case 8:
			sprite = new ImageIcon("graphics/knight.png");
			break;
		case 9:
			sprite = new ImageIcon("graphics/pawn.png");
			break;
		case 10:
			sprite = new ImageIcon("graphics/queen.png");
			break;
		case 11:
			sprite = new ImageIcon("graphics/rook.png");
			break;
		default:
			sprite = new ImageIcon("graphics/bishop.png");
			break;
		}
		return sprite;
	}

		public int getSpriteID() {
		return spriteid;
	}

}
