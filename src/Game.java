import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import acm.graphics.GImage;
import acm.graphics.GLine;

public class Game extends GraphicsProgram {
	BlackPawn[] blackPawns;
	WhitePawn[] whitePawns;
	Bishop[] blackBishops;
	Bishop[] whiteBishops;
	King blackKing;
	King whiteKing;
	Knight[] blackKnights;
	Knight[] whiteKnights;
	Queen blackQueen;
	Queen whiteQueen;
	Rook[] blackRooks;
	Rook[] whiteRooks;
	GRect[][] tiles;
	int pressX;
	int pressY;
	int mouseX;
	int mouseY;
	double cacheX;
	double cacheY;
	GObject selectedObject;
	GObject collisionObject;
	GRect selectionIndicator;
	int turn = 0;
	int turnColor = 0;
	int collisionColor = 0;

	public void run() {
		drawGame();
		addMouseListeners();
	}

	public void mousePressed(MouseEvent e) {
		pressX = e.getX();
		pressY = e.getY();
		if (selectedObject == null) {
			runMouseChecks();
		} else {
			sendPositionToClass();
			getCollisionObject();
			handleSelection();
		}
	}

	private void getCollisionObject() {
		collisionColor = 2;
		collisionObject = getElementAt(convertXInput(pressX),
				convertYInput(pressY));
		if (collisionObject != null) {
			String s = selectedObject.toString();
			if (selectedObject != null && s.indexOf("GRect") == -1) {
				String colorcheck = collisionObject.toString();
				if (colorcheck.indexOf("black") != -1) {
					collisionColor = 1;
				} else {
					if (colorcheck.indexOf("white") != -1) {
						collisionColor = 0;
					} else {
						collisionColor = 2;
					}
				}
			}
		}
	}

	private void updateObjectPosition() {
		// hacked bullshit this is to get the x and y positions before a move
		// back into the class gobject is such a bullshit thing
		if (selectedObject != null) {
			selectedObject.getColor();
		}
	}

	private void handleSelection() {
		String colorcheck = selectedObject.toString();
		if (colorcheck.indexOf("black") != -1) {
			turnColor = 1;
		} else {
			turnColor = 0;
		}
		if (legalMove()) {
			moveFigure();
			turn = turn + 1;
		} else {
			deselectObject();
		}
	}

	private boolean legalMove() {
		boolean legal = false;

		if (collisionObject != null) {
			String thisCodeIsSuchTrashPartTwo = selectedObject.toString();
			if (thisCodeIsSuchTrashPartTwo.indexOf("pawn") != -1) {
				legal = checkPawnMovement1();
			}
		}

		if (selectedObject != null) {
			String thisCodeIsSuchTrash = selectedObject.toString();
			println(thisCodeIsSuchTrash);
			if (thisCodeIsSuchTrash.indexOf("Legal") != -1) {
				legal = true;
			}
		}

		if (collisionObject != null && legal == true) {
			String thisCodeIsSuchTrashPartTwo = selectedObject.toString();
			if (thisCodeIsSuchTrashPartTwo.indexOf("pawn") != -1) {
				legal = checkPawnMovement2();
			}
		}

		if (turn % 2 != turnColor) {
			legal = false;
		}
		println(turnColor);
		if (turnColor == collisionColor) {
			legal = false;
		}
		if (pressX < 42 || pressX > 378 || pressY < 42 || pressY > 378) {
			legal = false;
		}
		println(collisionColor);
		
		
		return legal;
	}


