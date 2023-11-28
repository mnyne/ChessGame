import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ChessGame extends GraphicsProgram {

	ChessBoard chessBoard = new ChessBoard(64);
	Movement movement;
	Figure selectedFigure;
	GRect selectionIndicator;
	boolean clicked = false;
	int mouseX;
	int mouseY;
	int currentTurn = 0;
	CoordinateHelper ch = new CoordinateHelper();
	LogHelper lh = new LogHelper();

	public void run() {
		clearLogFile();
		drawGame();
		addMouseListeners();
	}

	private void clearLogFile() {
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

	private void drawGame() {
		setSize(500, 500);
		drawBackground();
		initializeBoard();
		drawFigures();
	}

	public void mousePressed(MouseEvent e) {
		mouseX = (int) e.getX();
		mouseY = (int) e.getY();
		if (!clicked) {
			runFirstClick();
		} else {
			runSecondClick();
		}
	}

	private void runSecondClick() {
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
		int targetIndex = ch.convertOpticalXYtoIndex(mouseX, mouseY);
		if (movement.isIndexAllowed(targetIndex)) {
			chessBoard.logMove(selectedFigure, targetIndex);
			chessBoard.makeMove(selectedFigure,
					ch.convertIndextoX(targetIndex),
					ch.convertIndextoY(targetIndex));
			chessBoard.removeOldFigure(xCache, yCache);
			currentTurn = currentTurn + 1;
			// println("current turn ingame: " + currentTurn);
		}
		setCanvasForNextRound();
	}

	private void setCanvasForNextRound() {
		removeAll();
		handleEnPassant();
		handleCastling();
		handlePawnAtBorder();
		drawBackground();
		drawFigures();
		deselectObject();
		clicked = false;
	}

	private void handlePawnAtBorder() {
		String logFilePath = "chess_board_log.txt";
		String lastLine = readLastLine(logFilePath);
		int type = lh.getFigureTypefromLog(lastLine);
		int color = lh.getColorfromLog(lastLine);
		int newX = lh.getNewXfromLog(lastLine);
		int newY = lh.getNewYfromLog(lastLine);
		
		if (type == 3 && color == 0 && newY == 0) {
			Figure pawn = chessBoard.getFigureAt(newX, newY);
			transformPawn(pawn);
		}
		if (type == 3 && color == 1 && newY == 7) {
			Figure pawn = chessBoard.getFigureAt(newX, newY);
			transformPawn(pawn);
		}

	}

	private void transformPawn(Figure pawn) {
		//code for selection maybe later, why would you want anything but a queen in 99% of cases...
		int x = pawn.getXPosition();
		int y = pawn.getYPosition();
		int color = pawn.getFigureColor();
		int index = ch.convertXYtoIndex(x, y);
		chessBoard.removeOldFigure(x,y);
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
			
		chessBoard.addFigure(index, new Queen("white", ch.convertCoordsToNotationString(x,y)));
		}
		if (color == 1) {
		chessBoard.addFigure(index, new Queen("black", ch.convertCoordsToNotationString(x,y)));
		}
	}

	private void handleCastling() {
		String logFilePath = "chess_board_log.txt";
		String lastLine = readLastLine(logFilePath);
		int type = lh.getFigureTypefromLog(lastLine);
		int xMovement = lh.getXMovementfromLog(lastLine);
		int Y = lh.getNewYfromLog(lastLine);
		if (type == 1) {
			if (xMovement == -2 && Y == 0) {
				// topleft
				selectedFigure = chessBoard.getFigureAt(0, 0);
				int targetIndex = ch.convertXYtoIndex(3, 0);
				chessBoard.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						ch.convertIndextoX(targetIndex),
						ch.convertIndextoY(targetIndex));
				chessBoard.removeOldFigure(0, 0);
			}
			if (xMovement == 2 && Y == 0) {
				// topright
				selectedFigure = chessBoard.getFigureAt(7, 0);
				int targetIndex = ch.convertXYtoIndex(5, 0);
				chessBoard.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						ch.convertIndextoX(targetIndex),
						ch.convertIndextoY(targetIndex));
				chessBoard.removeOldFigure(7, 0);
			}
			if (xMovement == -2 && Y == 7) {
				// bottomleft
				selectedFigure = chessBoard.getFigureAt(0, 7);
				int targetIndex = ch.convertXYtoIndex(3, 7);
				chessBoard.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						ch.convertIndextoX(targetIndex),
						ch.convertIndextoY(targetIndex));
				chessBoard.removeOldFigure(0, 7);
			}
			if (xMovement == 2 && Y == 7) {
				// bottomright
				selectedFigure = chessBoard.getFigureAt(7, 7);
				int targetIndex = ch.convertXYtoIndex(5, 7);
				chessBoard.logMove(selectedFigure, targetIndex);
				chessBoard.makeMove(selectedFigure,
						ch.convertIndextoX(targetIndex),
						ch.convertIndextoY(targetIndex));
				chessBoard.removeOldFigure(7, 7);
			}
		}

	}

	private String readLastLine(String filePath) {
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

	private void handleEnPassant() {
		String logFilePath = "chess_board_log.txt";
		String secondToLastLine = readSecondToLastLine(logFilePath);
		String lastLine = readLastLine(logFilePath);
		int type1 = lh.getFigureTypefromLog(secondToLastLine);
		int type2 = lh.getFigureTypefromLog(lastLine);
		int color1 = lh.getColorfromLog(secondToLastLine);
		int color2 = lh.getColorfromLog(lastLine);
		int yMovement1 = lh.getYMovementfromLog(secondToLastLine);
		int newX1 = lh.getNewXfromLog(secondToLastLine);
		int newX2 = lh.getNewXfromLog(lastLine);
		int newY1 = lh.getNewYfromLog(secondToLastLine);
		int newY2 = lh.getNewYfromLog(lastLine);
		System.out.println(lastLine);
		System.out.println(secondToLastLine);
		if (type1 == 3 && type2 == 3 && color1 == 1 && color2 == 0
				&& yMovement1 == 2 && newX1 == newX2 && newY2 == newY1 - 1) {
			chessBoard.removeOldFigure(newX1, newY1);
		}
		if (type1 == 3 && type2 == 3 && color2 == 1 && color1 == 0
				&& yMovement1 == -2 && newX1 == newX2 && newY2 == newY1 + 1) {
			chessBoard.removeOldFigure(newX1, newY1);
		}
	}

	private void deselectObject() {
		selectedFigure = null;
		remove(selectionIndicator);
	}

	private void runFirstClick() {
		if (chessBoard.getClickedFigure(mouseX, mouseY) != null) {
			highlightSelection();
			showLegalMoves();
			clicked = true;
		}
	}

	private void showLegalMoves() {
		movement = new Movement(chessBoard, currentTurn);
		for (int indexFirstPass = 0; indexFirstPass < 64; indexFirstPass++) {
			movement.isLegal(indexFirstPass, selectedFigure);
			if (movement.isIndexAllowed(indexFirstPass)) {
				CheckIfKingIsInDanger(indexFirstPass);
			}
			if (movement.isIndexAllowed(indexFirstPass)) {
				GRect validMoveIndicator = new GRect(20, 20);
				validMoveIndicator.setColor(Color.GREEN);
				add(validMoveIndicator,
						ch.convertIndextoOpticalX(indexFirstPass) + 11,
						ch.convertIndextoOpticalY(indexFirstPass) + 11);
			}
		}
	}

	private void CheckIfKingIsInDanger(int indexFirstPass) {
		// temporarily make move
		ChessBoard tempMove = chessBoard.deepCopy();
		int selectedIndex = ch.convertXYtoIndex(selectedFigure.getXPosition(),
				selectedFigure.getYPosition());
		Figure selectedFigureTemp = tempMove.getFigureAtIndex(selectedIndex);
		int xCache = selectedFigureTemp.getXPosition();
		int yCache = selectedFigureTemp.getYPosition();
		if (movement.isIndexAllowed(indexFirstPass)) {
			int newX = ch.convertIndextoX(indexFirstPass);
			int newY = ch.convertIndextoY(indexFirstPass);
			tempMove.makeMove(selectedFigureTemp, newX, newY);
			tempMove.removeOldFigure(xCache, yCache);
		}

		// find opponent figures in second pass
		ChessBoard enemyFigures = new ChessBoard(16);
		int enemyIndex = 0;
		for (int boardIndex = 0; boardIndex < tempMove.getLength(); boardIndex++) {
			Figure tempSecondPass = tempMove.getFigureAtIndex(boardIndex);
			if (tempSecondPass != null
					&& tempSecondPass.getFigureColor() != currentTurn % 2) {
				enemyFigures.addFigure(enemyIndex, tempSecondPass);
				enemyIndex += 1;
			}
		}

		// check every opponent figures' possible moves in third pass
		Movement kingCheck = new Movement(tempMove, currentTurn + 1);
		for (int i = 0; i < enemyIndex; i++) {
			Figure tempThirdPass = enemyFigures.getFigureAtIndex(i);

			for (int indexThirdPass = 0; indexThirdPass < 64; indexThirdPass++) {
				int kingIndex = 0;
				kingCheck.isLegal(indexThirdPass, tempThirdPass);
				// get king index of current player (figuretype = 1, figureColor
				// == currentTurn % 2)
				for (int boardIndex = 0; boardIndex < tempMove.getLength(); boardIndex++) {
					Figure potentialKing = tempMove
							.getFigureAtIndex(boardIndex);
					if (potentialKing != null
							&& potentialKing.getFigureType() == 1
							&& potentialKing.getFigureColor() == currentTurn % 2) {
						kingIndex = boardIndex;
					}
				}

				if (kingCheck.isIndexAllowed(kingIndex)) {
					movement.setIndexToFalse(indexFirstPass);
					GRect validMoveIndicator = new GRect(20, 20);
					validMoveIndicator.setColor(Color.RED);
					add(validMoveIndicator,
							ch.convertIndextoOpticalX(indexFirstPass) + 11,
							ch.convertIndextoOpticalY(indexFirstPass) + 11);
				}
			}
		}
	}

	private void highlightSelection() {
		selectedFigure = chessBoard.getClickedFigure(mouseX, mouseY);
		selectionIndicator = new GRect(42, 42);
		selectionIndicator.setColor(Color.BLUE);
		add(selectionIndicator, selectedFigure.getGraphicX(),
				selectedFigure.getGraphicY());
	}

	private void drawFigures() {
		for (int i = 0; i < chessBoard.getLength(); i++) {
			if (chessBoard.getFigureAtIndex(i) != null) {
				add(chessBoard.getFigureAtIndex(i).getSprite(), chessBoard
						.getFigureAtIndex(i).getGraphicX(), chessBoard
						.getFigureAtIndex(i).getGraphicY());
			}
		}
	}

	private void initializeBoard() {
		chessBoard.addFigure(0, new Rook("black", "a8"));
		chessBoard.addFigure(1, new Knight("black", "b8"));
		chessBoard.addFigure(2, new Bishop("black", "c8"));
		chessBoard.addFigure(3, new Queen("black", "d8"));
		chessBoard.addFigure(4, new King("black", "e8"));
		chessBoard.addFigure(5, new Bishop("black", "f8"));
		chessBoard.addFigure(6, new Knight("black", "g8"));
		chessBoard.addFigure(7, new Rook("black", "h8"));
		chessBoard.addFigure(8, new Pawn("black", "a7"));
		chessBoard.addFigure(9, new Pawn("black", "b7"));
		chessBoard.addFigure(10, new Pawn("black", "c7"));
		chessBoard.addFigure(11, new Pawn("black", "d7"));
		chessBoard.addFigure(12, new Pawn("black", "e7"));
		chessBoard.addFigure(13, new Pawn("black", "f7"));
		chessBoard.addFigure(14, new Pawn("black", "g7"));
		chessBoard.addFigure(15, new Pawn("black", "h7"));
		chessBoard.addFigure(48, new Pawn("white", "a2"));
		chessBoard.addFigure(49, new Pawn("white", "b2"));
		chessBoard.addFigure(50, new Pawn("white", "c2"));
		chessBoard.addFigure(51, new Pawn("white", "d2"));
		chessBoard.addFigure(52, new Pawn("white", "e2"));
		chessBoard.addFigure(53, new Pawn("white", "f2"));
		chessBoard.addFigure(54, new Pawn("white", "g2"));
		chessBoard.addFigure(55, new Pawn("white", "h2"));
		chessBoard.addFigure(56, new Rook("white", "a1"));
		chessBoard.addFigure(57, new Knight("white", "b1"));
		chessBoard.addFigure(58, new Bishop("white", "c1"));
		chessBoard.addFigure(59, new Queen("white", "d1"));
		chessBoard.addFigure(60, new King("white", "e1"));
		chessBoard.addFigure(61, new Bishop("white", "f1"));
		chessBoard.addFigure(62, new Knight("white", "g1"));
		chessBoard.addFigure(63, new Rook("white", "h1"));
	}

	private void drawBackground() {
		GRect[][] background_board = generatedBoardTiles();
		for (int x = 0; x < background_board.length; x++) {
			for (int y = 0; y < background_board.length; y++) {
				add(background_board[x][y], x * 42, y * 42);
			}
		}
	}

	private GRect[][] generatedBoardTiles() {
		GRect[][] tileArray = new GRect[8][8];
		for (int x = 0; x < tileArray.length; x++) {
			for (int y = 0; y < tileArray.length; y++) {
				tileArray[x][y] = new GRect(42, 42);
				if (x % 2 == y % 2) {
					tileArray[x][y].setColor(Color.GRAY);
				} else {
					tileArray[x][y].setColor(Color.WHITE);
				}
				tileArray[x][y].setFilled(true);
			}
		}
		return tileArray;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}
}
