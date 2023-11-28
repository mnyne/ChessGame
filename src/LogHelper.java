import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//update when LogArray is ready
public class LogHelper {
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	
	public LogHelper() {
		
	}
	
	public String readLastLine(String filePath) {
		try (BufferedReader reader = new BufferedReader(
				new FileReader(filePath))) {
			String currentLine;
			String lastLine = null;

			while ((currentLine = reader.readLine()) != null) {
				lastLine = currentLine;
			}

			return lastLine;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String readSecondToLastLine(String filePath) {
		String lastLine = null;
		String secondToLastLine = null;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				secondToLastLine = lastLine;
				lastLine = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return secondToLastLine;
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
	
	public void logMove(Figure figure, int targetIndex) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("chess_board_log.txt", true))) {
            int oldX = figure.getXPosition();
            int oldY = figure.getYPosition();
            String id = figure.getFigureID();
            int type = figure.getFigureType();
            int color = figure.getFigureColor();
            int newX = coordinateHelper.convertIndextoX(targetIndex);
            int newY = coordinateHelper.convertIndextoY(targetIndex);

            // Schreibe die Informationen in die Textdatei
            writer.println(String.format("Move: ID=%s, Type=%d, Color=%d, OldX=%d, OldY=%d, NewX=%d, NewY=%d",
                    id, type, color, oldX, oldY, newX, newY));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void clearLogFile() {
		try {
			File file = new File("chess_board_log.txt");
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
