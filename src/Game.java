public class Game {
	
	private final int BOARD_LENGTH = 8;
	
	private int currentHalfMove = 2;
	private int currentFullMove = currentHalfMove / 2;
	//todo: implement halfmove clock, add function to update full moves whenever halfmoves change
	private int halfMoveClock = 0;
	
	private ChessBoard chessBoard;
	private Figure selectedFigure;
	private MovementArray legalMoveArray;
	
	FenSetup fenSetup = new FenSetup();
	MovementHandler movementHandler = new MovementHandler();
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	GameLog gameLog;

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
	
	public int getCurrentHalfMove() {
		return currentHalfMove;
	}
	
	public int getCurrentFullMove() {
		return currentFullMove;
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
		legalMoveArray = movementHandler.legalMoveArray(chessBoard, selectedFigure, currentHalfMove, gameLog);
	}
	
	public void makeMove(int targetIndex) {
		//maybe circumvent the cache stuff with log once thats done
		//todo: actual method and logging for captures
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
		if (legalMoveArray.moveAtIndexAllowed(targetIndex)) {
			gameLog.logMove(selectedFigure, targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex));
			chessBoard.removeFigure(xCache, yCache);
			currentHalfMove += 1;
		}
		selectedFigure.updateEnPassantEligibility(gameLog);
		handleSpecialCasesAfterMove();
	}
	
	public void handleSpecialCasesAfterMove() {
		movementHandler.removePawnAfterEnPassant(chessBoard, gameLog);
		movementHandler.moveTowerAfterCastling(chessBoard, gameLog);
		movementHandler.handlePawnAtBorder(chessBoard, gameLog);
	}

	public void initializeGame() {
		//fenSetup.setFenNotation("8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1");
		chessBoard = fenSetup.generateChessBoard();
		currentHalfMove = fenSetup.getCurrentHalfMove();
		halfMoveClock = fenSetup.getHalfMoveClock();
		gameLog = fenSetup.getGameLog();
	}
	
	
}
