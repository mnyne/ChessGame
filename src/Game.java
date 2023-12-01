import java.util.ArrayList;

public class Game {
	
	private final int BOARD_LENGTH = 8;
	
	private int currentTurn = 0;
	private ChessBoard chessBoard = new ChessBoard(BOARD_LENGTH * BOARD_LENGTH);
	private Figure selectedFigure;
	private MovementArray legalMoveArray;
	
	MovementHandler movementHandler = new MovementHandler();
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	GameLog gameLog = new GameLog();

	public Game() {

	}
	
	public GameLog getGameLog() {
		return gameLog;
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
			gameLog.logMove(selectedFigure, targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex));
			chessBoard.removeFigure(xCache, yCache);
			currentTurn += 1;
		}
		chessBoard.updateMovedStatusForFigures(gameLog);
	}
	
	public void handleSpecialCasesAfterMove() {
		movementHandler.removePawnAfterEnPassant(chessBoard, gameLog);
		movementHandler.moveTowerAfterCastling(chessBoard, gameLog);
		movementHandler.handlePawnAtBorder(chessBoard, gameLog);
	}

	public void initializeGame() {
		// get index from XY coords, get rid of strings, make starting grid more flexible
		chessBoard.addFigure(0, new Rook(1, 0));
		chessBoard.addFigure(1, new Knight(1,1));
		chessBoard.addFigure(2, new Bishop(1,2));
		chessBoard.addFigure(3, new Queen(1,3));
		chessBoard.addFigure(4, new King(1,4));
		chessBoard.addFigure(5, new Bishop(1,5));
		chessBoard.addFigure(6, new Knight(1,6));
		chessBoard.addFigure(7, new Rook(1,7));
		chessBoard.addFigure(8, new Pawn(1,8));
		chessBoard.addFigure(9, new Pawn(1,9));
		chessBoard.addFigure(10, new Pawn(1,10));
		chessBoard.addFigure(11, new Pawn(1,11));
		chessBoard.addFigure(12, new Pawn(1,12));
		chessBoard.addFigure(13, new Pawn(1,13));
		chessBoard.addFigure(14, new Pawn(1,14));
		chessBoard.addFigure(15, new Pawn(1,15));
		chessBoard.addFigure(48, new Pawn(0,48));
		chessBoard.addFigure(49, new Pawn(0, 49));
		chessBoard.addFigure(50, new Pawn(0, 50));
		chessBoard.addFigure(51, new Pawn(0, 51));
		chessBoard.addFigure(52, new Pawn(0, 52));
		chessBoard.addFigure(53, new Pawn(0, 53));
		chessBoard.addFigure(54, new Pawn(0, 54));
		chessBoard.addFigure(55, new Pawn(0, 55));
		chessBoard.addFigure(56, new Rook(0, 56));
		chessBoard.addFigure(57, new Knight(0, 57));
		chessBoard.addFigure(58, new Bishop(0, 58));
		chessBoard.addFigure(59, new Queen(0, 59));
		chessBoard.addFigure(60, new King(0, 60));
		chessBoard.addFigure(61, new Bishop(0, 61));
		chessBoard.addFigure(62, new Knight(0, 62));
		chessBoard.addFigure(63, new Rook(0, 63));
	}
	
	
}
