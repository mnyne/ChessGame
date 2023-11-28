public class LogHelper {
	public LogHelper() {

	}

	public String getFigureIDfromLog(String line) {
		String id = "000";
		if (line != null) {
			char a = line.charAt(9);
			char b = line.charAt(10);
			char c = line.charAt(11);
			id = a + "" + b + "" + c;
		}
		return id;
	}

	public int getColorfromLog(String line) {
		int color = -1;
		if (line != null) {
			int chara = (int) line.charAt(28);
			color = chara - 48;
		}
		return color;
	}

	public int getFigureTypefromLog(String line) {
		int type = -1;
		if (line != null) {
			int chara = (int) line.charAt(19);
			type = chara - 48;
		}
		return type;
	}

	public int getXMovementfromLog(String line) {
		int xMovement = 0;
		if (line != null) {
			int chara = (int) line.charAt(36);
			int charb = (int) line.charAt(52);
			int oldX = chara - 48;
			int newX = charb - 48;
			xMovement = newX - oldX;
		}
		return xMovement;
	}

	public int getYMovementfromLog(String line) {
		int yMovement = 0;
		if (line != null) {
			int chara = (int) line.charAt(44);
			int charb = (int) line.charAt(60);
			int oldY = chara - 48;
			int newY = charb - 48;
			yMovement = newY - oldY;
		}
		return yMovement;
	}
	
	public int getOldXfromLog(String line) {
		int oldX = 0;
		if (line != null) {
			int chara = (int) line.charAt(36);
			oldX = chara - 48;
		}
		return oldX;
	}
	
	public int getNewXfromLog(String line) {
		int newX = 0;
		if (line != null) {
			int chara = (int) line.charAt(52);
			newX = chara - 48;
		}
		return newX;
	}
	
	public int getOldYfromLog(String line) {
		int oldY = 0;
		if (line != null) {
			int chara = (int) line.charAt(44);
			oldY = chara - 48;
		}
		return oldY;
	}
	
	public int getNewYfromLog(String line) {
		int newY = 0;
		if (line != null) {
			int chara = (int) line.charAt(60);
			newY = chara - 48;
		}
		return newY;
	}
}
