import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class ChessGame extends GraphicsProgram {

	private final int TILE_SIZE = 42;
	private final int BOARD_LENGTH = 8;
	private final int MOVE_INDICATOR_SIZE = TILE_SIZE / 2 - 1;
	private final int CANVAS_SIZE = 500;

	CoordinateHelper coordinateHelper = new CoordinateHelper();
	Game game = new Game();

	GRect selectionIndicator;
	boolean clicked = false;
	int mouseX;
	int mouseY;

	// code that does shit
	
	public void run() {
		game.initializeGame();
		drawGame();
		addMouseListeners();
	}

	private void drawGame() {
		setSize(CANVAS_SIZE, CANVAS_SIZE);
		drawBackground();
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

	private void runFirstClick() {
		int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX,mouseY);
		if (game.getChessBoard().getFigureAtIndex(targetIndex) != null) {
			game.setSelectedFigure(game.getChessBoard().getFigureAtIndex(targetIndex));
			highlightSelection();
			game.updateLegalMoveArray();
			showLegalMoves();
			showCheckMoves();
			clicked = true;
		}
	}

	private void runSecondClick() {
		int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX, mouseY);
		game.makeMove(targetIndex);
		setCanvasForNextTurn();
	}

	private void setCanvasForNextTurn() {
		removeAll();
		drawBackground();
		drawFigures();
		deselectObject();
		clicked = false;
	}

	private void deselectObject() {
		game.setSelectedFigureToNull();
		remove(selectionIndicator);
	}

	// rather messy graphical code, clean up at some point
	

	private void showCheckMoves() {
		MovementArray checkMoveArray = game.getMovementHandler().kingInCheckArray(game.getChessBoard(),game.getSelectedFigure(), game.getCurrentTurn());
		for (int kingInCheckIndex = 0; kingInCheckIndex < game.getChessBoard().getLength(); kingInCheckIndex++) {
			if (!checkMoveArray.moveAtIndexAllowed(kingInCheckIndex)) {
				GRect checkMoveIndicator = new GRect(MOVE_INDICATOR_SIZE,MOVE_INDICATOR_SIZE);
				checkMoveIndicator.setColor(Color.RED);
				add(checkMoveIndicator,coordinateHelper.convertIndextoOpticalX(kingInCheckIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(kingInCheckIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2));
			}
		}
	}

	private void showLegalMoves() {
		for (int legalMoveIndex = 0; legalMoveIndex < game.getChessBoard().getLength(); legalMoveIndex++) {
			if (game.getLegalMoveArray().moveAtIndexAllowed(legalMoveIndex)) {
				GRect validMoveIndicator = new GRect(MOVE_INDICATOR_SIZE,MOVE_INDICATOR_SIZE);
				validMoveIndicator.setColor(Color.GREEN);
				add(validMoveIndicator,coordinateHelper.convertIndextoOpticalX(legalMoveIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(legalMoveIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2));
			}
		}
	}

	private void highlightSelection() {
		selectionIndicator = new GRect(TILE_SIZE, TILE_SIZE);
		selectionIndicator.setColor(Color.BLUE);
		add(selectionIndicator, game.getSelectedFigure().getOpticalX(), game
				.getSelectedFigure().getOpticalY());
	}

	private void drawFigures() {
		for (int i = 0; i < game.getChessBoard().getLength(); i++) {
			if (game.getChessBoard().getFigureAtIndex(i) != null) {
				add(game.getChessBoard().getFigureAtIndex(i).getSprite(), game.getChessBoard().getFigureAtIndex(i).getOpticalX(),game.getChessBoard().getFigureAtIndex(i).getOpticalY());
			}
		}
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

}
