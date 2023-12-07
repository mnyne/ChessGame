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

	public void addEntry(String entry) {
		gameLog.add(entry);
	}

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

	public boolean hasFigureMoved(Figure figure) {
		int figureID = figure.getFigureID();
		for (int index = 0; index < gameLog.size(); index++) {
			if (gameLog.get(index).contains(""+ figureID)) {
				return true;
			}
		}
		return false;
	}
	

	public String getPriorEntry(int steps) {
		String entry = "DUMY: ID=000000, TYPE=000, COLOR=0, OLDX=0, OLDY=0, NEWX=0, NEWY=0";
		if (gameLog.size() > steps-1) {
			entry = gameLog.get(gameLog.size()-steps);
		}
		return entry;
	}

	public String getEntryAt(int position) {
		return gameLog.get(position);
	}

	public int getLength() {
		return gameLog.size();
	}
	
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
	
	private int convertCharToInt(char input) {
		return input - ASCII_NUMBER_DIFF;
	}

	public int getColorfromEntry(String entry) {
		if (entry != null) {
			return convertCharToInt(entry.charAt(33));
		}
		return -1;
	}

	public int getFigureTypefromEntry(String entry) {
		if (entry != null) {
			int int1 = convertCharToInt(entry.charAt(22));
			int int2 = convertCharToInt(entry.charAt(23));
			int int3 = convertCharToInt(entry.charAt(24));
			return (int1 * 100) + (int2 * 10) + int3;
		}
		return -1;
	}
	
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

	public int getOldXfromEntry(String entry) {
		int oldX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(41);
			oldX = chara - ASCII_NUMBER_DIFF;
		}
		return oldX;
	}

	public int getNewXfromEntry(String entry) {
		int newX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(57);
			newX = chara - ASCII_NUMBER_DIFF;
		}
		return newX;
	}

	public int getOldYfromEntry(String entry) {
		int oldY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(49);
			oldY = chara - ASCII_NUMBER_DIFF;
		}
		return oldY;
	}

	public int getNewYfromEntry(String entry) {
		int newY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(65);
			newY = chara - ASCII_NUMBER_DIFF;
		}
		return newY;
	}

	public void clearLog() {
		gameLog.clear();
	}

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
public int getMovementTypeFromEntry(String entry) {
		char c = entry.charAt(entry.length()-1);
		return (int) c - ASCII_NUMBER_DIFF;
}
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

	private String getDisambiguationString(String rawEntry) {
		return coordinateHelper.convertCoordsToNotationString(getOldXfromEntry(rawEntry), getOldYfromEntry(rawEntry));
	}

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

	public void disableLogging() {
		loggingEnabled = false;
	}

	public void enableLogging() {
		loggingEnabled = true;
	}

	public void removeEntry(int index) {
		gameLog.remove(index);
	}
}
