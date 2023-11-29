import java.util.ArrayList;

public class GameLog {

	CoordinateHelper coordinateHelper = new CoordinateHelper();
	ArrayList<String> gameLog;

	public GameLog() {
		gameLog = new ArrayList<>();
	}

	public void addEntry(String entry) {
		gameLog.add(entry);
	}

	public void logMove(Figure figure, int targetIndex) {
		int oldX = figure.getXPosition();
		int oldY = figure.getYPosition();
		String id = figure.getFigureID();
		int type = figure.getFigureType();
		int color = figure.getFigureColor();
		int newX = coordinateHelper.convertIndextoX(targetIndex);
		int newY = coordinateHelper.convertIndextoY(targetIndex);
		addEntry("Move: ID=" + id + ", Type=" + type + ", Color=" + color
				+ ", OldX=" + oldX + ", OldY=" + oldY + ", NewX=" + newX
				+ ", NewY=" + newY);
		System.out.println("Move: ID=" + id + ", Type=" + type + ", Color=" + color
				+ ", OldX=" + oldX + ", OldY=" + oldY + ", NewX=" + newX
				+ ", NewY=" + newY);
	}

	public boolean hasFigureMoved(Figure figure) {
		boolean hasMoved = false;
		if (gameLog == null) {
			hasMoved = false;
		}

		String figureID = figure.getFigureID();
		for (int index = 0; index < gameLog.size(); index++) {
			String entry = gameLog.get(index);
			if (entry.contains(figureID)) {
				hasMoved = true;
				System.out.println("figure with id " + figureID + " has moved!");
			}
		}
		return hasMoved;
	}
	
	
	

	public String getLastEntry() {
		String entry = "DUMY: ID=000, TYPE=0, COLOR=0, OLDX=0, OLDY=0, NEWX=0, NEWY=0";
		if (gameLog.size() > 0) {
			entry = gameLog.get(gameLog.size()-1);
		}
		return entry;

	}

	public String getSecondToLastEntry() {
		String entry = "DUMY: ID=000, TYPE=0, COLOR=0, OLDX=0, OLDY=0, NEWX=0, NEWY=0";
		if (gameLog.size() > 1) {
			entry = gameLog.get(gameLog.size() - 2);
		}
		return entry;
	}

	public String getFigureIDfromEntry(String entry) {
		String id = "000";
		if (entry != null) {
			char a = entry.charAt(9);
			char b = entry.charAt(10);
			char c = entry.charAt(11);
			id = a + "" + b + "" + c;
		}
		return id;
	}

	public int getColorfromEntry(String entry) {
		int color = -1;
		if (entry != null) {
			int chara = (int) entry.charAt(28);
			color = chara - 48;
		}
		return color;
	}

	public int getFigureTypefromEntry(String entry) {
		int type = -1;
		if (entry != null) {
			int chara = (int) entry.charAt(19);
			type = chara - 48;
		}
		return type;
	}

	public int getXMovementfromEntry(String entry) {
		int xMovement = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(36);
			int charb = (int) entry.charAt(52);
			int oldX = chara - 48;
			int newX = charb - 48;
			xMovement = newX - oldX;
		}
		return xMovement;
	}

	public int getYMovementfromEntry(String entry) {
		int yMovement = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(44);
			int charb = (int) entry.charAt(60);
			int oldY = chara - 48;
			int newY = charb - 48;
			yMovement = newY - oldY;
		}
		return yMovement;
	}

	public int getOldXfromEntry(String entry) {
		int oldX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(36);
			oldX = chara - 48;
		}
		return oldX;
	}

	public int getNewXfromEntry(String entry) {
		int newX = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(52);
			newX = chara - 48;
		}
		return newX;
	}

	public int getOldYfromEntry(String entry) {
		int oldY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(44);
			oldY = chara - 48;
		}
		return oldY;
	}

	public int getNewYfromEntry(String entry) {
		int newY = 0;
		if (entry != null) {
			int chara = (int) entry.charAt(60);
			newY = chara - 48;
		}
		return newY;
	}
}