	private boolean checkPawnMovement2() {
		boolean bool = true;
		int xBefore = (int) cacheX / 42;
		int yBefore = (int) cacheY / 42;
		int xAfter = (int) pressX / 42;
		int yAfter = (int) pressY / 42;
		int xDiff = xAfter - xBefore;
		int yDiff = yAfter - yBefore;
		String thisCodeIsSuchTrashPartTwo = selectedObject.toString();
		if (thisCodeIsSuchTrashPartTwo.indexOf("white") != -1
				&& figureCollision()) {
			if (xDiff < 0) {
				xDiff = -xDiff;
			}
			if (yDiff == -1 && xDiff == 0) {
				bool = false;
			}
		}
		if (thisCodeIsSuchTrashPartTwo.indexOf("black") != -1
				&& figureCollision()) {
			if (xDiff < 0) {
				xDiff = -xDiff;
			}
			if (yDiff == 1 && xDiff == 0) {
				bool = false;
			}
		}
		return bool;
	}

	private boolean checkPawnMovement1() {
		boolean bool = false;
		int xBefore = (int) cacheX / 42;
		int yBefore = (int) cacheY / 42;
		int xAfter = (int) pressX / 42;
		int yAfter = (int) pressY / 42;
		int xDiff = xAfter - xBefore;
		int yDiff = yAfter - yBefore;

		String thisCodeIsSuchTrashPartTwo = selectedObject.toString();
		if (thisCodeIsSuchTrashPartTwo.indexOf("white") != -1
				&& figureCollision()) {
			if (xDiff < 0) {
				xDiff = -xDiff;
			}
			if (yDiff == -1 && xDiff == 1) {
				bool = true;
			}
		}
		if (thisCodeIsSuchTrashPartTwo.indexOf("black") != -1
				&& figureCollision()) {
			if (xDiff < 0) {
				xDiff = -xDiff;
			}
			if (yDiff == 1 && xDiff == 1) {
				bool = true;
			}
		}

		return bool;
	}

	private void sendPositionToClass() {
		// hacked bullshit this is to get the x and y positions before a move
		// back into the class gobject is such a bullshit thing

		if (selectedObject != null) {
			cacheX = selectedObject.getX();
			cacheY = selectedObject.getY();
			selectedObject.setLocation(convertXInput(pressX),
					convertYInput(pressY));
			selectedObject.getHeight();
			selectedObject.setLocation(cacheX, cacheY);
		}

	}

	private void highlightSelection() {
		selectionIndicator = new GRect(42, 42);
		selectionIndicator.setColor(Color.BLUE);
		add(selectionIndicator, (double) selectedObject.getX(),
				(double) selectedObject.getY());
	}

	public int convertXInput(int mouseX) {
		int xInput = mouseX / 42 * 42;
		return xInput;
	}

	public int convertYInput(int mouseY) {
		int yInput = mouseY / 42 * 42;
		return yInput;
	}

	private void runMouseChecks() {
		selectedObject = getElementAt(pressX, pressY);
		highlightSelection();
		if (figureSelected() == false) {
			deselectObject();
		}
	}

	private void deselectObject() {
		selectedObject = null;
		remove(selectionIndicator);
	}

	private boolean figureSelected() {
		boolean fs = false;
		String s = selectedObject.toString();
		if (selectedObject != null && s.indexOf("GRect") == -1) {
			updateObjectPosition();
			fs = true;
		}
		return fs;
	}

	private boolean figureCollision() {
		boolean fs = false;
		String s = collisionObject.toString();
		if (collisionObject != null && s.indexOf("GRect") == -1) {
			updateObjectPosition();
			fs = true;
		}
		return fs;
	}

	private void moveFigure() {
		if (collisionObject != null && collisionObject != selectedObject) {
			String s = collisionObject.toString();
			if (s.indexOf("GRect") == -1) {
				remove(collisionObject);
			}
		}
		selectedObject
				.setLocation(convertXInput(pressX), convertYInput(pressY));
		println(pressX + " " + pressY);
		deselectObject();
	}

	private void drawGame() {
		setSize(500, 500);
		drawBoard();
		initializeFigures();
	}

