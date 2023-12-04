public class MovementHandler {
	
	CoordinateHelper coordinateHelper = new CoordinateHelper();

	public MovementHandler() {

	}
	
	public MovementArray legalMoveArray(ChessBoard currentBoard, Figure selectedFigure, int currentHalfMove, GameLog gameLog) {
		
		MovementArray legalMoveArray = figureMovementArray(currentBoard, selectedFigure, currentHalfMove);
		MovementArray kingInCheckArray = kingInCheckArray(currentBoard, selectedFigure, currentHalfMove, gameLog);
		
		for (int arrayIndex = 0; arrayIndex < legalMoveArray.getLength(); arrayIndex++) {
			if(!kingInCheckArray.moveAtIndexAllowed(arrayIndex)) {
				legalMoveArray.setIndexToFalse(arrayIndex);
			}
		}
		
	return legalMoveArray;
	
	}
	
	public MovementArray kingInCheckArray(ChessBoard currentBoard, Figure selectedFigure, int currentHalfMove, GameLog gameLog) {
		
		MovementArray possibleMoveArray = figureMovementArray(currentBoard, selectedFigure, currentHalfMove);
		MovementArray kingInCheckArray = emptyTrueArray();
		
		for (int arrayIndex = 0; arrayIndex < kingInCheckArray.getLength(); arrayIndex++) {
			if(possibleMoveArray.moveAtIndexAllowed(arrayIndex)) {
				MoveSimulator moveSimulator = new MoveSimulator(currentBoard.deepCopy(), selectedFigure.deepCopy(), currentHalfMove, gameLog);
				moveSimulator.simulateMove(arrayIndex);
				if (moveSimulator.possiblePlayerMoves().moveAtIndexAllowed(moveSimulator.getSimulatedKingIndex())) {
					kingInCheckArray.setIndexToFalse(arrayIndex);
				}
			}
		}
		
		return kingInCheckArray;
	}

	public MovementArray emptyTrueArray() {

		MovementArray emptyTrueArray = new MovementArray();

		for (int arrayIndex = 0; arrayIndex < emptyTrueArray.getLength(); arrayIndex++) {
			emptyTrueArray.setIndexToTrue(arrayIndex);
		}

		return emptyTrueArray;
	}

	public boolean isWrongColor (Figure selectedFigure, ChessBoard currentBoard, int targetIndex, int currentHalfMove) {
		return currentHalfMove % 2 != selectedFigure.getFigureColor() || isWrongTargetColor(selectedFigure, currentBoard, targetIndex, currentHalfMove);
	}

	private boolean isWrongTargetColor (Figure selectedFigure, ChessBoard currentBoard, int targetIndex, int currentHalfMove) {
		return currentBoard.getFigureAtIndex(targetIndex) != null && currentBoard.getFigureAtIndex(targetIndex).getFigureColor() == selectedFigure.getFigureColor();
	}

	public MovementArray figureMovementArray(ChessBoard currentBoard, Figure selectedFigure, int currentHalfMove) {
		
		MovementArray figureMovementArray = emptyTrueArray();
		
		for (int arrayIndex = 0; arrayIndex < figureMovementArray.getLength(); arrayIndex++) {
            if (!selectedFigure.moveIsLegal(currentBoard, selectedFigure, arrayIndex)) {
                figureMovementArray.setIndexToFalse(arrayIndex);
            } else if (isWrongColor(selectedFigure, currentBoard, arrayIndex, currentHalfMove)) {
                figureMovementArray.setIndexToFalse(arrayIndex);
            }
		}
		
		return figureMovementArray;
	}

	// move to separate capturing method, probably within ChessBoard class
	//probably easier to just do a Figure oldEntry = getFigureFromEntry and then use methods from within Figure class instead of doing all this 
	public void removePawnAfterEnPassant(ChessBoard currentBoard, GameLog gameLog) {
		String secondToLastEntry = gameLog.getPriorEntry(2);
		String lastEntry = gameLog.getPriorEntry(1);
		int type1 = gameLog.getFigureTypefromEntry(secondToLastEntry);
		int type2 = gameLog.getFigureTypefromEntry(lastEntry);
		int color1 = gameLog.getColorfromEntry(secondToLastEntry);
		int color2 = gameLog.getColorfromEntry(lastEntry);
		int yMovement1 = gameLog.getYMovementfromEntry(secondToLastEntry);
		int newX1 = gameLog.getNewXfromEntry(secondToLastEntry);
		int newX2 = gameLog.getNewXfromEntry(lastEntry);
		int newY1 = gameLog.getNewYfromEntry(secondToLastEntry);
		int newY2 = gameLog.getNewYfromEntry(lastEntry);
		if (type1 == 3 && type2 == 3 && color1 == 1 && color2 == 0
				&& yMovement1 == 2 && newX1 == newX2 && newY2 == newY1 - 1) {
			currentBoard.removeFigure(newX1, newY1);
			gameLog.setMovementTypeForLastEntry(1);
		}
		if (type1 == 3 && type2 == 3 && color2 == 1 && color1 == 0
				&& yMovement1 == -2 && newX1 == newX2 && newY2 == newY1 + 1) {
			currentBoard.removeFigure(newX1, newY1);
			gameLog.setMovementTypeForLastEntry(1);
		}
	}

	// move to separate capturing method, probably within ChessBoard class
	//probably easier to just do a Figure oldEntry = getFigureFromEntry and then use methods from within Figure class instead of doing all this 
	public void moveTowerAfterCastling(ChessBoard currentBoard, GameLog gameLog) {
		Figure selectedFigure;
		String lastEntry = gameLog.getPriorEntry(1);
		int type = gameLog.getFigureTypefromEntry(lastEntry);
		int xMovement = gameLog.getXMovementfromEntry(lastEntry);
		int Y = gameLog.getNewYfromEntry(lastEntry);
		if (type == 1) {
			if (xMovement == -2 && Y == 0) {
				// topleft
				selectedFigure = currentBoard.getFigureAt(0, 0);
				int targetIndex = coordinateHelper.convertXYtoIndex(3, 0);
				currentBoard.moveFigure(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex), gameLog);
				currentBoard.removeFigure(0, 0);
				gameLog.setMovementTypeForLastEntry(2);
			}
			if (xMovement == 2 && Y == 0) {
				// topright
				selectedFigure = currentBoard.getFigureAt(7, 0);
				int targetIndex = coordinateHelper.convertXYtoIndex(5, 0);
				currentBoard.moveFigure(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex), gameLog);
				currentBoard.removeFigure(7, 0);
				gameLog.setMovementTypeForLastEntry(2);
			}
			if (xMovement == -2 && Y == 7) {
				// bottomleft
				selectedFigure = currentBoard.getFigureAt(0, 7);
				int targetIndex = coordinateHelper.convertXYtoIndex(3, 7);
				currentBoard.moveFigure(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex), gameLog);
				currentBoard.removeFigure(0, 7);
				gameLog.setMovementTypeForLastEntry(2);
			}
			if (xMovement == 2 && Y == 7) {
				// bottomright
				selectedFigure = currentBoard.getFigureAt(7, 7);
				int targetIndex = coordinateHelper.convertXYtoIndex(5, 7);
				currentBoard.moveFigure(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex), gameLog);
				currentBoard.removeFigure(7, 7);
				gameLog.setMovementTypeForLastEntry(2);
			}
		}

	}

	// move to ChessBoard class
	public void handlePawnAtBorder(ChessBoard currentBoard, GameLog gameLog) {
		String lastEntry = gameLog.getPriorEntry(1);
		int type = gameLog.getFigureTypefromEntry(lastEntry);
		int color = gameLog.getColorfromEntry(lastEntry);
		int newX = gameLog.getNewXfromEntry(lastEntry);
		int newY = gameLog.getNewYfromEntry(lastEntry);

		if (type == 3 && color == 0 && newY == 0) {
			Figure pawn = currentBoard.getFigureAt(newX, newY);
			tradeInPawn(pawn, currentBoard, gameLog);

		}
		if (type == 3 && color == 1 && newY == 7) {
			Figure pawn = currentBoard.getFigureAt(newX, newY);
			tradeInPawn(pawn, currentBoard, gameLog);
		}

	}

	// move to ChessBoard class, try to figure out how to work that into a GUI...
	private void tradeInPawn(Figure pawn, ChessBoard currentBoard, GameLog gameLog) {
		// code for selection maybe later, why would you want anything but a
		// queen in 99% of cases...
		int x = pawn.getXPosition();
		int y = pawn.getYPosition();
		int color = pawn.getFigureColor();
		int index = coordinateHelper.convertXYtoIndex(x, y);
		currentBoard.removeFigure(x, y);
		if (color == 0) {
			// ImageIcon whiteSelector = new
			// ImageIcon("graphics/selection_white.png");
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
					new Queen(0, coordinateHelper
							.convertXYtoIndex(x, y)));
		}
		if (color == 1) {
			currentBoard.addFigure(
					index,
					new Queen(1, coordinateHelper
							.convertXYtoIndex(x, y)));
		}
		gameLog.setMovementTypeForLastEntry(5);
	}

}
