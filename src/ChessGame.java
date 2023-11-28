import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class ChessGame extends GraphicsProgram {
	
	private final int TILE_SIZE = 42;
	private final int MOVE_INDICATOR_SIZE = TILE_SIZE/2-1;
	private final int BOARD_LENGTH = 8;
	private final int CANVAS_SIZE = 500;
	private final int FIGURES_PER_PLAYER = 16;

	MovementArray potentialMoves;
	MovementHandler movementHandler = new MovementHandler();
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	LogHelper logHelper = new LogHelper();
	
	ChessBoard chessBoard = new ChessBoard(BOARD_LENGTH * BOARD_LENGTH);
	Figure selectedFigure;
	int currentTurn = 0;
	
	GRect selectionIndicator;
	boolean clicked = false;
	int mouseX;
	int mouseY;


	public void run() {
		logHelper.clearLogFile();
		drawGame();
		addMouseListeners();
	}

	private void drawGame() {
		setSize(CANVAS_SIZE, CANVAS_SIZE);
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
		int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX, mouseY);
		//if (movementHandler.moveIsLegal() {;
		//movementHandler.makeMove()
		//currentTurn += 1;
		//}
		if (potentialMoves.moveToIndexAllowed(targetIndex)) {
			logHelper.logMove(selectedFigure, targetIndex);
			chessBoard.makeMove(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex));
			chessBoard.removeFigure(xCache, yCache);
			currentTurn += 1;
			// println("current turn ingame: " + currentTurn);
		}
		setCanvasForNextTurn();
	}

	private void setCanvasForNextTurn() {
		removeAll();
		movementHandler.handleEnPassant(chessBoard);
		movementHandler.handleCastling(chessBoard);
		movementHandler.handlePawnAtBorder(chessBoard);
		drawBackground();
		drawFigures();
		deselectObject();
		clicked = false;
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
		potentialMoves = new MovementArray(chessBoard, currentTurn);
		for (int indexFirstPass = 0; indexFirstPass < chessBoard.getLength(); indexFirstPass++) {
			potentialMoves.isLegal(indexFirstPass, selectedFigure);
			if (potentialMoves.moveToIndexAllowed(indexFirstPass)) {
				CheckIfKingIsInDanger(indexFirstPass);
			}
			if (potentialMoves.moveToIndexAllowed(indexFirstPass)) {
				GRect validMoveIndicator = new GRect(MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
				validMoveIndicator.setColor(Color.GREEN);
				add(validMoveIndicator,
						coordinateHelper.convertIndextoOpticalX(indexFirstPass) + ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),
						coordinateHelper.convertIndextoOpticalY(indexFirstPass) + ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2));
			}
		}
	}

	private void CheckIfKingIsInDanger(int indexFirstPass) {
		//move to MoveSimulator
		// temporarily make move
		ChessBoard tempMove = chessBoard.deepCopy();
		int selectedIndex = coordinateHelper.convertXYtoIndex(selectedFigure.getXPosition(),
				selectedFigure.getYPosition());
		Figure selectedFigureTemp = tempMove.getFigureAtIndex(selectedIndex);
		int xCache = selectedFigureTemp.getXPosition();
		int yCache = selectedFigureTemp.getYPosition();
		if (potentialMoves.moveToIndexAllowed(indexFirstPass)) {
			int newX = coordinateHelper.convertIndextoX(indexFirstPass);
			int newY = coordinateHelper.convertIndextoY(indexFirstPass);
			tempMove.makeMove(selectedFigureTemp, newX, newY);
			tempMove.removeFigure(xCache, yCache);
		}

		// find opponent figures in second pass
		ChessBoard enemyFigures = new ChessBoard(FIGURES_PER_PLAYER);
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
		MovementArray kingCheck = new MovementArray(tempMove, currentTurn + 1);
		for (int i = 0; i < enemyIndex; i++) {
			Figure tempThirdPass = enemyFigures.getFigureAtIndex(i);

			for (int indexThirdPass = 0; indexThirdPass < chessBoard.getLength(); indexThirdPass++) {
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

				if (kingCheck.moveToIndexAllowed(kingIndex)) {
					potentialMoves.setIndexToFalse(indexFirstPass);
					GRect validMoveIndicator = new GRect(MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
					validMoveIndicator.setColor(Color.RED);
					add(validMoveIndicator,
							coordinateHelper.convertIndextoOpticalX(indexFirstPass) + ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),
							coordinateHelper.convertIndextoOpticalY(indexFirstPass) + ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2));
				}
			}
		}
	}

	private void highlightSelection() {
		selectedFigure = chessBoard.getClickedFigure(mouseX, mouseY);
		selectionIndicator = new GRect(TILE_SIZE, TILE_SIZE);
		selectionIndicator.setColor(Color.BLUE);
		add(selectionIndicator, selectedFigure.getOpticalX(),
				selectedFigure.getOpticalY());
	}

	private void drawFigures() {
		for (int i = 0; i < chessBoard.getLength(); i++) {
			if (chessBoard.getFigureAtIndex(i) != null) {
				add(chessBoard.getFigureAtIndex(i).getSprite(), chessBoard
						.getFigureAtIndex(i).getOpticalX(), chessBoard
						.getFigureAtIndex(i).getOpticalY());
			}
		}
	}

	private void initializeBoard() {
		// try to get index from XY coords, get rid of strings, maybe find way to read starting grid from a file or smth
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
				add(background_board[x][y], x * TILE_SIZE, y * TILE_SIZE);
			}
		}
	}

	private GRect[][] generatedBoardTiles() {
		GRect[][] tileArray = new GRect[BOARD_LENGTH][BOARD_LENGTH];
		for (int x = 0; x < tileArray.length; x++) {
			for (int y = 0; y < tileArray.length; y++) {
				tileArray[x][y] = new GRect(TILE_SIZE, TILE_SIZE);
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
