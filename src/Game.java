public class Game {
	
	private final int BOARD_LENGTH = 8;
	
	private int currentHalfMove = 2;
	private int currentFullMove = currentHalfMove / 2;
	//todo: make game end when half move clock reaches 50
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

	public void updateHalfMoveClock() {
		if(gameLog.getMovementTypeFromEntry(gameLog.getPriorEntry(1)) != 1
		&& gameLog.getFigureTypefromEntry(gameLog.getPriorEntry(1)) != 3) {
			halfMoveClock += 1;
		} else {
			halfMoveClock = 0;
		}
	}
	
	public Figure getSelectedFigure() {
		return selectedFigure;
	}
	
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	private int clearLogAfter = -1;
	
	public void updateLegalMoveArray() {
		legalMoveArray = movementHandler.legalMoveArray(chessBoard, selectedFigure, currentHalfMove, gameLog);
	}
	
	public void makeMove(int targetIndex) {
		//maybe circumvent the cache stuff with log once thats done
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
		if (legalMoveArray.moveAtIndexAllowed(targetIndex)) {
			if(clearLogAfter != -1) {
				for (int i = gameLog.getLength()-1; i>clearLogAfter; i--) {
					gameLog.removeEntry(i);
				}
				clearLogAfter = -1;
			}
			gameLog.logMove(selectedFigure, targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex), gameLog);
			chessBoard.removeFigure(xCache, yCache);
			updateHalfMoveClock();
			currentHalfMove += 1;
		}
		selectedFigure.updateEnPassantEligibility(gameLog);
		handleSpecialCasesAfterMove(gameLog);
	}

	public void makeMoveWithoutChecks(int targetIndex, GameLog inputLog) {
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
			inputLog.logMove(selectedFigure, targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex), inputLog);
			chessBoard.removeFigure(xCache, yCache);
			currentHalfMove += 1;
		selectedFigure.updateEnPassantEligibility(inputLog);
		handleSpecialCasesAfterMove(inputLog);
	}
	
	public void handleSpecialCasesAfterMove(GameLog gameLog) {
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

	public void revertToMove(int moveIndex) {
		GameLog revertLog = new GameLog();
		chessBoard.clearBoard();
		chessBoard = fenSetup.generateChessBoard();
		currentHalfMove = 2;
		gameLog.disableLogging();

		for (int i = 0; i<=moveIndex; i++) {
			reconstructMoveAtIndex(i, revertLog);
		}
		revertLog.clearLog();
		gameLog.enableLogging();
		clearLogAfter = moveIndex;
	}

	private void reconstructMoveAtIndex(int i, GameLog revertLog) {
		String entry = gameLog.getEntryAt(i);
		int startIndex = coordinateHelper.convertXYtoIndex(gameLog.getOldXfromEntry(entry), gameLog.getOldYfromEntry(entry));
		int targetIndex = coordinateHelper.convertXYtoIndex(gameLog.getNewXfromEntry(entry), gameLog.getNewYfromEntry(entry));
		selectedFigure = chessBoard.getFigureAtIndex(startIndex);
		//todo: figure out why this won't work if you run it with validity checks... probably something to do with player turns
		makeMoveWithoutChecks(targetIndex, revertLog);
		setSelectedFigureToNull();
		//todo: highlight square last piece moved from
	}


}
