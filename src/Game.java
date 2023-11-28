public class Game {
	
	private final int BOARD_LENGTH = 8;
	
	private int currentTurn = 0;
	private ChessBoard chessBoard = new ChessBoard(BOARD_LENGTH * BOARD_LENGTH);
	private Figure selectedFigure;
	private MovementArray legalMoveArray;
	
	MovementHandler movementHandler = new MovementHandler();
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	LogHelper logHelper = new LogHelper();

	public Game() {

	}
	
	public MovementHandler getMovementHandler() {
		return movementHandler;
	}
	
	public MovementArray getLegalMoveArray() {
		return legalMoveArray;
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	
	public void setSelectedFigure(Figure selectedFigure) {
		this.selectedFigure = selectedFigure;
	}
	
	public void setSelectedFigureToNull() {
		selectedFigure = null;
	}
	
	public Figure getSelectedFigure() {
		return selectedFigure;
	}
	
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	public void updateLegalMoveArray() {
		legalMoveArray = movementHandler.legalMoveArray(chessBoard, selectedFigure, currentTurn);
	}
	
	public void makeMove(int targetIndex) {
		//maybe circumvent the cache stuff with log once thats done
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
		if (legalMoveArray.moveAtIndexAllowed(targetIndex)) {
			logHelper.logMove(selectedFigure, targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex));
			chessBoard.removeFigure(xCache, yCache);
			currentTurn += 1;
		}
	}
	
	public void handleSpecialCasesAfterMove() {
		movementHandler.handleEnPassant(chessBoard);
		movementHandler.handleCastling(chessBoard);
		movementHandler.handlePawnAtBorder(chessBoard);
	}

	public void initializeGame() {
		// try to get index from XY coords, get rid of strings, maybe find way to read starting grid from a file or smth
		logHelper.clearLogFile();
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
	
	
}
