import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import acm.graphics.GImage;
import acm.graphics.GLine;

public class ChessGame extends GraphicsProgram {

	ChessBoard chessBoard = new ChessBoard(64);
	Figure selectedFigure;
	GRect selectionIndicator;
	boolean clicked = false;
	int mouseX;
	int mouseY;

	public void run() {
		drawGame();
		addMouseListeners();
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
		if (chessBoard.legalMove(selectedFigure, mouseX, mouseY)) {
			int xCache = selectedFigure.getXPosition();
			int yCache = selectedFigure.getYPosition();
			chessBoard.makeMove(selectedFigure, mouseX, mouseY);
			chessBoard.removeOldFigure(xCache, yCache);
		}
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
			// showLegalMoves();
			clicked = true;
		}
	}

	private void highlightSelection() {
		selectedFigure = chessBoard.getClickedFigure(mouseX, mouseY);
		println(selectedFigure.toString());
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
				print(chessBoard.getFigureAtIndex(i).toShortString());
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
}
