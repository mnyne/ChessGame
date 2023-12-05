public class CoordinateHelper {

	private final int TILE_SIZE = 96;
	private final int BOARD_SIZE = 8;
	private final int ASCII_LETTER_DIFF = 97;
	private final int ASCII_NUMBER_DIFF = 48;

	public CoordinateHelper() {

	}

	// shorten functions, update code to use indexes whenever possible and get
	// rid of unnecessary functions
	public int convertXYtoIndex(int x, int y) {
		return (y * BOARD_SIZE) + x;
	}

	public int convertIndextoX(int index) {
		return (index % BOARD_SIZE);
	}

	public int convertIndextoY(int index) {
		return (index / BOARD_SIZE);
	}

	public int convertIndextoOpticalX(int index) {
		return (index % BOARD_SIZE) * TILE_SIZE;
	}

	public int convertIndextoOpticalY(int index) {
		return (index / BOARD_SIZE) * TILE_SIZE;
	}

	public int convertCoordToOptical(int coordinate) {
		return coordinate * TILE_SIZE;
	}

	public int convertOpticalToRegular(int coordinate) {
		return coordinate / TILE_SIZE;
	}

	public int convertOpticalXYtoIndex(int x, int y) {
		return convertXYtoIndex(convertOpticalToRegular(x),convertOpticalToRegular(y));
	}

	public int getAdjustedDiff(int targetCoordinate, int currentCoordinate) {
		int diff = currentCoordinate - targetCoordinate;
		if (diff < 0) {
			diff = -diff;
		}
		return diff;
	}

	public int getRawDiff(int targetCoordiante, int currentCoordinate) {
		return currentCoordinate - targetCoordiante;
	}

	public String convertCoordsToNotationString(int xCoordinate, int yCoordinate) {
		xCoordinate += ASCII_LETTER_DIFF;
		char xNotation = (char) xCoordinate;
		int yNotation = BOARD_SIZE - yCoordinate;
		return xNotation + "" + yNotation;
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

	public int convertNotationToIndex(String notation) {
		return convertXYtoIndex(convertNotationToX(notation), convertNotationToY(notation));
	}
}
