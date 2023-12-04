import java.util.ArrayList;

public class GameLog {

	private final int ASCII_NUMBER_DIFF = 48;
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	
	ArrayList<String> gameLog;

	public GameLog() {
		gameLog = new ArrayList<>();
	}

	public void addEntry(String entry) {
		gameLog.add(entry);
	}
// remove temp variables
//todo: turn this into a Figure array instead, will make so many things easier instead of relying on strings & will make reverting moves and such easier
	//todo: will need a way to denote capturing and castling
	public void logMove(Figure figure, int targetIndex) {
		int oldX = figure.getXPosition();
		int oldY = figure.getYPosition();
		int id = figure.getFigureID();
		int type = figure.getFigureType();
		int color = figure.getFigureColor();
		int newX = coordinateHelper.convertIndextoX(targetIndex);
		int newY = coordinateHelper.convertIndextoY(targetIndex);
		addEntry("Move: ID=" + id + ", Type=" + type + ", Color=" + color
				+ ", OldX=" + oldX + ", OldY=" + oldY + ", NewX=" + newX
				+ ", NewY=" + newY);
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
		String entry = "DUMY: ID=0000, TYPE=0, COLOR=0, OLDX=0, OLDY=0, NEWX=0, NEWY=0";
		if (gameLog.size() > steps-1) {
			entry = gameLog.get(gameLog.size()-steps);
		}
		return entry;
	}
	
	public int getFigureIDfromEntry(String entry) {
		if (entry != null) {
			int int1 = convertCharToInt(entry.charAt(9));
			int int2 = convertCharToInt(entry.charAt(10));
			int int3 = convertCharToInt(entry.charAt(11));
			int int4 = convertCharToInt(entry.charAt(12));
			return (int1 * 1000) + (int2 * 100) + (int3 * 10) + int4;
		}
		return 0;
	}
	
	private int convertCharToInt(char input) {
		return input - ASCII_NUMBER_DIFF;
	}

	public int getColorfromEntry(String entry) {
		if (entry != null) {
			return convertCharToInt(entry.charAt(29));
		}
		return -1;
	}

	public int getFigureTypefromEntry(String entry) {
		if (entry != null) {
			return convertCharToInt(entry.charAt(20));
		}
		return -1;
	}
	
//duplicate code to coordinateHelper
	public int getXMovementfromEntry(String entry) {
		int xMovement = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(37);
			int charb = (int) entry.charAt(53);
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
			int chara = (int) entry.charAt(45);
			int charb = (int) entry.charAt(61);
			int oldY = chara - ASCII_NUMBER_DIFF;
			int newY = charb - ASCII_NUMBER_DIFF;
			yMovement = newY - oldY;
		}
		return yMovement;
	}

	public int getOldXfromEntry(String entry) {
		int oldX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(37);
			oldX = chara - ASCII_NUMBER_DIFF;
		}
		return oldX;
	}

	public int getNewXfromEntry(String entry) {
		int newX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(53);
			newX = chara - ASCII_NUMBER_DIFF;
		}
		return newX;
	}

	public int getOldYfromEntry(String entry) {
		int oldY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(45);
			oldY = chara - ASCII_NUMBER_DIFF;
		}
		return oldY;
	}

	public int getNewYfromEntry(String entry) {
		int newY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(61);
			newY = chara - ASCII_NUMBER_DIFF;
		}
		return newY;
	}
}
