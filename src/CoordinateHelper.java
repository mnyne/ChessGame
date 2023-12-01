public class CoordinateHelper {

	private final int TILE_SIZE = 42;
	private final int BOARD_SIZE = 8;
	private final int ASCII_LETTER_DIFF = 97;
	private final int ASCII_NUMBER_DIFF = 48;

	public CoordinateHelper() {

	}

	// shorten functions, update code to use indexes whenever possible and get
	// rid of unnecessary functions
	public int convertXYtoIndex(int x, int y) {
		int index = 0;
		index = (y * BOARD_SIZE) + x;
		return index;
	}

	public int convertIndextoX(int index) {
		int x = (index % BOARD_SIZE);
		return x;
	}

	public int convertIndextoY(int index) {
		int y = (index / BOARD_SIZE);
		return y;
	}

	public int convertIndextoOpticalX(int index) {
		int x = (index % BOARD_SIZE);
		x = x * TILE_SIZE;
		return x;
	}

	public int convertIndextoOpticalY(int index) {
		int y = (index / BOARD_SIZE);
		y = y * TILE_SIZE;
		return y;
	}

	public int convertCoordToOptical(int coordinate) {
		return coordinate * TILE_SIZE;
	}

	public int convertOpticalToRegular(int coordinate) {
		return coordinate / TILE_SIZE;
	}

	public int convertOpticalXYtoIndex(int x, int y) {
		int index = (convertXYtoIndex(convertOpticalToRegular(x),
				convertOpticalToRegular(y)));
		return index;
	}

	public int getAdjustedDiff(int potential, int current) {
		int diff = current - potential;
		if (diff < 0) {
			diff = -diff;
		}
		return diff;
	}

	public int getRawDiff(int potential, int current) {
		int diff = current - potential;
		return diff;
	}

	public String convertCoordsToNotationString(int x, int y) {
		x += ASCII_LETTER_DIFF;
		char convX = (char) x;
		int convY = BOARD_SIZE - y;
		return convX + "" + convY;
	}

	public int convertNotationToY(String notation) {
		char c = notation.charAt(1);
		int i = (int) c - ASCII_NUMBER_DIFF;
		int convertY = BOARD_SIZE - i;
		return convertY;
	}

	public int convertNotationToX(String notation) {
		char c = notation.charAt(0);
		int convertX = (int) c - ASCII_LETTER_DIFF;
		return convertX;
	}
}
