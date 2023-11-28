public class MovementHandler {
	LogHelper logHelper = new LogHelper();
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	public MovementHandler() {
		
	}
	
	//update when LogArray is done
	public void handleEnPassant(ChessBoard chessBoard) {
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
			chessBoard.removeFigure(newX1, newY1);
		}
		if (type1 == 3 && type2 == 3 && color2 == 1 && color1 == 0
				&& yMovement1 == -2 && newX1 == newX2 && newY2 == newY1 + 1) {
			chessBoard.removeFigure(newX1, newY1);
		}
	}

	public void handleCastling(ChessBoard chessBoard) {
		Figure selectedFigure;
		String logFilePath = "chess_board_log.txt";
		String lastLine = logHelper.readLastLine(logFilePath);
		int type = logHelper.getFigureTypefromLog(lastLine);
		int xMovement = logHelper.getXMovementfromLog(lastLine);
		int Y = logHelper.getNewYfromLog(lastLine);
		if (type == 1) {
			if (xMovement == -2 && Y == 0) {
				// topleft
				selectedFigure = chessBoard.getFigureAt(0, 0);
				int targetIndex = coordinateHelper.convertXYtoIndex(3, 0);
				logHelper.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				chessBoard.removeFigure(0, 0);
			}
			if (xMovement == 2 && Y == 0) {
				// topright
				selectedFigure = chessBoard.getFigureAt(7, 0);
				int targetIndex = coordinateHelper.convertXYtoIndex(5, 0);
				logHelper.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				chessBoard.removeFigure(7, 0);
			}
			if (xMovement == -2 && Y == 7) {
				// bottomleft
				selectedFigure = chessBoard.getFigureAt(0, 7);
				int targetIndex = coordinateHelper.convertXYtoIndex(3, 7);
				logHelper.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				chessBoard.removeFigure(0, 7);
			}
			if (xMovement == 2 && Y == 7) {
				// bottomright
				selectedFigure = chessBoard.getFigureAt(7, 7);
				int targetIndex = coordinateHelper.convertXYtoIndex(5, 7);
				logHelper.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						coordinateHelper.convertIndextoX(targetIndex),
						coordinateHelper.convertIndextoY(targetIndex));
				chessBoard.removeFigure(7, 7);
			}
		}

	}
	
	//update when LogArray is done
	public void handlePawnAtBorder(ChessBoard chessBoard) {
		String logFilePath = "chess_board_log.txt";
		String lastLine = logHelper.readLastLine(logFilePath);
		int type = logHelper.getFigureTypefromLog(lastLine);
		int color = logHelper.getColorfromLog(lastLine);
		int newX = logHelper.getNewXfromLog(lastLine);
		int newY = logHelper.getNewYfromLog(lastLine);
		
		if (type == 3 && color == 0 && newY == 0) {
			Figure pawn = chessBoard.getFigureAt(newX, newY);
			transformPawn(pawn, chessBoard);
		}
		if (type == 3 && color == 1 && newY == 7) {
			Figure pawn = chessBoard.getFigureAt(newX, newY);
			transformPawn(pawn, chessBoard);
		}

	}

	private void transformPawn(Figure pawn, ChessBoard chessBoard) {
		//code for selection maybe later, why would you want anything but a queen in 99% of cases...
		int x = pawn.getXPosition();
		int y = pawn.getYPosition();
		int color = pawn.getFigureColor();
		int index = coordinateHelper.convertXYtoIndex(x, y);
		chessBoard.removeFigure(x,y);
		if (color == 0) {
//			GImage whiteSelector = new GImage("graphics/selection_white.png");
//			add(whiteSelector,80,80);
//			switch(mouseInput) {
//			case 0:
//				chessBoard.addFigure(index, new Queen("white", ch.convertCoordsToNotationString(x,y)));
//				break;
//			case 1:
//				chessBoard.addFigure(index, new Rook("white", ch.convertCoordsToNotationString(x,y)));
//				break;
//			case 2:
//				chessBoard.addFigure(index, new Bishop("white", ch.convertCoordsToNotationString(x,y)));
//				break;
//			case 3:
//				chessBoard.addFigure(index, new Knight("white", ch.convertCoordsToNotationString(x,y)));
//				break;
//			default:
//				break;
//			}
			
		chessBoard.addFigure(index, new Queen("white", coordinateHelper.convertCoordsToNotationString(x,y)));
		}
		if (color == 1) {
		chessBoard.addFigure(index, new Queen("black", coordinateHelper.convertCoordsToNotationString(x,y)));
		}
	}

}
