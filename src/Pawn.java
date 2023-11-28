import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pawn extends Figure {
	LogHelper lh = new LogHelper();
	public Pawn(String s_color, String startingPosition) {
		super(3, s_color, startingPosition);
	}
	
	public Pawn(int color) {
		super(3, color);
	}
	
	boolean doubleMove = false;

	public boolean moveIsLegal(int potentialX, int potentialY, int currentX,
			int currentY, int color, ChessBoard currentBoard) {
		boolean bool = false;
		int xDiff = ch.getAdjustedDiff(potentialX, currentX);
		int yDiffRaw = ch.getRawDiff(potentialY, currentY);
		//blackpawn
		if (color == 1) {
			if (xDiff == 0 && -yDiffRaw > 0 && -yDiffRaw < 2) {
				bool = true;
			} else {
				bool = false;
			}
			int index = ch.convertXYtoIndex(potentialX, potentialY);
			if (currentY == 1 && xDiff == 0 && -yDiffRaw > 0 && -yDiffRaw == 2 && currentBoard.getFigureAtIndex(index) == null) {
				bool = true;
			}
			if (potentialY == currentY + 1) {
				index = ch.convertXYtoIndex(potentialX, potentialY);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY + 2 && currentY == 1) {
				index = ch.convertXYtoIndex(potentialX, potentialY-1);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY + 1) {
				if (potentialX == currentX - 1 || potentialX == currentX + 1) {
					index = ch.convertXYtoIndex(potentialX, potentialY);
					if (currentBoard.getFigureAtIndex(index) != null) {
						bool = true;
					}
					if(currentBoard.getFigureAt(potentialX, potentialY-1) != null && currentBoard.getFigureAt(potentialX, potentialY-1).isEligibleForShitMove()) {
						bool = true;
					}
				}
			}
		}
		//whitepawn
		if (color == 0) {
			if (xDiff == 0 && -yDiffRaw < 0 && -yDiffRaw > -2) {
				bool = true;
			} else {
				bool = false;
			}
			int index = ch.convertXYtoIndex(potentialX, potentialY);
			if (currentY == 6 && xDiff == 0 && -yDiffRaw < 0 && -yDiffRaw == -2 && currentBoard.getFigureAtIndex(index) == null) {
				bool = true;
			}
			if (potentialY == currentY - 1) {
				index = ch.convertXYtoIndex(potentialX, potentialY);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY - 2 && currentY == 6) {
				index = ch.convertXYtoIndex(potentialX, potentialY+1);
				if (currentBoard.getFigureAtIndex(index) != null) {
					bool = false;
				}
			}
			if (potentialY == currentY - 1) {
				if (potentialX == currentX - 1 || potentialX == currentX + 1) {
					index = ch.convertXYtoIndex(potentialX, potentialY);
					if (currentBoard.getFigureAtIndex(index) != null) {
						bool = true;
					}
					if(currentBoard.getFigureAt(potentialX, potentialY+1) != null && currentBoard.getFigureAt(potentialX, potentialY+1).isEligibleForShitMove()) {
						bool = true;
					}
				}
			}
		}
		return bool;
	}
	
	public Figure deepCopy() {
		Pawn copy = new Pawn(this.getFigureColor());
		copy.setNewPosition(this.getXPosition(), this.getYPosition());
		return copy;
	}
	
	public boolean isEligibleForShitMove() {
		boolean bool = false;
		String logFilePath = "chess_board_log.txt";
        String lastLine = readLastLine(logFilePath);
        String logID = lh.getFigureIDfromLog(lastLine);
        int yMovement = lh.getYMovementfromLog(lastLine);
        if(logID.equals(this.getFigureID())) {
        	if(yMovement == 2 || yMovement == -2) {
        		bool = true;
        		System.out.println("En Passant!");
        	}
        }
		return bool;
	}
	
	 private String readLastLine(String filePath) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
}