	private void drawBoard() {
		tiles = new GRect[9][9];
		for (int x = 1; x < 9; x++) {
			for (int y = 1; y < 9; y++) {
				tiles[x][y] = new GRect(42, 42);
				if (x % 2 == y % 2) {
					tiles[x][y].setColor(Color.GRAY);
				} else {
					tiles[x][y].setColor(Color.WHITE);
				}
				tiles[x][y].setFilled(true);
				add(tiles[x][y], x * 42, y * 42);
			}
		}
	}

	private void initializeFigures() {

		blackPawns = new BlackPawn[8];
		blackPawns[0] = new BlackPawn(1, 2);
		blackPawns[1] = new BlackPawn(2, 2);
		blackPawns[2] = new BlackPawn(3, 2);
		blackPawns[3] = new BlackPawn(4, 2);
		blackPawns[4] = new BlackPawn(5, 2);
		blackPawns[5] = new BlackPawn(6, 2);
		blackPawns[6] = new BlackPawn(7, 2);
		blackPawns[7] = new BlackPawn(8, 2);
		whitePawns = new WhitePawn[8];
		whitePawns[0] = new WhitePawn(1, 7);
		whitePawns[1] = new WhitePawn(2, 7);
		whitePawns[2] = new WhitePawn(3, 7);
		whitePawns[3] = new WhitePawn(4, 7);
		whitePawns[4] = new WhitePawn(5, 7);
		whitePawns[5] = new WhitePawn(6, 7);
		whitePawns[6] = new WhitePawn(7, 7);
		whitePawns[7] = new WhitePawn(8, 7);

		for (int j = 0; j < 8; j++) {
			add(blackPawns[j], blackPawns[j].getXBoard(),
					blackPawns[j].getYBoard());
			;
			add(whitePawns[j], whitePawns[j].getXBoard(),
					whitePawns[j].getYBoard());
		}

		blackBishops = new Bishop[2];
		blackBishops[0] = new Bishop(3, 1, "black");
		blackBishops[1] = new Bishop(6, 1, "black");

		whiteBishops = new Bishop[2];
		whiteBishops[0] = new Bishop(3, 8, "white");
		whiteBishops[1] = new Bishop(6, 8, "white");

		blackRooks = new Rook[2];
		blackRooks[0] = new Rook(1, 1, "black");
		blackRooks[1] = new Rook(8, 1, "black");

		whiteRooks = new Rook[2];
		whiteRooks[0] = new Rook(1, 8, "white");
		whiteRooks[1] = new Rook(8, 8, "white");

		blackKnights = new Knight[2];
		blackKnights[0] = new Knight(2, 1, "black");
		blackKnights[1] = new Knight(7, 1, "black");

		whiteKnights = new Knight[2];
		whiteKnights[0] = new Knight(2, 8, "white");
		whiteKnights[1] = new Knight(7, 8, "white");

		for (int k = 0; k < 2; k++) {
			add(blackBishops[k], blackBishops[k].getXBoard(),
					blackBishops[k].getYBoard());
			add(whiteBishops[k], whiteBishops[k].getXBoard(),
					whiteBishops[k].getYBoard());
			add(blackRooks[k], blackRooks[k].getXBoard(),
					blackRooks[k].getYBoard());
			add(whiteRooks[k], whiteRooks[k].getXBoard(),
					whiteRooks[k].getYBoard());
			add(blackKnights[k], blackKnights[k].getXBoard(),
					blackKnights[k].getYBoard());
			add(whiteKnights[k], whiteKnights[k].getXBoard(),
					whiteKnights[k].getYBoard());
		}

		blackQueen = new Queen(5, 1, "black");
		whiteQueen = new Queen(5, 8, "white");
		blackKing = new King(4, 1, "black");
		whiteKing = new King(4, 8, "white");
		add(blackQueen, blackQueen.getXBoard(), blackQueen.getYBoard());
		add(whiteQueen, whiteQueen.getXBoard(), whiteQueen.getYBoard());
		add(blackKing, blackKing.getXBoard(), blackKing.getYBoard());
		add(whiteKing, whiteKing.getXBoard(), whiteKing.getYBoard());

	}
}
