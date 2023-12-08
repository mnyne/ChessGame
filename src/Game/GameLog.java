package Game;

import Figures.Figure;
import Tools.CoordinateHelper;

import java.util.ArrayList;

public class GameLog {

	private final int ASCII_NUMBER_DIFF = 48;
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	
	ArrayList<String> gameLog;
	boolean loggingEnabled = true;

	public GameLog() {
		gameLog = new ArrayList<>();
	}

	/**
	 * Adds an entry to the game log.
	 *
	 * @param  entry  the entry to be added to the game log
	 */
	public void addEntry(String entry) {
		gameLog.add(entry);
	}

	/**
	 * Logs the move of a figure.
	 *
	 * @param  figure       the figure object to be moved
	 * @param  targetIndex  the index of the target position
	 */
	public void logMove(Figure figure, int targetIndex) {
		if (loggingEnabled) {
			int oldX = figure.getXPosition();
			int oldY = figure.getYPosition();
			String id = padToSixDigits(figure.getFigureID());
			String type = padToThreeDigits(figure.getFigureType());
			int color = figure.getFigureColor();
			int newX = coordinateHelper.convertIndextoX(targetIndex);
			int newY = coordinateHelper.convertIndextoY(targetIndex);
			addEntry("Move: ID=" + id + ", Type=" + type + ", Color=" + color
					+ ", OldX=" + oldX + ", OldY=" + oldY + ", NewX=" + newX
					+ ", NewY=" + newY + ", MovementType = 0");
		}
	}

