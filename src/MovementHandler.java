public class MovementHandler {

	LogHelper logHelper = new LogHelper();
	CoordinateHelper coordinateHelper = new CoordinateHelper();

	public MovementHandler() {

	}

	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure,
			int targetIndex, int currentTurn) {
		boolean moveIsLegal = true;
		// MovementArray trueArray
		// MovementArray playerTurnArray
		// MovementArray figureColorArray
		// MovementArray figureMovementArray
		// MovementArray kinginCheckArray
		// overwrite trueArray

		return moveIsLegal;
	}

	public MovementArray trueArray() {
		MovementArray trueArray = new MovementArray();
		for (int arrayIndex = 0; arrayIndex < trueArray.getLength(); arrayIndex++) {
			trueArray.setIndexToTrue(arrayIndex);
		}
		return trueArray;
	}

	public MovementArray playerTurnArray(Figure selectedFigure, int currentTurn) {
		MovementArray playerTurnArray = new MovementArray();
		for (int arrayIndex = 0; arrayIndex < playerTurnArray.getLength(); arrayIndex++) {
			if (currentTurn % 2 != selectedFigure.getFigureColor()) {
				playerTurnArray.setIndexToFalse(arrayIndex);
			}
		}
		return playerTurnArray;
	}

	public MovementArray figureColorArray(ChessBoard currentBoard,
			Figure selectedFigure) {
		MovementArray figureColorArray = new MovementArray();
		for (int arrayIndex = 0; arrayIndex < figureColorArray.getLength(); arrayIndex++) {
			if (currentBoard.getFigureAtIndex(arrayIndex) != null
					&& currentBoard.getFigureAtIndex(arrayIndex).getFigureColor() != selectedFigure
							.getFigureColor()) {
				figureColorArray.setIndexToFalse(arrayIndex);
			}
		}
		return figureColorArray;
	}
	
	public MovementArray figureMovementArray(ChessBoard currentBoard, Figure selectedFigure) {
		MovementArray figureMovementArray = new MovementArray();
		for (int arrayIndex = 0; arrayIndex < figureMovementArray.getLength(); arrayIndex++) {
			//continue from here
		}
		
		return figureMovementArray;
	}
	
	
	
	
	// update when LogArray is done
	public void handleEnPassant(ChessBoard currentBoard) {
		String logFilePath = "chess_board_log.txt";
		String secondToLastLine = logHelper.readSecondToLastLine(logFilePath);
		String lastLine = logHelper.readLastLine(logFilePath);
		int type1 = logHelper.getFigureTypefromLog(secondToLastLine);
		int type2 = logHelper.getFigureTypefromLog(lastLine);
		int color1 = logHelper.getColorfromLog(secondToLastLine);
		int color2 = logHelper.getColorfromLog(lastLine);
		int yMovement1 = logHelper.getYMovementfromLog(secondToLastLine);
		int newX1 = logHelper.getNewXfromLog(secondToLastLine);
		int newX2 = logHelper.getNewXfromLog(lastLine);
		int newY1 = logHelper.getNewYfromLog(secondToLastLine);
		int newY2 = logHelper.getNewYfromLog(lastLine);
		if (type1 == 3 && type2 == 3 && color1 == 1 && color2 == 0
				&& yMovement1 == 2 && newX1 == newX2 && newY2 == newY1 - 1) {
			currentBoard.removeFigure(newX1, newY1);
		}
		if (type1 == 3 && type2 == 3 && color2 == 1 && color1 == 0
				&& yMovement1 == -2 && newX1 == newX2 && newY2 == newY1 + 1) {
			currentBoard.removeFigure(newX1, newY1);
		}
	}

	public void handleCastling(ChessBoard currentBoard) {
		Figure selectedFigure;
		String logFilePath = "chess_board_log.txt";
		String lastLine = logHelper.readLastLine(logFilePath);
		int type = logHelper.getFigureTypefromLog(lastLine);
		int xMovement = logHelper.getXMovementfromLog(lastLine);
		int Y = logHelper.getNewYfromLog(lastLine);
		if (type == 1) {
			if (xMovement == -2 && Y == 0) {
				// topleft
				selectedFigure = currentBoard.getFigureAt(0, 0);
				int targetIndex = coordinateHelper.convertXYtoIndex(3, 0);
				logHelper.logMove(selectedFigure, targetIndex);
				currentBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				currentBoard.removeFigure(0, 0);
			}
			if (xMovement == 2 && Y == 0) {
				// topright
				selectedFigure = currentBoard.getFigureAt(7, 0);
				int targetIndex = coordinateHelper.convertXYtoIndex(5, 0);
				logHelper.logMove(selectedFigure, targetIndex);
				currentBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				currentBoard.removeFigure(7, 0);
			}
			if (xMovement == -2 && Y == 7) {
				// bottomleft
				selectedFigure = currentBoard.getFigureAt(0, 7);
				int targetIndex = coordinateHelper.convertXYtoIndex(3, 7);
				logHelper.logMove(selectedFigure, targetIndex);
				currentBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				currentBoard.removeFigure(0, 7);
			}
			if (xMovement == 2 && Y == 7) {
				// bottomright
				selectedFigure = currentBoard.getFigureAt(7, 7);
				int targetIndex = coordinateHelper.convertXYtoIndex(5, 7);
				logHelper.logMove(selectedFigure, targetIndex);
				currentBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				currentBoard.removeFigure(7, 7);
			}
		}

	}

	// update when LogArray is done
	public void handlePawnAtBorder(ChessBoard currentBoard) {
		String logFilePath = "chess_board_log.txt";
		String lastLine = logHelper.readLastLine(logFilePath);
		int type = logHelper.getFigureTypefromLog(lastLine);
		int color = logHelper.getColorfromLog(lastLine);
		int newX = logHelper.getNewXfromLog(lastLine);
		int newY = logHelper.getNewYfromLog(lastLine);

		if (type == 3 && color == 0 && newY == 0) {
			Figure pawn = currentBoard.getFigureAt(newX, newY);
			transformPawn(pawn, currentBoard);
		}
		if (type == 3 && color == 1 && newY == 7) {
			Figure pawn = currentBoard.getFigureAt(newX, newY);
			transformPawn(pawn, currentBoard);
		}

	}

	private void transformPawn(Figure pawn, ChessBoard currentBoard) {
		// code for selection maybe later, why would you want anything but a
		// queen in 99% of cases...
		int x = pawn.getXPosition();
		int y = pawn.getYPosition();
		int color = pawn.getFigureColor();
		int index = coordinateHelper.convertXYtoIndex(x, y);
		currentBoard.removeFigure(x, y);
		if (color == 0) {
			// GImage whiteSelector = new
			// GImage("graphics/selection_white.png");
			// add(whiteSelector,80,80);
			// switch(mouseInput) {
			// case 0:
			// chessBoard.addFigure(index, new Queen("white",
			// ch.convertCoordsToNotationString(x,y)));
			// break;
			// case 1:
			// chessBoard.addFigure(index, new Rook("white",
			// ch.convertCoordsToNotationString(x,y)));
			// break;
			// case 2:
			// chessBoard.addFigure(index, new Bishop("white",
			// ch.convertCoordsToNotationString(x,y)));
			// break;
			// case 3:
			// chessBoard.addFigure(index, new Knight("white",
			// ch.convertCoordsToNotationString(x,y)));
			// break;
			// default:
			// break;
			// }

			currentBoard.addFigure(
					index,
					new Queen("white", coordinateHelper
							.convertCoordsToNotationString(x, y)));
		}
		if (color == 1) {
			currentBoard.addFigure(
					index,
					new Queen("black", coordinateHelper
							.convertCoordsToNotationString(x, y)));
		}
	}

}
