package Tools;

public class CoordinateHelper {

	private final int TILE_SIZE = 96;
	private final int BOARD_SIZE = 8;
	private final int ASCII_LETTER_DIFF = 97;
	private final int ASCII_NUMBER_DIFF = 48;

	public CoordinateHelper() {

	}

	// shorten functions, update code to use indexes whenever possible and get
	// rid of unnecessary functions

	/**
	 * Converts the given x and y coordinates to an index value.
	 *
	 * @param  x  the x coordinate
	 * @param  y  the y coordinate
	 * @return    the index value
	 */
	public int convertXYtoIndex(int x, int y) {
		return (y * BOARD_SIZE) + x;
	}

	/**
	 * Converts the given index to its corresponding x-coordinate value on the board.
	 *
	 * @param  index  the index to be converted
	 * @return        the x-coordinate value on the board
	 */
	public int convertIndextoX(int index) {
		return (index % BOARD_SIZE);
	}

	/**
	 * Converts the given index to the corresponding value of Y coordinate.
	 *
	 * @param  index  the index to be converted
	 * @return        the value of Y coordinate
	 */
	public int convertIndextoY(int index) {
		return (index / BOARD_SIZE);
	}

	/**
	 * Converts the given index to the corresponding optical x-coordinate.
	 *
	 * @param  index  the index to be converted
	 * @return        the optical x-coordinate
	 */
	public int convertIndextoOpticalX(int index) {
		return (index % BOARD_SIZE) * TILE_SIZE;
	}

	/**
	 * Converts the given index to the optical Y coordinate on the board.
	 *
	 * @param  index  the index to be converted
	 * @return        the optical Y coordinate
	 */
	public int convertIndextoOpticalY(int index) {
		return (index / BOARD_SIZE) * TILE_SIZE;
	}

	/**
	 * Converts a coordinate to its optical representation.
	 *
	 * @param  coordinate  the coordinate value to be converted
	 * @return             the optical representation of the coordinate
	 */
	public int convertCoordToOptical(int coordinate) {
		return coordinate * TILE_SIZE;
	}

	/**
	 * Converts an optical coordinate to a regular coordinate.
	 *
	 * @param  coordinate  the optical coordinate to be converted
	 * @return             the regular coordinate
	 */
	public int convertOpticalToRegular(int coordinate) {
		return coordinate / TILE_SIZE;
	}

	/**
	 * Converts the optical X and Y coordinates to an index.
	 *
	 * @param  x  the optical X coordinate
	 * @param  y  the optical Y coordinate
	 * @return    the index corresponding to the optical coordinates
	 */
	public int convertOpticalXYtoIndex(int x, int y) {
		return convertXYtoIndex(convertOpticalToRegular(x),convertOpticalToRegular(y));
	}

	/**
	 * Calculates the adjusted difference between the target coordinate and the current coordinate.
	 *
	 * @param  targetCoordinate  the target coordinate to compare with the current coordinate
	 * @param  currentCoordinate the current coordinate to compare with the target coordinate
	 * @return                   the adjusted difference between the target coordinate and the current coordinate
	 */
	public int getAdjustedDiff(int targetCoordinate, int currentCoordinate) {
		int diff = currentCoordinate - targetCoordinate;
		if (diff < 0) {
			diff = -diff;
		}
		return diff;
	}

	/**
	 * Calculate the difference between the target coordinate and the current coordinate.
	 *
	 * @param  targetCoordinate  the target coordinate value
	 * @param  currentCoordinate the current coordinate value
	 * @return                   the difference between the current and target coordinates
	 */
	public int getRawDiff(int targetCoordinate, int currentCoordinate) {
		return currentCoordinate - targetCoordinate;
	}

	/**
	 * Converts the given x and y coordinates to a notation string.
	 *
	 * @param  xCoordinate  the x coordinate of the point
	 * @param  yCoordinate  the y coordinate of the point
	 * @return              the notation string representing the point
	 */
	public String convertCoordsToNotationString(int xCoordinate, int yCoordinate) {
		xCoordinate += ASCII_LETTER_DIFF;
		char xNotation = (char) xCoordinate;
		int yNotation = BOARD_SIZE - yCoordinate;
		return xNotation + "" + yNotation;
	}

	/**
	 * Converts a given notation to the corresponding Y value on the board.
	 *
	 * @param  notation  the notation to be converted
	 * @return           the Y value on the board
	 */
	public int convertNotationToY(String notation) {
		char c = notation.charAt(1);
		int i = (int) c - ASCII_NUMBER_DIFF;
		int convertY = BOARD_SIZE - i;
		return convertY;
	}

	/**
	 * Converts a notation string to an integer value.
	 *
	 * @param  notation  the notation string to be converted
	 * @return           the converted integer value
	 */
	public int convertNotationToX(String notation) {
		char c = notation.charAt(0);
		int convertX = (int) c - ASCII_LETTER_DIFF;
		return convertX;
	}

	/**
	 * Converts a given notation to an index.
	 *
	 * @param  notation  the notation to convert
	 * @return           the corresponding index
	 */
	public int convertNotationToIndex(String notation) {
		return convertXYtoIndex(convertNotationToX(notation), convertNotationToY(notation));
	}
}