	/**
	 * Pad the given value to three digits.
	 *
	 * @param  figureType  the value to be padded
	 * @return             the value padded to three digits
	 */
	private String padToThreeDigits(int figureType) {
		String s = "" + figureType;
		if(s.length() == 1) {
			s = "00" + s;
		}
		if(s.length() == 2) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * Pads a given figure ID to six digits.
	 *
	 * @param  figureID	the figure ID to be padded
	 * @return         	the padded figure ID as a string
	 */
	private String padToSixDigits(int figureID) {
		String s = "" + figureID;
		if(s.length() == 1) {
			s = "00000" + s;
		}
		if(s.length() == 2) {
			s = "0000" + s;
		}
		if(s.length() == 3) {
			s = "000" + s;
		}
		if(s.length() == 4) {
			s = "00" + s;
		}
		if(s.length() == 5) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * Sets the movement type for the last entry in the game log.
	 *
	 * @param  movementType  the movement type to set
	 */
	//move 0: normal
	//move 1: capture
	//move 2: castling
	//move 3: pawn promotes to bishop
	//move 4: pawn promotes to knight
	//move 5: pawn promotes to queen
	//move 6: pawn promotes to rook
	public void setMovementTypeForLastEntry(int movementType) {
		if (this.getPriorEntry(1) != null) {
			String entryToEdit = this.getPriorEntry(1);
			entryToEdit = entryToEdit.substring(0, entryToEdit.length()-1);
			gameLog.set(gameLog.size()-1, entryToEdit + movementType);
		}
	}

	/**
	 * Logs the move of a figure from a given FEN position.
	 *
	 * @param  figure      the figure being moved
	 * @param  startIndex  the starting index of the figure
	 */
	public void logMoveFromFen(Figure figure, int startIndex) {
		int oldX = coordinateHelper.convertIndextoX(startIndex);
		int oldY = coordinateHelper.convertIndextoY(startIndex);
		int id = figure.getFigureID();
		int type = figure.getFigureType();
		int color = figure.getFigureColor();
		int newX = figure.getXPosition();
		int newY = figure.getYPosition();
		addEntry("Move: ID=" + id + ", Type=" + type + ", Color=" + color
				+ ", OldX=" + oldX + ", OldY=" + oldY + ", NewX=" + newX
				+ ", NewY=" + newY);
	}

	/**
	 * Determines if a specific figure has moved during the game.
	 *
	 * @param  figure  the figure to check
	 * @return         true if the figure has moved, false otherwise
	 */
	public boolean hasFigureMoved(Figure figure) {
		int figureID = figure.getFigureID();
		for (int index = 0; index < gameLog.size(); index++) {
			if (gameLog.get(index).contains(""+ figureID)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves the prior entry from the game log based on the number of steps.
	 *
	 * @param  steps  the number of steps to go back in the game log
	 * @return        the prior entry from the game log, or a default entry if not available
	 */
	public String getPriorEntry(int steps) {
		String entry = "DUMY: ID=000000, TYPE=000, COLOR=0, OLDX=0, OLDY=0, NEWX=0, NEWY=0";
		if (gameLog.size() > steps-1) {
			entry = gameLog.get(gameLog.size()-steps);
		}
		return entry;
	}

	/**
	 * Retrieves the entry at the specified position in the game log.
	 *
	 * @param  position  the position of the entry to retrieve
	 * @return           the entry at the specified position
	 */
	public String getEntryAt(int position) {
		return gameLog.get(position);
	}

	/**
	 * Returns the length of the gameLog.
	 *
	 * @return the length of the gameLog
	 */
	public int getLength() {
		return gameLog.size();
	}

	/**
	 * Retrieves the figure ID from the given entry.
	 *
	 * @param  entry  the entry from which the figure ID is retrieved
	 * @return        the figure ID retrieved from the entry
	 */
	public int getFigureIDfromEntry(String entry) {
		if (entry != null) {
			int int1 = convertCharToInt(entry.charAt(9));
			int int2 = convertCharToInt(entry.charAt(10));
			int int3 = convertCharToInt(entry.charAt(11));
			int int4 = convertCharToInt(entry.charAt(12));
			int int5 = convertCharToInt(entry.charAt(13));
			int int6 = convertCharToInt(entry.charAt(14));
			return (int1 * 100000) + (int2 * 10000) + (int3 * 1000) + (int4 * 100) + (int5 * 10) + (int6);
		}
		return 0;
	}

	/**
	 * Converts a character to an integer.
	 *
	 * @param  input  the character to be converted
	 * @return        the corresponding integer value
	 */
	private int convertCharToInt(char input) {
		return input - ASCII_NUMBER_DIFF;
	}

	public int getColorfromEntry(String entry) {
		if (entry != null) {
			return convertCharToInt(entry.charAt(33));
		}
		return -1;
	}

	/**
	 * Calculates the figure type from the given entry.
	 *
	 * @param  entry  the string representation of the entry
	 * @return        the figure type extracted from the entry
	 */
	public int getFigureTypefromEntry(String entry) {
		if (entry != null) {
			int int1 = convertCharToInt(entry.charAt(22));
			int int2 = convertCharToInt(entry.charAt(23));
			int int3 = convertCharToInt(entry.charAt(24));
			return (int1 * 100) + (int2 * 10) + int3;
		}
		return -1;
	}
	/**
	 * Calculates the X movement from the given entry.
	 *
	 * @param  entry  the entry string from which to calculate the X movement
	 * @return        the X movement calculated from the entry string
	 */
//duplicate code to coordinateHelper
	public int getXMovementfromEntry(String entry) {
		int xMovement = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(41);
			int charb = (int) entry.charAt(57);
			int oldX = chara - ASCII_NUMBER_DIFF;
			int newX = charb - ASCII_NUMBER_DIFF;
			xMovement = newX - oldX;
		}
		return xMovement;
	}

	/**
	 * Returns the y-movement from the given entry string.
	 *
	 * @param  entry  the entry string from which to extract the y-movement
	 * @return        the y-movement value calculated from the entry string
	 */
//duplicate code to coordinateHelper
	public int getYMovementfromEntry(String entry) {
		int yMovement = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(49);
			int charb = (int) entry.charAt(65);
			int oldY = chara - ASCII_NUMBER_DIFF;
			int newY = charb - ASCII_NUMBER_DIFF;
			yMovement = newY - oldY;
		}
		return yMovement;
	}

	/**
	 * Retrieves the oldX value from the given entry.
	 *
	 * @param  entry  the entry from which to retrieve the oldX value
	 * @return        the oldX value
	 */
	public int getOldXfromEntry(String entry) {
		int oldX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(41);
			oldX = chara - ASCII_NUMBER_DIFF;
		}
		return oldX;
	}

	/**
	 * Retrieves the new X value from the given entry.
	 *
	 * @param  entry  the entry from which to retrieve the new X value
	 * @return        the new X value calculated from the given entry
	 */
	public int getNewXfromEntry(String entry) {
		int newX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(57);
			newX = chara - ASCII_NUMBER_DIFF;
		}
		return newX;
	}

	/**
	 * Retrieves the old Y value from the given entry.
	 *
	 * @param  entry  the entry containing the value
	 * @return        the old Y value
	 */
	public int getOldYfromEntry(String entry) {
		int oldY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(49);
			oldY = chara - ASCII_NUMBER_DIFF;
		}
		return oldY;
	}

