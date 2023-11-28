import acm.graphics.GRect;
import java.awt.Color;

public class BoardTile {
	private int x;
	private int y;
	private Color col;

public BoardTile(char in_x, int in_y, Color in_col) {
	x = (int) in_x;
	y = in_y;
	col = in_col;
}

public GRect drawTile(int x, int y) {
	GRect tile = new GRect(25,25);
	tile.setFilled(true);
	tile.setColor(col);
	return tile;
}
}