	/**
	 * Returns the new Y value based on the given entry.
	 *
	 * @param  entry  the entry to calculate the new Y value from
	 * @return        the new Y value
	 */
	public int getNewYfromEntry(String entry) {
		int newY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(65);
			newY = chara - ASCII_NUMBER_DIFF;
		}
		return newY;
	}

	/**
	 * Clears the game log.
	 *
	 * @return None
	 */
	public void clearLog() {
		gameLog.clear();
	}

	/**
	 * Retrieves the notated entry at the specified index in the game log.
	 *
	 * @param  index  the index of the entry to retrieve
	 * @return        the notated entry at the specified index
	 */
	public String getNotatedEntryAt(int index) {
		String rawEntry = gameLog.get(index);
		if (rawEntry.charAt(rawEntry.length()-1) == '2') {
			if(getXMovementfromEntry(rawEntry) > 0) {
				return "0-0";
			}
			if(getXMovementfromEntry(rawEntry) < 0) {
				return "0-0-0";
			}
		}
		String destinationSquare = coordinateHelper.convertCoordsToNotationString(this.getNewXfromEntry(rawEntry), this.getNewYfromEntry(rawEntry));
		String promotionIndicator = getPromotionIndicator(rawEntry);
		String captureIndicator = "";
		if (rawEntry.charAt(rawEntry.length()-1) == '1') {
			captureIndicator = "x";
		}
		String disambiguation = getDisambiguationString(rawEntry);
		String pieceIndicator = getPieceNotation(rawEntry);
		return pieceIndicator + disambiguation + captureIndicator + destinationSquare + promotionIndicator;
	}

	/**
	 * Returns the movement type from the given entry.
	 *
	 * @param  entry  the entry from which to extract the movement type
	 * @return        the movement type as an integer
	 */
public int getMovementTypeFromEntry(String entry) {
		char c = entry.charAt(entry.length()-1);
		return (int) c - ASCII_NUMBER_DIFF;
}

	/**
	 * Returns the promotion indicator based on the last character of the raw entry.
	 *
	 * @param  rawEntry the raw entry string
	 * @return the promotion indicator ('B' for '3', 'N' for 4, 'Q' for 5, 'R' for 6, and empty string for other characters)
	 */
	private String getPromotionIndicator(String rawEntry) {
		switch(rawEntry.charAt(rawEntry.length()-1)) {
			case '3':
				return "B";
			case 4:
				return "N";
			case 5:
				return "Q";
			case 6:
				return "R";
			default:
				return "";
		}
	}

	/**
	 * Returns a disambiguation string for the given raw entry.
	 *
	 * @param  rawEntry  the raw entry to get the disambiguation string for
	 * @return           the disambiguation string
	 */
	private String getDisambiguationString(String rawEntry) {
		return coordinateHelper.convertCoordsToNotationString(getOldXfromEntry(rawEntry), getOldYfromEntry(rawEntry));
	}

	/**
	 * Generates the piece notation for a given raw entry.
	 *
	 * @param  rawEntry	the raw entry to convert
	 * @return         	the piece notation corresponding to the raw entry
	 */
	private String getPieceNotation(String rawEntry) {
		int figureType = this.getFigureTypefromEntry(rawEntry);
		switch(figureType) {
			case 0:
				return "B";
			case 1:
				return "K";
			case 2:
				return "N";
//			case 3:
//				if (rawEntry.charAt(rawEntry.length()-1) == '1') {
//					String s = coordinateHelper.convertCoordsToNotationString(getOldXfromEntry(rawEntry), 0);
//					return "" + s.charAt(0);
//				} else {
//					return "";
//				}
			case 4:
				return "Q";
			case 5:
				return "R";
			default:
				return "";
		}
	}

	/**
	 * Disables logging.
	 */
	public void disableLogging() {
		loggingEnabled = false;
	}

	/**
	 * Enables logging.
	 *
	 */
	public void enableLogging() {
		loggingEnabled = true;
	}

	/**
	 * Removes the entry at the specified index from the game log.
	 *
	 * @param  index  the index of the entry to be removed
	 */
	public void removeEntry(int index) {
		gameLog.remove(index);
	}
}
